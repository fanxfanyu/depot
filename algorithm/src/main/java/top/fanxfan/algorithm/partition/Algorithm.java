package top.fanxfan.algorithm.partition;

import lombok.extern.slf4j.Slf4j;
import top.fanxfan.algorithm.partition.entity.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fanxfan
 */
@Slf4j
@SuppressWarnings("all")
public class Algorithm {

    private Algorithm() {

    }

    /**
     * 构建二叉树：分治
     */
    private static Node dfs(int[] preorder, Map<Integer, Integer> inorderMap, int i, int l, int r) {
        // 子树区间为空时终止
        if (r - l < 0) {
            return null;
        }
        // 初始化根节点
        Node root = new Node(preorder[i]);
        // 查询 m ，从而划分左右子树
        int m = inorderMap.get(preorder[i]);
        // 子问题：构建左子树
        root.setLeft(dfs(preorder, inorderMap, i + 1, l, m - 1));
        // 子问题：构建右子树
        root.setRight(dfs(preorder, inorderMap, i + 1 + m - l, m + 1, r));
        // 返回根节点
        return root;
    }

    /* 构建二叉树 */
    public static Node buildTree(int[] preorder, int[] inorder) {
        // 初始化哈希表，存储 inorder 元素到索引的映射
        Map<Integer, Integer> inorderMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }
        Node root = dfs(preorder, inorderMap, 0, 0, inorder.length - 1);
        return root;
    }

    /**
     * 汉诺塔问题
     *
     * @param src 原目标
     * @param buf 缓存目标
     * @param tar 目的目标
     */
    public static void solveHanota(List<Integer> src, List<Integer> buf, List<Integer> tar) {
        int size = src.size();
        hanotaDfs(size, src, buf, tar);
    }

    private static void move(List<Integer> src, List<Integer> tar) {
        Integer pan = src.get(src.size() - 1);
        tar.add(pan);
    }

    private static void hanotaDfs(int size, List<Integer> src, List<Integer> buf, List<Integer> tar) {
        if (size == 1) {
            move(src, tar);
            return;
        }
        hanotaDfs(size - 1, src, tar, buf);
        move(src, tar);
        hanotaDfs(size - 1, buf, src, tar);
    }
}
