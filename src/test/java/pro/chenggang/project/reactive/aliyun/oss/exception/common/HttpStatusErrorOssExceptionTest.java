package pro.chenggang.project.reactive.aliyun.oss.exception.common;

import org.junit.jupiter.api.Test;
import pro.chenggang.project.reactive.aliyun.oss.entity.http.MultiValueMap;
import pro.chenggang.project.reactive.aliyun.oss.exception.OssExceptionTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
class HttpStatusErrorOssExceptionTest extends OssExceptionTest {

    @Test
    public void test() {
        HttpStatusErrorOssException httpStatusErrorOssException = new HttpStatusErrorOssException(400, new MultiValueMap<>());
        assertThat(httpStatusErrorOssException, notNullValue());
    }

}