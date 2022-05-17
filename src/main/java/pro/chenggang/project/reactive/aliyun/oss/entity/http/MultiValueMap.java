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
public class MultiValueMap extends CaseInsensitiveLinkedMap<String, List<String>> {

    private static final long serialVersionUID = 7154336457175941243L;

    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * Gets first.
     *
     * @param key the key
     * @return the first
     */
    public String getFirst(String key) {
        List<String> values = super.get(key);
        return (values != null && !values.isEmpty() ? values.get(0) : null);
    }

    /**
     * Add key and value.
     *
     * @param key   the key
     * @param value the value
     */
    public void add(String key, String value) {
        List<String> values = super.computeIfAbsent(key, k -> new ArrayList<>(1));
        values.add(value);
    }

    /**
     * Add all key and values.
     *
     * @param key    the key
     * @param values the values
     */
    public void addAll(String key, List<String> values) {
        List<String> currentValues = super.computeIfAbsent(key, k -> new ArrayList<>(1));
        currentValues.addAll(values);
    }

    /**
     * Add all multiValueMap.
     *
     * @param values the values
     */
    public void addAll(MultiValueMap values) {
        for (Map.Entry<String, List<String>> entry : values.entrySet()) {
            addAll(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Set key and replace values.
     *
     * @param key   the key
     * @param value the value
     */
    public void set(String key, String value) {
        List<String> values = new ArrayList<>(1);
        values.add(value);
        this.put(key, values);
    }

    /**
     * Sets all key and replace values.
     *
     * @param values the values
     */
    public void setAll(Map<String, String> values) {
        values.forEach(this::set);
    }

    /**
     * To single value map map.
     *
     * @return the map
     */
    public Map<String, String> toSingleValueMap() {
        Map<String, String> singleValueMap = new LinkedHashMap<>((int) (super.size() / DEFAULT_LOAD_FACTOR), DEFAULT_LOAD_FACTOR);
        for (Map.Entry<String, List<String>> entry : super.entrySet()) {
            String key = entry.getKey();
            List<String> values = entry.getValue();
            if (values != null && !values.isEmpty()) {
                singleValueMap.put(key, values.get(0));
            }
        }
        return singleValueMap;
    }

}
