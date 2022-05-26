package pro.chenggang.project.reactive.aliyun.oss.http.message.convertor;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.JsonNode;
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
public class DefaultJacksonMessageConvertor implements JacksonMessageConvertor {

    private final ObjectMapper jsonObjectMapper = JacksonObjectMapperBuilder.json()
            .failOnUnknownProperties(false)
            .build();

    private final ObjectMapper xmlObjectMapper = JacksonObjectMapperBuilder.xml()
            .failOnUnknownProperties(false)
            .build();

    public static DefaultJacksonMessageConvertor getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public JsonNode convertResponse(String contentType, String responseData) {
        if (StrUtil.startWith(contentType, JSON.getValue())) {
            try {
                return this.jsonObjectMapper.readTree(responseData);
            } catch (Exception e) {
                throw new SerializeFailedException(e);
            }
        }
        if (StrUtil.startWith(contentType, XML.getValue()) || StrUtil.startWith(contentType, TEXT_XML.getValue())) {
            try {
                return this.xmlObjectMapper.readTree(responseData);
            } catch (Exception e) {
                throw new SerializeFailedException(e);
            }
        }
        throw new SerializeFailedException("Could not convert response data with [" + contentType + "]");
    }

    @Override
    public <T> T convertResponse(String contentType, JsonNode responseData, Class<T> elementType) {
        if (StrUtil.startWith(contentType, JSON.getValue())) {
            try {
                return this.jsonObjectMapper.treeToValue(responseData, elementType);
            } catch (Exception e) {
                throw new SerializeFailedException(e);
            }
        }
        if (StrUtil.startWith(contentType, XML.getValue()) || StrUtil.startWith(contentType, TEXT_XML.getValue())) {
            try {
                return this.xmlObjectMapper.treeToValue(responseData, elementType);
            } catch (Exception e) {
                throw new SerializeFailedException(e);
            }
        }
        throw new SerializeFailedException("Could not convert response data with [" + contentType + "]");
    }

    /**
     * DefaultJacksonMessageConvertor instance holder
     */
    private static class InstanceHolder {

        private static final DefaultJacksonMessageConvertor INSTANCE;

        static {
            INSTANCE = new DefaultJacksonMessageConvertor();
        }
    }
}
