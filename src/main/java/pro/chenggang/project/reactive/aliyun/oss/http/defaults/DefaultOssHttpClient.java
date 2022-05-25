package pro.chenggang.project.reactive.aliyun.oss.http.defaults;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.DefaultCookie;
import io.netty.handler.timeout.TimeoutException;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import pro.chenggang.project.reactive.aliyun.oss.configuration.ReactiveHttpClientConfiguration;
import pro.chenggang.project.reactive.aliyun.oss.entity.http.MultiValueMap;
import pro.chenggang.project.reactive.aliyun.oss.entity.http.OssHttpRequest;
import pro.chenggang.project.reactive.aliyun.oss.exception.client.RequestTimeoutException;
import pro.chenggang.project.reactive.aliyun.oss.exception.client.ResponseTimeoutException;
import pro.chenggang.project.reactive.aliyun.oss.http.OssHttpClient;
import pro.chenggang.project.reactive.aliyun.oss.http.handler.error.OssHttpResponseErrorHandler;
import pro.chenggang.project.reactive.aliyun.oss.http.message.convertor.JacksonMessageConvertor;
import pro.chenggang.project.reactive.aliyun.oss.http.message.convertor.OssHttpSimpleMessageConvertor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.client.HttpClient.RequestSender;
import reactor.netty.http.client.HttpClientForm;
import reactor.netty.http.client.HttpClientResponse;

import java.io.File;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;

