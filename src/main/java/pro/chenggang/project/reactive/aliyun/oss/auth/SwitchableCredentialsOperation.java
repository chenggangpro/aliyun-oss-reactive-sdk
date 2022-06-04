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
    String CREDENTIALS_CONTEXT_KEY = "CREDENTIALS_@@[" + SwitchableCredentialsOperation.class.getName() + "]";

    /**
     * The constant DEFAULT_CREDENTIALS_IDENTITY
     */
    String DEFAULT_CREDENTIALS_IDENTITY = "DEFAULT_CREDENTIALS_IDENTITY_@@[" + SwitchableCredentialsOperation.class.getName() + "]";

    /**
     * Switch credentials .
     *
     * @param credentialsIdentity the credentials identity
     * @param fallbackWithDefault whether fallback to default
     * @return Void
     */
    Mono<Void> switchCredentials(String credentialsIdentity, boolean fallbackWithDefault);

    /**
     * Switch credentials . Not attempt to back to default
     *
     * @param credentialsIdentity the credentials identity
     * @return Void
     */
    default Mono<Void> switchCredentials(String credentialsIdentity) {
        return switchCredentials(credentialsIdentity, false);
    }

    /**
     * Using default credentials
     * @return Void
     */
    default Mono<Void> usingDefaultCredentials() {
        return switchCredentials(DEFAULT_CREDENTIALS_IDENTITY, true);
    }

}
