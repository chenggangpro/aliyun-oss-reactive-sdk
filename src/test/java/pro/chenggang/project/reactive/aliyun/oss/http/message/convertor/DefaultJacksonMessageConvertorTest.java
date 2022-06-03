package pro.chenggang.project.reactive.aliyun.oss.http.message.convertor;

import cn.hutool.http.ContentType;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pro.chenggang.project.reactive.aliyun.oss.exception.client.SerializeFailedException;

import java.util.HashMap;
import java.util.List;

/**
 * @author Gang Cheng
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
        JsonNode result = defaultJacksonMessageConvertor.convertResponse(ContentType.JSON.getValue(), this.json);
        Assertions.assertNotNull(result);
        result = defaultJacksonMessageConvertor.convertResponse(ContentType.XML.getValue(), this.xml);
        Assertions.assertNotNull(result);
        result = defaultJacksonMessageConvertor.convertResponse(ContentType.TEXT_XML.getValue(), this.xml);
        Assertions.assertNotNull(result);
        Assertions.assertThrowsExactly(SerializeFailedException.class,()-> defaultJacksonMessageConvertor.convertResponse(ContentType.TEXT_XML.getValue(), this.text));
        Assertions.assertThrowsExactly(SerializeFailedException.class,()-> defaultJacksonMessageConvertor.convertResponse(ContentType.TEXT_PLAIN.getValue(), this.text));
        Assertions.assertThrowsExactly(SerializeFailedException.class,()-> defaultJacksonMessageConvertor.convertResponse(ContentType.JSON.getValue(), this.text));
    }

    @Test
    void testConvertResponse2() {
        DefaultJacksonMessageConvertor defaultJacksonMessageConvertor = DefaultJacksonMessageConvertor.getInstance();
        JsonNode jsonNode = defaultJacksonMessageConvertor.convertResponse(ContentType.JSON.getValue(), this.json);
        HashMap result = defaultJacksonMessageConvertor.convertResponse(ContentType.JSON.getValue(), jsonNode, HashMap.class);
        Assertions.assertNotNull(result);
        jsonNode = defaultJacksonMessageConvertor.convertResponse(ContentType.XML.getValue(), this.xml);
        result = defaultJacksonMessageConvertor.convertResponse(ContentType.XML.getValue(), jsonNode, HashMap.class);
        Assertions.assertNotNull(result);
        jsonNode = defaultJacksonMessageConvertor.convertResponse(ContentType.TEXT_XML.getValue(), this.xml);
        result = defaultJacksonMessageConvertor.convertResponse(ContentType.TEXT_XML.getValue(), jsonNode, HashMap.class);
        Assertions.assertNotNull(result);
        Assertions.assertThrowsExactly(SerializeFailedException.class,() -> {
            JsonNode node = defaultJacksonMessageConvertor.convertResponse(ContentType.JSON.getValue(), this.json);
            defaultJacksonMessageConvertor.convertResponse(ContentType.TEXT_PLAIN.getValue(),node,Integer.class);
        });
    }

    @Test
    void testConvertResponseError() {
        DefaultJacksonMessageConvertor defaultJacksonMessageConvertor = DefaultJacksonMessageConvertor.getInstance();
        Assertions.assertThrows(SerializeFailedException.class,() -> defaultJacksonMessageConvertor.convertResponse(ContentType.JSON.getValue(), "\\{\\?ssdf@!:33<{{"));
        Assertions.assertThrows(SerializeFailedException.class,() -> defaultJacksonMessageConvertor.convertResponse(ContentType.XML.getValue(), "\\{\\?ssdf@!:33<{{"));
        Assertions.assertThrows(SerializeFailedException.class,() -> defaultJacksonMessageConvertor.convertResponse(ContentType.XML.getValue(),errorJsonNode(),String.class));
        Assertions.assertThrows(SerializeFailedException.class,() -> defaultJacksonMessageConvertor.convertResponse(ContentType.JSON.getValue(),errorJsonNode(),String.class));
    }

    private JsonNode errorJsonNode(){
        return new JsonNode() {
            @Override
            public <T extends JsonNode> T deepCopy() {
                throw new UnsupportedOperationException();
            }

            @Override
            public JsonNode get(int index) {
                throw new UnsupportedOperationException();
            }

            @Override
            public JsonNode path(String fieldName) {
                throw new UnsupportedOperationException();
            }

            @Override
            public JsonNode path(int index) {
                throw new UnsupportedOperationException();
            }

            @Override
            protected JsonNode _at(JsonPointer ptr) {
                throw new UnsupportedOperationException();
            }

            @Override
            public JsonNodeType getNodeType() {
                throw new UnsupportedOperationException();
            }

            @Override
            public String asText() {
                throw new UnsupportedOperationException();
            }

            @Override
            public JsonNode findValue(String fieldName) {
                throw new UnsupportedOperationException();
            }

            @Override
            public JsonNode findPath(String fieldName) {
                throw new UnsupportedOperationException();
            }

            @Override
            public JsonNode findParent(String fieldName) {
                throw new UnsupportedOperationException();
            }

            @Override
            public List<JsonNode> findValues(String fieldName, List<JsonNode> foundSoFar) {
                throw new UnsupportedOperationException();
            }

            @Override
            public List<String> findValuesAsText(String fieldName, List<String> foundSoFar) {
                throw new UnsupportedOperationException();
            }

            @Override
            public List<JsonNode> findParents(String fieldName, List<JsonNode> foundSoFar) {
                throw new UnsupportedOperationException();
            }

            @Override
            public String toString() {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean equals(Object o) {
                return false;
            }

            @Override
            public JsonToken asToken() {
                throw new UnsupportedOperationException();
            }

            @Override
            public JsonParser.NumberType numberType() {
                throw new UnsupportedOperationException();
            }

            @Override
            public JsonParser traverse() {
                throw new UnsupportedOperationException();
            }

            @Override
            public JsonParser traverse(ObjectCodec codec) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void serialize(JsonGenerator gen, SerializerProvider serializers) {

            }

            @Override
            public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) {

            }
        };
    }
}