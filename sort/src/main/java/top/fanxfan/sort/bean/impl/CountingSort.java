package top.fanxfan.sort.bean.impl;

import top.fanxfan.sort.bean.SortStrategy;

/**
 * 计数排序 时间复杂度 O(n+k) 空间复杂度 O(k) 稳定
 * 通过统计元素数量来实现排序，通常应用于整数数组
 *
 * @author fanxfan
 */
public final class CountingSort implements SortStrategy {
    @Override
    public void sort(int[] arr) {
        int ma = 0;
        // 获取最大数
        for (int num : arr) {
            ma = Math.max(ma, num);
        }
        // 构建一个数组。存储每个数出现的次数
        int[] temp = new int[ma + 1];
        for (int num : arr) {
            temp[num]++;
        }
        int i = 0;
        // 遍历全部数组
        for (int num = 0; num < ma + 1; num++) {
            // 当前数出现的次数
            for (int j = 0; j < temp[num]; j++, i++) {
                arr[i] = num;
            }
        }
    }
}
