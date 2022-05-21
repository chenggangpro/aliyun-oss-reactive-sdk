package pro.chenggang.project.reactive.aliyun.oss.entity.http;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
class MultiValueMapTest {

    String key = "key";
    String noKey = "noKey";
    String value = "value";
    String otherValue = "otherValue";

    MultiValueMap<String,String> multiValueMap;

    @BeforeEach
    void setUp() {
        multiValueMap = new MultiValueMap<>();
        assertNotNull(multiValueMap);
        multiValueMap.add(key, value);
    }

    @Test
    public void testConstruct() {
        MultiValueMap map = new MultiValueMap<>();
        assertNotNull(map);
    }

    @Test
    void testGetFirst() {
        String result = multiValueMap.getFirst(key);
        Assertions.assertEquals(value, result);
    }

    @Test
    void testGetFirst2() {
        multiValueMap.add(key, otherValue);
        String result = multiValueMap.getFirst(key);
        Assertions.assertEquals(value, result);
    }

    @Test
    void testGetFirst3() {
        multiValueMap.put(noKey, null);
        String result = multiValueMap.getFirst(noKey);
        assertNull(result);
    }

    @Test
    void testGetFirst4() {
        multiValueMap.put(noKey, Collections.emptyList());
        String result = multiValueMap.getFirst(noKey);
        assertNull(result);
    }

    @Test
    void testAdd() {
        multiValueMap.add(key, value);
    }

    @Test
    void testAddAll() {
        multiValueMap.addAll(noKey, Collections.singletonList(value));
    }

    @Test
    void testAddAll2() {
        multiValueMap.addAll(new MultiValueMap<>());
    }

    @Test
    void testAddAll3() {
        MultiValueMap<String,String> map = new MultiValueMap<>();
        map.add(key, value);
        multiValueMap.addAll(map);
    }

    @Test
    void testSet() {
        multiValueMap.set(key, value);
    }

    @Test
    void testSetAll() {
        Map<String, String> map = new HashMap<>();
        map.put(key, value);
        multiValueMap.setAll(map);
    }

    @Test
    void testToSingleValueMap() {
        Map<String, String> result = multiValueMap.toSingleValueMap();
        Assertions.assertEquals(new HashMap<String, String>() {{
            put(key, value);
        }}, result);
    }

    @Test
    void testToSingleValueMap2() {
        multiValueMap.addAll(noKey, Collections.emptyList());
        Map<String, String> result = multiValueMap.toSingleValueMap();
        assertFalse(result.containsKey(noKey));
    }

    @Test
    void testToSingleValueMap3() {
        multiValueMap.put(noKey, null);
        Map<String, String> result = multiValueMap.toSingleValueMap();
        assertFalse(result.containsKey(noKey));
    }
}