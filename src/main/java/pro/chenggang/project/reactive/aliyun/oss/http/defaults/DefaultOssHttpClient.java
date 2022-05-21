package pro.chenggang.project.reactive.aliyun.oss.http.defaults;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.chenggang.project.reactive.aliyun.oss.configuration.ReactiveHttpClientConfiguration;
import pro.chenggang.project.reactive.aliyun.oss.entity.http.OssHttpRequest;
import pro.chenggang.project.reactive.aliyun.oss.http.OssHttpClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.nio.ByteBuffer;

/**
 * The default implementation of OssHttpClient
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class DefaultOssHttpClient implements OssHttpClient {

    private final ReactiveHttpClientConfiguration reactiveHttpClientConfiguration;
    private final HttpClient httpClient;

    @Override
    public <T> Mono<T> exchangeToMono(OssHttpRequest ossHttpRequest, TypeReference<? extends T> elementTypeReference) {
        return Mono.empty();
    }

    @Override
    public <T> Flux<T> exchangeToFlux(OssHttpRequest ossHttpRequest, TypeReference<? extends T> elementTypeReference) {
        return Flux.empty();
    }

    @Override
    public Flux<ByteBuffer> exchange(OssHttpRequest ossHttpRequest) {
        return Flux.empty();
    }
}
