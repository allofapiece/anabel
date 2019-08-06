package com.pinwheel.anabel.service.module.asset;

import com.pinwheel.anabel.service.module.asset.entity.Asset;
import com.pinwheel.anabel.util.DomUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.beans.Introspector;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * XML asset populator. Populates asset storage by xml file.
 */
@Service
@RequiredArgsConstructor
@Setter
@Getter
public class XmlAssetPopulator implements AssetPopulator {
    /**
     * Injection of {@link DocumentBuilder} bean.
     */
    private final DocumentBuilder documentBuilder;

    /**
     * Injection of {@link XPath} bean.
     */
    private final XPath xPath;

    /**
     * Path to xml file that contains mapping of assets.
     */
    private String assetsFileUrl = "xml/assets.xml";

    /**
     * {@inheritDoc}
     */
    @Override
    public void populate(AssetStorage storage) throws Exception {
        NodeList assetsNodeList = getAssetsNodeList();

        DomUtils.getElementsStream(assetsNodeList)
                .map(this::node2Assets)
                .forEach(assets -> assets.forEach(storage::add));
    }

    /**
     * Converts node from xml file to list of assets. This list will be contains the same assets but with different
     * template names.
     *
     * @param node target node.
     * @return list of assets.
     */
    public List<Asset> node2Assets(Node node) {
        try {
            Asset asset = assetFromClassNameAndArgs(
                    ((Element) node).getAttribute("class"),
                    DomUtils.getChildElementsMap(node)
            );

            return getTemplatesStream(node)
                    .map(t -> this.cloneWithTemplate(asset, t))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BeanCreationException("Can not populate asset store", e);
        }
    }

    /**
     * Creates asset entity by class name of asset and passed arguments.
     *
     * @param className class name of result asset.
     * @param args      arguments that associated with fields of asset.
     * @return result asset.
     * @throws Exception
     */
    protected Asset assetFromClassNameAndArgs(String className, Map<String, Object> args) throws Exception {
        Class clazz = Class.forName(className);
        Object object = clazz.getConstructor().newInstance();

        if (!(object instanceof Asset)) {
            throw new BeanCreationException("Asset must by instance of " + Asset.class.getPackageName());
        }

        Asset asset = (Asset) object;

        Arrays.stream(Introspector.getBeanInfo(clazz).getPropertyDescriptors())
                .filter(descriptor -> args.containsKey(descriptor.getName()))
                .forEach(descriptor -> {
                    try {
                        descriptor.getWriteMethod().invoke(asset, args.get(descriptor.getName()));
                    } catch (ReflectiveOperationException e) {
                        throw new BeanCreationException("An error occurred while setting the properties");
                    }
                });

        return asset;
    }

    /**
     * Retrieves stream of template names associated with passed node.
     *
     * @param node target node.
     * @return stream of template names.
     */
    protected Stream<String> getTemplatesStream(Node node) {
        Optional<Node> opt = DomUtils.getFirstChildElementByTagName(node, "templates");

        if (opt.isEmpty()) {
            throw new BeanCreationException("Asset must have `templates` element");
        }

        return DomUtils.getChildElementsStream(opt.get())
                .map(Node::getTextContent);
    }

    /**
     * Makes clone of passed asset and sets to it new passed template name.
     *
     * @param asset    target asset.
     * @param template new template name.
     * @return result asset.
     */
    protected Asset cloneWithTemplate(Asset asset, String template) {
        try {
            Asset newAsset = (Asset) asset.clone();
            newAsset.setTemplate(template);

            return newAsset;
        } catch (CloneNotSupportedException e) {
            throw new BeanCreationException("Can not populate asset store", e);
        }
    }

    /**
     * Reads xml file and retrieves node list.
     *
     * @return node list from xml file.
     * @throws IOException
     * @throws SAXException
     * @throws XPathExpressionException
     */
    protected NodeList getAssetsNodeList() throws IOException, SAXException, XPathExpressionException {
        InputStream resource = new ClassPathResource(assetsFileUrl).getInputStream();
        Document document = documentBuilder.parse(resource);

        return (NodeList) xPath.evaluate("//root/assets/asset", document, XPathConstants.NODESET);
    }
}
