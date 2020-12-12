package ru.academits.java.glushkov.list;

import java.util.Collection;
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
            nextItem = new ListItem<>(e, null);

            if (isFirstItem) {
                head = nextItem;
                item = nextItem;
                size++;
                isFirstItem = false;
                continue;
            }

            item.setNext(nextItem);
            size++;
            item = item.getNext();
        }
    }

    public int getSize() {
        return size;
    }

    private ListItem<T> getItem(int index) {
        if (head == null) {
            throw new NullPointerException("List is empty");
        }

        if (isNotValid(index)) {
            throw new IndexOutOfBoundsException(String.format(
                    "Index must be non-negative and must be less than list size (%d): index = %d", size, index));
        }

        ListItem<T> item = head;

        for (int i = 0; i < index; i++) {
            item = item.getNext();
        }

        return item;
    }

    public T getFirstItemData() {
        if (head == null) {
            throw new NullPointerException("List is empty");
        }

        return head.getData();
    }

    public T getItemDataAt(int index) {
        return getItem(index).getData();
    }

    public T setItemDataAt(int index, T data) {
        ListItem<T> item = getItem(index);
        T oldData = item.getData();
        item.setData(data);

        return oldData;
    }

    public T remove(int index) {
        if (isNotValid(index)) {
            throw new IndexOutOfBoundsException(String.format(
                    "Index must be non-negative and must be less than list size (%d): index = %d", size, index));
        }

        if (index == 0) {
            return removeFirstItem();
        }

        ListItem<T> previousItem = getItem(index - 1);
        ListItem<T> currentItem = previousItem.getNext();

        T removedData = currentItem.getData();

        previousItem.setNext(currentItem.getNext());
        size--;

        return removedData;
    }

    public void insertAtBeginning(T data) {
        head = new ListItem<>(data, head);
        size++;
    }

    public void insertAt(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(String.format(
                    "Index must be non-negative and must be not bigger than list size (%d): index = %d", size, index));
        }

        if (index == 0) {
            insertAtBeginning(data);

            return;
        }

        ListItem<T> previousItem = getItem(index - 1);
        ListItem<T> newItem = new ListItem<>(data, previousItem.getNext());
        previousItem.setNext(newItem);
        size++;
    }

    public boolean removeItemByData(T data) {
        for (ListItem<T> p = head, prev = null; p != null; prev = p, p = p.getNext()) {
            if (Objects.equals(data, p.getData())) {
                if (prev == null) {
                    head = p.getNext();
                } else {
                    prev.setNext(p.getNext());
                }

                size--;

                return true;
            }
        }

        return false;
    }

    public T removeFirstItem() {
        if (head == null) {
            throw new NullPointerException("List is empty");
        }

        T data = head.getData();
        head = head.getNext();
        size--;

        return data;
    }

    public void reverse() {
        if (size == 0) {
            throw new UnsupportedOperationException("Reversion is supported only for non-zero length lists");
        }

        if (size == 1) {
            return;
        }

        ListItem<T> prePrev = null;
        ListItem<T> prev = head;
        ListItem<T> p = head.getNext();

        do {
            prev.setNext(prePrev);

            prePrev = prev;
            prev = p;
            p = p.getNext();
        } while (p != null);

        prev.setNext(prePrev);
        head = prev;
    }

    public SinglyLinkedList<T> copy() {
        if (size == 0) {
            return new SinglyLinkedList<>();
        }

        SinglyLinkedList<T> copy = new SinglyLinkedList<>();
        copy.head = new ListItem<>(head.getData(), null);
        copy.size++;

        ListItem<T> item = copy.head;
        ListItem<T> nextItem;

        for (ListItem<T> p = head.getNext(); p != null; p = p.getNext()) {
            nextItem = new ListItem<>(p.getData(), null);

            item.setNext(nextItem);
            copy.size++;
            item = item.getNext();
        }

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

    private boolean isNotValid(int index) {
        return index < 0 || index >= size;
    }
}