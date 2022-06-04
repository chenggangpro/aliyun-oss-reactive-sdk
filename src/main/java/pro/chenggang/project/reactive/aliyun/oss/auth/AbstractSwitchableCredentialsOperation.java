package pro.chenggang.project.reactive.aliyun.oss.auth;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import pro.chenggang.project.reactive.aliyun.oss.auth.manager.CredentialsProviderManager;
import pro.chenggang.project.reactive.aliyun.oss.entity.auth.Credentials;
import pro.chenggang.project.reactive.aliyun.oss.entity.auth.CredentialsContext;
import pro.chenggang.project.reactive.aliyun.oss.exception.client.CredentialsNotFoundException;
import reactor.core.publisher.Mono;

/**
 * The Switchable credentials operation.
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
public abstract class AbstractSwitchableCredentialsOperation implements SwitchableCredentialsOperation {

    /**
     * Get credentials provider manager
     * @return the CredentialsProviderManager
     */
    protected abstract CredentialsProviderManager getCredentialsProviderManager();

    /**
     * Gets credentials .
     *
     * @param credentialsIdentity the credentials identity
     * @param fallbackWithDefault whether fallback to default
     * @return the credentials
     */
    public Mono<Credentials> getCredentials(String credentialsIdentity, boolean fallbackWithDefault) {
        return Mono.justOrEmpty(this.getCredentialsProviderManager()
                        .getCredentialsProvider(credentialsIdentity, fallbackWithDefault)
                )
                .flatMap(credentialsProvider -> credentialsProvider.getCredentials(credentialsIdentity));
    }

    /**
     * Switch credentials mono.
     *
     * @param credentialsIdentity the credentials identity
     * @return the mono
     */
    @Override
    public Mono<Void> switchCredentials(String credentialsIdentity, boolean fallbackWithDefault) {
        return Mono.justOrEmpty(credentialsIdentity)
                .filter(StrUtil::isNotBlank)
                .switchIfEmpty(Mono.error(new CredentialsNotFoundException("Credentials identity could not be blank")))
                .contextWrite(context -> {
                    CredentialsContext credentialsContext = new CredentialsContext(credentialsIdentity, fallbackWithDefault);
                    boolean hasKey = context.hasKey(CREDENTIALS_CONTEXT_KEY);
                    if (hasKey) {
                        Object existContext = context.get(CREDENTIALS_CONTEXT_KEY);
                        log.warn("Credentials identity ({}) has already exist,it will replaced by {}", existContext, credentialsContext);
                    }
                    return context.put(CREDENTIALS_CONTEXT_KEY, credentialsContext);
                })
                .then();
    }

}
