package com.pinwheel.anabel.service.module.asset;

import com.pinwheel.anabel.external.category.Unit;
import com.pinwheel.anabel.service.module.asset.entity.Asset;
import com.pinwheel.anabel.service.module.asset.entity.Css;
import com.pinwheel.anabel.service.module.asset.entity.Js;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@Category(Unit.class)
class XmlAssetPopulatorUnitTest {
    private static final String XML_FILE_URL = "xml/assets.xml";
    private static DocumentBuilder documentBuilder;
    private static Document document;
    private static XPath xPath;
    private XmlAssetPopulator xmlAssetPopulator;

    @BeforeAll
    static void init() throws IOException, SAXException, XPathExpressionException, ParserConfigurationException {
        InputStream resource = new ClassPathResource(XML_FILE_URL).getInputStream();
        documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        document = documentBuilder.parse(resource);
        xPath = XPathFactory.newInstance().newXPath();
    }

    @BeforeEach
    void beforeEach() {
        xmlAssetPopulator = new XmlAssetPopulator(documentBuilder, xPath);
    }

    @Test
    void shouldPopulateStorage() throws Exception {
        XmlAssetPopulator populator = new XmlAssetPopulator(documentBuilder, xPath);
        MapAssetStorage assetStorage = new MapAssetStorage();

        populator.populate(assetStorage);

        var defaultAssets = assetStorage.getAllByTemplate("default");

        assertNotNull(defaultAssets.get("css"));
        assertEquals(defaultAssets.get("css").size(), 1);
        assertNotNull(defaultAssets.get("js"));
        assertEquals(defaultAssets.get("js").size(), 2);
    }

    @Test
    void shouldConvertNode2Asset() throws XPathExpressionException {
        Node node = ((NodeList) xPath.evaluate("//root/assets/asset", document, XPathConstants.NODESET)).item(0);
        var assets = xmlAssetPopulator.node2Assets(node);

        assertEquals(1, assets.size());
        assertTrue(assets.get(0) instanceof Css);
        assertEquals("/static/assets/css/default.min.css", ((Css) assets.get(0)).getHref());
        assertEquals("default", assets.get(0).getTemplate());
    }

    @Test
    void shouldMakeAssetFromClassNameAndArgs() throws Exception {
        var args = Map.of("tag", (Object) "script", "src", (Object) "jssrc");
        Asset asset = xmlAssetPopulator.assetFromClassNameAndArgs(
                "com.pinwheel.anabel.service.module.asset.entity.Js",
                args
        );

        assertTrue(asset instanceof Js);

        var js = (Js) asset;

        assertEquals("script", js.getTag());
        assertEquals("jssrc", js.getSrc());
        assertEquals("js", js.getName());
    }

    @Test
    void shouldGetTemplateStream() throws Exception {
        Node node = ((NodeList) xPath.evaluate("//root/assets/asset", document, XPathConstants.NODESET)).item(0);
        var stream = xmlAssetPopulator.getTemplatesStream(node);
        var list = stream.collect(Collectors.toList());

        assertEquals(1, list.size());
        assertEquals("default", list.get(0));
    }

    @Test
    void shouldCloneWithAnotherTemplateName() {
        var initialAsset = Js.builder()
                .src("src")
                .template("initTemplate")
                .name("js")
                .build();

        var resultAsset = xmlAssetPopulator.cloneWithTemplate(initialAsset, "default");

        assertTrue(resultAsset instanceof Js);

        var jsAsset = (Js) resultAsset;

        assertEquals(initialAsset.getSrc(), jsAsset.getSrc());
        assertEquals(initialAsset.getName(), jsAsset.getName());
        assertEquals(initialAsset.getTemplate(), "initTemplate");
        assertEquals(jsAsset.getTemplate(), "default");
    }
}
