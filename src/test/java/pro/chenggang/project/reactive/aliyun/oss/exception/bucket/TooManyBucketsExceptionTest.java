package pro.chenggang.project.reactive.aliyun.oss.exception.bucket;

import org.junit.jupiter.api.Test;
import pro.chenggang.project.reactive.aliyun.oss.exception.OssExceptionTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
class TooManyBucketsExceptionTest extends OssExceptionTest {

    @Test
    public void test() {
        TooManyBucketsException tooManyBucketsException = new TooManyBucketsException(errorCode, errorMessage, requestId, hostId);
        assertThat(tooManyBucketsException, notNullValue());
    }

}
