package pro.chenggang.project.reactive.aliyun.oss.http.message.convertor;

import cn.hutool.http.ContentType;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pro.chenggang.project.reactive.aliyun.oss.exception.client.SerializeFailedException;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
class DefaultOssHttpSimpleMessageConvertorTest {

    String xml;
    String json;
    String text;
    String none = null;

    @BeforeEach
    public void setup() {
        this.xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<Error>\n" +
                "  <Code>NoSuchKey</Code>\n" +
                "  <Message>The specified key does not exist.</Message>\n" +
                "  <RequestId>5CAC0FEADE0170*****</RequestId>\n" +
                "  <HostId>versioning-get.oss-cn-hangzhou.aliyun*****</HostId>\n" +
                "  <Key>example</Key>\n" +
                "</Error>";
        this.json = "{\n" +
                "  \"code\": \"00000\",\n" +
                "  \"message\": \"Success\",\n" +
                "  \"data\": \"6213071f19ecdbd9f02f1b0e\"" +
                "}";
        this.text = "text";
    }

    @Test
    void testGetInstance() {
        DefaultOssHttpSimpleMessageConvertor result = DefaultOssHttpSimpleMessageConvertor.getInstance();
        Assertions.assertNotNull(result);
    }

    @Test
    void testSupportContentType() {
        DefaultOssHttpSimpleMessageConvertor defaultOssHttpSimpleMessageConvertor = DefaultOssHttpSimpleMessageConvertor.getInstance();
        Assertions.assertTrue(defaultOssHttpSimpleMessageConvertor.supportContentType(ContentType.JSON.getValue()));
        Assertions.assertTrue(defaultOssHttpSimpleMessageConvertor.supportContentType(ContentType.XML.getValue()));
        Assertions.assertTrue(defaultOssHttpSimpleMessageConvertor.supportContentType(ContentType.TEXT_XML.getValue()));
        Assertions.assertTrue(defaultOssHttpSimpleMessageConvertor.supportContentType(ContentType.TEXT_PLAIN.getValue()));
        Assertions.assertFalse(defaultOssHttpSimpleMessageConvertor.supportContentType(ContentType.OCTET_STREAM.getValue()));
    }

    @Test
    void testConvertRequestBody() {
        DefaultOssHttpSimpleMessageConvertor defaultOssHttpSimpleMessageConvertor = DefaultOssHttpSimpleMessageConvertor.getInstance();
        String result = defaultOssHttpSimpleMessageConvertor.convertRequestBody(ContentType.JSON.getValue(), this.json);
        Assertions.assertNotNull(result);
        result = defaultOssHttpSimpleMessageConvertor.convertRequestBody(ContentType.XML.getValue(), this.xml);
        Assertions.assertNotNull(result);
        result = defaultOssHttpSimpleMessageConvertor.convertRequestBody(ContentType.TEXT_XML.getValue(), this.xml);
        Assertions.assertNotNull(result);
        result = defaultOssHttpSimpleMessageConvertor.convertRequestBody(ContentType.TEXT_PLAIN.getValue(), this.text);
        Assertions.assertNotNull(result);
        Assertions.assertThrowsExactly(SerializeFailedException.class, () -> defaultOssHttpSimpleMessageConvertor.convertRequestBody(ContentType.OCTET_STREAM.getValue(), this.text));
    }

