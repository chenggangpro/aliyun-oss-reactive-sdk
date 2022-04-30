package pro.chenggang.project.reactive.aliyun.oss.exception;

import org.jeasy.random.Random;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
class OssClientExceptionTest {

    @Random
    protected String errorCode;
    @Random
    protected String errorMessage;
    @Random
    protected String requestId;
    @Mock
    protected Throwable throwable;

    @Test
    public void testConstruct1() {
        OssClientException ossClientException = new OssClientException(errorCode, errorMessage);
        assertThat(ossClientException, notNullValue());
    }

    @Test
    public void testConstruct2() {
        OssClientException ossClientException = new OssClientException(errorCode, errorMessage, throwable);
        assertThat(ossClientException, notNullValue());
    }

    @Test
    public void testConstruct3() {
        OssClientException ossClientException = new OssClientException(errorCode, errorMessage, requestId);
        assertThat(ossClientException, notNullValue());
    }

    @Test
    public void testConstruct4() {
        OssClientException ossClientException = new OssClientException(errorCode, errorMessage, requestId, throwable);
        assertThat(ossClientException, notNullValue());
    }


}