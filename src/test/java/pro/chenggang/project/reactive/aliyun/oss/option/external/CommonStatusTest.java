package pro.chenggang.project.reactive.aliyun.oss.option.external;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
class CommonStatusTest {

    @Test
    void testValues() {
        CommonStatus[] result = CommonStatus.values();
        Assertions.assertArrayEquals(CommonStatus.values(), result);
    }

    @Test
    void testValueOf() {
        CommonStatus result = CommonStatus.valueOf("ENABLED");
        Assertions.assertEquals(CommonStatus.ENABLED, result);
    }
}