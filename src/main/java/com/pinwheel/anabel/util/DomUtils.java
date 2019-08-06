package com.pinwheel.anabel.util;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Dom utils. Contains static method for working with DOM elements.
 *
 * @author Listratenko Stanislav
 * @version 1.0.0
 */
public class DomUtils {
    /**
     * Variant of {@link DomUtils#getStreamByNodeTypes(NodeList, List)}. Uses single node type value.
     *
     * @param nodeList node list.
     * @param nodeType node type value.
     * @return stream of nodes.
     * @see Node
     */
    public static Stream<Node> getStreamByNodeType(NodeList nodeList, int nodeType) {
        return getStreamByNodeTypes(nodeList, new int[]{nodeType});
    }

    /**
     * Variant of {@link DomUtils#getStreamByNodeTypes(NodeList, List)}. Uses array of node types.
     *
     * @param nodeList  node list.
     * @param nodeTypes node type values.
     * @return stream of nodes.
     * @see Node
     */
    public static Stream<Node> getStreamByNodeTypes(NodeList nodeList, int[] nodeTypes) {
        return getStreamByNodeTypes(nodeList, IntStream.of(nodeTypes).boxed().collect(Collectors.toList()));
    }

    /**
     * Returns stream of nodes according on passed node list. Uses node types of {@link Node}. Uses list of node types.
     *
     * @param nodeList  node list.
     * @param nodeTypes node type values.
     * @return stream of nodes.
     * @see Node
     */
    public static Stream<Node> getStreamByNodeTypes(NodeList nodeList, List<Integer> nodeTypes) {
        return IntStream.range(0, nodeList.getLength())
                .mapToObj(nodeList::item)
                .filter(node -> nodeTypes.contains((int) node.getNodeType()));
    }

    /**
     * Variant of {@link DomUtils#getChildCountByNodeTypes(NodeList, List)}. Uses single node type value.
     *
     * @param nodeList node list.
     * @param nodeType node type value.
     * @return count of nodes.
     * @see Node
     */
    public static long getChildCountByNodeType(NodeList nodeList, int nodeType) {
        return getChildCountByNodeTypes(nodeList, new int[]{nodeType});
    }

    /**
     * Variant of {@link DomUtils#getChildCountByNodeTypes(NodeList, List)}. Uses array of node types.
     *
     * @param nodeList  node list.
     * @param nodeTypes node type values.
     * @return count of nodes.
     * @see Node
     */
    public static long getChildCountByNodeTypes(NodeList nodeList, int[] nodeTypes) {
        return getChildCountByNodeTypes(nodeList, IntStream.of(nodeTypes).boxed().collect(Collectors.toList()));
    }

    /**
     * Returns count of nodes according on passed node list. Uses node types of {@link Node}. Uses list of node types.
     *
     * @param nodeList  node list.
     * @param nodeTypes node type values.
     * @return count of nodes.
     * @see Node
     */
    public static long getChildCountByNodeTypes(NodeList nodeList, List<Integer> nodeTypes) {
        return getStreamByNodeTypes(nodeList, nodeTypes).count();
    }

    /**
     * Variant of {@link DomUtils#getChildCountByNodeTypes(Node, List)}. Uses single node type value.
     *
     * @param node     node for counting children.
     * @param nodeType node type value.
     * @return count of child nodes.
     * @see Node
     */
    public static long getChildCountByNodeType(Node node, int nodeType) {
        return getChildCountByNodeTypes(node, new int[]{nodeType});
    }

    /**
     * Variant of {@link DomUtils#getChildCountByNodeTypes(Node, List)}. Uses array of node types.
     *
     * @param node      node for counting children.
     * @param nodeTypes node type value.
     * @return count of child nodes.
     * @see Node
     */
    public static long getChildCountByNodeTypes(Node node, int[] nodeTypes) {
        return getChildCountByNodeTypes(node, IntStream.of(nodeTypes).boxed().collect(Collectors.toList()));
    }

    /**
     * Returns count of child nodes of passed nodes. Uses node types of {@link Node}. Uses list of node types.
     *
     * @param node      node for counting children.
     * @param nodeTypes node type value.
     * @return count of child nodes.
     * @see Node
     */
    public static long getChildCountByNodeTypes(Node node, List<Integer> nodeTypes) {
        return getChildCountByNodeTypes(node.getChildNodes(), nodeTypes);
    }

    /**
     * Gets first child element with passed name.
     *
     * @param node node with children for searching.
     * @param name tag name of searching element.
     * @return {@link Optional} of found node.
     */
    public static Optional<Node> getFirstChildElementByTagName(Node node, String name) {
        return DomUtils.getChildElementsStream(node)
                .filter(n -> name.equalsIgnoreCase(n.getNodeName()))
                .findAny();
    }

    /**
     * Returns children of passed node.
     *
     * @param node target node.
     * @return children of the node.
     */
    public static List<Node> getChildElements(Node node) {
        return DomUtils.getElements(node.getChildNodes());
    }

    /**
     * Returns list of nodes according on passed node list.
     *
     * @param nodeList target node list.
     * @return list of nodes.
     */
    public static List<Node> getElements(NodeList nodeList) {
        return DomUtils.getElementsStream(nodeList)
                .collect(Collectors.toList());
    }

    /**
     * Returns stream of nodes according on passed node list.
     *
     * @param nodeList target node list.
     * @return stream of nodes.
     */
    public static Stream<Node> getElementsStream(NodeList nodeList) {
        return DomUtils.getStreamByNodeType(nodeList, Node.ELEMENT_NODE);
    }

    /**
     * Returns stream of children nodes according on passed node.
     *
     * @param node target node.
     * @return stream of children nodes.
     */
    public static Stream<Node> getChildElementsStream(Node node) {
        return DomUtils.getElementsStream(node.getChildNodes());
    }

    /**
     * Returns map of nodes recursively:
     * <pre>
     * {@code
     *  <first>First</first>
     *  <second>
     *      <first>First</first>
     *      <second>Second</second>
     *  </second>
     * }
     * </pre>
     * will be converted to:
     * <pre>
     *  {
     *      "first" => "First",
     *      "second" => {
     *          "first" => "First",
     *          "second" => "Second"
     *      }
     *  }
     * </pre>
     *
     * @param nodeList target node list.
     * @return map of node list.
     */
    public static Map<String, Object> getElementsMap(NodeList nodeList) {
        return DomUtils.getElementsStream(nodeList)
                .collect(Collectors.toMap(
                        Node::getNodeName,
                        node -> getChildCountByNodeType(node, Node.ELEMENT_NODE) != 0
                                ? getChildElementsMap(node)
                                : node.getTextContent(),
                        (left, right) -> right
                ));
    }

    /**
     * Returns map of children nodes recursively.
     *
     * @param node target node.
     * @return map of children nodes.
     * @see DomUtils#getElementsMap(NodeList)
     */
    public static Map<String, Object> getChildElementsMap(Node node) {
        return DomUtils.getElementsMap(node.getChildNodes());
    }
}
