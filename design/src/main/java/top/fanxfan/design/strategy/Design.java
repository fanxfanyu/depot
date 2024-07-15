package top.fanxfan.design.strategy;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author fanxfan
 */
@Slf4j
public class Design {


    private static final String RED_DRAGON_EMERGES = "Red dragon emerges.";
    private static final String GREEN_DRAGON_SPOTTED = "Green dragon spotted ahead!";
    private static final String BLACK_DRAGON_LANDS = "Black dragon lands before you.";

    public static void main(String[] args) {
// GoF Strategy pattern
        log.info(GREEN_DRAGON_SPOTTED);
        var dragonSlayer = new DragonSlayer(new MeleeStrategy());
        dragonSlayer.goToBattle();
        log.info(RED_DRAGON_EMERGES);
        dragonSlayer.changeStrategy(new ProjectileStrategy());
        dragonSlayer.goToBattle();
        log.info(BLACK_DRAGON_LANDS);
        dragonSlayer.changeStrategy(new SpellStrategy());
        dragonSlayer.goToBattle();

        // Java 8 functional implementation Strategy pattern
        log.info(GREEN_DRAGON_SPOTTED);
        dragonSlayer = new DragonSlayer(
                () -> log.info("With your Excalibur you severe the dragon's head!"));
        dragonSlayer.goToBattle();
        log.info(RED_DRAGON_EMERGES);
        dragonSlayer.changeStrategy(() -> log.info(
                "You shoot the dragon with the magical crossbow and it falls dead on the ground!"));
        dragonSlayer.goToBattle();
        log.info(BLACK_DRAGON_LANDS);
        dragonSlayer.changeStrategy(() -> log.info(
                "You cast the spell of disintegration and the dragon vaporizes in a pile of dust!"));
        dragonSlayer.goToBattle();

        // Java 8 lambda implementation with enum Strategy pattern
        log.info(GREEN_DRAGON_SPOTTED);
        dragonSlayer.changeStrategy(Strategy.MELEE_STRATEGY);
        dragonSlayer.goToBattle();
        log.info(RED_DRAGON_EMERGES);
        dragonSlayer.changeStrategy(Strategy.PROJECTILE_STRATEGY);
        dragonSlayer.goToBattle();
        log.info(BLACK_DRAGON_LANDS);
        dragonSlayer.changeStrategy(Strategy.SPELL_STRATEGY);
        dragonSlayer.goToBattle();
    }


    @Getter
    private enum Strategy implements DragonSlayingStrategy {
        MELEE_STRATEGY(() -> {
            log.info("ENUM MELEE_STRATEGY");
        }),

        PROJECTILE_STRATEGY(() -> {
            log.info("ENUM PROJECTILE_STRATEGY");
        }),

        SPELL_STRATEGY(() -> {
            log.info("ENUM SPELL_STRATEGY");
        });

        private final DragonSlayingStrategy dragonSlayingStrategy;

        Strategy(DragonSlayingStrategy dragonSlayingStrategy) {
            this.dragonSlayingStrategy = dragonSlayingStrategy;
        }

        @Override
        public void execute() {
            dragonSlayingStrategy.execute();
        }
    }

    @FunctionalInterface
    interface DragonSlayingStrategy {
        void execute();
    }

    static class SpellStrategy implements DragonSlayingStrategy {

        @Override
        public void execute() {
            log.info("the spell dragon");
        }
    }

    static class ProjectileStrategy implements DragonSlayingStrategy {

        @Override
        public void execute() {
            log.info("the projectile dragon");
        }
    }

    static class MeleeStrategy implements DragonSlayingStrategy {

        @Override
        public void execute() {
            log.info("the melee dragon");
        }
    }

    static class DragonSlayer {
        private DragonSlayingStrategy strategy;

        public DragonSlayer(DragonSlayingStrategy strategy) {
            this.strategy = strategy;
        }

        public void changeStrategy(DragonSlayingStrategy strategy) {
            this.strategy = strategy;
        }

        public void goToBattle() {
            strategy.execute();
        }
    }
}