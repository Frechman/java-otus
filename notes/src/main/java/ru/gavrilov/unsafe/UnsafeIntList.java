package ru.gavrilov.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author gavrilov-sv
 * created on 23.04.2020
 */
public class UnsafeIntList implements AutoCloseable {

    private static final Unsafe unsafe = getUnsafe();

    private static final int ELEMENTS_SIZE_BYTE = Integer.BYTES; // 32 / 8

    private final static int DEFAULT_CAPACITY = 10 * ELEMENTS_SIZE_BYTE;

    private long baseAddressArray;

    private int capacity;
    private int size;

    private static Unsafe getUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe"); // or getDeclaredConstructor; constructor.newInstance();
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UnsafeIntList() {
        this(DEFAULT_CAPACITY);
    }

    public UnsafeIntList(int size) {
        if (unsafe == null) {
            throw new IllegalStateException("not declared Unsafe");
        }
        this.capacity = Math.max(size, ELEMENTS_SIZE_BYTE);

        this.baseAddressArray = unsafe.allocateMemory(capacity * ELEMENTS_SIZE_BYTE);
    }

    public void add(int value) {
        if (size >= capacity) {
            capacity *= 2;
            baseAddressArray = unsafe.reallocateMemory(baseAddressArray, capacity * ELEMENTS_SIZE_BYTE);
        }

        unsafe.putInt(this.getAddressForElement(size), value);
        size++;
    }

    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return unsafe.getInt(this.getAddressForElement(index));
    }

    public void clear() {
        size = 0;
    }

    private long getAddressForElement(int offset) {
        return baseAddressArray + offset * ELEMENTS_SIZE_BYTE;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void close() {
        unsafe.freeMemory(this.baseAddressArray);
    }
}
