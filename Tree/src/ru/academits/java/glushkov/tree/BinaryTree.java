package ru.academits.java.glushkov.tree;

import java.util.*;
import java.util.function.Consumer;

public class BinaryTree<T> {
    private TreeNode<T> root;
    private int size;
    private final Comparator<? super T> comparator;

    public BinaryTree(Comparator<? super T> comparator) {
        this.comparator = comparator;
    }

    public BinaryTree(Collection<? extends T> c) {
        this(c, null);
    }

    public BinaryTree(Collection<? extends T> c, Comparator<? super T> comparator) {
        this(comparator);

        if (c == null) {
            throw new NullPointerException("Collection argument c must be not null");
        }

        for (T e : c) {
            add(e);
        }

        size = c.size();
    }

    public int getSize() {
        return size;
    }

    public void add(T data) {
        if (root == null) {
            root = new TreeNode<>(data);
            size = 1;

            return;
        }

        TreeNode<T> currentNode = root;

        while (true) {
            int comparingResult = compare(data, currentNode.getData());

            if (comparingResult < 0) {
                if (currentNode.getLeftChild() != null) {
                    currentNode = currentNode.getLeftChild();
                } else {
                    currentNode.setLeftChild(new TreeNode<>(data));
                    break;
                }
            } else {
                if (currentNode.getRightChild() != null) {
                    currentNode = currentNode.getRightChild();
                } else {
                    currentNode.setRightChild(new TreeNode<>(data));
                    break;
                }
            }
        }

        size++;
    }

    public boolean contains(T data) {
        return getNodeAndParentByData(data) != null;
    }

    public boolean remove(T data) {
        TreeNode<T>[] nodeToRemoveAndParent = getNodeAndParentByData(data);

        if (nodeToRemoveAndParent == null) {
            return false;
        }

        TreeNode<T> nodeToRemove = nodeToRemoveAndParent[0];
        TreeNode<T> parentNode = nodeToRemoveAndParent[1];
        TreeNode<T> nodeToRemoveLeftChild = nodeToRemove.getLeftChild();
        TreeNode<T> nodeToRemoveRightChild = nodeToRemove.getRightChild();
        TreeNode<T> nodeToSet;

        TreeNode<T>[] rightSubtreeMinNodeAndParent;
        TreeNode<T> rightSubtreeMinNode = null;
        TreeNode<T> rightSubtreeMinNodeParent = null;

        if (nodeToRemoveLeftChild == null && nodeToRemoveRightChild == null) {
            nodeToSet = null;
        } else if (nodeToRemoveLeftChild != null && nodeToRemoveRightChild == null) {
            nodeToSet = nodeToRemoveLeftChild;
        } else if (nodeToRemoveLeftChild == null) {
            nodeToSet = nodeToRemoveRightChild;
        } else {
            rightSubtreeMinNodeAndParent = getRightSubtreeMinNodeAndParent(nodeToRemove);
            rightSubtreeMinNode = rightSubtreeMinNodeAndParent[0];
            rightSubtreeMinNodeParent = rightSubtreeMinNodeAndParent[1];

            nodeToSet = rightSubtreeMinNode;
        }

        if (nodeToRemove == root) {
            root = nodeToSet;
        } else {
            if (isLeftChild(nodeToRemove, parentNode)) {
                parentNode.setLeftChild(nodeToSet);
            } else {
                parentNode.setRightChild(nodeToSet);
            }
        }

        if (nodeToRemoveLeftChild != null && nodeToRemoveRightChild != null) {
            rightSubtreeMinNode.setLeftChild(nodeToRemoveLeftChild);

            if (rightSubtreeMinNode != nodeToRemoveRightChild) {
                rightSubtreeMinNodeParent.setLeftChild(rightSubtreeMinNode.getRightChild());
                rightSubtreeMinNode.setRightChild(nodeToRemoveRightChild);
            }
        }

        size--;

        return true;
    }

    public void walkInBreadth(Consumer<? super T> action) {
        if (size == 0) {
            return;
        }

        Queue<TreeNode<T>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode<T> currentNode = queue.remove();
            action.accept(currentNode.getData());

            if (currentNode.getLeftChild() != null) {
                queue.add(currentNode.getLeftChild());
            }

            if (currentNode.getRightChild() != null) {
                queue.add(currentNode.getRightChild());
            }
        }
    }

    public void walkInDepth(Consumer<? super T> action) {
        if (size == 0) {
            return;
        }

        ArrayList<TreeNode<T>> stack = new ArrayList<>();
        stack.add(root);

        while (!stack.isEmpty()) {
            TreeNode<T> currentNode = stack.remove(stack.size() - 1);
            action.accept(currentNode.getData());

            if (currentNode.getRightChild() != null) {
                stack.add(currentNode.getRightChild());
            }

            if (currentNode.getLeftChild() != null) {
                stack.add(currentNode.getLeftChild());
            }
        }
    }

    public void walkInDepthRecursive(Consumer<? super T> action) {
        if (size == 0) {
            return;
        }

        visit(root, action);
    }

    private void visit(TreeNode<T> node, Consumer<? super T> action) {
        action.accept(node.getData());

        if (node.getLeftChild() != null) {
            visit(node.getLeftChild(), action);
        }

        if (node.getRightChild() != null) {
            visit(node.getRightChild(), action);
        }
    }

    private TreeNode<T>[] getNodeAndParentByData(T data) {
        if (root == null) {
            return null;
        }

        TreeNode<T> parentNode = null;
        TreeNode<T> currentNode = root;

        while (true) {
            int comparingResult = compare(data, currentNode.getData());

            if (comparingResult == 0) {
                //noinspection unchecked
                return (TreeNode<T>[]) new TreeNode<?>[]{currentNode, parentNode};
            }

            if (comparingResult < 0) {
                if (currentNode.getLeftChild() != null) {
                    parentNode = currentNode;
                    currentNode = currentNode.getLeftChild();
                } else {
                    return null;
                }
            } else {
                if (currentNode.getRightChild() != null) {
                    parentNode = currentNode;
                    currentNode = currentNode.getRightChild();
                } else {
                    return null;
                }
            }
        }
    }

    private TreeNode<T>[] getRightSubtreeMinNodeAndParent(TreeNode<T> node) {
        TreeNode<T> parentNode = null;
        TreeNode<T> currentNode = node.getRightChild();

        while (currentNode.getLeftChild() != null) {
            parentNode = currentNode;
            currentNode = currentNode.getLeftChild();
        }

        //noinspection unchecked
        return (TreeNode<T>[]) new TreeNode<?>[]{currentNode, parentNode};
    }

    private boolean isLeftChild(TreeNode<T> node, TreeNode<T> parent) {
        return parent.getLeftChild() == node;
    }

    private int compare(T data1, T data2) {
        if (comparator != null) {
            return comparator.compare(data1, data2);
        }

        if (data1 == null && data2 != null) {
            return -1;
        }

        if (data1 != null && data2 == null) {
            return 1;
        }

        if (data1 == null) {
            return 0;
        }

        if (!(data1 instanceof Comparable)) {
            throw new IllegalArgumentException("If using instance of BinaryTree class without comparator class of " +
                    "data argument in methods add(T data), contains(T data) and remove(T data) must implement interface Comparable");
        }

        //noinspection unchecked
        Comparable<T> data1CastedToComparable = (Comparable<T>) data1;

        return data1CastedToComparable.compareTo(data2);
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder("[");

        walkInBreadth(p -> stringBuilder.append(p).append(", "));

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length()).append("]");

        return stringBuilder.toString();
    }
}