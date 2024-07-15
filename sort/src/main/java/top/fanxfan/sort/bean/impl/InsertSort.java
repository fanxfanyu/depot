package top.fanxfan.sort.bean.impl;

import top.fanxfan.sort.bean.SortStrategy;

/**
 * 插入排序 时间复杂度 O(n^2) 空间复杂度 O(1) 原地排序 稳定
 * 将一个记录插入到已经排好序的有序表中，从而得到一个新的、记录数增1的有序表。
 *
 * @author fanxfan
 */
public final class InsertSort implements SortStrategy {

    /**
     * 排序
     *
     * @param arr 数组
     */
    @Override
    public void sort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int base = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > base) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = base;
        }
    }
}
