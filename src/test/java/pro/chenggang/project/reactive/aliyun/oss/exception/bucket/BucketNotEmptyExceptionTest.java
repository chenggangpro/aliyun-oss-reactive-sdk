package pro.chenggang.project.reactive.aliyun.oss.exception.bucket;

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
class BucketNotEmptyExceptionTest extends OssExceptionTest {

    @Test
    public void test(@Random String bucketName) {
        BucketNotEmptyException bucketNotEmptyException = new BucketNotEmptyException(errorCode, errorMessage, requestId, hostId, bucketName);
        assertThat(bucketNotEmptyException, notNullValue());
    }
}
