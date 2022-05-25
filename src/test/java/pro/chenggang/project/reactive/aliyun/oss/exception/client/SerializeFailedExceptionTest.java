package pro.chenggang.project.reactive.aliyun.oss.exception.client;

import org.junit.jupiter.api.Test;
import pro.chenggang.project.reactive.aliyun.oss.exception.OssExceptionTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
class SerializeFailedExceptionTest extends OssExceptionTest {

    @Test
    public void test() {
        SerializeFailedException serializeFailedException = new SerializeFailedException(errorMessage);
        assertThat(serializeFailedException, notNullValue());
    }

    @Test
    public void testConstruct() {
        SerializeFailedException serializeFailedException = new SerializeFailedException(new RuntimeException());
        assertThat(serializeFailedException, notNullValue());
    }
}