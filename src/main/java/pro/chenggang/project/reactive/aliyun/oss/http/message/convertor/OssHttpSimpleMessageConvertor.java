package pro.chenggang.project.reactive.aliyun.oss.http.message.convertor;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * The Oss http simple message convertor.
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
public interface OssHttpSimpleMessageConvertor {

    /**
     * Whether support content type.
     *
     * @param contentType the content type
     * @return the boolean
     */
    boolean supportContentType(String contentType);

    /**
     * Convert request body to string.
     *
     * @param contentType the content type
     * @param requestBodyData    the body data
     * @return the string
     */
    String convertRequestBody(String contentType, Object requestBodyData);

    /**
     * Convert response data to target element type.
     *
     * @param <T>          the type parameter
     * @param contentType  the content type
     * @param responseData the response data
     * @param elementType  the element type
     * @return the target element type
     */
    <T> T convertResponse(String contentType, String responseData, Class<T> elementType);

    /**
     * Convert response data to target type .
     *
     * @param <T>          the type parameter
     * @param contentType  the content type
     * @param responseData the response data
     * @param elementType  the element type
     * @return the target type
     */
    <T> T convertResponse(String contentType, String responseData, TypeReference<T> elementType);

}
