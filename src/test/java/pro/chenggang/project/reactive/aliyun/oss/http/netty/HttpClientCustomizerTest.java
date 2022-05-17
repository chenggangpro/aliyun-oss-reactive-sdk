package pro.chenggang.project.reactive.aliyun.oss.http.netty;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.netty.http.client.HttpClient;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
class HttpClientCustomizerTest {

    @Test
    public void testHttpClientCustomizer() {
        HttpClientCustomizer httpClientCustomizer = httpClient -> httpClient;
        HttpClient httpClient = HttpClient.create();
        HttpClient result = httpClientCustomizer.customize(httpClient);
        Assertions.assertEquals(httpClient, result);
    }

}