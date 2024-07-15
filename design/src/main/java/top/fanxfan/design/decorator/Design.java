package top.fanxfan.design.decorator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Collections;
import java.util.Set;

/**
 * @author fanxfan
 */
@Slf4j
public class Design {
    public static void main(String[] args) {
        SimpleTroll simpleTroll = new SimpleTroll();
        simpleTroll.attack();
        simpleTroll.fleeBattle();
        log.info("simple troll attack power {}", simpleTroll.getAttackPower());

        ClubbedTroll clubbedTroll = new ClubbedTroll(simpleTroll);
        clubbedTroll.attack();
        clubbedTroll.fleeBattle();
        log.info("club troll attack power {}", clubbedTroll.getAttackPower());

        // 已知使用
        InputStream inputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };

        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

        OutputStream outputStream = new OutputStream() {
            @Override
            public void write(int b) throws IOException {

            }
        };

        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

        Set<Object> objects = Collections.synchronizedSet(Collections.emptySet());
    }

    interface Troll {
        void attack();

        void fleeBattle();

        int getAttackPower();
    }

    @NoArgsConstructor
    static class SimpleTroll implements Troll {

        @Override
        public void attack() {
            log.info(" The Troll try to gray you");
        }

        @Override
        public void fleeBattle() {
            log.info("The troll shrieks in horror and run away");
        }

        @Override
        public int getAttackPower() {
            return 10;
        }
    }

    @Getter
    @Setter
    @RequiredArgsConstructor
    static class ClubbedTroll implements Troll {
        private final Troll decorated;

        @Override
        public void attack() {
            decorated.attack();
            log.info("The troll swing at you with a club");
        }

        @Override
        public void fleeBattle() {
            decorated.fleeBattle();
        }

        @Override
        public int getAttackPower() {
            return decorated.getAttackPower() + 10;
        }
    }
}
