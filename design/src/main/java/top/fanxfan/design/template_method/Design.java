package top.fanxfan.design.template_method;

import lombok.extern.slf4j.Slf4j;

/**
 * @author fanxfan
 */
@Slf4j
public class Design {
    public static void main(String[] args) {
        var thief = new HealingThief(new HitAndRunMethod());
        thief.steal();
        thief.changeMethod(new SubtleMethod());
        thief.steal();
    }

    abstract static class StealingMethod {
        public StealingMethod() {

        }

        protected abstract void confuseTarget(String target);

        protected abstract String pickTarget();

        public void steal() {
            var target = pickTarget();
            log.info("the target has been choose as {}", target);
            confuseTarget(target);
            stealTheItem(target);
        }

        protected abstract void stealTheItem(String target);
    }

    static class SubtleMethod extends StealingMethod {
        public SubtleMethod() {

        }

        @Override
        protected void confuseTarget(String target) {
            log.info("Approach the {} with tears running and hug him!", target);
        }

        @Override
        protected String pickTarget() {
            return "shop keeper";
        }

        @Override
        protected void stealTheItem(String target) {
            log.info("While in close contact grab the {}'s wallet.", target);
        }

    }

    static class HitAndRunMethod extends StealingMethod {
        public HitAndRunMethod() {

        }

        @Override
        protected void confuseTarget(String target) {
            log.info("Approach the {} from behind.", target);
        }

        @Override
        protected String pickTarget() {
            return "old goblin woman";
        }

        @Override
        protected void stealTheItem(String string) {
            log.info("Grab the handbag and run away fast!");
        }
    }

    static class HealingThief {
        private StealingMethod method;

        public HealingThief(StealingMethod method) {
            this.method = method;
        }

        public void changeMethod(StealingMethod method) {
            this.method = method;
        }

        public void steal() {
            method.steal();
        }
    }
}
