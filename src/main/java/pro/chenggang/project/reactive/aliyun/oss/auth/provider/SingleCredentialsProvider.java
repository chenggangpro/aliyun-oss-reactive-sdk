package pro.chenggang.project.reactive.aliyun.oss.auth.provider;

import pro.chenggang.project.reactive.aliyun.oss.entity.auth.Credentials;
import reactor.core.publisher.Mono;

/**
 * The default credentials provider
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
public class SingleCredentialsProvider implements CredentialsProvider {

    private final Credentials credentials;

    public SingleCredentialsProvider(String endpoint, String accessKeyId, String secretAccessKey) {
        this.credentials = Credentials.of(endpoint, accessKeyId, secretAccessKey);
    }

    public SingleCredentialsProvider(String endpoint, String accessKeyId, String secretAccessKey, String securityToken) {
        this.credentials = Credentials.of(endpoint, accessKeyId, secretAccessKey, securityToken);
    }

    @Override
    public boolean supportCredentialsIdentity(String credentialsIdentity) {
        return true;
    }

    @Override
    public Mono<Credentials> getCredentials(String credentialsIdentity) {
        return Mono.just(credentials);
    }

    @Override
    public int order() {
        return Integer.MAX_VALUE;
    }
}
