package pro.chenggang.project.reactive.aliyun.oss.http.message;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;
import com.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.databind.ser.std.NumberSerializer;
import com.fasterxml.jackson.databind.ser.std.NumberSerializers;
import com.fasterxml.jackson.databind.ser.std.StdArraySerializers;
import com.fasterxml.jackson.dataformat.xml.XmlTypeResolverBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pro.chenggang.project.reactive.aliyun.oss.http.message.JacksonObjectMapperBuilder;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
class JacksonObjectMapperBuilderTest {

    private final JacksonObjectMapperBuilder jacksonObjectMapperBuilder = JacksonObjectMapperBuilder.json();

    @Test
    void testJson() {
        JacksonObjectMapperBuilder result = JacksonObjectMapperBuilder.json();
        Assertions.assertNotNull(result);
    }

    @Test
    void testXml() {
        JacksonObjectMapperBuilder result = JacksonObjectMapperBuilder.xml();
        Assertions.assertNotNull(result);
    }

    @Test
    void testDateFormat() {
        JacksonObjectMapperBuilder result = jacksonObjectMapperBuilder.dateFormat(DateFormat.getInstance());
        Assertions.assertNotNull(result);
    }

    @Test
    void testSimpleDateFormat() {
        JacksonObjectMapperBuilder result = jacksonObjectMapperBuilder.simpleDateFormat("yyyy-MM-dd");
        Assertions.assertNotNull(result);
    }

    @Test
    void testLocale() {
        JacksonObjectMapperBuilder result = jacksonObjectMapperBuilder.locale(Locale.getDefault());
        Assertions.assertNotNull(result);
    }

    @Test
    void testTimeZone() {
        JacksonObjectMapperBuilder result = jacksonObjectMapperBuilder.timeZone(TimeZone.getDefault());
        Assertions.assertNotNull(result);
    }

    @Test
    void testAnnotationIntrospector() {
        JacksonObjectMapperBuilder result = jacksonObjectMapperBuilder.annotationIntrospector(NopAnnotationIntrospector.instance);
        Assertions.assertNotNull(result);
    }

    @Test
    void testPropertyNamingStrategy() {
        JacksonObjectMapperBuilder result = jacksonObjectMapperBuilder.propertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
        Assertions.assertNotNull(result);
    }

    @Test
    void testDefaultTyping() {
        JacksonObjectMapperBuilder result = jacksonObjectMapperBuilder.defaultTyping(XmlTypeResolverBuilder.noTypeInfoBuilder());
        Assertions.assertNotNull(result);
    }

    @Test
    void testSerializationInclusion() {
        JacksonObjectMapperBuilder result = jacksonObjectMapperBuilder.serializationInclusion(JsonInclude.Include.ALWAYS);
        Assertions.assertNotNull( result);
    }

    @Test
    void testSerializationInclusion2() {
        JacksonObjectMapperBuilder result = jacksonObjectMapperBuilder.serializationInclusion(JsonInclude.Value.empty());
        Assertions.assertNotNull(result);
    }

    @Test
    void testFilters() {
        JacksonObjectMapperBuilder result = jacksonObjectMapperBuilder.filters(new SimpleFilterProvider());
        Assertions.assertNotNull(result);
    }

    @Test
    void testSerializers() {
        JacksonObjectMapperBuilder result = jacksonObjectMapperBuilder.serializers(new DateSerializer());
        Assertions.assertNotNull(result);
    }

    @Test
    void testSerializerByType() {
        JacksonObjectMapperBuilder result = jacksonObjectMapperBuilder.serializerByType(Date.class, new DateSerializer());
        Assertions.assertNotNull(result);
    }

    @Test
    void testDeserializers() {
        JacksonObjectMapperBuilder result = jacksonObjectMapperBuilder.deserializers(new NumberDeserializers.BigDecimalDeserializer());
        Assertions.assertNotNull(result);
    }

    @Test
    void testDeserializerByType() {
        JacksonObjectMapperBuilder result = jacksonObjectMapperBuilder.deserializerByType(null, null);
        Assertions.assertNotNull(result);
    }

    @Test
    void testAutoDetectFields() {
        JacksonObjectMapperBuilder result = jacksonObjectMapperBuilder.autoDetectFields(true);
        Assertions.assertNotNull(result);
    }

    @Test
    void testAutoDetectGettersSetters() {
        JacksonObjectMapperBuilder result = jacksonObjectMapperBuilder.autoDetectGettersSetters(true);
        Assertions.assertNotNull(result);
    }

    @Test
    void testDefaultViewInclusion() {
        JacksonObjectMapperBuilder result = jacksonObjectMapperBuilder.defaultViewInclusion(true);
        Assertions.assertNotNull(result);
    }

    @Test
    void testFailOnUnknownProperties() {
        JacksonObjectMapperBuilder result = jacksonObjectMapperBuilder.failOnUnknownProperties(true);
        Assertions.assertNotNull(result);
    }

    @Test
    void testFailOnEmptyBeans() {
        JacksonObjectMapperBuilder result = jacksonObjectMapperBuilder.failOnEmptyBeans(true);
        Assertions.assertNotNull(result);
    }

    @Test
    void testIndentOutput() {
        JacksonObjectMapperBuilder result = jacksonObjectMapperBuilder.indentOutput(true);
        Assertions.assertNotNull(result);
    }

    @Test
    void testDefaultUseWrapper() {
        JacksonObjectMapperBuilder result = jacksonObjectMapperBuilder.defaultUseWrapper(true);
        Assertions.assertNotNull(result);
    }

    @Test
    void testVisibility() {
        JacksonObjectMapperBuilder result = jacksonObjectMapperBuilder.visibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.ANY);
        Assertions.assertNotNull(result);
    }

    @Test
    void testFeaturesToEnable() {
        JacksonObjectMapperBuilder result = jacksonObjectMapperBuilder.featuresToEnable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        Assertions.assertNotNull(result);
    }

    @Test
    void testFeaturesToDisable() {
        JacksonObjectMapperBuilder result = jacksonObjectMapperBuilder.featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        Assertions.assertNotNull(result);
    }

    @Test
    void testHandlerInstantiator() {
        JacksonObjectMapperBuilder result = jacksonObjectMapperBuilder.handlerInstantiator(null);
        Assertions.assertNotNull(result);
    }

    @Test
    void testPostConfigurer() {
        JacksonObjectMapperBuilder result = jacksonObjectMapperBuilder.postConfigurer(objectMapper -> {});
        Assertions.assertNotNull(result);
    }

    @Test
    void testBuild() {
        ObjectMapper objectMapper = jacksonObjectMapperBuilder.build();
        Assertions.assertNotNull(objectMapper);
    }

}
