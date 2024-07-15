package top.fanxfan.design.flyweigth;

import lombok.extern.slf4j.Slf4j;

import java.util.EnumMap;
import java.util.List;

/**
 * @author fanxfan
 */
@Slf4j
@SuppressWarnings("all")
public class Design {
    public static void main(String[] args) {
        var alchemistShop = new AlchemistShop();
        alchemistShop.drinkPotions();
        Integer one1 = Integer.valueOf(127);
        Integer one2 = Integer.valueOf(127);
        // 已知使用
        log.info("{} ,{},{},{}", one1.hashCode(), one2.hashCode(), one1.equals(one2), one1 == one2);

        Integer two1 = 127;
        Integer two2 = 127;
        log.info("{} ,{} ,{},{}", two1, two2, two1.equals(two2), two1 == two2);

        Integer three1 = new Integer(127);
        Integer three2 = new Integer(127);
        log.info("{} ,{} ,{},{}", three1, two2, three1.equals(three2), three1 == three2);
    }

    enum PotionType {
        HEALING,
        HOLY_WATER,
        INVISIBILITY,
        POISON,
        STRENGTH;
    }

    interface Potion {
        void drink();
    }

    static class PoisonPotion implements Potion {

        @Override
        public void drink() {
            log.info("poison potion {}", System.identityHashCode(this));
        }
    }

    static class InvisibilityPotion implements Potion {

        @Override
        public void drink() {
            log.info("invisibility potion {}", System.identityHashCode(this));
        }
    }

    static class HolyWaterPotion implements Potion {

        @Override
        public void drink() {
            log.info("holy water potion {}", System.identityHashCode(this));
        }
    }

    static class HealingPotion implements Potion {

        @Override
        public void drink() {
            log.info("healing potion {}", System.identityHashCode(this));
        }
    }

    static class StrengthPotion implements Potion {

        @Override
        public void drink() {
            log.info("strength potion {}", System.identityHashCode(this));
        }
    }

    static class AlchemistShop {
        private final List<Potion> bottomShelf;

        private final List<Potion> topShelf;

        public AlchemistShop() {
            var factory = new PotionFactory();
            topShelf = List.of(
                    factory.createPotion(PotionType.HEALING),
                    factory.createPotion(PotionType.HEALING),
                    factory.createPotion(PotionType.HOLY_WATER),
                    factory.createPotion(PotionType.INVISIBILITY),
                    factory.createPotion(PotionType.INVISIBILITY),
                    factory.createPotion(PotionType.POISON),
                    factory.createPotion(PotionType.STRENGTH),
                    factory.createPotion(PotionType.INVISIBILITY)
            );
            bottomShelf = List.of(
                    factory.createPotion(PotionType.HEALING),
                    factory.createPotion(PotionType.INVISIBILITY),
                    factory.createPotion(PotionType.POISON),
                    factory.createPotion(PotionType.STRENGTH),
                    factory.createPotion(PotionType.HOLY_WATER)
            );
        }

        public void drinkPotions() {
            log.info("drinking top shelf potions");
            topShelf.forEach(Potion::drink);
            log.info("drinking bottom shelf potions");
            bottomShelf.forEach(Potion::drink);
        }

        public List<Potion> getBottomShelf() {
            return List.copyOf(this.bottomShelf);
        }

        public List<Potion> getTopShelf() {
            return List.copyOf(this.topShelf);
        }

    }

    static class PotionFactory {
        private final EnumMap<PotionType, Potion> potions;

        public PotionFactory() {
            potions = new EnumMap<>(PotionType.class);
        }

        Potion createPotion(PotionType type) {
            var potion = potions.get(type);
            if (potion == null) {
                switch (type) {
                    case HEALING -> potion = new HealingPotion();
                    case HOLY_WATER -> potion = new HolyWaterPotion();
                    case INVISIBILITY -> potion = new InvisibilityPotion();
                    case POISON -> potion = new PoisonPotion();
                    case STRENGTH -> potion = new StrengthPotion();
                    default -> {
                    }
                }
            }
            if (potion != null) {
                potions.put(type, potion);
            }
            return potion;
        }
    }
}
