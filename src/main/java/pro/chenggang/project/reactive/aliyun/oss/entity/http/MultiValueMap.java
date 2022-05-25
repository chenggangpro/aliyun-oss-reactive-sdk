package pro.chenggang.project.reactive.aliyun.oss.entity.http;

import cn.hutool.core.map.CaseInsensitiveLinkedMap;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The case-insensitive multi value map with String key and String value
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
public class MultiValueMap<K, V> extends CaseInsensitiveLinkedMap<K, List<V>> {

    private static final long serialVersionUID = 7154336457175941243L;

    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * Gets first.
     *
     * @param key the key
     * @return the first
     */
    public V getFirst(K key) {
        List<V> values = super.get(key);
        return (values != null && !values.isEmpty() ? values.get(0) : null);
    }

    /**
     * Add key and value.
     *
     * @param key   the key
     * @param value the value
     */
    public void add(K key, V value) {
        List<V> values = super.computeIfAbsent(super.customKey(key), k -> new ArrayList<>(1));
        values.add(value);
    }

    /**
     * Add all key and values.
     *
     * @param key    the key
     * @param values the values
     */
    public void addAll(K key, List<V> values) {
        List<V> currentValues = super.computeIfAbsent(super.customKey(key), k -> new ArrayList<>(1));
        currentValues.addAll(values);
    }

    /**
     * Add all multiValueMap.
     *
     * @param values the values
     */
    public void addAll(MultiValueMap<K, V> values) {
        for (Map.Entry<K, List<V>> entry : values.entrySet()) {
            addAll(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Set key and replace values.
     *
     * @param key   the key
     * @param value the value
     */
    public void set(K key, V value) {
        List<V> values = new ArrayList<>(1);
        values.add(value);
        this.put(key, values);
    }

    /**
     * Sets all key and replace values.
     *
     * @param values the values
     */
    public void setAll(Map<K, V> values) {
        values.forEach(this::set);
    }

    /**
     * To single value map map.
     *
     * @return the map
     */
    public Map<K, V> toSingleValueMap() {
        Map<K, V> singleValueMap = new LinkedHashMap<>((int) (super.size() / DEFAULT_LOAD_FACTOR), DEFAULT_LOAD_FACTOR);
        for (Map.Entry<K, List<V>> entry : super.entrySet()) {
            K key = entry.getKey();
            List<V> values = entry.getValue();
            if (values != null && !values.isEmpty()) {
                singleValueMap.put(key, values.get(0));
            }
        }
        return singleValueMap;
    }

}
