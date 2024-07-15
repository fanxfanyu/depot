package top.fanxfan.search.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * 查找工具类
 *
 * @author fanxfan
 */
@Slf4j
public class SearchUtil {

    private SearchUtil() {

    }

    /**
     * 二分查找
     *
     * @param nums   有序数组
     * @param target 目标值
     * @return 目标值索引
     */
    public static int binarySearch(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    /**
     * 二分查找插入点
     *
     * @param nums   有序数组
     * @param target 目标值
     * @return 目标值索引
     */
    public static int binarySearchInsertionSimple(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                return mid;
            }
        }
        return left;
    }

    /**
     * 二分插入存在重复元素
     *
     * @param nums   有序数组
     * @param target 目标值
     * @return 目标值索引
     */
    public static int binarySearchInsertion(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[right] > target) {
                right = mid - 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }
}