    @Test
    void testConvertResponse() {
        DefaultOssHttpSimpleMessageConvertor defaultOssHttpSimpleMessageConvertor = DefaultOssHttpSimpleMessageConvertor.getInstance();
        HashMap result = defaultOssHttpSimpleMessageConvertor.convertResponse(ContentType.JSON.getValue(),this.json,  HashMap.class);
        Assertions.assertNotNull(result);
        result = defaultOssHttpSimpleMessageConvertor.convertResponse( ContentType.XML.getValue(), this.xml,HashMap.class);
        Assertions.assertNotNull(result);
        result = defaultOssHttpSimpleMessageConvertor.convertResponse(ContentType.TEXT_XML.getValue(), this.xml, HashMap.class);
        Assertions.assertNotNull(result);
        String data = defaultOssHttpSimpleMessageConvertor.convertResponse(ContentType.TEXT_PLAIN.getValue(), this.text, String.class);
        Assertions.assertNotNull(data);
        Assertions.assertThrowsExactly(SerializeFailedException.class, () -> defaultOssHttpSimpleMessageConvertor.convertResponse(ContentType.TEXT_PLAIN.getValue(),this.text,HashMap.class));
        Assertions.assertThrowsExactly(SerializeFailedException.class, () -> defaultOssHttpSimpleMessageConvertor.convertResponse(ContentType.OCTET_STREAM.getValue(),this.text,  HashMap.class));
    }

    @Test
    void testConvertResponse2() {
        DefaultOssHttpSimpleMessageConvertor defaultOssHttpSimpleMessageConvertor = DefaultOssHttpSimpleMessageConvertor.getInstance();
        HashMap result = defaultOssHttpSimpleMessageConvertor.convertResponse(ContentType.JSON.getValue(), this.json, new TypeReference<HashMap>() {});
        Assertions.assertNotNull(result);
        result = defaultOssHttpSimpleMessageConvertor.convertResponse(ContentType.XML.getValue(), this.xml, new TypeReference<HashMap>() {});
        Assertions.assertNotNull(result);
        result = defaultOssHttpSimpleMessageConvertor.convertResponse(ContentType.TEXT_XML.getValue(), this.xml, new TypeReference<HashMap>() {});
        Assertions.assertNotNull(result);
        String data = defaultOssHttpSimpleMessageConvertor.convertResponse(ContentType.TEXT_PLAIN.getValue(), this.text, new TypeReference<String>() {});
        Assertions.assertNotNull(data);
        Assertions.assertThrowsExactly(SerializeFailedException.class, () -> defaultOssHttpSimpleMessageConvertor.convertResponse(ContentType.TEXT_PLAIN.getValue(), this.text,new TypeReference<HashMap>() {}));
        Assertions.assertThrowsExactly(SerializeFailedException.class, () -> defaultOssHttpSimpleMessageConvertor.convertResponse(ContentType.OCTET_STREAM.getValue(), this.text,new TypeReference<HashMap>() {}));
    }

    @Test
    void testConvertResponseError() {
        DefaultOssHttpSimpleMessageConvertor defaultOssHttpSimpleMessageConvertor = DefaultOssHttpSimpleMessageConvertor.getInstance();
        Assertions.assertThrows(SerializeFailedException.class,() -> defaultOssHttpSimpleMessageConvertor.convertResponse(ContentType.JSON.getValue(),"\\{\\?ssdf@!:33<{{",  HashMap.class));
        Assertions.assertThrows(SerializeFailedException.class,() -> defaultOssHttpSimpleMessageConvertor.convertResponse(ContentType.XML.getValue(),"\\{\\?ssdf@!:33<{{",  HashMap.class));
        Assertions.assertThrows(SerializeFailedException.class,() -> defaultOssHttpSimpleMessageConvertor.convertResponse(ContentType.JSON.getValue(),"\\{\\?ssdf@!:33<{{",  new TypeReference<HashMap>() {}));
        Assertions.assertThrows(SerializeFailedException.class,() -> defaultOssHttpSimpleMessageConvertor.convertResponse(ContentType.XML.getValue(),"\\{\\?ssdf@!:33<{{",  new TypeReference<HashMap>() {}));
        Assertions.assertThrows(SerializeFailedException.class,() -> defaultOssHttpSimpleMessageConvertor.convertRequestBody(ContentType.JSON.getValue(), errorType()));
        Assertions.assertThrows(SerializeFailedException.class,() -> defaultOssHttpSimpleMessageConvertor.convertRequestBody(ContentType.XML.getValue(), errorType()));
    }

    private Object errorType(){
        return new TypeReference<String>() {
            @Override
            public Type getType() {
                throw new RuntimeException();
            }
        };
    }
}