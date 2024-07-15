package top.fanxfan.design.bridge;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Bridge
 *
 * @author fanxfan
 */
@Slf4j
public class Design {
    public static void main(String[] args) {
        var enchantedSword = new Sword(new SoulEatingEnchantment());
        enchantedSword.wield();
        enchantedSword.swing();
        enchantedSword.unwield();

        enchantedSword = new Sword(new FlyingEnchantment());
        enchantedSword.wield();
        enchantedSword.swing();
        enchantedSword.unwield();

        var hammer = new Hammer(new FlyingEnchantment());
        hammer.wield();
        hammer.swing();
        hammer.unwield();
    }

    interface Weapon {
        Enchantment getEnchantment();

        void swing();

        void unwield();

        void wield();
    }

    interface Enchantment {

        void apply();

        void onActive();

        void onDeactivate();
    }

    @Setter
    @Getter
    static class Sword implements Weapon {
        private Enchantment enchantment;

        public Sword(Enchantment enchantment) {
            this.enchantment = enchantment;
        }

        @Override
        public Enchantment getEnchantment() {
            return this.enchantment;
        }

        @Override
        public void swing() {
            log.info("The sword is swing");
            enchantment.apply();
        }

        @Override
        public void unwield() {
            log.info("The sword is unwield");
            enchantment.onDeactivate();
        }

        @Override
        public void wield() {
            log.info("The sword is wield");
            enchantment.onActive();
        }
    }

    @Setter
    @Getter
    static class Hammer implements Weapon {
        private Enchantment enchantment;

        public Hammer(Enchantment enchantment) {
            this.enchantment = enchantment;
        }

        @Override
        public Enchantment getEnchantment() {
            return this.enchantment;
        }

        @Override
        public void swing() {
            log.info("The hammer is swing");
            enchantment.apply();
        }

        @Override
        public void unwield() {
            log.info("The hammer is unwield");
            enchantment.onDeactivate();
        }

        @Override
        public void wield() {
            log.info("The hammer is wield");
            enchantment.onActive();
        }
    }

    static class SoulEatingEnchantment implements Enchantment {
        public SoulEatingEnchantment() {

        }

        @Override
        public void apply() {
            log.info("The item eats the soul of enemies.");
        }

        @Override
        public void onActive() {
            log.info("The item spreads bloodlust.");
        }

        @Override
        public void onDeactivate() {
            log.info("Bloodlust slowly disappears.");
        }
    }


    static class FlyingEnchantment implements Enchantment {


        public FlyingEnchantment() {

        }

        @Override
        public void apply() {
            log.info("The item flies and strikes the enemies finally returning to owner's hand.");
        }

        @Override
        public void onActive() {
            log.info("The item begins to glow faintly.");
        }

        @Override
        public void onDeactivate() {
            log.info("The item's glow fades.");
        }
    }

}
