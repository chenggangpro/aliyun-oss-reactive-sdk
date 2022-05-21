package pro.chenggang.project.reactive.aliyun.oss.http.defaults;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivestreams.Publisher;
import pro.chenggang.project.reactive.aliyun.oss.configuration.ReactiveHttpClientConfiguration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.test.StepVerifier;

import java.nio.ByteBuffer;

import static org.mockito.Mockito.*;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class DefaultOssHttpClientTest {

    @Mock
    DefaultOssHttpClient defaultOssHttpClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExchangeToMono() {
        TypeReference<String> typeReference = new TypeReference<String>() {};
        when(defaultOssHttpClient.exchangeToMono(null, typeReference))
                .thenReturn(Mono.just("ok"));
        Mono<String> result = defaultOssHttpClient.exchangeToMono(null, typeReference);
        StepVerifier.create(result)
                .expectNext("ok")
                .verifyComplete();
    }

}
