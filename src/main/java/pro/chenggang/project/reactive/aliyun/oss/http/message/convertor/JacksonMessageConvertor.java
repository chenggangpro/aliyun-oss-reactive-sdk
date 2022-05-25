package pro.chenggang.project.reactive.aliyun.oss.http.message.convertor;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * The jackson message convertor.
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
public interface JacksonMessageConvertor {

    /**
     * Convert json response to JsonNode.
     *
     * @param responseData the response data
     * @param contentType  the content type
     * @return the json object
     */
    JsonNode convertResponse(String responseData, String contentType);

    /**
     * Convert JsonNode to target type.
     *
     * @param <T>         the type parameter
     * @param contentType the content type
     * @param jsonNode    the json node
     * @param elementType the element type
     * @return the target type
     */
    <T> T convertResponse(String contentType, JsonNode jsonNode, Class<T> elementType);

}
