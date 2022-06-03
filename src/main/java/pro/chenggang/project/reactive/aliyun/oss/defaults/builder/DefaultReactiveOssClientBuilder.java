package pro.chenggang.project.reactive.aliyun.oss.defaults.builder;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import pro.chenggang.project.reactive.aliyun.oss.ReactiveOssClient;
import pro.chenggang.project.reactive.aliyun.oss.auth.manager.CredentialsProviderManager;
import pro.chenggang.project.reactive.aliyun.oss.auth.manager.DefaultCredentialsProviderManager;
import pro.chenggang.project.reactive.aliyun.oss.auth.provider.CredentialsProvider;
import pro.chenggang.project.reactive.aliyun.oss.auth.provider.MultiCredentialsProvider;
import pro.chenggang.project.reactive.aliyun.oss.auth.provider.SingleCredentialsProvider;
import pro.chenggang.project.reactive.aliyun.oss.configuration.ReactiveHttpClientConfiguration;
import pro.chenggang.project.reactive.aliyun.oss.configuration.ReactiveOssConfiguration;
import pro.chenggang.project.reactive.aliyun.oss.defaults.DefaultReactiveOssClient;
import pro.chenggang.project.reactive.aliyun.oss.entity.auth.Credentials;
import pro.chenggang.project.reactive.aliyun.oss.http.netty.HttpClientCustomizer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The default ReactiveOssClient Builder
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultReactiveOssClientBuilder {

    /**
     * The http client customizers
     */
    private final List<HttpClientCustomizer> httpClientCustomizerList = new ArrayList<>();
    /**
     * The credentials provider manager
     */
    private CredentialsProviderManager credentialsProviderManager;
    /**
     * The reactive http client configuration
     */
    private ReactiveHttpClientConfiguration reactiveHttpClientConfiguration;
    /**
     * The response timeout when oss download file
     * Default is PT1M
     */
    private Duration downloadResponseTimeout = Duration.ofMinutes(1);
    /**
     * The response timeout when oss upload file
     * Default is PT1M
     */
    private Duration uploadResponseTimeout = Duration.ofMinutes(1);
    /**
     * The single credentials provider
     */
    private SingleCredentialsProvider singleCredentialsProvider;
    /**
     * The multi credentials provider
     */
    private MultiCredentialsProvider multiCredentialsProvider;
    /**
     * The custom credentials providers
     */
    private List<CredentialsProvider> customCredentialsProviderList;

    /**
     * New builder default reactive oss client builder.
     *
     * @return the default reactive oss client builder
     */
    public static DefaultReactiveOssClientBuilder newBuilder() {
        return new DefaultReactiveOssClientBuilder();
    }

    /**
     * Single or default credentials provider builder.
     *
     * @return the single credentials provider builder
     */
    public SingleOrDefaultCredentialsProviderBuilder singleOrDefaultCredentials() {
        return new SingleOrDefaultCredentialsProviderBuilder(this);
    }

    /**
     * Multi credentials provider builder.
     *
     * @return the multi credentials provider builder
     */
    public MultiCredentialsProviderBuilder multiCredentials() {
        return new MultiCredentialsProviderBuilder(this);
    }

    /**
     * Custom credentials provider builder.
     *
     * @return the custom credentials provider builder
     */
    public CustomCredentialsProviderBuilder customCredentials() {
        return new CustomCredentialsProviderBuilder(this);
    }

    /**
     * Custom credentials provider builder.
     *
     * @param credentialsProviderManager the specific credentialsProviderManager
     * @return the custom credentials provider builder
     */
    public CustomCredentialsProviderBuilder customCredentials(CredentialsProviderManager credentialsProviderManager) {
        this.credentialsProviderManager = credentialsProviderManager;
        return new CustomCredentialsProviderBuilder(this);
    }

    /**
     * With the reactive http client configuration
     * @return the DefaultReactiveOssClientBuilder
     */
    public DefaultReactiveOssClientBuilder withReactiveHttpClientConfiguration(ReactiveHttpClientConfiguration reactiveHttpClientConfiguration) {
        this.reactiveHttpClientConfiguration = reactiveHttpClientConfiguration;
        return this;
    }

    /**
     * With the response timeout when oss download file
     * Default is PT1M
     * @return the DefaultReactiveOssClientBuilder
     */
    public DefaultReactiveOssClientBuilder withDownloadResponseTimeout(Duration downloadResponseTimeout) {
        this.downloadResponseTimeout = downloadResponseTimeout;
        return this;
    }

    /**
     * With the response timeout when oss upload file
     * Default is PT1M
     * @return the DefaultReactiveOssClientBuilder
     */
    public DefaultReactiveOssClientBuilder withUploadResponseTimeout(Duration uploadResponseTimeout) {
        this.uploadResponseTimeout = uploadResponseTimeout;
        return this;
    }

    /**
     * Add http client customizer
     *
     * @param httpClientCustomizer the http client customizer
     * @return the DefaultReactiveOssClientBuilder
     */
    public DefaultReactiveOssClientBuilder addHttpClientCustomizer(HttpClientCustomizer httpClientCustomizer) {
        this.httpClientCustomizerList.add(httpClientCustomizer);
        return this;
    }

    /**
     * Build ReactiveOssClient
     *
     * @return the ReactiveOssClient
     */
    public ReactiveOssClient build() {
        Assert.notNull(this.reactiveHttpClientConfiguration, "ReactiveHttpClientConfiguration could not be null");
        Assert.notNull(this.downloadResponseTimeout, "DownloadResponseTimeout could not be null");
        Assert.notNull(this.uploadResponseTimeout, "UploadResponseTimeout could not be null");
        CredentialsProviderManager targetCredentialsProviderManager = this.credentialsProviderManager;
        if (Objects.nonNull(targetCredentialsProviderManager) && CollectionUtil.isNotEmpty(this.customCredentialsProviderList)) {
            this.customCredentialsProviderList.forEach(targetCredentialsProviderManager::registerCredentialsProvider);
        } else if (Objects.isNull(targetCredentialsProviderManager)) {
            DefaultCredentialsProviderManager defaultCredentialsProviderManager = new DefaultCredentialsProviderManager();
            if (Objects.nonNull(this.singleCredentialsProvider)) {
                defaultCredentialsProviderManager.setDefaultCredentialsProvider(this.singleCredentialsProvider);
            }
            if (Objects.nonNull(this.multiCredentialsProvider)) {
                defaultCredentialsProviderManager.registerCredentialsProvider(this.multiCredentialsProvider);
            }
            if (CollectionUtil.isNotEmpty(this.customCredentialsProviderList)) {
                this.customCredentialsProviderList.forEach(defaultCredentialsProviderManager::registerCredentialsProvider);
            }
            targetCredentialsProviderManager = defaultCredentialsProviderManager;
        }
        ReactiveOssConfiguration reactiveOssConfiguration = new ReactiveOssConfiguration(this.downloadResponseTimeout, this.uploadResponseTimeout, targetCredentialsProviderManager);
        return new DefaultReactiveOssClient(this.reactiveHttpClientConfiguration, reactiveOssConfiguration, this.httpClientCustomizerList);
    }

    /**
     * The Single credentials provider builder.
     */
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class SingleOrDefaultCredentialsProviderBuilder {

        private final DefaultReactiveOssClientBuilder defaultReactiveOssClientBuilder;
        private SingleCredentialsProvider singleCredentialsProvider;

        /**
         * Add a single credentials .
         *
         * @param endpoint        the endpoint
         * @param accessKeyId     the access key id
         * @param secretAccessKey the secret access key
         * @return the single credentials provider builder
         */
        public SingleOrDefaultCredentialsProviderBuilder singleCredentials(String endpoint, String accessKeyId, String secretAccessKey) {
            this.singleCredentialsProvider = new SingleCredentialsProvider(endpoint, accessKeyId, secretAccessKey);
            return this;
        }

        /**
         * Add a single credentials .
         *
         * @param endpoint        the endpoint
         * @param accessKeyId     the access key id
         * @param secretAccessKey the secret access key
         * @param securityToken   the security token
         * @return the single credentials provider builder
         */
        public SingleOrDefaultCredentialsProviderBuilder singleCredentials(String endpoint, String accessKeyId, String secretAccessKey, String securityToken) {
            this.singleCredentialsProvider = new SingleCredentialsProvider(endpoint, accessKeyId, secretAccessKey, securityToken);
            return this;
        }

        /**
         * Then return DefaultReactiveOssClientBuilder
         *
         * @return the default reactive oss client builder
         */
        public DefaultReactiveOssClientBuilder then() {
            this.defaultReactiveOssClientBuilder.singleCredentialsProvider = this.singleCredentialsProvider;
            return this.defaultReactiveOssClientBuilder;
        }
    }

    /**
     * The Multi credentials provider builder.
     */
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class MultiCredentialsProviderBuilder {

        private final DefaultReactiveOssClientBuilder defaultReactiveOssClientBuilder;
        private final MultiCredentialsProvider multiCredentialsProvider = new MultiCredentialsProvider();

        /**
         * Add a credentials .
         *
         * @param credentialsIdentity the credentials identity
         * @param endpoint            the endpoint
         * @param accessKeyId         the access key id
         * @param secretAccessKey     the secret access key
         * @return the multi credentials provider builder
         */
        public MultiCredentialsProviderBuilder addCredentials(String credentialsIdentity, String endpoint, String accessKeyId, String secretAccessKey) {
            multiCredentialsProvider.addCredentials(credentialsIdentity, Credentials.of(endpoint, accessKeyId, secretAccessKey));
            return this;
        }

        /**
         * Add a credentials .
         *
         * @param credentialsIdentity the credentials identity
         * @param accessKeyId         the access key id
         * @param secretAccessKey     the secret access key
         * @param securityToken       the security token
         * @return the multi credentials provider builder
         */
        public MultiCredentialsProviderBuilder addCredentials(String credentialsIdentity, String endpoint, String accessKeyId, String secretAccessKey, String securityToken) {
            multiCredentialsProvider.addCredentials(credentialsIdentity, Credentials.of(endpoint, accessKeyId, secretAccessKey, securityToken));
            return this;
        }

        /**
         * Then return DefaultReactiveOssClientBuilder
         *
         * @return the default reactive oss client builder
         */
        public DefaultReactiveOssClientBuilder then() {
            this.defaultReactiveOssClientBuilder.multiCredentialsProvider = this.multiCredentialsProvider;
            return this.defaultReactiveOssClientBuilder;
        }
    }

    /**
     * The Custom credentials provider builder.
     */
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CustomCredentialsProviderBuilder {

        private final DefaultReactiveOssClientBuilder defaultReactiveOssClientBuilder;
        private final List<CredentialsProvider> customCredentialsProviderList = new ArrayList<>();

        /**
         * Add credentials provider .
         *
         * @param credentialsProvider the credentials provider
         * @return the custom credentials provider builder
         */
        public CustomCredentialsProviderBuilder addCredentialsProvider(CredentialsProvider credentialsProvider) {
            this.customCredentialsProviderList.add(credentialsProvider);
            return this;
        }

        /**
         * Then return DefaultReactiveOssClientBuilder
         *
         * @return the default reactive oss client builder
         */
        public DefaultReactiveOssClientBuilder then() {
            this.defaultReactiveOssClientBuilder.customCredentialsProviderList = this.customCredentialsProviderList;
            return this.defaultReactiveOssClientBuilder;
        }
    }
}
