package ru.academits.java.glushkov.list;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SinglyLinkedList<T> {
    private ListItem<T> head;
    private int size;

    public SinglyLinkedList() {
    }

    public SinglyLinkedList(Collection<? extends T> c) {
        if (c == null) {
            throw new NullPointerException("Collection argument c must be not null");
        }

        if (c.size() == 0) {
            return;
        }

        ListItem<T> item = null;
        ListItem<T> nextItem;
        boolean isFirstItem = true;

        for (T e : c) {
            nextItem = new ListItem<>(e);

            if (isFirstItem) {
                head = nextItem;
                item = nextItem;
                isFirstItem = false;
                continue;
            }

            item.setNext(nextItem);
            item = item.getNext();
        }

        size = c.size();
    }

    public int getSize() {
        return size;
    }

    private ListItem<T> getItem(int index) {
        checkIndex(index, false);

        ListItem<T> item = head;

        for (int i = 0; i < index; i++) {
            item = item.getNext();
        }

        return item;
    }

    public T getFirst() {
        if (head == null) {
            throw new NoSuchElementException("List is empty");
        }

        return head.getData();
    }

    public T get(int index) {
        return getItem(index).getData();
    }

    public T set(int index, T data) {
        ListItem<T> item = getItem(index);
        T oldData = item.getData();
        item.setData(data);

        return oldData;
    }

    public T remove(int index) {
        checkIndex(index, false);

        if (index == 0) {
            return removeFirst();
        }

        ListItem<T> previousItem = getItem(index - 1);
        ListItem<T> currentItem = previousItem.getNext();

        T removedData = currentItem.getData();

        previousItem.setNext(currentItem.getNext());
        size--;

        return removedData;
    }

    public void insertFirst(T data) {
        head = new ListItem<>(data, head);
        size++;
    }

    public void insert(int index, T data) {
        checkIndex(index, true);

        if (index == 0) {
            insertFirst(data);

            return;
        }

        ListItem<T> previousItem = getItem(index - 1);
        ListItem<T> newItem = new ListItem<>(data, previousItem.getNext());
        previousItem.setNext(newItem);
        size++;
    }

    public boolean removeByData(T data) {
        for (ListItem<T> currentItem = head, previousItem = null; currentItem != null; previousItem = currentItem, currentItem = currentItem.getNext()) {
            if (Objects.equals(data, currentItem.getData())) {
                if (previousItem == null) {
                    head = currentItem.getNext();
                } else {
                    previousItem.setNext(currentItem.getNext());
                }

                size--;

                return true;
            }
        }

        return false;
    }

    public T removeFirst() {
        if (head == null) {
            throw new NoSuchElementException("List is empty");
        }

        T data = head.getData();
        head = head.getNext();
        size--;

        return data;
    }

    public void reverse() {
        if (size == 0 || size == 1) {
            return;
        }

        ListItem<T> previousItem = null;
        ListItem<T> currentItem = head;
        ListItem<T> nextItem = head.getNext();

        do {
            currentItem.setNext(previousItem);

            previousItem = currentItem;
            currentItem = nextItem;
            nextItem = nextItem.getNext();
        } while (nextItem != null);

        currentItem.setNext(previousItem);
        head = currentItem;
    }

    public SinglyLinkedList<T> copy() {
        if (size == 0) {
            return new SinglyLinkedList<>();
        }

        SinglyLinkedList<T> copy = new SinglyLinkedList<>();
        copy.head = new ListItem<>(head.getData());

        ListItem<T> item = copy.head;
        ListItem<T> nextItem;

        for (ListItem<T> p = head.getNext(); p != null; p = p.getNext()) {
            nextItem = new ListItem<>(p.getData());

            item.setNext(nextItem);
            item = item.getNext();
        }

        copy.size = size;

        return copy;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder("[");

        for (ListItem<T> p = head; p != null; p = p.getNext()) {
            stringBuilder.append(p.getData()).append(", ");
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length()).append("]");

        return stringBuilder.toString();
    }

    private void checkIndex(int index, boolean isAllowedEquality) {
        if (isAllowedEquality) {
            if (index < 0 || index > size) {
                throw new IndexOutOfBoundsException(String.format(
                        "Index must be non-negative and not bigger than list size: index = %d; list size = %d", index, size));
            }
        } else {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException(String.format(
                        "Index must be non-negative and less than list size: index = %d, list size = %d", index, size));
            }
        }
    }
}