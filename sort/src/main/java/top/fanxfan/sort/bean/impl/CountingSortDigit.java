package top.fanxfan.sort.bean.impl;

import lombok.extern.slf4j.Slf4j;
import top.fanxfan.sort.bean.SortStrategy;

import java.util.Arrays;

/**
 * 基数排序 时间复杂度 O(nk) 空间复杂度 O(n+d) 非原地排序 稳定
 * 依次对每一位进行排序，从而得到最终的排序结果
 *
 * @author fanxfan
 */
@Slf4j
public final class CountingSortDigit implements SortStrategy {
    @Override
    public void sort(int[] arr) {
        countingSortDigit(arr);
    }

    int getDigit(int num, int digit) {
        return (int) (num / Math.pow(10, digit)) % 10;
    }

    void countingSortDigit(int[] arr) {
        int max = Arrays.stream(arr).max().orElse(Integer.MIN_VALUE);
        int digitMax = 0;
        while (max > 0) {
            max /= 10;
            digitMax++;
        }
        int j = 0;
        for (int digit = 0; digit < digitMax; digit++) {
            int[] counts = new int[10];
            for (int num : arr) {
                j = getDigit(num, digit);
                counts[j]++;
            }
            for (j = 1; j < 10; j++) {
                counts[j] = counts[j] + counts[j - 1];
            }
            int[] temp = new int[arr.length];
            for (int k = arr.length - 1; k >= 0; k--) {
                j = getDigit(arr[k], digit);
                int i = counts[j] - 1;
                temp[i] = arr[k];
                counts[j]--;
            }
            for (j = 0; j < temp.length; j++) {
                arr[j] = temp[j];
            }
        }


    }
}
