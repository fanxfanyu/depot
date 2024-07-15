package top.fanxfan.algorithm.partition.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
