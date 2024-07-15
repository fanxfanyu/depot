package top.fanxfan.design.composite;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fanxfan
 */
@Slf4j
public class Design {
    public static void main(String[] args) {
        Messenger messenger = new Messenger();
        log.info("messageFromElves");
        messenger.messageFromElves().print();
        log.info("messageFromOrcs");
        messenger.messageFromOrcs().print();

        // 已知使用
        // Component
    }

    @Getter
    @Setter
    @NoArgsConstructor
    static abstract class LetterComposite {
        private List<LetterComposite> children = new ArrayList<>();

        public void add(LetterComposite letter) {
            children.add(letter);
        }

        public int count() {
            return children.size();
        }

        public void print() {
            printThisBefore();
            children.forEach(LetterComposite::print);
            printThisAfter();
        }

        protected void printThisBefore() {

        }

        protected void printThisAfter() {

        }
    }

    @RequiredArgsConstructor
    static class Letter extends LetterComposite {
        private final char character;

        @Override
        public void printThisBefore() {
            // log.info("character {}", this.character);
            System.out.print(character);
        }
    }

    static class Word extends LetterComposite {
        public Word(List<Letter> letters) {
            letters.forEach(this::add);
        }

        public Word(char... letters) {
            for (char letter : letters) {
                add(new Letter(letter));
            }
        }

        @Override
        public void printThisBefore() {
            // log.info("");
            System.out.print(" ");
        }

    }

    static class Sentence extends LetterComposite {
        public Sentence(List<Word> words) {
            words.forEach(this::add);
        }

        @Override
        public void printThisAfter() {
            // log.info("\n");
            System.out.println(".\n");
        }
    }

    @NoArgsConstructor
    static class Messenger {
        public LetterComposite messageFromElves() {

            var words = List.of(
                    new Word('h', 'e', 'l', 'l', 'o'),
                    new Word('w', 'o', 'r', 'l', 'd')
            );
            return new Sentence(words);
        }

        public LetterComposite messageFromOrcs() {
            var words = List.of(
                    new Word('n', 'i'),
                    new Word('h', 'a', 'o')
            );
            return new Sentence(words);
        }

    }

}
