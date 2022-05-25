package pro.chenggang.project.reactive.aliyun.oss.http.handler.error;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import pro.chenggang.project.reactive.aliyun.oss.entity.http.MultiValueMap;
import pro.chenggang.project.reactive.aliyun.oss.exception.OssException;
import pro.chenggang.project.reactive.aliyun.oss.exception.common.HttpStatusErrorOssException;
import pro.chenggang.project.reactive.aliyun.oss.exception.common.UnidentifiedOssException;
import pro.chenggang.project.reactive.aliyun.oss.http.message.JacksonObjectMapperBuilder;
import pro.chenggang.project.reactive.aliyun.oss.http.message.convertor.DefaultJacksonMessageConvertor;
import pro.chenggang.project.reactive.aliyun.oss.http.message.convertor.DefaultOssHttpSimpleMessageConvertor;
import pro.chenggang.project.reactive.aliyun.oss.http.message.convertor.JacksonMessageConvertor;
import pro.chenggang.project.reactive.aliyun.oss.http.message.convertor.OssHttpSimpleMessageConvertor;
import pro.chenggang.project.reactive.aliyun.oss.option.internal.OssErrorCode;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Optional;

import static cn.hutool.http.Header.CONTENT_TYPE;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
public class DefaultOssHttpResponseErrorHandler implements OssHttpResponseErrorHandler{

    private final JacksonMessageConvertor jacksonMessageConvertor = DefaultJacksonMessageConvertor.getInstance();

    @Override
    public OssException handleErrorResponse(int statusCode, MultiValueMap<String, String> headers) {
        return new HttpStatusErrorOssException(statusCode,headers);
    }

    @Override
    public OssException handleErrorResponse(int statusCode, MultiValueMap<String, String> headers, JsonNode responseBody) {
        return Optional.ofNullable(responseBody)
                .map(body -> body.get("Code"))
                .flatMap(code -> OssErrorCode.valueOfErrorCode(code.asText()))
                .map(ossErrorCode -> {
                    String contentType = headers.getFirst(CONTENT_TYPE.getValue());
                    OssException ossException = jacksonMessageConvertor.convertResponse(contentType, responseBody, ossErrorCode.getErrorExceptionClass());
                    return ossException;
                })
                .orElseGet(() -> new UnidentifiedOssException(statusCode, headers, responseBody == null ? null : responseBody.toString()));
    }

}
