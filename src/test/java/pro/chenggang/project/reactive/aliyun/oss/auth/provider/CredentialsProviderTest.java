package pro.chenggang.project.reactive.aliyun.oss.auth.provider;

import org.junit.jupiter.api.Test;
import pro.chenggang.project.reactive.aliyun.oss.entity.auth.Credentials;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
class CredentialsProviderTest {

    @Test
    void order() {
        CredentialsProvider credentialsProvider = new CredentialsProvider() {

            @Override
            public boolean supportCredentialsIdentity(String credentialsIdentity) {
                return false;
            }

            @Override
            public Mono<Credentials> getCredentials(String credentialsIdentity) {
                return null;
            }
        };
        assertEquals(0,credentialsProvider.order());
    }
}