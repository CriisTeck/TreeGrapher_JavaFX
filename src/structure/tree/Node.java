package structure.tree;

public class Node<T> {
    private Node<T> right;
    private Node<T> left;
    private final T data;

    public Node(T data) {
        right = null;
        left = null;
        this.data = data;
    }

    public Node<T> getRight() {
        return right;
    }

    public Node<T> getLeft() {
        return left;
    }

    public T getData() {
        return data;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }
}
