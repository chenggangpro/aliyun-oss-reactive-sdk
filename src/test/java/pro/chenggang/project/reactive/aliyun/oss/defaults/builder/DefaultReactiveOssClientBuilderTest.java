package pro.chenggang.project.reactive.aliyun.oss.defaults.builder;

import org.junit.jupiter.api.Test;
import pro.chenggang.project.reactive.aliyun.oss.ReactiveOssClient;
import pro.chenggang.project.reactive.aliyun.oss.auth.manager.DefaultCredentialsProviderManager;
import pro.chenggang.project.reactive.aliyun.oss.auth.provider.MultiCredentialsProvider;
import pro.chenggang.project.reactive.aliyun.oss.auth.provider.SingleCredentialsProvider;
import pro.chenggang.project.reactive.aliyun.oss.configuration.ReactiveHttpClientConfiguration;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
class DefaultReactiveOssClientBuilderTest {

    ReactiveHttpClientConfiguration reactiveHttpClientConfiguration = new ReactiveHttpClientConfiguration();

    @Test
    void newBuilder() {
        DefaultReactiveOssClientBuilder defaultReactiveOssClientBuilder = DefaultReactiveOssClientBuilder.newBuilder();
        assertNotNull(defaultReactiveOssClientBuilder);
    }

    @Test
    void singleOrDefaultCredentials() {
        ReactiveOssClient reactiveOssClient = DefaultReactiveOssClientBuilder.newBuilder()
                .withReactiveHttpClientConfiguration(this.reactiveHttpClientConfiguration)
                .singleOrDefaultCredentials()
                .singleCredentials("", "", "")
                .singleCredentials("", "", "", "")
                .then()
                .build();
        assertNotNull(reactiveOssClient);
    }

    @Test
    void multiCredentials() {
        ReactiveOssClient reactiveOssClient = DefaultReactiveOssClientBuilder.newBuilder()
                .withReactiveHttpClientConfiguration(this.reactiveHttpClientConfiguration)
                .multiCredentials()
                .addCredentials("a","", "", "")
                .addCredentials("b","", "", "", "")
                .then()
                .build();
        assertNotNull(reactiveOssClient);
    }

    @Test
    void customCredentials() {
        ReactiveOssClient reactiveOssClient = DefaultReactiveOssClientBuilder.newBuilder()
                .withReactiveHttpClientConfiguration(this.reactiveHttpClientConfiguration)
                .customCredentials()
                .addCredentialsProvider(new MultiCredentialsProvider())
                .then()
                .build();
        assertNotNull(reactiveOssClient);
    }

    @Test
    void testCustomCredentials() {
        ReactiveOssClient reactiveOssClient = DefaultReactiveOssClientBuilder.newBuilder()
                .withReactiveHttpClientConfiguration(this.reactiveHttpClientConfiguration)
                .customCredentials(new DefaultCredentialsProviderManager())
                .addCredentialsProvider(new MultiCredentialsProvider())
                .then()
                .build();
        assertNotNull(reactiveOssClient);
    }

    @Test
    void customCredentials2() {
        ReactiveOssClient reactiveOssClient = DefaultReactiveOssClientBuilder.newBuilder()
                .withReactiveHttpClientConfiguration(this.reactiveHttpClientConfiguration)
                .customCredentials(new DefaultCredentialsProviderManager())
                .then()
                .build();
        assertNotNull(reactiveOssClient);
    }

    @Test
    void withReactiveHttpClientConfiguration() {
        DefaultReactiveOssClientBuilder defaultReactiveOssClientBuilder = DefaultReactiveOssClientBuilder.newBuilder()
                .withReactiveHttpClientConfiguration(this.reactiveHttpClientConfiguration);
        assertNotNull(defaultReactiveOssClientBuilder);
    }

    @Test
    void withDownloadResponseTimeout() {
        DefaultReactiveOssClientBuilder defaultReactiveOssClientBuilder = DefaultReactiveOssClientBuilder.newBuilder()
                .withDownloadResponseTimeout(Duration.ofMinutes(1));
        assertNotNull(defaultReactiveOssClientBuilder);
    }

    @Test
    void withUploadResponseTimeout() {
        DefaultReactiveOssClientBuilder defaultReactiveOssClientBuilder = DefaultReactiveOssClientBuilder.newBuilder()
                .withUploadResponseTimeout(Duration.ofMillis(1));
        assertNotNull(defaultReactiveOssClientBuilder);
    }

    @Test
    void addHttpClientCustomizer() {
        DefaultReactiveOssClientBuilder defaultReactiveOssClientBuilder = DefaultReactiveOssClientBuilder.newBuilder()
                        .addHttpClientCustomizer(httpClient -> httpClient);
        assertNotNull(defaultReactiveOssClientBuilder);
    }

    @Test
    void build() {
        ReactiveOssClient reactiveOssClient = DefaultReactiveOssClientBuilder.newBuilder()
                .withReactiveHttpClientConfiguration(this.reactiveHttpClientConfiguration)
                .multiCredentials()
                .addCredentials("", "", "", "")
                .then()
                .customCredentials()
                .addCredentialsProvider(new SingleCredentialsProvider("", "", ""))
                .then()
                .build();
        assertNotNull(reactiveOssClient);
    }
}