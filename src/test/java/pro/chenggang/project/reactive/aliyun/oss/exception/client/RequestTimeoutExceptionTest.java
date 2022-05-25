package pro.chenggang.project.reactive.aliyun.oss.exception.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pro.chenggang.project.reactive.aliyun.oss.exception.OssExceptionTest;
import pro.chenggang.project.reactive.aliyun.oss.exception.common.AccessDeniedException;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.*;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
class RequestTimeoutExceptionTest extends OssExceptionTest {

    @Test
    public void test() {
        RequestTimeoutException requestTimeoutException = new RequestTimeoutException();
        assertThat(requestTimeoutException, notNullValue());
    }

    @Test
    public void testConstruct() {
        RequestTimeoutException requestTimeoutException = new RequestTimeoutException(new RuntimeException());
        assertThat(requestTimeoutException, notNullValue());
    }

}