package top.fanxfan.sort.bean;

/**
 * 排序算法
 *
 * @author fanxfan
 */
public interface SortStrategy {
    /**
     * 排序
     *
     * @param arr 数组
     */
    void sort(int[] arr);
}
