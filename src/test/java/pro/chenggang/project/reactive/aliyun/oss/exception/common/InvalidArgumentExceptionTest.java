package pro.chenggang.project.reactive.aliyun.oss.exception.common;

import org.jeasy.random.EasyRandomExtension;
import org.jeasy.random.Random;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pro.chenggang.project.reactive.aliyun.oss.exception.OssExceptionTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
@ExtendWith(EasyRandomExtension.class)
class InvalidArgumentExceptionTest extends OssExceptionTest {

    @Test
    public void test(@Random String argumentName, @Random String argumentValue) {
        InvalidArgumentException invalidArgumentException = new InvalidArgumentException(errorCode, errorMessage, requestId, hostId, argumentName, argumentValue);
        assertThat(invalidArgumentException, notNullValue());
    }
}