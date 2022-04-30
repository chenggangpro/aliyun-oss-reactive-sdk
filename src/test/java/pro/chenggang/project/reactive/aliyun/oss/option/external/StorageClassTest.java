package pro.chenggang.project.reactive.aliyun.oss.option.external;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
class StorageClassTest {

    @Test
    void testValues() {
        StorageClass[] result = StorageClass.values();
        assertArrayEquals(StorageClass.values(), result);
    }

    @Test
    void testValueOf() {
        StorageClass result = StorageClass.valueOf("STANDARD");
        assertEquals(StorageClass.STANDARD, result);
    }
}
