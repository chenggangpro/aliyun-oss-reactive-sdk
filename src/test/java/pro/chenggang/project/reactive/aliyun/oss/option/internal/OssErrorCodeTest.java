package pro.chenggang.project.reactive.aliyun.oss.option.internal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static pro.chenggang.project.reactive.aliyun.oss.option.internal.OssErrorCode.ACCESS_DENIED;

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
        Assertions.assertEquals(ACCESS_DENIED, result);
    }

    @Test
    void testValueOfErrorCode() {
        Optional<OssErrorCode> result = OssErrorCode.valueOfErrorCode(ACCESS_DENIED.getValue());
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(ACCESS_DENIED,result.get());
    }
}