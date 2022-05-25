package pro.chenggang.project.reactive.aliyun.oss.http;

import com.fasterxml.jackson.core.type.TypeReference;
import org.reactivestreams.Publisher;
import pro.chenggang.project.reactive.aliyun.oss.entity.http.OssHttpRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;
import java.util.function.Function;

/**
 * The Oss http client.
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
public interface OssHttpClient {

    /**
     * Exchange to mono string.
     *
     * @param ossHttpRequest the oss http request
     * @return the mono
     */
    Mono<String> exchangeToString(OssHttpRequest ossHttpRequest);

    /**
     * Exchange to mono with target class.
     *
     * @param <T>            the type parameter
     * @param ossHttpRequest the oss http request
     * @param elementClass   the element class
     * @return the mono
     */
    <T> Mono<T> exchangeToMono(OssHttpRequest ossHttpRequest, Class<T> elementClass);

    /**
     * Exchange to mono with type reference.
     *
     * @param <T>                  the type parameter
     * @param ossHttpRequest       the oss http request
     * @param elementTypeReference the element type reference
     * @return the mono
     */
    <T> Mono<T> exchangeToMono(OssHttpRequest ossHttpRequest, TypeReference<T> elementTypeReference);

    /**
     * Exchange to ByteBuffer flux.
     *
     * @param ossHttpRequest the oss http request
     * @return the flux
     */
    Flux<ByteBuffer> exchange(OssHttpRequest ossHttpRequest);

    /**
     * Exchange to target publisher by process with ByteBufferFlux.
     *
     * @param <T>              the type parameter
     * @param ossHttpRequest   the oss http request
     * @param byteBufProcessor the byte buf processor
     * @return the publisher
     */
    default <T> Publisher<T> exchange(OssHttpRequest ossHttpRequest, Function<Flux<ByteBuffer>, Publisher<T>> byteBufProcessor) {
        return byteBufProcessor.apply(exchange(ossHttpRequest));
    }

}