package top.fanxfan.sort.bean.impl;

import top.fanxfan.sort.bean.SortStrategy;

/**
 * 快速排序 时间复杂度O(nlogn) 空间复杂度O(logn) 原地排序 不稳定
 * 哨兵分治 选择基准数，左侧小于，右侧大于
 *
 * @author fanxfan
 */
public final class QuickSort implements SortStrategy {
    @Override
    public void sort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }


    void quickSort(int[] arr, int left, int right) {
        if (left > right) {
            return;
        }
        int pivot = partition(arr, left, right);
        quickSort(arr, left, pivot - 1);
        quickSort(arr, pivot + 1, right);
    }

    int partition(int[] arr, int left, int right) {
        int i = left;
        int j = right;
        while (i < j) {
            while (i < j && arr[j] > arr[left]) {
                j--;
            }
            while (i < j && arr[i] < arr[left]) {
                i++;
            }
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
        int temp = arr[i];
        arr[i] = arr[left];
        arr[left] = temp;
        return i;
    }
}
