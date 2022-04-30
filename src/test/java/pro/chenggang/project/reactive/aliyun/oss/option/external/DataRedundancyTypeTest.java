package pro.chenggang.project.reactive.aliyun.oss.option.external;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
class DataRedundancyTypeTest {

    @Test
    void testValues() {
        DataRedundancyType[] result = DataRedundancyType.values();
        Assertions.assertArrayEquals(DataRedundancyType.values(), result);
    }

    @Test
    void testValueOf() {
        DataRedundancyType result = DataRedundancyType.valueOf("LRS");
        Assertions.assertEquals(DataRedundancyType.LRS, result);
    }
}