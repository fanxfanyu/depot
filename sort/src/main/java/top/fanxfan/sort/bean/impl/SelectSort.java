package top.fanxfan.sort.bean.impl;

import top.fanxfan.sort.bean.SortStrategy;

/**
 * 选择排序 时间复杂度 O(n^2) 空间复杂度 O(1) 原地排序 稳定
 * 选择最小的元素与第一个元素交换位置，然后选择第二小的元素与第二个元素交换位置，依次类推
 *
 * @author fanxfan
 */
public final class SelectSort implements SortStrategy {
    @Override
    public void sort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            int min = i;
            for (int j = i + 1; j < n; j++) {
                min = arr[j] < arr[min] ? j : min;
            }
            int temp = arr[i];
            arr[i] = arr[min];
            arr[min] = temp;
        }
    }
}
