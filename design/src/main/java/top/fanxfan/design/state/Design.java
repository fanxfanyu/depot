package top.fanxfan.design.state;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author fanxfan
 */
@Slf4j
public class Design {
    public static void main(String[] args) {
        var mammoth = new Mammoth();
        mammoth.observe();
        mammoth.timePasses();
        mammoth.observe();
        mammoth.timePasses();
        mammoth.observe();

        log.info("'---");
        mammoth = new Mammoth(new PeacefulStat(mammoth));
        mammoth.observe();

        mammoth = new Mammoth(new AngryState(mammoth));
        mammoth.observe();
    }

    interface State {
        void observe();

        void onEnterState();
    }

    @Getter
    @Setter
    static class Mammoth {
        private State state;

        public Mammoth() {
            state = new PeacefulStat(this);
        }

        public Mammoth(State state) {
            this.state = state;
            this.state.onEnterState();
        }

        private void changeStateTo(State newState) {
            this.state = newState;
            this.state.onEnterState();
        }

        public void observe() {
            this.state.observe();
        }

        public void timePasses() {
            if (state.getClass().equals(PeacefulStat.class)) {
                changeStateTo(new AngryState(this));
            } else {
                changeStateTo(new PeacefulStat(this));
            }

        }

        @Override
        public String toString() {
            return "the Mammoth " + state.toString();
        }
    }


    static class PeacefulStat implements State {
        private final Mammoth mammoth;

        public PeacefulStat(Mammoth mammoth) {
            this.mammoth = mammoth;
        }

        @Override
        public void observe() {
            log.info("{} is clam and peaceful", mammoth);
        }

        @Override
        public void onEnterState() {
            log.info("{} clams down", mammoth);
        }

    }

    static class AngryState implements State {

        private final Mammoth mammoth;

        public AngryState(Mammoth mammoth) {
            this.mammoth = mammoth;
        }

        @Override
        public void observe() {
            log.info("{} is furious ", mammoth);
        }

        @Override
        public void onEnterState() {
            log.info("{} gets angry", mammoth);
        }
    }
}
