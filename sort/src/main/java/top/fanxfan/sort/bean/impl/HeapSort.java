package top.fanxfan.sort.bean.impl;

import top.fanxfan.sort.bean.SortStrategy;

/**
 * 堆排序 时间复杂度 O(nlogn) 空间复杂度 O(1) 不稳定
 * 建一个堆，堆顶与堆底交换，然后重新调整堆，直到排序完成
 *
 * @author fanxfan
 */
public final class HeapSort implements SortStrategy {
    @Override
    public void sort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n / 2 + 1; i++) {
            siftDown(arr, n, i);
        }
        for (int i = n - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            siftDown(arr, i, 0);
        }
    }


    void siftDown(int[] arr, int n, int i) {
        while (true) {
            int l = i * 2 + 1;
            int r = i * 2 + 2;
            int ma = i;
            if (l < n && arr[l] > arr[ma]) {
                ma = l;
            }
            if (r < n && arr[r] > arr[ma]) {
                ma = r;
            }
            if (ma == i) {
                break;
            }
            int temp = arr[i];
            arr[i] = arr[ma];
            arr[ma] = temp;
            i = ma;
        }
    }
}
