import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ArrayListImplTest {

    ArrayListImpl<String> myArrayList;

    @BeforeEach
    public void setUp() {
        myArrayList = new ArrayListImpl<>();
        myArrayList.add("love");
        myArrayList.add("java");
    }

    @Test
    public void add() {
        myArrayList.add("hello");
        Assertions.assertEquals(3, myArrayList.size());
        Assertions.assertEquals("hello", myArrayList.get(2));
    }

    @Test
    public void removeUsingIndex() {
        myArrayList.remove(1);
        Assertions.assertEquals(1, myArrayList.size());
    }

    @Test
    public void removeUsingValue() {
        myArrayList.remove("love");
        Assertions.assertEquals("java", myArrayList.get(0));
    }

    @Test
    public void contains() {
        Assertions.assertTrue(myArrayList.contains("love"));
    }

    @Test
    public void isEmpty() {
        Assertions.assertFalse(myArrayList.isEmpty());
    }

    @Test
    public void toArray() {
        Assertions.assertArrayEquals(new String[] {"love", "java"}, myArrayList.toArray());
    }

    @Test
    public void addAll() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("hello");
        myArrayList.addAll(arrayList);
        Assertions.assertEquals(3, myArrayList.size());
    }

    @Test
    public void addAllFromStartIndex() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("hello");
        myArrayList.addAll(0, arrayList);
        Assertions.assertEquals("hello", myArrayList.get(0));
    }

    @Test
    public void retainAll() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("love");
        arrayList.add("java");
        arrayList.add("hello");
        myArrayList.retainAll(arrayList);
        Assertions.assertEquals(2, myArrayList.size());
    }

    @Test
    public void removeAll() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("love");
        arrayList.add("java");
        arrayList.add("hello");
        myArrayList.removeAll(arrayList);
        Assertions.assertEquals(1, myArrayList.size());
    }

    @Test
    public void set() {
        myArrayList.set(1, "fun");
        Assertions.assertEquals("fun", myArrayList.get(1));
    }

    @Test
    public void clear() {
        myArrayList.clear();
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> myArrayList.get(0));
    }

    @Test
    public void lastIndexOf() {
        Assertions.assertEquals(0, myArrayList.lastIndexOf("love"));
        Assertions.assertEquals(-1, myArrayList.lastIndexOf("hello"));
    }

    @ParameterizedTest
    @MethodSource("collectionForTesting")
    public void subList(Collection<String> c) {
        myArrayList.addAll(c);
        Assertions.assertArrayEquals(new String[] {"it's", "all", "for", "now"}, myArrayList.subList(3, 6));
    }

    private static Collection<Collection<String>> collectionForTesting() {
        return List.of(Arrays.asList("and", "it's", "all", "for", "now"));
    }
}