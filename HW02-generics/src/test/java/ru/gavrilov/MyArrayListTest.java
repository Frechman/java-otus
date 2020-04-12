package ru.gavrilov;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MyArrayListTest {

    @Test
    @DisplayName("should added elements")
    void testAdded() {
        List<Integer> myList = new MyArrayList<>();

        int testSize = 10_000;

        for (int i = 0; i < testSize; i++) {
            myList.add(i);
        }

        assertEquals(testSize, myList.size());
    }

    @Test
    @DisplayName("should work method Collections::addAll")
    void testAddAll() {
        List<Integer> myList = new MyArrayList<>();

        int testSize = 10_000;
        Integer[] libList = new Integer[testSize];
        for (int i = 0; i < testSize; i++) {
            libList[i] = i << 1;
        }

        boolean isInserted = Collections.addAll(myList, libList);
        assertTrue(isInserted);

        assertEquals(testSize, myList.size());
        assertArrayEquals(libList, myList.toArray());
    }

    @Test
    @DisplayName("should work method Collections::copy")
    void testCopy() {
        int testSize = 10_000;

        List<String> myList = new MyArrayList<>(testSize);
        List<String> srcList = new ArrayList<>(testSize);

        for (int i = 0; i < testSize; i++) {
            myList.add("my list elm: " + i);
            srcList.add("arrayList elm :" + i);
        }

        Collections.copy(myList, srcList);

        assertEquals(testSize, myList.size());
    }

    @Test
    @DisplayName("should work method Collections::sort")
    void testSort() {

        int testSize = 10_000;
        List<Integer> myList = new MyArrayList<>();
        for (int i = testSize - 1; i >= 0; i--) {
            myList.add((int) (Math.random() * 1000));
        }

        Collections.sort(myList, Comparator.comparingInt(Integer::intValue));

        assertEquals(testSize, myList.size());
    }
}