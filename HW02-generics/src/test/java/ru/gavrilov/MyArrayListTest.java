package ru.gavrilov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("MyArrayList ")
class MyArrayListTest {

    private List<String> list;

    @BeforeEach
    public void setUpListWith100Elements() {
        list = new MyArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            list.add("i: " + i);
        }
        assertEquals(100, list.size());
    }

    @Test
    @DisplayName("method Collections::addAll should work")
    void testMethodCollectionsAddAll() {
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
    void testMethodCollectionsCopy() {
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
    void testMethodCollectionsSort() {
        int testSize = 10_000;
        List<Integer> myList = new MyArrayList<>();
        for (int i = testSize - 1; i >= 0; i--) {
            myList.add((int) (Math.random() * 1000));
        }

        Collections.sort(myList, Comparator.comparingInt(Integer::intValue));

        assertEquals(testSize, myList.size());
    }

    @Test
    @DisplayName("should added elements")
    void whenAddedElementsThenListSizeIsCorrect() {
        List<Integer> myList = new MyArrayList<>();

        int testSize = 10_000;

        for (int i = 0; i < testSize; i++) {
            myList.add(i);
        }

        assertEquals(testSize, myList.size());
    }

    @Test
    @DisplayName("should add elements by index")
    void testAddByIndex() {
        list.add(0, "add_element");
        list.add(0, "add_element2");
        list.add(0, "add_element3");

        assertEquals(103, list.size());
        assertEquals(list.get(0), "add_element3");
        assertEquals(list.get(1), "add_element2");
        assertEquals(list.get(2), "add_element");
        assertEquals(list.get(3), "i: 0");
    }

    @Test
    @DisplayName("size should equals zero")
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
        assertArrayEquals(list.toArray(), actualArr2);
    }

    @Test
    @DisplayName("when called iterator::remove should removed object")
    void whenIteratorRemoveThenSizeDecreaseByOne() {
        Iterator<String> iter = list.iterator();
        while (iter.hasNext()) {
            if (iter.next().equals("i: 50")) {
                iter.remove();
            }
        }
        assertFalse(list.contains("i: 50"));
        assertEquals(99, list.size());
    }

    @Test
    @DisplayName("should removed by object")
    void whenRemoveByObjectThenRemovedElement() {
        list.remove("i: 99");
        assertEquals(99, list.size());
        assertFalse(list.contains("i: 99"));

        list.remove("i: 101");
        assertEquals(99, list.size());
        assertFalse(list.contains("i: 101"));
    }

    @Test
    @DisplayName("when add all elements from other collections then myList grow up")
    void whenAddAllElementsFromOtherCollectionsThenMyListSizeEqualsOldSizePlusSizeOtherList() {
        var list2 = new MyArrayList<String>(30);
        for (int i = 100; i < 130; i++) {
            list2.add("i: " + i);
        }
        var emptyList = new MyArrayList<String>();

        list.addAll(list2);
        list.addAll(emptyList);
        assertEquals(130, list.size());
        for (int i = 0; i < 130; i++) {
            assertTrue(list.contains("i: " + i));
        }
    }

    @Test
    @DisplayName("should add all by index")
    void whenAddAllToIndexPositionThenCorrectInserted() {
        var list2 = new MyArrayList<String>(30);
        for (int i = 100; i < 130; i++) {
            list2.add("i: " + i);
        }
        var emptyList = new MyArrayList<String>();

        assertTrue(list.addAll(0, list2));
        assertFalse(list.addAll(0, emptyList));

        assertEquals(130, list.size());

        for (int i = 0; i < 30; i++) {
            assertEquals("i: " + (i + 100), list.get(i));
        }
        for (int i = 30; i < 130; i++) {
            assertEquals("i: " + (i - 30), list.get(i));
        }
    }

    @Test
    @DisplayName("when my list contains all elements from another list then returned true")
    void whenContainsAllElementsThenReturnedTrue() {
        var list2 = new ArrayList<String>(30);
        for (int i = 0; i < 50; i++) {
            list2.add("i: " + i);
        }

        boolean actual = list.containsAll(list2);
        assertTrue(actual);
    }

    @Test
    @DisplayName("when my list contains NOT all elements from another list then returned false")
    void whenContainsNotAllElementsThenReturnedFalse() {
        var list2 = new MyArrayList<String>(30);
        for (int i = 0; i < 50; i++) {
            list2.add("i: " + i);
        }
        list2.add("i: " + 100);

        boolean actual = list.containsAll(list2);
        assertFalse(actual);
    }

    @Test
    @DisplayName("should remove all elements")
    void whenRemoveAllElementsThenReturnedFalse() {
        var list2 = new MyArrayList<String>(30);
        for (int i = 0; i < 50; i++) {
            list2.add("i: " + i);
        }

        boolean actual = list.removeAll(list2);
        assertTrue(actual);
        assertEquals(50, list.size());
        for (int i = 0; i < 50; i++) {
            assertFalse(list.contains("i: " + i));
        }
    }

    @Test
    @DisplayName("should retain all")
    void whenRetainAllElementsThenReturnedFalse() {
        var list2 = new MyArrayList<String>(30);
        for (int i = 0; i < 50; i++) {
            list2.add("i: " + i);
        }

        boolean actual = list.retainAll(list2);
        assertTrue(actual);
        assertEquals(50, list.size());

        for (int i = 0; i < 50; i++) {
            assertTrue(list.contains("i: " + i));
        }
        for (int i = 50; i < 100; i++) {
            assertFalse(list.contains("i: " + i));//removed
        }
    }

    @Test
    @DisplayName("when called clear then list will be empty")
    void whenClearThenEmptyList() {
        list.clear();

        assertEquals(0, list.size());
    }

    @Test
    @DisplayName("should return the index of the last occurrence elements when called lastIndexOf")
    void whenLastIndexOfThen() {
        list.set(45, "i: 55");
        list.set(55, "i: 55");

        int actual = list.lastIndexOf("i: 55");
        assertEquals(55, actual);
    }

    @Test
    @DisplayName("should return index elements when called indexOf")
    void whenIndexOffThen() {
        list.set(45, "i: 55");
        list.set(55, "i: 55");

        int actual = list.indexOf("i: 55");
        assertEquals(45, actual);
    }

}