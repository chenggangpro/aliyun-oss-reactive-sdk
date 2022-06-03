package pro.chenggang.project.reactive.aliyun.oss.exception.client;

import org.junit.jupiter.api.Test;
import pro.chenggang.project.reactive.aliyun.oss.exception.OssExceptionTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
class CredentialsNotFoundExceptionTest extends OssExceptionTest {

    @Test
    public void test() {
        CredentialsNotFoundException credentialsNotFoundException = new CredentialsNotFoundException("");
        assertThat(credentialsNotFoundException, notNullValue());
    }

    @Test
    public void testConstruct() {
        CredentialsNotFoundException credentialsNotFoundException = new CredentialsNotFoundException(new RuntimeException());
        assertThat(credentialsNotFoundException, notNullValue());
    }

}