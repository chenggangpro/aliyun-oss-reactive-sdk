package pro.chenggang.project.reactive.aliyun.oss.auth.manager;

import lombok.Setter;
import pro.chenggang.project.reactive.aliyun.oss.auth.provider.CredentialsProvider;

import java.util.Comparator;
import java.util.Optional;
import java.util.PriorityQueue;

/**
 * The Default credentials provider manager.
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
public class DefaultCredentialsProviderManager implements CredentialsProviderManager {

    private final PriorityQueue<CredentialsProvider> credentialsProviders = new PriorityQueue<>(Comparator.comparing(CredentialsProvider::order));
    @Setter
    private CredentialsProvider defaultCredentialsProvider;

    @Override
    public void registerCredentialsProvider(CredentialsProvider credentialsProvider) {
        this.credentialsProviders.add(credentialsProvider);
    }

    @Override
    public Optional<CredentialsProvider> getCredentialsProvider(String credentialsIdentity, boolean fallbackToDefault) {
        if (credentialsProviders.isEmpty()) {
            return Optional.ofNullable(this.defaultCredentialsProvider);
        }
        Optional<CredentialsProvider> optionalCredentialsProvider = Optional.ofNullable(credentialsIdentity)
                .flatMap(identity -> this.credentialsProviders.stream()
                        .filter(credentialsProvider -> credentialsProvider.supportCredentialsIdentity(identity))
                        .findFirst()
                );
        if (!fallbackToDefault) {
            return optionalCredentialsProvider;
        }
        if (optionalCredentialsProvider.isPresent()) {
            return optionalCredentialsProvider;
        }
        return Optional.ofNullable(defaultCredentialsProvider);
    }
}
