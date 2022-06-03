package pro.chenggang.project.reactive.aliyun.oss.auth.provider;

import cn.hutool.core.util.StrUtil;
import pro.chenggang.project.reactive.aliyun.oss.entity.auth.Credentials;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The multi credentials provider
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
public class MultiCredentialsProvider implements CredentialsProvider {

    /**
     * The credentials container
     */
    private final Map<String, Credentials> credentialsContainer = new ConcurrentHashMap<>();

    /**
     * Add credentials.
     *
     * @param credentialsIdentity the credentials identity
     * @param credentials         the credentials
     */
    public void addCredentials(String credentialsIdentity, Credentials credentials) {
        this.credentialsContainer.put(credentialsIdentity, credentials);
    }

    @Override
    public boolean supportCredentialsIdentity(String credentialsIdentity) {
        return credentialsIdentity != null && credentialsContainer.containsKey(credentialsIdentity);
    }

    @Override
    public Mono<Credentials> getCredentials(String credentialsIdentity) {
        return Mono.justOrEmpty(credentialsIdentity)
                .filter(StrUtil::isNotBlank)
                .flatMap(identity -> Mono.justOrEmpty(this.credentialsContainer.get(identity)));
    }

}