import static cn.hutool.http.Header.CONTENT_TYPE;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * The default implementation of OssHttpClient
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class DefaultOssHttpClient implements OssHttpClient {

    private final ReactiveHttpClientConfiguration reactiveHttpClientConfiguration;
    private final HttpClient httpClient;
    private final OssHttpSimpleMessageConvertor ossHttpSimpleMessageConvertor;
    private final JacksonMessageConvertor jacksonMessageConvertor;
    private final OssHttpResponseErrorHandler ossHttpResponseErrorHandler;

    @Override
    public Mono<String> exchangeToString(OssHttpRequest ossHttpRequest) {
        Duration globalResponseTimeout = reactiveHttpClientConfiguration.getResponseTimeout();
        return Mono.from(sendHttpRequestInternal(
                        ossHttpRequest,
                        (httpClientResponse, byteBufFlux) -> {
                            final MultiValueMap<String, String> responseHeaders = extractResponseHeaders(httpClientResponse);
                            HttpResponseStatus httpResponseStatus = httpClientResponse.status();
                            if (!HttpResponseStatus.OK.equals(httpResponseStatus)) {
                                return this.handleOssError(httpResponseStatus, responseHeaders, byteBufFlux);
                            }
                            return byteBufFlux.aggregate()
                                    .asString(UTF_8);
                        }))
                .timeout(globalResponseTimeout, Mono.error(new ResponseTimeoutException(globalResponseTimeout)))
                .onErrorMap(TimeoutException.class, RequestTimeoutException::new);
    }

    @Override
    public <T> Mono<T> exchangeToMono(OssHttpRequest ossHttpRequest, Class<T> elementClass) {
        Duration globalResponseTimeout = reactiveHttpClientConfiguration.getResponseTimeout();
        return Mono.from(sendHttpRequestInternal(
                        ossHttpRequest,
                        (httpClientResponse, byteBufFlux) -> {
                            final MultiValueMap<String, String> responseHeaders = extractResponseHeaders(httpClientResponse);
                            HttpResponseStatus httpResponseStatus = httpClientResponse.status();
                            if (!HttpResponseStatus.OK.equals(httpResponseStatus)) {
                                return this.handleOssError(httpResponseStatus, responseHeaders, byteBufFlux);
                            }
                            String contentType = responseHeaders.getFirst(CONTENT_TYPE.getValue());
                            return byteBufFlux.aggregate()
                                    .asString(UTF_8)
                                    .map(responseBody -> this.ossHttpSimpleMessageConvertor.convertResponse(contentType, responseBody, elementClass));
                        }))
                .timeout(globalResponseTimeout, Mono.error(new ResponseTimeoutException(globalResponseTimeout)))
                .onErrorMap(TimeoutException.class, RequestTimeoutException::new);
    }

    @Override
    public <T> Mono<T> exchangeToMono(OssHttpRequest ossHttpRequest, TypeReference<T> elementTypeReference) {
        Duration globalResponseTimeout = reactiveHttpClientConfiguration.getResponseTimeout();
        return Mono.from(sendHttpRequestInternal(
                        ossHttpRequest,
                        (httpClientResponse, byteBufFlux) -> {
                            final MultiValueMap<String, String> responseHeaders = extractResponseHeaders(httpClientResponse);
                            HttpResponseStatus httpResponseStatus = httpClientResponse.status();
                            if (!HttpResponseStatus.OK.equals(httpResponseStatus)) {
                                return this.handleOssError(httpResponseStatus, responseHeaders, byteBufFlux);
                            }
                            String contentType = responseHeaders.getFirst(CONTENT_TYPE.getValue());
                            return byteBufFlux.aggregate()
                                    .asString(UTF_8)
                                    .map(responseBody -> this.ossHttpSimpleMessageConvertor.convertResponse(contentType, responseBody, elementTypeReference));
                        }))
                .timeout(globalResponseTimeout, Mono.error(new ResponseTimeoutException(globalResponseTimeout)))
                .onErrorMap(TimeoutException.class, RequestTimeoutException::new);
    }

    @Override
    public Flux<ByteBuffer> exchange(OssHttpRequest ossHttpRequest) {
        Duration globalResponseTimeout = reactiveHttpClientConfiguration.getResponseTimeout();
        return Flux.from(sendHttpRequestInternal(
                        ossHttpRequest,
                        (httpClientResponse, byteBufFlux) -> {
                            final MultiValueMap<String, String> responseHeaders = extractResponseHeaders(httpClientResponse);
                            HttpResponseStatus httpResponseStatus = httpClientResponse.status();
                            if (!HttpResponseStatus.OK.equals(httpResponseStatus)) {
                                return this.handleOssError(httpResponseStatus, responseHeaders, byteBufFlux);
                            }
                            return byteBufFlux.asByteBuffer();
                        }))
                .timeout(globalResponseTimeout, Mono.error(new ResponseTimeoutException(globalResponseTimeout)))
                .onErrorMap(TimeoutException.class, RequestTimeoutException::new);
    }

    /**
     * extract response headers
     *
     * @param httpClientResponse the HttpClientResponse
     * @return the response headers
     */
    private MultiValueMap<String, String> extractResponseHeaders(HttpClientResponse httpClientResponse) {
        HttpHeaders httpHeaders = httpClientResponse.responseHeaders();
        final MultiValueMap<String, String> responseHeaders = new MultiValueMap<>();
        for (Map.Entry<String, String> entry : httpHeaders) {
            responseHeaders.add(entry.getKey(), entry.getValue());
        }
        return responseHeaders;
    }

    /**
     * handle oss error
     *
     * @param httpResponseStatus the HttpResponseStatus
     * @param responseHeaders    the response headers
     * @param byteBufFlux        the ByteBufFlux
     * @return the mono error
     */
    @SuppressWarnings("unchecked")
    private <T> Mono<T> handleOssError(HttpResponseStatus httpResponseStatus, MultiValueMap<String, String> responseHeaders, ByteBufFlux byteBufFlux) {
        int statusCode = httpResponseStatus.code();
        String contentType = responseHeaders.getFirst(CONTENT_TYPE.getValue());
        return (Mono<T>) byteBufFlux.aggregate()
                .asString(UTF_8)
                .filter(StrUtil::isNotBlank)
                .map(responseBody -> jacksonMessageConvertor.convertResponse(responseBody, contentType))
                .flatMap(jsonNode -> Mono.error(ossHttpResponseErrorHandler.handleErrorResponse(statusCode, responseHeaders, jsonNode)))
                .switchIfEmpty(Mono.error(ossHttpResponseErrorHandler.handleErrorResponse(statusCode, responseHeaders)));
    }

    /**
     * send http request internal
     *
     * @param ossHttpRequest the oss http request
     * @param receiver       the response receiver
     * @param <V>            the result data type
     * @return the result publisher
     */
    private <V> Publisher<V> sendHttpRequestInternal(OssHttpRequest ossHttpRequest, BiFunction<? super HttpClientResponse, ? super ByteBufFlux, ? extends Publisher<V>> receiver) {
        RequestSender requestSender = this.assembleRequestSender(ossHttpRequest);
        if (ossHttpRequest.hasSimpleBody()) {
            Object bodyData = ossHttpRequest.getSimpleBody().getBodyData();
            return requestSender
                    .send(ByteBufFlux.fromString(Mono.just(this.ossHttpSimpleMessageConvertor
                                    .convertRequestBody(bodyData, ossHttpRequest.getContentType()))
                            )
                    )
                    .response(receiver);
        }
        if (ossHttpRequest.hasFormBody()) {
            Map<String, Object> formContent = ossHttpRequest.getFormBody().getFormContent();
            return requestSender
                    .sendForm((httpClientRequest, httpClientForm) -> {
                        HttpClientForm clientForm = httpClientForm.multipart(true);
                        Set<Map.Entry<String, Object>> entries = formContent.entrySet();
                        for (Map.Entry<String, Object> entry : entries) {
                            String key = entry.getKey();
                            Object value = entry.getValue();
                            if (Objects.isNull(value)) {
                                continue;
                            }
                            if (value instanceof File) {
                                clientForm = clientForm.file(key, (File) value);
                                continue;
                            }
                            clientForm = clientForm.attr(key, value.toString());
                        }
                    })
                    .response(receiver);
        }
        if (ossHttpRequest.hasFileBody()) {
            return requestSender
                    .send((httpClientRequest, nettyOutbound) -> nettyOutbound
                            .sendFile(ossHttpRequest.getFileBody().getPath())
                    )
                    .response(receiver);
        }
        if (ossHttpRequest.hasByteBufferBody()) {
            ByteBufFlux byteBufFlux = ByteBufFlux.fromInbound(ossHttpRequest.getByteBufferBody()
                    .getByteBufferFlux()
                    .map(Unpooled::wrappedBuffer)
            );
            return requestSender.send(byteBufFlux).response(receiver);
        }
        return requestSender.response(receiver);
    }

    /**
     * Assemble request sender
     *
     * @param ossHttpRequest the oss http request
     * @return the request sender
     */
    private RequestSender assembleRequestSender(OssHttpRequest ossHttpRequest) {
        HttpClient currentHttpClient = this.httpClient
                .headers(httpHeaders -> {
                    final DefaultHttpHeaders defaultHttpHeaders = new DefaultHttpHeaders();
                    ossHttpRequest.getHeaders().forEach((k, v) -> defaultHttpHeaders.set(k, v));
                    httpHeaders.add(defaultHttpHeaders);
                });
        MultiValueMap<String, String> cookies = ossHttpRequest.getCookies();
        for (Map.Entry<String, List<String>> entry : cookies) {
            String key = entry.getKey();
            List<String> values = entry.getValue();
            for (String value : values) {
                if (Objects.isNull(value)) {
                    continue;
                }
                Cookie cookie = new DefaultCookie(key, value);
                currentHttpClient = currentHttpClient.cookie(cookie);
            }
        }
        return currentHttpClient
                .request(HttpMethod.valueOf(ossHttpRequest.getOssHttpMethod().name()))
                .uri(ossHttpRequest.getUrlWithQuery());
    }

}
