import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class HashMapImplTest {
    HashMapImpl<String, Integer> hashMap;

    @BeforeEach
    public void setUp() {
        hashMap = new HashMapImpl<>();
        hashMap.put("Snezhana", 18);
        hashMap.put("Arina", 19);
    }

    @Test
    public void put() {
        hashMap.put("Artem", 20);
        Assertions.assertEquals(3, hashMap.size());
    }

    @Test
    public void isEmpty() {
        Assertions.assertFalse(hashMap.isEmpty());
    }

    @Test
    public void containsKey() {
        Assertions.assertTrue(hashMap.containsKey("Snezhana"));
        Assertions.assertFalse(hashMap.containsKey("Misha"));
    }

    @Test
    public void containsValue() {
        Assertions.assertTrue(hashMap.containsValue(19));
        Assertions.assertFalse(hashMap.containsValue(9));
    }

    @Test
    public void get() {
        Assertions.assertEquals(18, hashMap.get("Snezhana"));
    }

    @Test
    public void remove() {
        hashMap.remove("Arina");
        Assertions.assertNull(hashMap.get("Arina"));
    }

    @Test
    public void clear() {
        hashMap.clear();
        Assertions.assertNull(hashMap.get("Artem"));
    }

    @Test
    public void keySet() {
        Set<String> set = hashMap.keySet();
        Assertions.assertEquals(2, set.size());
    }
}