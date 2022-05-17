package pro.chenggang.project.reactive.aliyun.oss.http.netty;

import reactor.netty.http.client.HttpClient;

/**
 * The {@link HttpClient} customizer
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
@FunctionalInterface
public interface HttpClientCustomizer {

    /**
     * Customize the specified {@link HttpClient}.
     *
     * @param httpClient the {@link HttpClient} to customize.
     * @return the customized {@link HttpClient}.
     */
    HttpClient customize(HttpClient httpClient);
}
