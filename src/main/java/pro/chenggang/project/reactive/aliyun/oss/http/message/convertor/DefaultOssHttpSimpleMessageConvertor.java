package pro.chenggang.project.reactive.aliyun.oss.http.message.convertor;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pro.chenggang.project.reactive.aliyun.oss.exception.client.SerializeFailedException;
import pro.chenggang.project.reactive.aliyun.oss.http.message.JacksonObjectMapperBuilder;

import static cn.hutool.http.ContentType.JSON;
import static cn.hutool.http.ContentType.TEXT_XML;
import static cn.hutool.http.ContentType.XML;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultOssHttpSimpleMessageConvertor implements OssHttpSimpleMessageConvertor {

    private final ObjectMapper jsonObjectMapper = JacksonObjectMapperBuilder.json()
            .failOnUnknownProperties(false)
            .build();

    private final ObjectMapper xmlObjectMapper = JacksonObjectMapperBuilder.xml()
            .failOnUnknownProperties(false)
            .build();

    public static DefaultOssHttpSimpleMessageConvertor getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public boolean supportContentType(String contentType) {
        if (StrUtil.startWith(contentType, JSON.getValue())) {
            return true;
        }
        if (StrUtil.startWith(contentType, XML.getValue()) || StrUtil.equals(contentType, TEXT_XML.getValue())) {
            return true;
        }
        return StrUtil.startWith(contentType, "text/");
    }

    @Override
    public String convertRequestBody(Object bodyData, String contentType) {
        if (!supportContentType(contentType)) {
            throw new SerializeFailedException("Could not convert body data with [" + contentType + "]");
        }
        if (StrUtil.startWith(contentType, JSON.getValue())) {
            try {
                return this.jsonObjectMapper.writeValueAsString(bodyData);
            } catch (Exception e) {
                throw new SerializeFailedException(e);
            }
        }
        if (StrUtil.startWith(contentType, XML.getValue()) || StrUtil.equals(contentType, TEXT_XML.getValue())) {
            try {
                return this.xmlObjectMapper.writeValueAsString(bodyData);
            } catch (Exception e) {
                throw new SerializeFailedException(e);
            }
        }
        return bodyData.toString();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T convertResponse(String contentType, String responseData, Class<T> elementType) {
        if (String.class.equals(elementType)) {
            return (T) responseData;
        }
        if (StrUtil.startWith(contentType, JSON.getValue())) {
            try {
                return this.jsonObjectMapper.readValue(responseData, elementType);
            } catch (Exception e) {
                throw new SerializeFailedException(e);
            }
        }
        if (StrUtil.startWith(contentType, XML.getValue()) || StrUtil.equals(contentType, TEXT_XML.getValue())) {
            try {
                return this.xmlObjectMapper.readValue(responseData, elementType);
            } catch (Exception e) {
                throw new SerializeFailedException(e);
            }
        }
        throw new SerializeFailedException("Could not convert response data with [" + contentType + "]");
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T convertResponse(String contentType, String responseData, TypeReference<T> elementType) {
        if (String.class.equals(elementType.getType())) {
            return (T) responseData;
        }
        if (StrUtil.startWith(contentType, JSON.getValue())) {
            try {
                return this.jsonObjectMapper.readValue(responseData, elementType);
            } catch (Exception e) {
                throw new SerializeFailedException(e);
            }
        }
        if (StrUtil.startWith(contentType, XML.getValue()) || StrUtil.startWith(contentType, TEXT_XML.getValue())) {
            try {
                return this.xmlObjectMapper.readValue(responseData, elementType);
            } catch (Exception e) {
                throw new SerializeFailedException(e);
            }
        }
        throw new SerializeFailedException("Could not convert response data with [" + contentType + "]");
    }

    /**
     * DefaultOssHttpSimpleMessageConvertor instance holder
     */
    private static class InstanceHolder {

        private static final DefaultOssHttpSimpleMessageConvertor INSTANCE;

        static {
            INSTANCE = new DefaultOssHttpSimpleMessageConvertor();
        }
    }
}
