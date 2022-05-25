package pro.chenggang.project.reactive.aliyun.oss.exception.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pro.chenggang.project.reactive.aliyun.oss.exception.OssExceptionTest;

import java.time.Duration;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.*;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
class ResponseTimeoutExceptionTest extends OssExceptionTest {

    @Test
    public void test() {
        ResponseTimeoutException responseTimeoutException = new ResponseTimeoutException(Duration.ZERO);
        assertThat(responseTimeoutException, notNullValue());
    }

    @Test
    public void testConstruct() {
        ResponseTimeoutException responseTimeoutException = new ResponseTimeoutException(new RuntimeException());
        assertThat(responseTimeoutException, notNullValue());
    }
}