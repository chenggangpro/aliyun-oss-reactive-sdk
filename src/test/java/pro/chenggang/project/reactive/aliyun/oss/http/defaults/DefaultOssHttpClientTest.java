package pro.chenggang.project.reactive.aliyun.oss.http.defaults;

import cn.hutool.http.ContentType;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pro.chenggang.project.reactive.aliyun.oss.configuration.ReactiveHttpClientConfiguration;
import pro.chenggang.project.reactive.aliyun.oss.entity.http.MultiValueMap;
import pro.chenggang.project.reactive.aliyun.oss.entity.http.OssHttpRequest;
import pro.chenggang.project.reactive.aliyun.oss.entity.http.OssRequestBuilder;
import pro.chenggang.project.reactive.aliyun.oss.exception.common.AccessDeniedException;
import pro.chenggang.project.reactive.aliyun.oss.http.OssHttpClient;
import pro.chenggang.project.reactive.aliyun.oss.option.http.OssHttpMethod;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.test.StepVerifier;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.stream.Collectors;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
class DefaultOssHttpClientTest extends MockHttpServerTest {

    OssHttpClient ossHttpClient;

    @BeforeEach
    void setUp() {
        ReactiveHttpClientConfiguration reactiveHttpClientConfiguration = new ReactiveHttpClientConfiguration();
        this.ossHttpClient = DefaultOssHttpClientBuilder.newBuilder(reactiveHttpClientConfiguration)
                .build();
    }

    @Test
    void testExchangeToString() {
        OssHttpRequest ossHttpRequest = OssHttpRequest.newBuilder(httpBaseUrl + getUri, OssHttpMethod.GET)
                .build();
        StepVerifier.create(this.ossHttpClient.exchangeToString(ossHttpRequest))
                .expectNext(successXml)
                .verifyComplete();
        OssHttpRequest badRequest = OssHttpRequest.newBuilder(httpBaseUrl + errorUri, OssHttpMethod.GET)
                .build();
        StepVerifier.create(this.ossHttpClient.exchangeToString(badRequest))
                .expectError(AccessDeniedException.class)
                .verify();
    }

    @Test
    void testExchangeToMono() {
        OssHttpRequest ossHttpRequest = OssHttpRequest.newBuilder(httpBaseUrl + getUri, OssHttpMethod.GET)
                .build();
        StepVerifier.create(this.ossHttpClient.exchangeToMono(ossHttpRequest, String.class))
                .expectNext(successXml)
                .verifyComplete();
        OssHttpRequest badRequest = OssHttpRequest.newBuilder(httpBaseUrl + errorUri, OssHttpMethod.GET)
                .build();
        StepVerifier.create(this.ossHttpClient.exchangeToMono(badRequest, String.class))
                .expectError(AccessDeniedException.class)
                .verify();
    }

    @Test
    void testExchangeToMono2() {
        OssHttpRequest ossHttpRequest = OssHttpRequest.newBuilder(httpBaseUrl + getUri, OssHttpMethod.GET)
                .build();
        StepVerifier.create(this.ossHttpClient.exchangeToMono(ossHttpRequest, new TypeReference<String>() {}))
                .expectNext(successXml)
                .verifyComplete();
        OssHttpRequest badRequest = OssHttpRequest.newBuilder(httpBaseUrl + errorUri, OssHttpMethod.GET)
                .build();
        StepVerifier.create(this.ossHttpClient.exchangeToMono(badRequest, new TypeReference<String>() {}))
                .expectError(AccessDeniedException.class)
                .verify();
    }

    @Test
    void testExchange() {
        OssHttpRequest ossHttpRequest = OssHttpRequest.newBuilder(httpBaseUrl + getFileUri, OssHttpMethod.GET)
                .build();
        StepVerifier.create(this.ossHttpClient.exchange(
                ossHttpRequest,
                byteBufferFlux -> byteBufferFlux
                        .collect(Collectors.summarizingLong(Buffer::capacity))
                        .map(LongSummaryStatistics::getSum)
                ))
                .expectNext(super.filePath.toFile().length())
                .verifyComplete();
        OssHttpRequest badRequest = OssHttpRequest.newBuilder(httpBaseUrl + errorUri, OssHttpMethod.GET)
                .build();
        StepVerifier.create(this.ossHttpClient.exchange(
                        badRequest,
                        byteBufferFlux -> byteBufferFlux
                                .collect(Collectors.summarizingLong(Buffer::capacity))
                                .map(LongSummaryStatistics::getSum)
                ))
                .expectError(AccessDeniedException.class)
                .verify();
    }

    @Test
    void testGetWithParametersAndCookie() throws Exception {
        MultiValueMap<String,String> params = new MultiValueMap<>();
        params.add("key","1");
        params.add("key","2");
        params.add("subKey","subValue1");
        params.add("subKey","subValue2");
        params.add("subKey",null);
        OssRequestBuilder ossRequestBuilder = OssHttpRequest.newBuilder(httpBaseUrl + getWithParamUri, OssHttpMethod.GET);
        params.forEach((k,v) -> v.forEach(s -> ossRequestBuilder.addParameter(k,s)));
        OssHttpRequest ossHttpRequest = ossRequestBuilder
                .addCookie("cookie","value")
                .addCookie("cookie-null",null)
                .build();
        StepVerifier.create(this.ossHttpClient.exchangeToString(ossHttpRequest))
                .expectNext(super.objectMapper.writeValueAsString(params))
                .verifyComplete();
    }

    @Test
    void testPostForm() throws Exception {
        OssHttpRequest ossHttpRequest = OssHttpRequest.newBuilder(httpBaseUrl + postForm, OssHttpMethod.POST)
                .newFormBody()
                .addFormData("key","1")
                .addFormData("file1",super.filePath.toFile())
                .addFormData("file2",super.filePath)
                .addFormData("keys",null)
                .and()
                .build();
        List<String> resultList = new ArrayList<>();
        resultList.add("1");
        resultList.add(String.valueOf(super.filePath.toFile().length()));
        resultList.add(String.valueOf(super.filePath.toFile().length()));
        StepVerifier.create(this.ossHttpClient.exchangeToString(ossHttpRequest))
                .expectNext(super.objectMapper.writeValueAsString(resultList))
                .verifyComplete();
    }

    @Test
    void testPostFile() {
        OssHttpRequest ossHttpRequest = OssHttpRequest.newBuilder(httpBaseUrl + postFile, OssHttpMethod.POST)
                .newFileBody(super.filePath)
                .build();
        StepVerifier.create(this.ossHttpClient.exchangeToString(ossHttpRequest))
                .expectNext(String.valueOf(super.filePath.toFile().length()))
                .verifyComplete();
    }

    @Test
    void testPostData() {
        OssHttpRequest ossHttpRequest = OssHttpRequest.newBuilder(httpBaseUrl + postData, OssHttpMethod.POST)
                .newByteBufferBody(ByteBufFlux.fromString(Mono.just(this.successXml)).asByteBuffer())
                .build();
        StepVerifier.create(this.ossHttpClient.exchangeToString(ossHttpRequest))
                .expectNext(this.successXml)
                .verifyComplete();
    }

    @Test
    void testPostSimpleData() {
        OssHttpRequest ossHttpRequest = OssHttpRequest.newBuilder(httpBaseUrl + postData, OssHttpMethod.POST)
                .newSimpleBody(ContentType.TEXT_PLAIN.getValue())
                .setBodyData(this.successXml)
                .and()
                .build();
        StepVerifier.create(this.ossHttpClient.exchangeToString(ossHttpRequest))
                .expectNext(this.successXml)
                .verifyComplete();
    }
}
