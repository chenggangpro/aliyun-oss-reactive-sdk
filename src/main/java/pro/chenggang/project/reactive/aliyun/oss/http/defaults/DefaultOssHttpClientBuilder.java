package pro.chenggang.project.reactive.aliyun.oss.http.defaults;

import lombok.RequiredArgsConstructor;
import pro.chenggang.project.reactive.aliyun.oss.configuration.ReactiveHttpClientConfiguration;
import pro.chenggang.project.reactive.aliyun.oss.http.netty.HttpClientCustomizer;
import pro.chenggang.project.reactive.aliyun.oss.http.netty.ReactorNettyHttpClientFactory;
import reactor.netty.http.client.HttpClient;

import java.util.ArrayList;
import java.util.List;

/**
 * DefaultOssHttpClient's builder
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class DefaultOssHttpClientBuilder {

    private final ReactiveHttpClientConfiguration reactiveHttpClientConfiguration;
    private final List<HttpClientCustomizer> httpClientCustomizers = new ArrayList<>();

    /**
     * New default oss http client builder.
     *
     * @param reactiveHttpClientConfiguration the reactive http client configuration
     * @return the DefaultOssHttpClientBuilder
     */
    public static DefaultOssHttpClientBuilder newBuilder(ReactiveHttpClientConfiguration reactiveHttpClientConfiguration) {
        return new DefaultOssHttpClientBuilder(reactiveHttpClientConfiguration);
    }

    /**
     * Add http client customizer .
     *
     * @param httpClientCustomizer the http client customizer
     * @return the DefaultOssHttpClientBuilder
     */
    public DefaultOssHttpClientBuilder addHttpClientCustomizer(HttpClientCustomizer httpClientCustomizer) {
        this.httpClientCustomizers.add(httpClientCustomizer);
        return this;
    }

    /**
     * Build default oss http client.
     *
     * @return the DefaultOssHttpClient
     */
    public DefaultOssHttpClient build() {
        ReactorNettyHttpClientFactory reactorNettyHttpClientFactory = new ReactorNettyHttpClientFactory(this.reactiveHttpClientConfiguration, this.httpClientCustomizers);
        HttpClient httpClient = reactorNettyHttpClientFactory.newReactorNettyHttpClient();
        return new DefaultOssHttpClient(this.reactiveHttpClientConfiguration, httpClient);
    }
}
