package pro.chenggang.project.reactive.aliyun.oss.auth.provider;

import org.junit.jupiter.api.Test;
import pro.chenggang.project.reactive.aliyun.oss.entity.auth.Credentials;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
class MultiCredentialsProviderTest {

    @Test
    void addCredentials() {
        MultiCredentialsProvider multiCredentialsProvider = new MultiCredentialsProvider();
        multiCredentialsProvider.addCredentials("a", Credentials.of("","",""));
        multiCredentialsProvider.addCredentials("b", Credentials.of("","","",""));
    }

    @Test
    void supportCredentialsIdentity() {
        MultiCredentialsProvider multiCredentialsProvider = new MultiCredentialsProvider();
        multiCredentialsProvider.addCredentials("a", Credentials.of("","","",""));
        assertTrue(multiCredentialsProvider.supportCredentialsIdentity("a"));
        assertFalse(multiCredentialsProvider.supportCredentialsIdentity("b"));
        assertFalse(multiCredentialsProvider.supportCredentialsIdentity(null));
    }

    @Test
    void getCredentials() {
        MultiCredentialsProvider multiCredentialsProvider = new MultiCredentialsProvider();
        multiCredentialsProvider.addCredentials("a", Credentials.of("","","",""));
        StepVerifier.create(multiCredentialsProvider.getCredentials("a"))
                .expectNextCount(1)
                .verifyComplete();
        StepVerifier.create(multiCredentialsProvider.getCredentials("b"))
                .verifyComplete();

    }
}