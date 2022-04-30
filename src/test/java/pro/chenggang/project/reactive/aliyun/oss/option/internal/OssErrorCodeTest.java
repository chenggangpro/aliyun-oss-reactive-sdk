package pro.chenggang.project.reactive.aliyun.oss.option.internal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
class OssErrorCodeTest {

    @Test
    void testValues() {
        OssErrorCode[] result = OssErrorCode.values();
        Assertions.assertArrayEquals(OssErrorCode.values(), result);
    }

    @Test
    void testValueOf() {
        OssErrorCode result = OssErrorCode.valueOf("ACCESS_DENIED");
        Assertions.assertEquals(OssErrorCode.ACCESS_DENIED, result);
    }
}