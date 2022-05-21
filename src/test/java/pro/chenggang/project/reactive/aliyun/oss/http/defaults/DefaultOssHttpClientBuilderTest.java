package pro.chenggang.project.reactive.aliyun.oss.http.defaults;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pro.chenggang.project.reactive.aliyun.oss.configuration.ReactiveHttpClientConfiguration;
import pro.chenggang.project.reactive.aliyun.oss.http.netty.HttpClientCustomizer;

import java.util.List;

import static org.mockito.Mockito.*;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
class DefaultOssHttpClientBuilderTest {

    @Test
    void testNewBuilder() {
        DefaultOssHttpClientBuilder result = DefaultOssHttpClientBuilder.newBuilder(new ReactiveHttpClientConfiguration());
        Assertions.assertNotNull(result);
    }

    @Test
    void testAddHttpClientCustomizer() {
        DefaultOssHttpClientBuilder result = DefaultOssHttpClientBuilder.newBuilder(new ReactiveHttpClientConfiguration())
                        .addHttpClientCustomizer(httpClient -> httpClient);
        Assertions.assertNotNull(result);
    }

    @Test
    void testBuild() {
        DefaultOssHttpClient result = DefaultOssHttpClientBuilder.newBuilder(new ReactiveHttpClientConfiguration())
                .build();
        Assertions.assertNotNull(result);
    }
}