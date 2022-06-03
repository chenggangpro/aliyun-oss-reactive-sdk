package pro.chenggang.project.reactive.aliyun.oss.auth.manager;

import pro.chenggang.project.reactive.aliyun.oss.auth.provider.CredentialsProvider;

import java.util.Optional;

/**
 * The Credentials provider manager.
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
public interface CredentialsProviderManager {

    /**
     * Register credentials provider.
     *
     * @param credentialsProvider the credentials provider
     */
    void registerCredentialsProvider(CredentialsProvider credentialsProvider);

    /**
     * Gets optional credentials provider
     *
     * @param credentialsIdentity the credentials identity
     * @param fallbackToDefault   the fallback to default
     * @return the credentials provider
     */
    Optional<CredentialsProvider> getCredentialsProvider(String credentialsIdentity, boolean fallbackToDefault);

    /**
     * Gets optional credentials provider.
     *
     * @param credentialsIdentity the credentials identity
     * @return the credentials provider
     */
    default Optional<CredentialsProvider> getCredentialsProvider(String credentialsIdentity) {
        return getCredentialsProvider(credentialsIdentity, false);
    }
}
