package com.pinwheel.anabel.util;

import com.pinwheel.anabel.external.category.Unit;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Category(Unit.class)
class DomUtilsTest {
    private static final String XML_FILE_URL = "xml/domUtils.xml";

    private static Node rootNode;
    private static Document document;
    private static XPath xPath;

    @BeforeAll
    static void init() throws IOException, SAXException, XPathExpressionException, ParserConfigurationException {
        InputStream resource = new ClassPathResource(XML_FILE_URL).getInputStream();
        document = (DocumentBuilderFactory.newInstance().newDocumentBuilder()).parse(resource);
        xPath = XPathFactory.newInstance().newXPath();
        rootNode = ((NodeList) xPath.evaluate("//root", document, XPathConstants.NODESET)).item(0);
    }

    @Test
    void shouldPresentFirstChildElementByTagName() {
        Optional<Node> node = DomUtils.getFirstChildElementByTagName(rootNode, "parent");
        assertTrue(node.isPresent());
    }

    @Test
    void shouldEmptyFirstChildElementByTagName() {
        Optional<Node> node = DomUtils.getFirstChildElementByTagName(rootNode, "nonexistent");
        assertTrue(node.isEmpty());
    }

    @Test
    void shouldReturnNodeStreamByNodeList() throws XPathExpressionException {
        NodeList nodeList = (NodeList) xPath.evaluate("//root/parent/child::*", document, XPathConstants.NODESET);

        assertEquals(DomUtils.getElementsStream(nodeList).count(), 3);
        assertEquals(DomUtils.getElementsStream(nodeList).findFirst().get().getNodeName(), "child1");
    }

    @Test
    void shouldReturnNodeStreamByNode() throws XPathExpressionException {
        Node node = ((NodeList) xPath.evaluate("//root/parent", document, XPathConstants.NODESET)).item(0);

        assertEquals(DomUtils.getChildElementsStream(node).count(), 3);
        assertEquals(DomUtils.getChildElementsStream(node).findFirst().get().getNodeName(), "child1");
    }

    @Test
    void shouldGetElementsListByNodeList() throws XPathExpressionException {
        NodeList nodeList = (NodeList) xPath.evaluate("//root/parent/child::*", document, XPathConstants.NODESET);

        assertEquals(DomUtils.getElements(nodeList).size(), 3);
        assertEquals(DomUtils.getElements(nodeList).get(0).getNodeName(), "child1");
    }

    @Test
    void shouldGetElementsListByNode() throws XPathExpressionException {
        Node node = ((NodeList) xPath.evaluate("//root/parent", document, XPathConstants.NODESET)).item(0);

        assertEquals(DomUtils.getChildElements(node).size(), 3);
        assertEquals(DomUtils.getChildElements(node).get(0).getNodeName(), "child1");
    }

    @Test
    void shouldGetElementsMap() throws XPathExpressionException {
        Node node = ((NodeList) xPath.evaluate("//root/parent/child2", document, XPathConstants.NODESET)).item(0);

        var map = DomUtils.getChildElementsMap(node);

        assertEquals(map.size(), 2);
        assertTrue(map.containsKey("first"));
        assertTrue(map.containsKey("second"));
        assertEquals(map.get("first"), "First");
    }

    @Test
    void shouldGetStreamByNodeTypes() throws XPathExpressionException {
        NodeList nodeList = (NodeList) xPath.evaluate("//root/parent/child::*", document, XPathConstants.NODESET);

        assertEquals(DomUtils.getStreamByNodeType(nodeList, Node.ELEMENT_NODE).count(), 3);
        assertEquals(DomUtils.getStreamByNodeType(nodeList, Node.ELEMENT_NODE).findFirst().get().getNodeName(), "child1");

        assertEquals(DomUtils.getStreamByNodeTypes(nodeList, new int[]{Node.ELEMENT_NODE}).count(), 3);
        assertEquals(DomUtils.getStreamByNodeTypes(nodeList, new int[]{Node.ELEMENT_NODE}).findFirst().get().getNodeName(), "child1");

        assertEquals(DomUtils.getStreamByNodeTypes(nodeList, List.of((int) Node.ELEMENT_NODE)).count(), 3);
        assertEquals(DomUtils.getStreamByNodeTypes(nodeList, List.of((int) Node.ELEMENT_NODE)).findFirst().get().getNodeName(), "child1");
    }

    @Test
    void shouldGetChildCountByNodeTypes() throws XPathExpressionException {
        NodeList nodeList = (NodeList) xPath.evaluate("//root/parent/child::*", document, XPathConstants.NODESET);

        assertEquals(DomUtils.getChildCountByNodeType(nodeList, Node.ELEMENT_NODE), 3);
        assertEquals(DomUtils.getChildCountByNodeTypes(nodeList, new int[]{Node.ELEMENT_NODE}), 3);
        assertEquals(DomUtils.getChildCountByNodeTypes(nodeList, List.of((int) Node.ELEMENT_NODE)), 3);

        Node node = ((NodeList) xPath.evaluate("//root/parent", document, XPathConstants.NODESET)).item(0);

        assertEquals(DomUtils.getChildCountByNodeType(node, Node.ELEMENT_NODE), 3);
        assertEquals(DomUtils.getChildCountByNodeTypes(node, new int[]{Node.ELEMENT_NODE}), 3);
        assertEquals(DomUtils.getChildCountByNodeTypes(node, List.of((int) Node.ELEMENT_NODE)), 3);
    }

    @Test
    void shouldBeEmptyForNotElementType() throws XPathExpressionException {
        NodeList nodeList = (NodeList) xPath.evaluate("//root/parent/child::*", document, XPathConstants.NODESET);

        assertEquals(DomUtils.getChildCountByNodeType(nodeList, Node.ATTRIBUTE_NODE), 0);
        assertEquals(DomUtils.getChildCountByNodeTypes(nodeList, new int[]{Node.ATTRIBUTE_NODE}), 0);
        assertEquals(DomUtils.getChildCountByNodeTypes(nodeList, List.of((int) Node.ATTRIBUTE_NODE)), 0);

        Node node = ((NodeList) xPath.evaluate("//root/parent", document, XPathConstants.NODESET)).item(0);

        assertEquals(DomUtils.getChildCountByNodeType(node, Node.ATTRIBUTE_NODE), 0);
        assertEquals(DomUtils.getChildCountByNodeTypes(node, new int[]{Node.ATTRIBUTE_NODE}), 0);
        assertEquals(DomUtils.getChildCountByNodeTypes(node, List.of((int) Node.ATTRIBUTE_NODE)), 0);
    }

    @Test
    void shouldGetOneLevelMapOfElements() throws XPathExpressionException {
        NodeList nodeList = (NodeList) xPath.evaluate("//root/parent/child2/child::*", document, XPathConstants.NODESET);

        assertEquals(DomUtils.getElementsMap(nodeList), Map.of("first", "First", "second", "Second"));
    }

    @Test
    void shouldGetMultiLevelMapOfElements() throws XPathExpressionException {
        NodeList nodeList = (NodeList) xPath.evaluate("//root/parent/child::*", document, XPathConstants.NODESET);

        assertEquals(DomUtils.getElementsMap(nodeList), Map.of(
                "child1", "Child 1",
                "child2", Map.of("first", "First", "second", "Second"),
                "multy", Map.of("child", "Multy child 3")
        ));
    }
}
