package pro.chenggang.project.reactive.aliyun.oss.auth.provider;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
class SingleCredentialsProviderTest {

    @Test
    void supportCredentialsIdentity() {
        SingleCredentialsProvider singleCredentialsProvider = new SingleCredentialsProvider("","","");
        assertTrue(singleCredentialsProvider.supportCredentialsIdentity(""));
        singleCredentialsProvider = new SingleCredentialsProvider("","","","");
        assertTrue(singleCredentialsProvider.supportCredentialsIdentity(""));
    }

    @Test
    void getCredentials() {
        SingleCredentialsProvider singleCredentialsProvider = new SingleCredentialsProvider("","","");
        StepVerifier.create(singleCredentialsProvider.getCredentials(""))
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void order() {
        SingleCredentialsProvider singleCredentialsProvider = new SingleCredentialsProvider("","","");
        assertEquals(Integer.MAX_VALUE,singleCredentialsProvider.order());
    }
}