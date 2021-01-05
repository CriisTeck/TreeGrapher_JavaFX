package structure.tree;

import java.util.*;

public class Tree {
    private Node<Integer> radix;
    private final Comparator<Integer> comparator;

    public Tree(Comparator<Integer> comparator) {
        this.comparator = comparator;
        radix = null;
    }

    public Tree(Tree tree){
        comparator = (Integer::compareTo);
        this.radix = tree.getRadix();
    }

    public void insert(int data) {
        radix = insertBinaryTree(data, radix);
    }

    private Node<Integer> insertBinaryTree(int data, Node<Integer> node) {
        if (node == null) {
            return new Node<>(data);
        } else {
            if (comparator.compare(node.getData(), data) > 0) {
                node.setLeft(insertBinaryTree(data, node.getLeft()));
            } else if (comparator.compare(node.getData(), data) < 0)
                node.setRight(insertBinaryTree(data, node.getRight()));
            return node;
        }
    }

    public int getMaximumLevel() {
        Map<Integer, List<Integer>> map = printOrder(radix, 0, new HashMap<>());
        return map.keySet().stream().reduce(Math::max).get() + 1;
    }

    public Map<Integer, List<Integer>> getDataByLevel(){
        return printOrder(radix, 0, new HashMap<>());
    }

    private Map<Integer, List<Integer>> printOrder(Node<Integer> root, Integer level, Map<Integer, List<Integer>> map) {
        if (root == null) {
            return map;
        }
        if (map.containsKey(level))
            map.get(level).add(root.getData());
        else {
            map.put(level, new ArrayList<>(Collections.singletonList(root.getData())));
        }
        if (root.getLeft() != null) {
            map = printOrder(root.getLeft(), level + 1, map);
        }
        if (root.getRight() != null) {
            map = printOrder(root.getRight(), level + 1, map);
        }

        return map;
    }

    public boolean exist(int data) {
        if (!isEmpty())
            return existBinaryTree(data, radix);
        else
            return false;
    }

    private boolean existBinaryTree(int data, Node<Integer> node) {
        if (comparator.compare(node.getData(),data) == 0) {
            return true;
        } else {
            boolean state = false;
            if (node.getLeft() != null) {
                state = existBinaryTree(data, node.getLeft());
            }
            if(state){
                return true;
            }
            if (node.getRight() != null) {
                state = existBinaryTree(data, node.getRight());
            }
            return state;
        }
    }

    public boolean isEmpty() {
        return radix == null;
    }

    public Node<Integer> getRadix() {
        return radix;
    }

    public Node<Integer> getLeft() {
        return radix.getLeft();
    }

    public Node<Integer> getRight() {
        return radix.getRight();
    }
}
