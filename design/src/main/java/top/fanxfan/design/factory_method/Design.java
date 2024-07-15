package top.fanxfan.design.factory_method;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.util.*;

/**
 * @author fanxfan
 */
@Slf4j
public class Design {

    private static final String MANUFACTURED = "{} manufactured {}";

    public static void main(String[] args) {
        Blacksmith blacksmith = new OrcBlacksmith();
        var weapon = blacksmith.manufachtureWeapon(WeaponType.SPEAR);
        log.info(MANUFACTURED, blacksmith, weapon);
        weapon = blacksmith.manufachtureWeapon(WeaponType.AXE);
        log.info(MANUFACTURED, blacksmith, weapon);


        blacksmith = new ElfBlacksmith();
        weapon = blacksmith.manufachtureWeapon(WeaponType.SPEAR);
        log.info(MANUFACTURED, blacksmith, weapon);
        weapon = blacksmith.manufachtureWeapon(WeaponType.AXE);
        log.info(MANUFACTURED, blacksmith, weapon);

        // 已知实现
        Locale[] availableLocales = Locale.getAvailableLocales();
        log.info("{}", Arrays.toString(Arrays.stream(availableLocales).filter(local->local.toString().contains("zh")).toArray()));
        Calendar instance = Calendar.getInstance(Locale.forLanguageTag("usa"));
        log.info("{}", instance.getTime());
        Charset charset = Charset.forName("gbk");
        log.info("{}", charset);

    }

    @Getter
    @AllArgsConstructor
    enum WeaponType {
        AXE("AXE"),
        SHORT_SWORD("SHORT_SWORD"),
        SPEAR("SPEAR"),
        UNDEFINED("UNDEFINED");

        private final String title;
    }

    interface Weapon {
        WeaponType getWeaponType();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    static class OrcWeapon implements Weapon {
        private WeaponType type;

        public OrcWeapon(WeaponType weaponType) {
            this.type = weaponType;
        }

        @Override
        public WeaponType getWeaponType() {
            return type;
        }

        @Override
        public String toString() {
            return "an orc " + getWeaponType();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    static class ElfWeapon implements Weapon {
        private WeaponType type;

        public ElfWeapon(WeaponType weaponType) {
            this.type = weaponType;
        }

        @Override
        public WeaponType getWeaponType() {
            return type;
        }

        @Override
        public String toString() {
            return "an elven " + getWeaponType();
        }
    }

    interface Blacksmith {
        Weapon manufachtureWeapon(WeaponType weaponType);
    }

    @NoArgsConstructor
    static class OrcBlacksmith implements Blacksmith {

        private static final Map<WeaponType, OrcWeapon> ORC_ARSENAL;

        static {
            ORC_ARSENAL = new EnumMap<>(WeaponType.class);
            Arrays.stream(WeaponType.values()).forEach(type -> ORC_ARSENAL.put(type, new OrcWeapon(type)));
        }

        @Override
        public Weapon manufachtureWeapon(WeaponType weaponType) {
            return ORC_ARSENAL.get(weaponType);
        }

        @Override
        public String toString() {
            return "the Orc Blacksmith";
        }
    }

    static class ElfBlacksmith implements Blacksmith {

        private static final Map<WeaponType, ElfWeapon> ELF_ARSENAL;

        static {
            ELF_ARSENAL = new EnumMap<>(WeaponType.class);
            Arrays.stream(WeaponType.values()).forEach(type -> ELF_ARSENAL.put(type, new ElfWeapon(type)));
        }

        @Override
        public Weapon manufachtureWeapon(WeaponType weaponType) {
            return ELF_ARSENAL.get(weaponType);
        }

        @Override

        public String toString() {
            return "the Elf Blacksmith";
        }
    }

}
