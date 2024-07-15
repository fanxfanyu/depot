package top.fanxfan.design.visitor;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @author fanxfan
 */
@Slf4j
public class Design {
    public static void main(String[] args) {
        var commander = new Commander(
                new Sergeant(new Soldier(), new Soldier(), new Soldier()),
                new Sergeant(new Soldier(), new Soldier(), new Soldier())
        );
        commander.accept(new SoldierVisitor());
        commander.accept(new SergeantVisitor());
        commander.accept(new CommanderVisitor());
    }

    abstract static class Unit {
        private final Unit[] children;

        public Unit(Unit[] children) {
            this.children = children;
        }

        public void accept(UnitVisitor visitor) {
            Arrays.stream(children).forEach(child -> child.accept(visitor));
        }
    }

    static class Commander extends Unit {

        public Commander(Unit... children) {
            super(children);
        }

        @Override
        public void accept(UnitVisitor visitor) {
            visitor.visit(this);
            super.accept(visitor);
        }

        @Override
        public String toString() {
            return "commander";
        }
    }

    static class Sergeant extends Unit {

        public Sergeant(Unit... children) {
            super(children);
        }

        @Override
        public void accept(UnitVisitor visitor) {
            visitor.visit(this);
            super.accept(visitor);
        }

        @Override
        public String toString() {
            return "sergeant";
        }

    }

    static class Soldier extends Unit {

        public Soldier(Unit... children) {
            super(children);
        }


        @Override
        public void accept(UnitVisitor visitor) {
            visitor.visit(this);
            super.accept(visitor);
        }

        @Override
        public String toString() {
            return "soldier";
        }
    }

    interface UnitVisitor {
        void visit(Commander commander);

        void visit(Sergeant sergeant);

        void visit(Soldier soldier);
    }

    static class SoldierVisitor implements UnitVisitor {


        @Override
        public void visit(Commander commander) {
            //
        }

        @Override
        public void visit(Sergeant sergeant) {
            //
        }

        @Override
        public void visit(Soldier soldier) {
            log.info("SoldierVisitor {}", soldier);
        }
    }

    static class SergeantVisitor implements UnitVisitor {

        @Override
        public void visit(Commander commander) {

        }

        @Override
        public void visit(Sergeant sergeant) {
            log.info("SergeantVisitor {}", sergeant);
        }

        @Override
        public void visit(Soldier soldier) {

        }
    }

    static class CommanderVisitor implements UnitVisitor {

        @Override
        public void visit(Commander commander) {
            log.info("CommanderVisitor {}", commander);
        }

        @Override
        public void visit(Sergeant sergeant) {

        }

        @Override
        public void visit(Soldier soldier) {

        }
    }
}


