package pro.chenggang.project.reactive.aliyun.oss.auth;

import reactor.core.publisher.Mono;

/**
 * The Switchable credentials operation.
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
public interface SwitchableCredentialsOperation {

    /**
     * The constant CREDENTIALS_CONTEXT_KEY.
     */
    String CREDENTIALS_CONTEXT_KEY = "CREDENTIALS_[" + SwitchableCredentialsOperation.class.getName() + "]";

    /**
     * Switch credentials mono.
     *
     * @param credentialsIdentity the credentials identity
     * @return the mono
     */
    Mono<Void> switchCredentials(String credentialsIdentity);

}
