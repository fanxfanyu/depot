package top.fanxfan.design.singleton;

import lombok.extern.slf4j.Slf4j;

/**
 * @author fanxfan
 */
@Slf4j
public class Design {
    public static void main(String[] args) {
// eagerly initialized singleton
        var ivoryTower1 = IvoryTower.getInstance();
        var ivoryTower2 = IvoryTower.getInstance();
        log.info("ivoryTower1={}", ivoryTower1);
        log.info("ivoryTower2={}", ivoryTower2);

        // lazily initialized singleton
        var threadSafeIvoryTower1 = ThreadSafeLazyLoadedIvoryTower.getInstance();
        var threadSafeIvoryTower2 = ThreadSafeLazyLoadedIvoryTower.getInstance();
        log.info("threadSafeIvoryTower1={}", threadSafeIvoryTower1);
        log.info("threadSafeIvoryTower2={}", threadSafeIvoryTower2);

        // enum singleton
        var enumIvoryTower1 = EnumIvoryPower.INSTANCE;
        var enumIvoryTower2 = EnumIvoryPower.INSTANCE;
        log.info("enumIvoryTower1={}", enumIvoryTower1);
        log.info("enumIvoryTower2={}", enumIvoryTower2);

        // double checked locking
        var dcl1 = ThreadSafeDoubleCheckLocking.getInstance();
        log.info(dcl1.toString());
        var dcl2 = ThreadSafeDoubleCheckLocking.getInstance();
        log.info(dcl2.toString());

        // initialize on demand holder idiom
        var demandHolderIdiom = InitializingOnDemandHolderIdiom.getInstance();
        log.info(demandHolderIdiom.toString());
        var demandHolderIdiom2 = InitializingOnDemandHolderIdiom.getInstance();
        log.info(demandHolderIdiom2.toString());
    }

    enum EnumIvoryPower {
        INSTANCE;
    }

    static class InitializingOnDemandHolderIdiom {
        private InitializingOnDemandHolderIdiom() {

        }

        public static InitializingOnDemandHolderIdiom getInstance() {
            return HelperHolder.INSTANCE;
        }

        static class HelperHolder {
            private static final InitializingOnDemandHolderIdiom INSTANCE
                    = new InitializingOnDemandHolderIdiom();

            private HelperHolder() {

            }
        }
    }


    static class IvoryTower {
        private static final IvoryTower INSTANCE = new IvoryTower();

        private IvoryTower() {

        }

        public static IvoryTower getInstance() {
            return INSTANCE;
        }
    }


    static final class ThreadSafeDoubleCheckLocking {
        private static volatile ThreadSafeDoubleCheckLocking instance;

        private ThreadSafeDoubleCheckLocking() {
            if (instance != null) {
                throw new IllegalStateException("Already initialized");
            }
        }

        public static ThreadSafeDoubleCheckLocking getInstance() {
            var result = instance;
            if (result == null) {
                synchronized (ThreadSafeDoubleCheckLocking.class) {
                    result = instance;
                    if (result == null) {
                        result = new ThreadSafeDoubleCheckLocking();
                        instance = result;
                    }
                }
            }
            return result;
        }
    }


    static final class ThreadSafeLazyLoadedIvoryTower {
        private static volatile ThreadSafeLazyLoadedIvoryTower instance;

        private ThreadSafeLazyLoadedIvoryTower() {
            if (instance != null) {
                throw new IllegalStateException("Already initialized");
            }
        }

        public static ThreadSafeLazyLoadedIvoryTower getInstance() {
            if (instance == null) {
                instance = new ThreadSafeLazyLoadedIvoryTower();
            }
            return instance;
        }
    }

}
