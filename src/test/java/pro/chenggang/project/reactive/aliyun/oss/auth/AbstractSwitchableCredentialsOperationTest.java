package pro.chenggang.project.reactive.aliyun.oss.auth;

import org.junit.jupiter.api.Test;
import pro.chenggang.project.reactive.aliyun.oss.auth.manager.CredentialsProviderManager;
import pro.chenggang.project.reactive.aliyun.oss.auth.manager.DefaultCredentialsProviderManager;
import pro.chenggang.project.reactive.aliyun.oss.entity.auth.Credentials;
import pro.chenggang.project.reactive.aliyun.oss.exception.client.CredentialsNotFoundException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static pro.chenggang.project.reactive.aliyun.oss.auth.SwitchableCredentialsOperation.CREDENTIALS_CONTEXT_KEY;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
class AbstractSwitchableCredentialsOperationTest {

    AbstractSwitchableCredentialsOperation abstractSwitchableCredentialsOperation = new AbstractSwitchableCredentialsOperation(){
        @Override
        protected CredentialsProviderManager getCredentialsProviderManager() {
            return new DefaultCredentialsProviderManager();
        }

        @Override
        public Mono<Credentials> getCredentials(String credentialsIdentity,boolean fallbackWithDefault) {
            return Mono.just(Credentials.of("","",""));
        }
    };

    @Test
    void getCredentials() {
        StepVerifier.create(abstractSwitchableCredentialsOperation.getCredentials("",true))
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void switchCredentials() {
        StepVerifier.create(abstractSwitchableCredentialsOperation.switchCredentials("",true))
                .expectError(CredentialsNotFoundException.class)
                .verify();
    }

    @Test
    void switchCredentials2() {
        StepVerifier.create(abstractSwitchableCredentialsOperation.switchCredentials("x")
                        .contextWrite(context -> context.put(CREDENTIALS_CONTEXT_KEY,"y"))
                )
                .expectAccessibleContext()
                .hasKey(CREDENTIALS_CONTEXT_KEY)
                .then()
                .verifyComplete();
    }

    @Test
    void switchCredentials3() {
        StepVerifier.create(abstractSwitchableCredentialsOperation.switchCredentials("x"))
                .expectAccessibleContext()
                .hasKey(CREDENTIALS_CONTEXT_KEY)
                .then()
                .verifyComplete();
    }

    @Test
    void switchCredentials4() {
        StepVerifier.create(abstractSwitchableCredentialsOperation.switchCredentials("x",true))
                .expectAccessibleContext()
                .hasKey(CREDENTIALS_CONTEXT_KEY)
                .then()
                .verifyComplete();
    }

    @Test
    void usingDefaultCredentials() {
        StepVerifier.create(abstractSwitchableCredentialsOperation.usingDefaultCredentials())
                .expectAccessibleContext()
                .hasKey(CREDENTIALS_CONTEXT_KEY)
                .then()
                .verifyComplete();
    }
}