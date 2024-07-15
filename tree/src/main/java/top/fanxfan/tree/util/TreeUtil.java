package top.fanxfan.tree.util;

import lombok.extern.slf4j.Slf4j;
import top.fanxfan.tree.util.entity.Node;

import java.util.*;

/**
 * 树相关操作
 *
 * @author fanxfan
 */
@Slf4j
@SuppressWarnings("all")
public class TreeUtil {
    private TreeUtil() {

    }

    private static Integer minValue = Integer.MIN_VALUE;

    private static Boolean leafStatus = Boolean.FALSE;

    /**
     * 是否为搜索二叉树
     *
     * @param node 二叉树
     * @return 是否为搜索二叉树
     */
    public static Boolean isBst(Node node) {
        if (node == null) {
            return true;
        }
        boolean leftBst = isBst(node.getLeft());
        if (!leftBst) {
            return false;
        }
        if (node.getValue() <= minValue) {
            return false;
        } else {
            minValue = node.getValue();
        }
        return isBst(node.getRight());
    }


    /**
     * 是否是完全二叉树
     *
     * @param node node
     * @return 是否为完全二叉树
     */
    public static Boolean isCtb(Node node) {
        Deque<Node> queue = new ArrayDeque();
        Node left = null;
        Node right = null;
        queue.push(node);
        while (!queue.isEmpty()) {
            Node poll = queue.poll();
            left = poll.getLeft();
            right = poll.getRight();
            if (right != null && left == null) {
                return false;
            }
            if (leafStatus && (left != null || right != null)) {
                return false;
            }
            if (left != null) {
                queue.offer(left);
            }
            if (right != null) {
                queue.offer(right);
            }
            if (left == null || right == null) {
                leafStatus = true;
            }
        }
        return true;
    }

    // 遍历


    /**
     * 层序遍历
     *
     * @param node node
     * @return 层序遍历结果
     */
    public static List<Integer> levelOrder(Node node) {
        Deque<Node> queue = new ArrayDeque();
        queue.push(node);
        List<Integer> list = new ArrayList<>();
        while (!queue.isEmpty()) {
            Node poll = queue.poll();
            list.add(poll.getValue());
            if (poll.getLeft() != null) {
                queue.offer(poll.getLeft());
            }
            if (poll.getRight() != null) {
                queue.offer(poll.getRight());
            }
        }
        return list;
    }

    /**
     * 前序遍历
     *
     * @param node 树节点
     * @param list 遍历结果
     */
    public static void preOrder(Node node, List<Integer> list) {
        if (node == null) {
            return;
        }
        list.add(node.getValue());
        preOrder(node.getLeft(), list);
        preOrder(node.getRight(), list);
    }

    /**
     * 前序遍历
     *
     * @param node 树节点
     * @return 遍历结果
     */
    public static List<Integer> preOrder(Node node) {
        if (node == null) {
            return Collections.emptyList();
        }
        List<Integer> list = new ArrayList<>();
        Deque<Node> stack = new ArrayDeque<>();
        stack.push(node);
        while (!stack.isEmpty()) {
            Node pop = stack.pop();
            list.add(pop.getValue());
            if (pop.getRight() != null) {
                stack.push(pop.getRight());
            }
            if (pop.getLeft() != null) {
                stack.push(pop.getLeft());
            }
        }
        return list;
    }

    /**
     * 中序遍历
     *
     * @param node 树节点
     * @param list 遍历结果
     */
    public static void inOrder(Node node, List<Integer> list) {
        if (node == null) {
            return;
        }
        inOrder(node.getLeft(), list);
        list.add(node.getValue());
        inOrder(node.getRight(), list);
    }

    /**
     * 中序遍历
     *
     * @param node 树节点
     * @return 遍历结果
     */
    public static List<Integer> inOrder(Node node) {
        if (node == null) {
            return Collections.emptyList();
        }
        List<Integer> list = new ArrayList<>();
        Deque<Node> stack = new ArrayDeque<>();
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.getLeft();
            }
            node = stack.pop();
            list.add(node.getValue());
            node = node.getRight();
        }
        return list;
    }

    /**
     * 中序遍历
     *
     * @param node 树节点
     * @return 遍历结果
     */
    public static List<Integer> inOrder1(Node node) {
        if (node == null) {
            return Collections.emptyList();
        }
        List<Integer> list = new ArrayList<>();
        Deque<Node> stack = new ArrayDeque<>();
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                node = node.getLeft();
            } else {
                node = stack.pop();
                list.add(node.getValue());
                node = node.getRight();
            }
        }
        return list;
    }

    /**
     * 后序遍历
     *
     * @param node 树节点
     * @param list 遍历结果
     */
    public static void postOrder(Node node, List<Integer> list) {
        if (node == null) {
            return;
        }
        postOrder(node.getLeft(), list);
        postOrder(node.getRight(), list);
        list.add(node.getValue());
    }

    /**
     * 后序遍历
     *
     * @param node 树节点
     */
    public static List<Integer> postOrder(Node node) {
        if (node == null) {
            return Collections.emptyList();
        }
        List<Integer> list = new ArrayList<>();
        Deque<Node> stack = new ArrayDeque<>();
        Node previousNode = null;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.getLeft();
            }
            node = stack.pop();
            if (node.getRight() == null || node.getRight() == previousNode) {
                list.add(node.getValue());
                previousNode = node;
                node = null;
            } else {
                stack.push(node);
                node = node.getRight();
            }
        }
        return list;
    }
}
