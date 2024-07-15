package top.fanxfan.design.adapter;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

/**
 * @author fanxfan
 */
@Slf4j
public class Design {
    public static void main(String[] args) {
        // 将一个接口转换成另一个客户所期望的接口
        var captain = new Captain(new FishBoatAdapter());
        captain.row();

        captain = new Captain(new ActualRowingBoat());
        captain.row();

        // 已知使用
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        List<String> strings = Arrays.asList("a", "b", "c");
        log.info("integers {},strings {}", integers, strings);
    }

    /**
     * 接口
     */
    interface RowingBoat {

        default void start() {
            log.info("rowing boat start");
        }

        void row();
    }

    /**
     * 调用方
     */
    static final class Captain {
        private final RowingBoat boat;

        public Captain(RowingBoat boat) {
            this.boat = boat;
        }

        public void row() {
            boat.row();
        }
    }

    /**
     * 正常实现接口
     */
    static final class ActualRowingBoat implements RowingBoat {

        @Override
        public void row() {
            log.info("ActualRowingBoat row");
        }
    }

    static class FishBoat {
        public void sail() {
            log.info("FishBoat sail");
        }
    }


    /**
     * 适配器转换接口
     */
    static class FishBoatAdapter implements RowingBoat {
        private final FishBoat boat = new FishBoat();

        public FishBoatAdapter() {
            log.info("FishBoatAdapter init");
        }

        @Override
        public void row() {
            boat.sail();
        }
    }
}
