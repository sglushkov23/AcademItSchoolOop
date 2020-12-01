package ru.academits.java.glushkov.array_list;

import java.util.*;

public class MyArrayList<T> implements List<T> {
    private T[] items;
    private int length;
    private int modCount = 0;

    public MyArrayList() {
        //noinspection unchecked
        items = (T[]) new Object[10];
    }

    public MyArrayList(int initialCapacity) {
        //noinspection unchecked
        items = (T[]) new Object[initialCapacity];
    }

    public MyArrayList(Collection<? extends T> c) {
        if (c == null) {
            throw new NullPointerException("Collection argument c must be not null");
        }

        //noinspection unchecked
        items = (T[]) c.toArray();
        length += c.size();
    }

    private void increaseCapacity() {
        items = Arrays.copyOf(items, items.length * 2);
    }

    public void ensureCapacity(int minCapacity) {
        if (items.length < minCapacity) {
            items = Arrays.copyOf(items, minCapacity);
        }
    }

    public void trimToSize() {
        if (items.length > length) {
            items = Arrays.copyOf(items, length);
        }
    }

    @Override
    public int size() {
        return length;
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < length; i++) {
            if (Objects.equals(o, items[i])) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyArrayListIterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(items, length);
    }

    @Override
    public <T1> T1[] toArray(T1[] array) {
        if (array == null) {
            throw new NullPointerException("The array argument must be not null");
        }

        if (array.length < length) {
            //noinspection unchecked
            return (T1[]) Arrays.copyOf(items, length, array.getClass());
        }

        System.arraycopy(items, 0, array, 0, length);

        return array;
    }

    @Override
    public boolean add(T t) {
        if (length >= items.length) {
            increaseCapacity();
        }

        items[length] = t;
        length++;
        modCount++;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);

        if (index != -1) {
            remove(index);

            return true;
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException("Collection argument c must be not null");
        }

        for (Object elem : c) {
            if (!contains(elem)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (c == null) {
            throw new NullPointerException("Collection argument c must be not null");
        }

        int collectionSize = c.size();

        if (collectionSize == 0) {
            return false;
        }

        ensureCapacity(length + collectionSize);
        //noinspection unchecked
        T[] collectionArray = c.toArray((T[]) new Object[collectionSize]);
        System.arraycopy(collectionArray, 0, items, length, collectionSize);
        length += collectionSize;
        modCount++;

        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (c == null) {
            throw new NullPointerException("Collection argument c must be not null");
        }

        int collectionSize = c.size();

        if (collectionSize == 0) {
            return false;
        }

        if (index < 0 || index > length) {
            throw new IndexOutOfBoundsException("Index must be non-negative and not bigger than list size: index = " + index);
        }

        ensureCapacity(length + collectionSize);
        //noinspection unchecked
        T[] collectionArray = c.toArray((T[]) new Object[collectionSize]);

        if (index < length) {
            System.arraycopy(items, index, items, index + collectionSize, length - index);
        }

        System.arraycopy(collectionArray, 0, items, index, collectionSize);
        length += collectionSize;
        modCount++;

        return true;
    }

    private boolean removeOrRetainAll(Collection<?> c, boolean isRemoving) {
        if (c == null) {
            throw new NullPointerException("Collection argument c must be not null");
        }

        int startLength = length;

        for (int i = startLength - 1; i >= 0; i--) {
            if (c.contains(items[i]) == isRemoving) {
                remove(i);
            }
        }

        if (startLength != length) {
            trimToSize();

            return true;
        }

        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return removeOrRetainAll(c, true);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return removeOrRetainAll(c, false);
    }

    @Override
    public void clear() {
        //noinspection unchecked
        items = (T[]) new Object[0];
        length = 0;
        modCount++;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Index must be non-negative and less than list size: index = " + index);
        }

        return items[index];
    }

    @Override
    public T set(int index, T element) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Index must be non-negative and less than list size: index = " + index);
        }

        T previousElement = items[index];
        items[index] = element;

        return previousElement;
    }

    @Override
    public void add(int index, T element) {
        if (index < 0 || index > length) {
            throw new IndexOutOfBoundsException("Index must be non-negative and not bigger than list size: index = " + index);
        }

        if (length >= items.length) {
            increaseCapacity();
        }

        if (index < length) {
            System.arraycopy(items, index, items, index + 1, length - index);
        }

        items[index] = element;
        length++;
        modCount++;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Index must be non-negative and less than list size: index = " + index);
        }

        T elementToRemove = items[index];

        if (index < length - 1) {
            System.arraycopy(items, index + 1, items, index, length - index - 1);
        }

        length--;
        modCount++;

        return elementToRemove;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < length; i++) {
            if (Objects.equals(o, items[i])) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = length - 1; i >= 0; i--) {
            if (Objects.equals(o, items[i])) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException("MyArrayList class does not support listIterator method");
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException("MyArrayList class does not support listIterator method");
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("MyArrayList class does not support subList method");
    }

    @Override
    public String toString() {
        StringBuilder listString = new StringBuilder("[");

        for (int i = 0; i < length; i++) {
            listString.append(items[i]).append(", ");
        }

        if (listString.length() >= 2) {
            listString.delete(listString.length() - 2, listString.length());
        }

        listString.append("]");

        return listString.toString();
    }

    private class MyArrayListIterator implements Iterator<T> {
        private int currentIndex = -1;
        private final int expectedModCount = modCount;

        @Override
        public boolean hasNext() {
            return currentIndex + 1 < length;
        }

        @Override
        public T next() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException("List has been changed during iteration");
            }

            currentIndex++;

            if (currentIndex >= length) {
                throw new NoSuchElementException();
            }

            return items[currentIndex];
        }
    }
}
