package pro.chenggang.project.reactive.aliyun.oss.exception.common;

import org.junit.jupiter.api.Test;
import pro.chenggang.project.reactive.aliyun.oss.exception.OssExceptionTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
class AccessDeniedExceptionTest extends OssExceptionTest {

    @Test
    public void test(){
        AccessDeniedException accessDeniedException = new AccessDeniedException(errorCode,errorMessage,requestId,hostId);
        assertThat(accessDeniedException,notNullValue());
    }

}