package pro.chenggang.project.reactive.aliyun.oss.exception.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pro.chenggang.project.reactive.aliyun.oss.entity.http.MultiValueMap;
import pro.chenggang.project.reactive.aliyun.oss.exception.OssExceptionTest;
import pro.chenggang.project.reactive.aliyun.oss.exception.client.ResponseTimeoutException;

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
class UnidentifiedOssExceptionTest extends OssExceptionTest {

    @Test
    public void test() {
        UnidentifiedOssException unidentifiedOssException = new UnidentifiedOssException(400,new MultiValueMap<>(),errorMessage);
        assertThat(unidentifiedOssException, notNullValue());
    }

}