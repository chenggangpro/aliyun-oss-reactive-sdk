package pro.chenggang.project.reactive.aliyun.oss.defaults;

import org.junit.jupiter.api.Test;
import pro.chenggang.project.reactive.aliyun.oss.BucketOperations;
import pro.chenggang.project.reactive.aliyun.oss.ReactiveOssClient;
import pro.chenggang.project.reactive.aliyun.oss.auth.provider.SingleCredentialsProvider;
import pro.chenggang.project.reactive.aliyun.oss.configuration.ReactiveHttpClientConfiguration;
import pro.chenggang.project.reactive.aliyun.oss.defaults.builder.DefaultReactiveOssClientBuilder;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
class DefaultReactiveOssClientTest {

    ReactiveOssClient reactiveOssClient = DefaultReactiveOssClientBuilder.newBuilder()
            .withReactiveHttpClientConfiguration(new ReactiveHttpClientConfiguration())
            .multiCredentials()
            .addCredentials("", "", "", "")
            .then()
            .customCredentials()
            .addCredentialsProvider(new SingleCredentialsProvider("", "", ""))
            .then()
            .build();

    @Test
    void bucketOperations() {
        BucketOperations bucketOperations = reactiveOssClient.bucketOperations();
        assertNotNull(bucketOperations);
    }

}