package pro.chenggang.project.reactive.aliyun.oss.option.external;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
class AccessControlListTest {

    @Test
    void testValues() {
        AccessControlList[] result = AccessControlList.values();
        Assertions.assertArrayEquals(AccessControlList.values(), result);
    }

    @Test
    void testValueOf() {
        AccessControlList result = AccessControlList.valueOf("DEFAULT");
        Assertions.assertEquals(AccessControlList.DEFAULT, result);
    }
}