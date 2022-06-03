package pro.chenggang.project.reactive.aliyun.oss.auth.provider;

import pro.chenggang.project.reactive.aliyun.oss.entity.auth.Credentials;
import reactor.core.publisher.Mono;

/**
 * The Credentials provider.
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
public interface CredentialsProvider {

    /**
     * Whether provider supports credentials with target identity .
     *
     * @param credentialsIdentity the credentials identity
     * @return the boolean
     */
    boolean supportCredentialsIdentity(String credentialsIdentity);

    /**
     * Gets credentials.
     *
     * @param credentialsIdentity the credential's identity
     * @return the credentials
     */
    Mono<Credentials> getCredentials(String credentialsIdentity);

    /**
     * The credentials order .
     *
     * @return the order default is 0
     */
    default int order() {
        return 0;
    }
}
