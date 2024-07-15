package top.fanxfan.design.iterator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author fanxfan
 */
@Slf4j
public class Design {

    private static final TreasureChest TREASURE_CHEST = new TreasureChest();

    private static void demonstrateTreasureChestIteratorForType(ItemType itemType) {
        log.info("------------------------");
        log.info("Item Iterator for ItemType " + itemType + ": ");
        var itemIterator = TREASURE_CHEST.iterator(itemType);
        while (itemIterator.hasNext()) {
            log.info(itemIterator.next().toString());
        }
    }

    private static TreeNode<Integer> buildIntegerBst() {
        var root = new TreeNode<>(8);
        root.insert(3);
        root.insert(10);
        root.insert(1);
        root.insert(6);
        root.insert(14);
        root.insert(4);
        root.insert(7);
        root.insert(13);
        return root;
    }

    private static void demonstrateBstIterator() {
        log.info("------------------------");
        log.info("BST Iterator: ");
        var root = buildIntegerBst();
        var bstIterator = new BstIterator<>(root);
        while (bstIterator.hasNext()) {
            log.info("Next node: " + bstIterator.next().getVal());
        }
    }

    public static void main(String[] args) {
        demonstrateTreasureChestIteratorForType(ItemType.RING);
        demonstrateTreasureChestIteratorForType(ItemType.POTION);
        demonstrateTreasureChestIteratorForType(ItemType.WEAPON);
        demonstrateTreasureChestIteratorForType(ItemType.ANY);

        demonstrateBstIterator();
    }

    enum ItemType {
        ANY,
        POTION,
        RING,
        WEAPON
    }

    @Getter
    @Setter
    @AllArgsConstructor
    static class Item {
        private ItemType type;
        private String name;

        @Override
        public String toString() {
            return name;
        }
    }

    @Getter
    @Setter
    static class TreasureChest {
        private List<Item> items;

        public TreasureChest() {
            items = List.of(
                    new Item(ItemType.POTION, "Potion of courage"),
                    new Item(ItemType.RING, "Ring of shadows"),
                    new Item(ItemType.POTION, "Potion of wisdom"),
                    new Item(ItemType.POTION, "Potion of blood"),
                    new Item(ItemType.WEAPON, "Sword of silver +1"),
                    new Item(ItemType.POTION, "Potion of rust"),
                    new Item(ItemType.POTION, "Potion of healing"),
                    new Item(ItemType.RING, "Ring of armor"),
                    new Item(ItemType.WEAPON, "Steel halberd"),
                    new Item(ItemType.WEAPON, "Dagger of poison")
            );
        }

        public Iterator<Item> iterator(ItemType itemType) {
            return new TreasureChestItemIterator(this, itemType);
        }
    }

    interface Iterator<T> {
        boolean hasNext();

        T next();
    }

    @Getter
    @Setter
    static class TreasureChestItemIterator implements Iterator<Item> {
        private TreasureChest chest;
        private int idx;
        private ItemType type;

        public TreasureChestItemIterator(TreasureChest chest, ItemType type) {
            this.chest = chest;
            this.type = type;
            this.idx = -1;
        }

        private int findNextIdx() {
            var items = chest.getItems();
            var tempIdx = idx;
            while (true) {
                tempIdx++;
                if (tempIdx >= items.size()) {
                    tempIdx = -1;
                    break;
                }
                if (type.equals(ItemType.ANY) || items.get(tempIdx).getType().equals(type)) {
                    break;
                }
            }
            return tempIdx;
        }

        @Override
        public boolean hasNext() {
            return findNextIdx() != -1;
        }

        @Override
        public Item next() {
            idx = findNextIdx();
            if (idx != -1) {
                return chest.getItems().get(idx);
            }
            return null;
        }

    }

    @Getter
    @Setter
    static class TreeNode<T extends Comparable<T>> {
        private TreeNode<T> left;
        private TreeNode<T> right;
        private T val;

        public TreeNode(T val) {
            this.val = val;
            this.left = null;
            this.right = null;
        }

        private T getPatentNodeOfValue(T valToinsrt) {
            return null;
        }

        private TreeNode<T> getParentNodeOfValueToBeInserted(T valToinsert) {
            TreeNode<T> parent = null;
            var curr = this;
            while (curr != null) {
                parent = curr;
                curr = curr.traverseOneLevelDown(valToinsert);
            }
            return parent;
        }

        private TreeNode<T> traverseOneLevelDown(T valToinsert) {
            if (this.isGreaterThan(valToinsert)) {
                return this.left;
            }
            return this.right;
        }

        private boolean isGreaterThan(T value) {
            return this.val.compareTo(value) > 0;
        }

        private boolean isLessThanOrEqual(T val) {
            return this.val.compareTo(val) < 1;
        }

        @Override
        public String toString() {
            return val.toString();
        }

        public void insert(T val) {
            var parent = getParentNodeOfValueToBeInserted(val);
            parent.insertNewChild(val);
        }

        private void insertNewChild(T val) {
            if (this.isLessThanOrEqual(val)) {
                this.setRight(new TreeNode<>(val));
            } else {
                this.setLeft(new TreeNode<>(val));
            }
        }
    }

    static class BstIterator<T extends Comparable<T>> implements Iterator<TreeNode<T>> {

        private final ArrayDeque<TreeNode<T>> stack;

        public BstIterator(TreeNode<T> root) {
            stack = new ArrayDeque<>();
            pushPathToNextSmallest(root);
        }

        private void pushPathToNextSmallest(TreeNode<T> node) {
            while (node != null) {
                stack.push(node);
                node = node.getLeft();
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public TreeNode<T> next() {
            if (stack.isEmpty()) {
                throw new NoSuchElementException();
            }
            var pop = stack.pop();
            pushPathToNextSmallest(pop.getRight());
            return pop;
        }
    }
}
