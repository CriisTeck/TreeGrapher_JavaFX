package models;

import structure.tree.Tree;

public class IntegerTree {
    private Tree tree;

    public IntegerTree() {
        tree = new Tree(Integer::compareTo);
    }

    public void insert(int data){
        tree.insert(data);
    }

    public Tree getTree(){
        return tree;
    }

    public void clearTree() {
        tree = new Tree(Integer::compareTo);
    }
}
