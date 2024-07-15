package top.fanxfan.sort.bean;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import top.fanxfan.sort.bean.impl.*;

import java.util.function.Supplier;

/**
 * 排序方式
 *
 * @author fanxfan
 */
@RequiredArgsConstructor
@Getter
public enum SortType {
    /**
     * 冒泡排序
     */
    BULLET_SORT(BulletSort::new),
    /**
     * 计数排序
     */
    COUNTING_SORT(CountingSort::new),
    /**
     * 基数排序
     */
    COUNTING_SORT_DIGIT(CountingSortDigit::new),
    /**
     * 堆排序
     */
    HEAP_SORT(HeapSort::new),
    /**
     * 插入排序
     */
    INSERT_SORT(InsertSort::new),
    /**
     * 归并排序
     */
    MERGE_SORT(MergeSort::new),
    /**
     * 快速排序
     */
    QUICK_SORT(QuickSort::new),
    /**
     * 选择排序
     */
    SELECT_SORT(SelectSort::new);

    private final Supplier<SortStrategy> constructor;
}
