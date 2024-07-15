package top.fanxfan.design.factory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.function.Supplier;

/**
 * @author fanxfan
 */
@Slf4j
public class Design {

    public static void main(String[] args) {
        var coin = CoinFactory.getCoin(CoinType.COPPER);
        log.info("{}", coin.getDescription());

        coin = CoinFactory.getCoin(CoinType.GOLD);
        log.info("{}", coin.getDescription());

        Calendar instance = Calendar.getInstance();
    }

    interface Coin {
        String getDescription();
    }

    @RequiredArgsConstructor
    static class GoldCoin implements Coin {
        private static final String DESCRIPTION = "gold coin";

        @Override
        public String getDescription() {
            return DESCRIPTION;
        }
    }

    @RequiredArgsConstructor
    static class CopperCoin implements Coin {
        private static final String DESCRIPTION = "copper coin";

        @Override
        public String getDescription() {
            return DESCRIPTION;
        }
    }

    @Getter
    @AllArgsConstructor
    public enum CoinType {
        COPPER(CopperCoin::new),
        GOLD(GoldCoin::new);

        private final Supplier<Coin> constructor;
    }

    static class CoinFactory {

        private CoinFactory() {

        }

        public static Coin getCoin(CoinType type) {
            return type.getConstructor().get();
        }
    }
}
