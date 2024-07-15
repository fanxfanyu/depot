package top.fanxfan.design.proxy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Proxy;

/**
 * @author fanxfan
 */
@Slf4j
public class Design {
    public static void main(String[] args) {
        var proxy = new WizardTowerProxy(new IvoryTower());
        proxy.enter(new Wizard("Red wizard"));
        proxy.enter(new Wizard("White wizard"));
        proxy.enter(new Wizard("Black wizard"));
        proxy.enter(new Wizard("Green wizard"));
        proxy.enter(new Wizard("Brown wizard"));

        // 已知使用
        boolean proxyClass = Proxy.isProxyClass(WizardTowerProxy.class);
        log.info("proxyClass {}", proxyClass);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    static class Wizard {
        private String name;

        @Override
        public String toString() {
            return getName();
        }
    }

    interface WizardTower {
        void enter(Wizard wizard);
    }

    static class IvoryTower implements WizardTower {

        @Override
        public void enter(Wizard wizard) {
            log.info("{} 进入象牙塔", wizard.getName());
        }
    }

    @Getter
    @Setter
    @RequiredArgsConstructor
    static class WizardTowerProxy implements WizardTower {
        private static final int NUM_ALLOWED = 3;
        private int numWizard;
        private WizardTower tower;

        public WizardTowerProxy(WizardTower tower) {
            this.tower = tower;
        }


        @Override
        public void enter(Wizard wizard) {
            if (numWizard < NUM_ALLOWED) {
                tower.enter(wizard);
                numWizard++;
            } else {
                log.info("{} 无法进入象牙塔", wizard.getName());
            }
        }
    }
}
