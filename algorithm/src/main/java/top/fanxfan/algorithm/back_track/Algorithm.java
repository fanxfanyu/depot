package top.fanxfan.algorithm.back_track;

import lombok.extern.slf4j.Slf4j;
import top.fanxfan.algorithm.partition.entity.Node;

import java.util.*;

/**
 * 回溯算法
 *
 * @author fanxfan
 */
@Slf4j
@SuppressWarnings("all")
public class Algorithm {

    private Algorithm() {

    }

    /**
     * 遍历二叉树-前序
     */
    public static void preOrder(Node node, List<Integer> list) {
        if (node == null) {
            return;
        }
        list.add(node.getValue());
        preOrder(node.getLeft(), list);
        preOrder(node.getRight(), list);
    }

    public static void main(String[] args) {
        // int[] nums = {3, 4, 5};
        // int target = 9;
        // List<List<Integer>> res = new ArrayList<>();
        // List<Integer> state = new ArrayList<>();
        // subset(state, target, 0, nums, res);
        //
        // log.info("res {}", res);
        // res.clear();
        // state.clear();
        // subsetResNoDuplicate(state, target, 0, nums, 0, res);
        // log.info("res {}", res);
        // res.clear();
        // state.clear();
        // subsetResNoDuplicate(state, target, nums, 0, res);
        // log.info("res {}", res);
        int[] nums = {15, 9, 7, 8, 20, -1, 4};

        shellSort(nums, nums.length);

        nQueue(1, 4, new int[5]);
        int n = 4;
        List<List<String>> state = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            List<String> row = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                row.add("#");
            }
            state.add(row);
        }
        boolean[] cols = new boolean[n]; // 记录列是否有皇后
        boolean[] diags1 = new boolean[2 * n - 1]; // 记录主对角线上是否有皇后
        boolean[] diags2 = new boolean[2 * n - 1]; // 记录次对角线上是否有皇后
        List<List<List<String>>> res = new ArrayList<>();
        nQueue(state, n, 0, cols, diags1, diags2, res);
        log.info("res {}", res);
    }


    private static void shellSort(int[] nums, int n) {

        int[] delta = new int[n / 2];
        int i = 0;
        int k = n;
        int dk, j, t;
        do {
            k = k / 2;
            delta[i++] = k;
        } while (k > 1);

        i = 0;
        while ((dk = delta[i]) > 0) {
            for (k = delta[i]; k < n; k++) {
                if (nums[k] < nums[k - dk]) {
                    t = nums[k];
                    for (j = k - dk; j >= 0 && t < nums[j]; j -= dk) {
                        nums[j + dk] = nums[j];
                    }
                    nums[j + dk] = t;
                }
            }
            log.info("nums {}", Arrays.toString(nums));
            i++;
        }
    }

    private static boolean place(int j, int[] queue) {
        int i;
        for (i = 1; i < j; i++) {
            if (queue[i] == queue[j] || Math.abs(queue[i] - queue[j]) == (j - i)) {
                return false;
            }
        }
        return true;
    }

    private static void nQueue(int j, int n, int[] queue) {
        int i;
        for (i = 1; i <= n; i++) {
            queue[j] = i;
            if (place(j, queue)) {
                if (j == n) {
                    log.info("{}", Arrays.toString(queue));
                } else {
                    nQueue(j + 1, n, queue);
                }
            }
        }
    }

    private static void nQueue(List<List<String>> state, int n, int row, boolean[] cols, boolean[] diags1, boolean[] diags2,
                               List<List<List<String>>> res) {
        if (row == n) {
            List<List<String>> copyList = new ArrayList<>();
            for (List<String> strings : state) {
                copyList.add(new ArrayList<>(strings));
            }
            res.add(copyList);
            return;
        }
        for (int col = 0; col < n; col++) {
            int diag1 = row - col + n - 1;
            int diag2 = row + col;
            if (!cols[col] && !diags1[diag1] && !diags2[diag2]) {
                state.get(row).set(col, "Q");
                cols[col] = diags1[diag1] = diags2[diag2] = true;
                nQueue(state, n, row + 1, cols, diags1, diags2, res);
                state.get(row).set(col, "#");
                cols[col] = diags1[diag1] = diags2[diag2] = false;
            }
        }
    }

    /**
     * 全排列问题
     *
     * @param state    当前状态，即nums中的所有元素都已经加入为true，否则为false
     * @param nums     候选数组，选择
     * @param selected 未避免重复，记录每个元素是否被选择
     * @param res      解
     */
    public static void fullPermutation(List<Integer> state, int[] nums, boolean[] selected, List<List<Integer>> res) {
        if (state.size() == nums.length) {
            res.add(new ArrayList<>(state));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (!selected[i]) {
                state.add(nums[i]);
                selected[i] = true;
                fullPermutation(state, nums, selected, res);
                selected[i] = false;
                state.remove(state.size() - 1);
            }
        }
    }


    /**
     * 全排列问题
     *
     * @param state    当前状态，即nums中的所有元素都已经加入为true，否则为false
     * @param nums     候选数组，选择
     * @param selected 未避免重复，记录每个元素是否被选择
     * @param res      解
     */
    public static void fullPermutationNoDuplicate(List<Integer> state, int[] nums, boolean[] selected, List<List<Integer>> res) {
        if (state.size() == nums.length) {
            res.add(new ArrayList<>(state));
            return;
        }
        // 记录当前循环内，被选择的元素
        Set<Integer> duplicate = new HashSet<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            if (!selected[i] && !duplicate.contains(nums[i])) {
                state.add(nums[i]);
                duplicate.add(nums[i]);
                selected[i] = true;
                fullPermutationNoDuplicate(state, nums, selected, res);
                selected[i] = false;
                state.remove(state.size() - 1);
            }
        }
    }

    /**
     * 子集和问题,元素可以重复使用
     *
     * @param state  字迹和小于目标值的元素
     * @param target 目标值
     * @param total  当前值
     * @param nums   选择对象
     * @param res    解
     */
    public static void subset(List<Integer> state, int target, int total, int[] nums, List<List<Integer>> res) {
        if (total == target) {
            res.add(new ArrayList<>(state));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (total + nums[i] > target) {
                continue;
            }
            state.add(nums[i]);
            subset(state, target, total + nums[i], nums, res);
            state.remove(state.size() - 1);
        }
    }

    /**
     * 子集和问题,元素可以重复使用
     *
     * @param state  字迹和小于目标值的元素
     * @param target 目标值
     * @param total  当前值
     * @param start  当前遍历的索引
     * @param nums   选择对象
     * @param res    解
     */
    public static void subsetResNoDuplicate(List<Integer> state, int target, int total, int[] nums, int start, List<List<Integer>> res) {
        log.info("i {}", start);
        if (total == target) {
            res.add(new ArrayList<>(state));
            return;
        }
        for (int i = start; i < nums.length; i++) {
            if (total + nums[i] > target) {
                continue;
            }
            state.add(nums[i]);
            subsetResNoDuplicate(state, target, total + nums[i], nums, start, res);
            start++;
            state.remove(state.size() - 1);
        }
    }

    /**
     * 子集和问题,元素可以重复使用
     *
     * @param state  字迹和小于目标值的元素
     * @param target 目标值
     * @param start  当前值
     * @param nums   选择对象
     * @param res    解
     */
    public static void subsetResNoDuplicate(List<Integer> state, int target, int[] nums, int start, List<List<Integer>> res) {
        log.info("i {}", start);
        if (0 == target) {
            res.add(new ArrayList<>(state));
            return;
        }
        for (int i = start; i < nums.length; i++) {
            if (target - nums[i] < 0) {
                break;
            }
            state.add(nums[i]);
            subsetResNoDuplicate(state, target - nums[i], nums, i, res);
            state.remove(state.size() - 1);
        }
    }

}
