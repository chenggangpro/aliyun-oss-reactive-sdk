package pro.chenggang.project.reactive.aliyun.oss.http.handler.error;

import cn.hutool.http.ContentType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pro.chenggang.project.reactive.aliyun.oss.entity.http.MultiValueMap;
import pro.chenggang.project.reactive.aliyun.oss.exception.OssException;
import pro.chenggang.project.reactive.aliyun.oss.exception.common.HttpStatusErrorOssException;
import pro.chenggang.project.reactive.aliyun.oss.exception.common.UnidentifiedOssException;
import pro.chenggang.project.reactive.aliyun.oss.http.message.convertor.DefaultJacksonMessageConvertor;
import pro.chenggang.project.reactive.aliyun.oss.http.message.convertor.JacksonMessageConvertor;

import static cn.hutool.http.Header.CONTENT_TYPE;
import static org.mockito.Mockito.*;

/**
 * @author evans
 * @version 1.0.0
 * @since 1.0.0
 */
class DefaultOssHttpResponseErrorHandlerTest {

    String xml;
    String errorXml;

    @BeforeEach
    public void setup() {
        this.xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<Error>\n" +
                "  <Code>AccessDenied</Code>\n" +
                "  <Message>The specified key does not exist.</Message>\n" +
                "  <RequestId>5CAC0FEADE0170*****</RequestId>\n" +
                "  <HostId>versioning-get.oss-cn-hangzhou.aliyun*****</HostId>\n" +
                "  <Key>example</Key>\n" +
                "</Error>";
        this.errorXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<Error>\n" +
                "  <Message>The specified key does not exist.</Message>\n" +
                "  <RequestId>5CAC0FEADE0170*****</RequestId>\n" +
                "  <HostId>versioning-get.oss-cn-hangzhou.aliyun*****</HostId>\n" +
                "  <Key>example</Key>\n" +
                "</Error>";
    }


    @Test
    void testHandleErrorResponse() {
        MultiValueMap<String, String> headers = new MultiValueMap<>();
        DefaultOssHttpResponseErrorHandler defaultOssHttpResponseErrorHandler = new DefaultOssHttpResponseErrorHandler();
        OssException result = defaultOssHttpResponseErrorHandler.handleErrorResponse(0, new MultiValueMap<>());
        Assertions.assertTrue(result instanceof HttpStatusErrorOssException);
        HttpStatusErrorOssException httpStatusErrorOssException = (HttpStatusErrorOssException) result;
        Assertions.assertEquals(0, httpStatusErrorOssException.getStatusCode());
        Assertions.assertEquals(headers, httpStatusErrorOssException.getResponseHeaders());
    }

    @Test
    void testHandleErrorResponse2() {
        JsonNode jsonNode = DefaultJacksonMessageConvertor.getInstance().convertResponse(this.xml, ContentType.XML.getValue());
        DefaultOssHttpResponseErrorHandler defaultOssHttpResponseErrorHandler = new DefaultOssHttpResponseErrorHandler();
        MultiValueMap<String, String> headers = new MultiValueMap<>();
        headers.add(CONTENT_TYPE.getValue(),ContentType.XML.getValue());
        OssException ossException = defaultOssHttpResponseErrorHandler.handleErrorResponse(0, headers, jsonNode);
        Assertions.assertNotNull(ossException);
        ossException = defaultOssHttpResponseErrorHandler.handleErrorResponse(0, headers, null);
        Assertions.assertTrue(ossException instanceof UnidentifiedOssException);
        ossException = defaultOssHttpResponseErrorHandler.handleErrorResponse(0, headers, new ObjectNode(JsonNodeFactory.instance));
        Assertions.assertTrue(ossException instanceof UnidentifiedOssException);
        JsonNode errorJsonNode = DefaultJacksonMessageConvertor.getInstance().convertResponse(this.errorXml, ContentType.XML.getValue());
        ossException = defaultOssHttpResponseErrorHandler.handleErrorResponse(0, headers, errorJsonNode);
        Assertions.assertTrue(ossException instanceof UnidentifiedOssException);
    }
}
