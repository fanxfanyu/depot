package top.fanxfan.sort.bean.impl;

import top.fanxfan.sort.bean.SortStrategy;

/**
 * 冒泡排序 时间复杂度 O(n^2) 空间复杂度 O(1) 原地排序 稳定排序
 * 大数下沉
 *
 * @author fanxfan
 */
public final class BulletSort implements SortStrategy {
    @Override
    public void sort(int[] arr) {
        int n = arr.length;
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
}
