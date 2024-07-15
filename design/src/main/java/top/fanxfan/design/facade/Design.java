package top.fanxfan.design.facade;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author fanxfan
 */
@Slf4j
public class Design {

    public static void main(String[] args) {
        DwarvenGoldmineFacade facade = new DwarvenGoldmineFacade();
        facade.startNewDay();
        facade.digOutGold();
        facade.endDay();
    }

    enum Action {
        GO_HONE,
        GO_TO_MINE,
        GO_TO_SLEEP,
        WAKE_UP,
        WORK
    }

    abstract static class DwarvenMineWorker {
        private void action(Action action) {
            switch (action) {
                case GO_TO_SLEEP -> goToSleep();
                case GO_TO_MINE -> goToMine();
                case GO_HONE -> goHome();
                case WAKE_UP -> wakeUp();
                case WORK -> work();
                default -> log.info("undefined action");
            }
        }

        public void action(Action... actions) {
            Arrays.stream(actions).forEach(this::action);
        }

        public void goHome() {
            log.info("{} go home", name());
        }

        public void goToMine() {
            log.info("{} go to mine", name());
        }

        public void goToSleep() {
            log.info("{} go to sleep", name());
        }

        public void wakeUp() {
            log.info("{} wake up", name());
        }

        abstract String name();

        abstract void work();
    }

    static class DwarvenTunnelDigger extends DwarvenMineWorker {

        @Override
        String name() {
            return "Dwarven Tunnel Digger";
        }

        @Override
        void work() {
            log.info("{} create another promising tunnel", name());
        }
    }

    static class DwarvenGoldDigger extends DwarvenMineWorker {


        @Override
        String name() {
            return "Dwarven Gold Digger";
        }

        @Override
        void work() {
            log.info("{} gigs for gold", name());
        }
    }

    static class DwarvenCartOperator extends DwarvenMineWorker {

        @Override
        String name() {
            return "Dwarven Cart Operator";
        }

        @Override
        void work() {
            log.info("{} moves gold chunks out of the mine", name());
        }
    }


    @Getter
    @Setter
    static class DwarvenGoldmineFacade {
        private List<DwarvenMineWorker> workers;

        public DwarvenGoldmineFacade() {
            workers = List.of(
                    new DwarvenTunnelDigger(),
                    new DwarvenCartOperator(),
                    new DwarvenGoldDigger()
            );
        }

        public void startNewDay() {
            makeActions(workers, Action.WAKE_UP, Action.GO_TO_MINE);
        }

        public void digOutGold() {
            makeActions(workers, Action.WORK);
        }

        public void endDay() {
            makeActions(workers, Action.GO_HONE, Action.GO_TO_SLEEP);
        }

        private void makeActions(Collection<DwarvenMineWorker> workers, Action... actions) {
            workers.forEach(worker -> worker.action(actions));
        }
    }
}
