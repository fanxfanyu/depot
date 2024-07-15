package top.fanxfan.design.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.util.Deque;
import java.util.LinkedList;

/**
 * command
 *
 * @author fanxfan
 */
@Slf4j
public class Design {
    public static void main(String[] args) {
        var wizard = new Wizard();
        var goblin = new Goblin();

        goblin.printStatus();

        wizard.caseSpell(goblin::changeSize);
        goblin.printStatus();

        wizard.caseSpell(goblin::changeVisibility);
        goblin.printStatus();


        wizard.undoLastSpell();
        goblin.printStatus();

        wizard.undoLastSpell();
        goblin.printStatus();

        wizard.redoLastSpell();
        goblin.printStatus();

        wizard.redoLastSpell();
        goblin.printStatus();


        // 已知使用
        new Runnable() {
            @Override
            public void run() {

            }
        };

        new Action() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }

            @Override
            public Object getValue(String key) {
                return null;
            }

            @Override
            public void putValue(String key, Object value) {

            }

            @Override
            public void setEnabled(boolean b) {

            }

            @Override
            public boolean isEnabled() {
                return false;
            }

            @Override
            public void addPropertyChangeListener(PropertyChangeListener listener) {

            }

            @Override
            public void removePropertyChangeListener(PropertyChangeListener listener) {

            }
        };
    }

    @Getter
    @AllArgsConstructor
    enum Size {
        NORMAL("NORMAL"),
        SMALL("SMALL");
        private final String title;
    }

    @Getter
    @AllArgsConstructor
    enum Visibility {
        INVISIBLE("INVISIBLE"),
        VISIBLE("VISIBLE");
        private final String title;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    static abstract class Target {
        private Size size;
        private Visibility visibility;

        public void printStatus() {
            log.info("{} ,size: {}, visibility: {}", this, getSize(), getVisibility());
        }
    }

    @Getter
    @Setter
    static class Goblin extends Target {

        public Goblin() {
            setSize(Size.NORMAL);
            setVisibility(Visibility.VISIBLE);
        }

        public void changeSize() {
            var size = getSize() == Size.NORMAL ? Size.SMALL : Size.NORMAL;
            setSize(size);
        }

        public void changeVisibility() {
            var visibility = getVisibility() == Visibility.INVISIBLE ? Visibility.VISIBLE : Visibility.INVISIBLE;
            setVisibility(visibility);
        }

        @Override
        public String toString() {
            return "Goblin";
        }
    }

    @Setter
    @Getter
    @NoArgsConstructor
    static class Wizard {
        private Deque<Runnable> redoStack = new LinkedList<>();
        private Deque<Runnable> undoStack = new LinkedList<>();

        public void caseSpell(Runnable runnable) {
            runnable.run();
            undoStack.offerLast(runnable);
        }

        public void redoLastSpell() {
            if (!redoStack.isEmpty()) {
                Runnable runnable = redoStack.pollLast();
                undoStack.offerLast(runnable);
                runnable.run();
            }
        }

        public void undoLastSpell() {
            if (!undoStack.isEmpty()) {
                Runnable runnable = undoStack.pollLast();
                redoStack.offerLast(runnable);
                runnable.run();
            }
        }

        @Override
        public String toString() {
            return "Wizard";
        }
    }

}
