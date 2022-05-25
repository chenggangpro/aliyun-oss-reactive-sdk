package pro.chenggang.project.reactive.aliyun.oss.http.message.convertor;

import cn.hutool.http.ContentType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pro.chenggang.project.reactive.aliyun.oss.exception.client.SerializeFailedException;

import java.util.HashMap;

import static org.mockito.Mockito.*;

/**
 * @author evans
 * @version 1.0.0
 * @since 1.0.0
 */
class DefaultJacksonMessageConvertorTest {

    String xml;
    String json;
    String text;

    @BeforeEach
    public void setup(){
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
        DefaultJacksonMessageConvertor result = DefaultJacksonMessageConvertor.getInstance();
        Assertions.assertNotNull(result);
    }

    @Test
    void testConvertResponse() {
        DefaultJacksonMessageConvertor defaultJacksonMessageConvertor = DefaultJacksonMessageConvertor.getInstance();
        JsonNode result = defaultJacksonMessageConvertor.convertResponse(this.json, ContentType.JSON.getValue());
        Assertions.assertNotNull(result);
        result = defaultJacksonMessageConvertor.convertResponse(this.xml, ContentType.XML.getValue());
        Assertions.assertNotNull(result);
        result = defaultJacksonMessageConvertor.convertResponse(this.xml, ContentType.TEXT_XML.getValue());
        Assertions.assertNotNull(result);
        Assertions.assertThrowsExactly(SerializeFailedException.class,()-> defaultJacksonMessageConvertor.convertResponse(this.text, ContentType.TEXT_XML.getValue()));
        Assertions.assertThrowsExactly(SerializeFailedException.class,()-> defaultJacksonMessageConvertor.convertResponse(this.text, ContentType.TEXT_PLAIN.getValue()));
        Assertions.assertThrowsExactly(SerializeFailedException.class,()-> defaultJacksonMessageConvertor.convertResponse(this.text, ContentType.JSON.getValue()));
    }

    @Test
    void testConvertResponse2() {
        DefaultJacksonMessageConvertor defaultJacksonMessageConvertor = DefaultJacksonMessageConvertor.getInstance();
        JsonNode jsonNode = defaultJacksonMessageConvertor.convertResponse(this.json, ContentType.JSON.getValue());
        HashMap result = defaultJacksonMessageConvertor.convertResponse(ContentType.JSON.getValue(), jsonNode, HashMap.class);
        Assertions.assertNotNull(result);
        jsonNode = defaultJacksonMessageConvertor.convertResponse(this.xml, ContentType.XML.getValue());
        result = defaultJacksonMessageConvertor.convertResponse(ContentType.XML.getValue(), jsonNode, HashMap.class);
        Assertions.assertNotNull(result);
        jsonNode = defaultJacksonMessageConvertor.convertResponse(this.xml, ContentType.TEXT_XML.getValue());
        result = defaultJacksonMessageConvertor.convertResponse(ContentType.TEXT_XML.getValue(), jsonNode, HashMap.class);
        Assertions.assertNotNull(result);
        Assertions.assertThrowsExactly(SerializeFailedException.class,() -> {
            JsonNode node = defaultJacksonMessageConvertor.convertResponse(this.json, ContentType.JSON.getValue());
            defaultJacksonMessageConvertor.convertResponse(ContentType.TEXT_PLAIN.getValue(),node,Integer.class);
        });
    }
}