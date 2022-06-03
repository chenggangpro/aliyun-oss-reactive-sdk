package pro.chenggang.project.reactive.aliyun.oss.defaults;

import pro.chenggang.project.reactive.aliyun.oss.BucketOperations;
import pro.chenggang.project.reactive.aliyun.oss.ReactiveOssClient;
import pro.chenggang.project.reactive.aliyun.oss.configuration.ReactiveHttpClientConfiguration;
import pro.chenggang.project.reactive.aliyun.oss.configuration.ReactiveOssConfiguration;
import pro.chenggang.project.reactive.aliyun.oss.http.OssHttpClient;
import pro.chenggang.project.reactive.aliyun.oss.http.defaults.DefaultOssHttpClientBuilder;
import pro.chenggang.project.reactive.aliyun.oss.http.netty.HttpClientCustomizer;

import java.util.List;

/**
 * The default reactive oss client
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
public class DefaultReactiveOssClient implements ReactiveOssClient {

    private final BucketOperations bucketOperations;

    public DefaultReactiveOssClient(ReactiveHttpClientConfiguration reactiveHttpClientConfiguration,
                                    ReactiveOssConfiguration reactiveOssConfiguration,
                                    List<HttpClientCustomizer> httpClientCustomizers) {
        OssHttpClient ossHttpClient = DefaultOssHttpClientBuilder.newBuilder(reactiveHttpClientConfiguration)
                .addHttpClientCustomizer(httpClientCustomizers)
                .build();
        this.bucketOperations = new DefaultBucketOperations(reactiveOssConfiguration, ossHttpClient);
    }

    @Override
    public BucketOperations bucketOperations() {
        return this.bucketOperations;
    }
}
