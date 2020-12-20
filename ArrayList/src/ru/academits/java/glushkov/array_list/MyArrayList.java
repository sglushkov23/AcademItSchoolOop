package ru.academits.java.glushkov.array_list;

import java.util.*;

public class MyArrayList<T> implements List<T> {
    private T[] items;
    private int size;
    private int modCount;
    private static final int DEFAULT_CAPACITY = 10;

    public MyArrayList() {
        //noinspection unchecked
        items = (T[]) new Object[DEFAULT_CAPACITY];
    }

    public MyArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Argument initialCapacity must be non-negative: initialCapacity = " + initialCapacity);
        }

        //noinspection unchecked
        items = (T[]) new Object[initialCapacity];
    }

    public MyArrayList(Collection<? extends T> c) {
        if (c == null) {
            throw new NullPointerException("Collection argument c must be not null");
        }

        //noinspection unchecked
        items = (T[]) c.toArray();
        size = c.size();
    }

    private void increaseCapacity() {
        items = Arrays.copyOf(items, items.length == 0 ? DEFAULT_CAPACITY : items.length * 2);
    }

    public void ensureCapacity(int minCapacity) {
        if (items.length < minCapacity) {
            items = Arrays.copyOf(items, minCapacity);
        }
    }

    public void trimToSize() {
        if (items.length > size) {
            items = Arrays.copyOf(items, size);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyArrayListIterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(items, size);
    }

    @Override
    public <T1> T1[] toArray(T1[] array) {
        if (array == null) {
            throw new NullPointerException("The array argument must be not null");
        }

        if (array.length < size) {
            //noinspection unchecked
            return (T1[]) Arrays.copyOf(items, size, array.getClass());
        }

        //noinspection SuspiciousSystemArraycopy
        System.arraycopy(items, 0, array, 0, size);

        if (array.length > size) {
            array[size] = null;
        }

        return array;
    }

    @Override
    public boolean add(T item) {
        add(size, item);

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

        for (Object item : c) {
            if (!contains(item)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return addAll(size, c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (c == null) {
            throw new NullPointerException("Collection argument c must be not null");
        }

        checkIndex(index, true);

        int collectionSize = c.size();

        if (collectionSize == 0) {
            return false;
        }

        ensureCapacity(size + collectionSize);

        if (index < size) {
            System.arraycopy(items, index, items, index + collectionSize, size - index);
        }

        int i = index;

        for (T item : c) {
            items[i] = item;
            i++;
        }

        size += collectionSize;
        modCount++;

        return true;
    }

    private boolean removeOrRetainAll(Collection<?> c, boolean isRemoving) {
        if (c == null) {
            throw new NullPointerException("Collection argument c must be not null");
        }

        int startSize = size;

        for (int i = startSize - 1; i >= 0; i--) {
            if (c.contains(items[i]) == isRemoving) {
                remove(i);
            }
        }

        return startSize != size;
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
        for (int i = 0; i < size; i++) {
            items[i] = null;
        }

        size = 0;
        modCount++;
    }

    @Override
    public T get(int index) {
        checkIndex(index, false);

        return items[index];
    }

    @Override
    public T set(int index, T item) {
        checkIndex(index, false);

        T oldItem = items[index];
        items[index] = item;

        return oldItem;
    }

    @Override
    public void add(int index, T item) {
        checkIndex(index, true);

        if (size >= items.length) {
            increaseCapacity();
        }

        if (index < size) {
            System.arraycopy(items, index, items, index + 1, size - index);
        }

        items[index] = item;
        size++;
        modCount++;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, false);

        T itemToRemove = items[index];

        if (index < size - 1) {
            System.arraycopy(items, index + 1, items, index, size - index - 1);
        }

        size--;
        items[size] = null;
        modCount++;

        return itemToRemove;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(o, items[i])) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
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
        if (isEmpty()) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder("[");

        for (int i = 0; i < size; i++) {
            stringBuilder.append(items[i]).append(", ");
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

    private class MyArrayListIterator implements Iterator<T> {
        private int currentIndex = -1;
        private final int expectedModCount = modCount;

        @Override
        public boolean hasNext() {
            return currentIndex + 1 < size;
        }

        @Override
        public T next() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException("List has been changed during iteration");
            }

            if (!hasNext()) {
                throw new NoSuchElementException(String.format("There is no item with index %d in the list", currentIndex + 1));
            }

            currentIndex++;

            return items[currentIndex];
        }
    }
}
