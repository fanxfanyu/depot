package top.fanxfan.design.builder;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import top.fanxfan.design.exception.CustomException;

import java.util.Calendar;

/**
 * builder
 *
 * @author fanxfan
 */
@Slf4j
@SuppressWarnings("all")
public class Design {

    public static void main(String[] args) {
        var mage = new Hero.Builder(Profession.MAGE, "Riobard")
                .withArmor(Armor.CLOTHES)
                .withHairColor(HairColor.BLACk)
                .withHairType(HairType.BALD)
                .withWeapon(Weapon.DAGGER)
                .build();
        log.info("mage {}", mage);

        // 已知使用
        StringBuffer buffer = new StringBuffer("a").append("b").append("c");
        log.info("buffer {}", buffer);


        StringBuilder builder = new StringBuilder("a").append("b").append("c");
        log.info("builder {}", builder);

        Calendar instance = Calendar.getInstance();
        log.info("{}", instance.get(Calendar.DATE));
        Calendar build = new Calendar.Builder()
                .set(Calendar.YEAR, instance.get(Calendar.YEAR))
                .set(Calendar.MONDAY, instance.get(Calendar.MONDAY))
                .set(Calendar.DAY_OF_MONTH, instance.get(Calendar.DAY_OF_MONTH))
                .build();
        log.info("{}", build.get(Calendar.DATE));
    }


    @Getter
    @AllArgsConstructor
    enum Armor {
        CHAIN_MAIL("CHAIN_MAIL"),
        CLOTHES("CLOTHES"),
        LEATHER("LEATHER"),
        PLATE_MAIL("PLATE_MAIL");

        private final String title;
    }

    enum HairColor {
        BLOND,
        BROWN,
        RED,
        WHITE,
        BLACk;
    }

    @Getter
    @AllArgsConstructor
    enum HairType {
        BALD("BALD"),
        CURLY("CURLY"),
        LONG_CURLY("LONG_CURLY"),
        LONG_STRAIGHT("LONG_STRAIGHT"),
        SHORT("SHORT");
        private final String title;
    }

    enum Profession {
        MAGE,
        PRIEST,
        THIEF,
        WARRIOR;
    }

    enum Weapon {
        AXE,
        BOW,
        DAGGER,
        SWORD,
        WAR_HAMMER;
    }


    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    static final class Hero {
        private Armor armor;
        private HairColor hairColor;
        private HairType hairType;
        private String name;
        private Profession profession;
        private Weapon weapon;

        private Hero(Builder builder) {
            this(builder.armor, builder.hairColor, builder.hairType, builder.name, builder.profession, builder.weapon);
        }

        @Setter
        @Getter
        static class Builder {
            private Armor armor;
            private HairColor hairColor;
            private HairType hairType;
            private String name;
            private Profession profession;
            private Weapon weapon;

            public Builder(Profession profession, String name) {
                if (profession == null || name == null) {
                    throw new CustomException("profession and name can not null");
                }
                this.profession = profession;
                this.name = name;
            }

            public Hero build() {
                return new Hero(this);
            }

            public Builder withArmor(Armor armor) {
                this.armor = armor;
                return this;
            }

            public Builder withHairColor(HairColor hairColor) {
                this.hairColor = hairColor;
                return this;
            }

            public Builder withHairType(HairType hairType) {
                this.hairType = hairType;
                return this;
            }

            public Builder withWeapon(Weapon weapon) {
                this.weapon = weapon;
                return this;
            }
        }
    }
}
