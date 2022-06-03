package pro.chenggang.project.reactive.aliyun.oss.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pro.chenggang.project.reactive.aliyun.oss.auth.manager.CredentialsProviderManager;

import java.time.Duration;

/**
 * The Reactive Oss configuration
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ReactiveOssConfiguration {

    /**
     * The response timeout when oss download file
     * Default is PT1M
     */
    private final Duration downloadResponseTimeout;

    /**
     * The response timeout when oss upload file
     * Default is PT1M
     */
    private final Duration uploadResponseTimeout;

    /**
     * The credentials provider manager
     */
    private final CredentialsProviderManager credentialsProviderManager;

}
