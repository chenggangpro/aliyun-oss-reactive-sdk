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
public class OssExceptionTest {

    @Random
    protected String errorCode;
    @Random
    protected String errorMessage;
    @Random
    protected String requestId;
    @Random
    protected String hostId;
    @Mock
    protected Throwable throwable;

    @Test
    public void testConstruct1() {
        OssException ossException = new OssException(errorCode, errorMessage, requestId, hostId);
        assertThat(ossException, notNullValue());
    }

    @Test
    public void testConstruct2() {
        OssException ossException = new OssException(errorCode, errorMessage, requestId, hostId, throwable);
        assertThat(ossException, notNullValue());
    }


}
