package pro.chenggang.project.reactive.aliyun.oss.http.handler.error;

import com.fasterxml.jackson.databind.JsonNode;
import pro.chenggang.project.reactive.aliyun.oss.entity.http.MultiValueMap;
import pro.chenggang.project.reactive.aliyun.oss.exception.OssException;
import reactor.core.publisher.Mono;

/**
 * The Oss http response error handler.
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
public interface OssHttpResponseErrorHandler {

    /**
     * Handle error returns mono error.
     *
     * @param statusCode the status code
     * @param headers    the headers
     * @return the mono
     */
    OssException handleErrorResponse(int statusCode, MultiValueMap<String, String> headers);

    /**
     * Handle error returns mono error.
     *
     * @param statusCode   the response status code
     * @param headers      the response headers
     * @param responseBody the response body in ObjectNode type
     * @return the mono error
     */
    OssException handleErrorResponse(int statusCode, MultiValueMap<String, String> headers, JsonNode responseBody);

}
