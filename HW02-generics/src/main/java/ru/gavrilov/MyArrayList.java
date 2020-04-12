package ru.gavrilov;

import java.util.*;

public class MyArrayList<E> implements List<E> {

    private static final int INITIAL_SIZE = 10;

    private Object[] data;

    private int size;

    public MyArrayList() {
        this.data = new Object[INITIAL_SIZE];
    }

    public MyArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException();
        }

        this.data = new Object[capacity];
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
        return this.indexOf(o) >= 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {

            private int index = 0;
            private int idxReturnedElm = -1;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public E next() {
                if (hasNext()) {
                    idxReturnedElm = index++;
                    return (E) data[idxReturnedElm];
                }
                throw new NoSuchElementException();
            }

            @Override
            public void remove() {
                if (idxReturnedElm < 0) {
                    throw new IllegalStateException("remove");
                }
                MyArrayList.this.remove(idxReturnedElm);
                index = idxReturnedElm;
                idxReturnedElm = -1;
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size];
        System.arraycopy(data, 0, arr, 0, size);
        return arr;
    }

    @Override
    public <T> T[] toArray(T[] arr) {
        Objects.requireNonNull(arr);

        if (arr.length < size) {
            return (T[]) toArray();
        }

        System.arraycopy(data, 0, arr, 0, size);

        if (arr.length > size) {
            for (int i = size; i < arr.length; i++) {
                arr[i] = null;
            }
        }
        return arr;
    }

    @Override
    public boolean add(E e) {
        if (size == data.length) {
            int newCapacity = size * 2;
            if (newCapacity < 0) { // > Integer.MAX_VALUE
                throw new OutOfMemoryError();
            }
            Object[] newData = new Object[newCapacity];
            System.arraycopy(data, 0, newData, 0, size);
            data = null; // will GC be happy?
            data = newData;
        }

        data[size++] = e;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(data[i], o)) {
                this.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object obj: collection) {
            if (!this.contains(obj)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        Objects.requireNonNull(collection);
        if (collection.isEmpty()) {
            return false;
        }

        for (E obj : collection) {
            this.add(obj);
        }
        return true;
    }

    @Override
    public boolean addAll(int i, Collection<? extends E> collection) {
        rangeCheck(i);
        Objects.requireNonNull(collection);
        if (collection.isEmpty()) {
            return false;
        }

        int colSize = collection.size();
        Object[] newArr = new Object[size * 2 + colSize];
        System.arraycopy(data, 0, newArr, 0, i); //old data copy from 0 to `i`
        System.arraycopy(data, i, newArr, i + colSize, colSize); //old data copy from `i` to `size`

        int idx = i;
        for(E obj : collection) {
            newArr[idx++] = obj;
        }

        data = null;
        data = newArr;
        size += colSize;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean result = false;
        for (Object obj: collection) {
            if (this.remove(obj)) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        boolean result = false;

        Iterator<E> iter = this.iterator();
        while (iter.hasNext()) {
            E elem = iter.next();
            if (!collection.contains(elem)) {
                iter.remove();
                result = true;
            }
        }
        return result;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            data[i] = null;
        }
        size = 0;
    }

    @Override
    public E get(int index) {
        rangeCheck(index);
        return (E) data[index];
    }

    @Override
    public E set(int index, E element) {
        rangeCheck(index);
        data[index] = element;
        return element;
    }

    @Override
    public void add(int index, E element) {
        rangeCheck(index);
        Object[] newArr = this.data;
        if (size >= data.length) {
            newArr = new Object[size * 2];
            System.arraycopy(data, 0, newArr, 0, index);
        }
        System.arraycopy(data, index, newArr, index + 1, size - index); //shift to the right
        newArr[index] = element;
        data = newArr;
        size++;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);

        Object elm = data[index];

        int last = size - 1;
        if (index != last) {
            System.arraycopy(data, index + 1, data, index, size - (index + 1)); // if not last
        }
        data[last] = null; // if last
        size--;
        return (E) elm;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(o, data[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (Objects.equals(o, data[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("sub list unsupported");
    }

    private void rangeCheck(int i) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }
    }
}
