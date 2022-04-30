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
class InvalidBucketNameExceptionTest extends OssExceptionTest {


    @Test
    public void test() {
        InvalidBucketNameException invalidBucketNameException = new InvalidBucketNameException(errorCode, errorMessage, requestId, hostId);
        assertThat(invalidBucketNameException, notNullValue());
    }

}