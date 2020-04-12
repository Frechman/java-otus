package ru.gavrilov;

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
    @DisplayName("method Collections::addAll should work")
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
    @DisplayName("method Collections::copy should work")
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
    @DisplayName("method Collections::sort should work")
    void testSort() {

        int testSize = 10_000;
        List<Integer> myList = new MyArrayList<>();
        for (int i = testSize - 1; i >= 0; i--) {
            myList.add((int) (Math.random() * 1000));
        }

        Collections.sort(myList, Comparator.comparingInt(Integer::intValue));

        assertEquals(testSize, myList.size());
    }

    @Test
    @DisplayName("should size equals zero")
    void whenCreateNewArrayListThenSizeEqualsZero() {
        var list = new MyArrayList<String>();
        var list2 = new MyArrayList<String>(100);

        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
        assertTrue(list2.isEmpty());
        assertEquals(0, list2.size());
    }

    @Test
    @DisplayName("should throw Exception")
    void whenCreatedNewArrayListWithCapacityLessZeroThenShouldBeException() {
        assertThrows(IllegalArgumentException.class, () -> new MyArrayList<String>(-2));
    }

    @Test
    @DisplayName("should true when contains value")
    void whenListContainsValueThenReturnedTrue() {
        var list = new MyArrayList<String>(100);
        for (int i = 0; i < 100; i++) {
            list.add("i: " + i);
        }

        assertTrue(list.contains("i: 0"));
        assertTrue(list.contains("i: 99"));
        assertTrue(list.contains("i: 55"));

        assertFalse(list.contains("i: -1"));
        assertFalse(list.contains("i: 100"));
    }

    @Test
    @DisplayName("should returned new Array from MyList")
    void whenCalledToArrayThenReturnedNewArray() {
        var list = new MyArrayList<String>(100);
        for (int i = 0; i < 100; i++) {
            list.add("i: " + i);
        }

        Object[] strArr = new String[101];
        strArr[100] = "will be null";

        Object[] actualArr = list.toArray(strArr);

        assertNotEquals(actualArr.length, list.size());
        assertEquals(101, actualArr.length);
        assertArrayEquals(strArr, actualArr);
        assertNull(actualArr[100]);


        String[] actualArr2 = list.toArray(new String[99]);
        assertEquals(list.size(), actualArr2.length);
    }

}