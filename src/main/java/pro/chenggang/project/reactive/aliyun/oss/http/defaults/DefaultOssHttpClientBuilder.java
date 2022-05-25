package pro.chenggang.project.reactive.aliyun.oss.http.defaults;

import lombok.RequiredArgsConstructor;
import pro.chenggang.project.reactive.aliyun.oss.configuration.ReactiveHttpClientConfiguration;
import pro.chenggang.project.reactive.aliyun.oss.http.handler.error.DefaultOssHttpResponseErrorHandler;
import pro.chenggang.project.reactive.aliyun.oss.http.handler.error.OssHttpResponseErrorHandler;
import pro.chenggang.project.reactive.aliyun.oss.http.message.convertor.DefaultJacksonMessageConvertor;
import pro.chenggang.project.reactive.aliyun.oss.http.message.convertor.DefaultOssHttpSimpleMessageConvertor;
import pro.chenggang.project.reactive.aliyun.oss.http.message.convertor.JacksonMessageConvertor;
import pro.chenggang.project.reactive.aliyun.oss.http.message.convertor.OssHttpSimpleMessageConvertor;
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
    private OssHttpSimpleMessageConvertor ossHttpSimpleMessageConvertor = DefaultOssHttpSimpleMessageConvertor.getInstance();
    private JacksonMessageConvertor jacksonMessageConvertor = DefaultJacksonMessageConvertor.getInstance();
    private OssHttpResponseErrorHandler ossHttpResponseErrorHandler = new DefaultOssHttpResponseErrorHandler();

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
     * With oss http simple message convertor.
     *
     * @param ossHttpSimpleMessageConvertor the oss http simple message convertor
     * @return the DefaultOssHttpClientBuilder
     */
    public DefaultOssHttpClientBuilder withOssHttpSimpleMessageConvertor(OssHttpSimpleMessageConvertor ossHttpSimpleMessageConvertor) {
        this.ossHttpSimpleMessageConvertor = ossHttpSimpleMessageConvertor;
        return this;
    }

    /**
     * With jackson message convertor.
     *
     * @param jacksonMessageConvertor the jackson message convertor
     * @return the DefaultOssHttpClientBuilder
     */
    public DefaultOssHttpClientBuilder withJacksonMessageConvertor(JacksonMessageConvertor jacksonMessageConvertor) {
        this.jacksonMessageConvertor = jacksonMessageConvertor;
        return this;
    }

    /**
     * With oss http response error handler.
     *
     * @param ossHttpResponseErrorHandler the oss http response error handler
     * @return the DefaultOssHttpClientBuilder
     */
    public DefaultOssHttpClientBuilder withOssHttpResponseErrorHandler(OssHttpResponseErrorHandler ossHttpResponseErrorHandler) {
        this.ossHttpResponseErrorHandler = ossHttpResponseErrorHandler;
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
        return new DefaultOssHttpClient(this.reactiveHttpClientConfiguration, httpClient, this.ossHttpSimpleMessageConvertor, this.jacksonMessageConvertor, this.ossHttpResponseErrorHandler);
    }
}
