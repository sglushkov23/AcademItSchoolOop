package ru.academits.java.glushkov.hash_table;

import ru.academits.java.glushkov.array_list.MyArrayList;

import java.util.*;

public class HashTable<T> implements Collection<T> {
    private MyArrayList<T>[] array;
    private int size;
    private int modCount;
    private static final int DEFAULT_ARRAY_SIZE = 10;

    public HashTable() {
        this(DEFAULT_ARRAY_SIZE);
    }

    public HashTable(int arraySize) {
        checkArraySizeArgument(arraySize);

        //noinspection unchecked
        array = (MyArrayList<T>[]) new MyArrayList<?>[arraySize];
    }

    public HashTable(Collection<? extends T> c) {
        this(c, DEFAULT_ARRAY_SIZE);
    }

    public HashTable(Collection<? extends T> c, int arraySize) {
        if (c == null) {
            throw new NullPointerException("Collection argument c must be not null");
        }

        checkArraySizeArgument(arraySize);

        //noinspection unchecked
        array = (MyArrayList<T>[]) new MyArrayList<?>[arraySize];

        addAll(c);
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
        int index = getIndex(o);

        return array[index] != null && array[index].contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return new HashTableIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] resultArray = new Object[size];

        int i = 0;

        for (T item : this) {
            resultArray[i] = item;
            i++;
        }

        return resultArray;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a == null) {
            throw new NullPointerException("The array argument must be not null");
        }

        if (a.length < size) {
            //noinspection unchecked
            return (T1[]) Arrays.copyOf(toArray(), size, a.getClass());
        }

        int i = 0;

        for (T item : this) {
            //noinspection unchecked
            a[i] = (T1) item;
            i++;
        }

        if (a.length > size) {
            a[size] = null;
        }

        return a;
    }

    @Override
    public boolean add(T t) {
        int index = getIndex(t);

        if (array[index] == null) {
            array[index] = new MyArrayList<>();
        }

        array[index].add(t);
        size++;
        modCount++;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = getIndex(o);

        if (array[index] == null) {
            return false;
        }

        boolean isRemoved = array[index].remove(o);

        if (isRemoved) {
            size--;
            modCount++;

            if (array[index].size() == 0) {
                array[index] = null;
            }
        }

        return isRemoved;
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
        if (c == null) {
            throw new NullPointerException("Collection argument c must be not null");
        }

        if (c.size() == 0) {
            return false;
        }

        for (T item : c) {
            add(item);
        }

        return true;
    }

    private boolean removeOrRetainAll(Collection<?> c, boolean isRemoving) {
        if (c == null) {
            throw new NullPointerException("Collection argument c must be not null");
        }

        int startSize = size;

        for (int i = 0; i < array.length; i++) {
            MyArrayList<T> currentList = array[i];

            if (currentList != null) {
                int startListSize = currentList.size();

                if (isRemoving) {
                    //noinspection SuspiciousMethodCalls
                    currentList.removeAll(c);
                } else {
                    //noinspection SuspiciousMethodCalls
                    currentList.retainAll(c);
                }

                int endListSize = currentList.size();
                size -= startListSize - endListSize;

                if (endListSize == 0) {
                    array[i] = null;
                }
            }
        }

        boolean isChanged = startSize != size;

        if (isChanged) {
            modCount++;
        }

        return isChanged;
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
        Arrays.fill(array, null);
        size = 0;
        modCount++;
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }

    private int getIndex(Object o) {
        return Math.abs(o == null ? 0 : o.hashCode() % array.length);
    }

    public void rebuild(int arraySize) {
        checkArraySizeArgument(arraySize);

        //noinspection unchecked
        T[] hashTableArray = (T[]) toArray();

        //noinspection unchecked
        array = (MyArrayList<T>[]) new MyArrayList<?>[arraySize];

        addAll(Arrays.asList(hashTableArray));
    }

    private void checkArraySizeArgument(int arraySize) {
        if (arraySize <= 0) {
            throw new IllegalArgumentException("Argument arraySize must be positive integer: arraySize = " + arraySize);
        }
    }

    private class HashTableIterator implements Iterator<T> {
        private int currentListIndex = -1;
        private int count = 0;
        private Iterator<T> listIterator;
        private final int expectedModCount = modCount;

        @Override
        public boolean hasNext() {
            return count < size;
        }

        @Override
        public T next() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException("HashTable has been changed during iteration");
            }

            if (!hasNext()) {
                throw new NoSuchElementException("There are no more items in HashTable: HashTable size = " + size);
            }

            if (listIterator == null || !listIterator.hasNext()) {
                for (int i = currentListIndex + 1; i < array.length; i++) {
                    if (array[i] != null) {
                        currentListIndex = i;
                        listIterator = array[currentListIndex].iterator();
                        break;
                    }
                }
            }

            if (listIterator == null) {
                throw new NullPointerException("listIterator is null");
            }

            count++;

            return listIterator.next();
        }
    }
}