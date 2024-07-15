package top.fanxfan.tree.util.entity;

import lombok.*;

/**
 * 树节点
 *
 * @author fanxfan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Node {

    private Integer value;

    private Node left;

    private Node right;

    public Node(int val) {
        this.value = val;
        this.left = null;
        this.right = null;
    }


    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }
}
