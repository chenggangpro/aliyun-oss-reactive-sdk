package pro.chenggang.project.reactive.aliyun.oss.http.message;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.dataformat.xml.XmlTypeResolverBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
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
        ObjectMapper result = JacksonObjectMapperBuilder.json()
                .build();
        Assertions.assertNotNull(result);
    }

    @Test
    void testXml() {
        ObjectMapper result = JacksonObjectMapperBuilder.xml()
                .defaultUseWrapper(null)
                .build();
        Assertions.assertNotNull(result);
    }

    @Test
    void testDateFormat() {
        ObjectMapper result = jacksonObjectMapperBuilder.dateFormat(DateFormat.getInstance()).build();
        Assertions.assertNotNull(result);
    }

    @Test
    void testSimpleDateFormat() {
        ObjectMapper result = jacksonObjectMapperBuilder.simpleDateFormat("yyyy-MM-dd").build();
        Assertions.assertNotNull(result);
    }

    @Test
    void testLocale() {
        ObjectMapper result = jacksonObjectMapperBuilder.locale(Locale.getDefault()).build();
        Assertions.assertNotNull(result);
    }

    @Test
    void testTimeZone() {
        ObjectMapper result = jacksonObjectMapperBuilder.timeZone(TimeZone.getDefault()).build();
        Assertions.assertNotNull(result);
    }

    @Test
    void testAnnotationIntrospector() {
        ObjectMapper result = jacksonObjectMapperBuilder.annotationIntrospector(NopAnnotationIntrospector.instance).build();
        Assertions.assertNotNull(result);
    }

    @Test
    void testPropertyNamingStrategy() {
        ObjectMapper result = jacksonObjectMapperBuilder.propertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE).build();
        Assertions.assertNotNull(result);
    }

    @Test
    void testDefaultTyping() {
        ObjectMapper result = jacksonObjectMapperBuilder.defaultTyping(XmlTypeResolverBuilder.noTypeInfoBuilder()).build();
        Assertions.assertNotNull(result);
    }

    @Test
    void testSerializationInclusion() {
        ObjectMapper result = jacksonObjectMapperBuilder.serializationInclusion(JsonInclude.Include.ALWAYS).build();
        Assertions.assertNotNull(result);
    }

    @Test
    void testSerializationInclusion2() {
        ObjectMapper result = jacksonObjectMapperBuilder.serializationInclusion(JsonInclude.Value.empty()).build();
        Assertions.assertNotNull(result);
    }

    @Test
    void testFilters() {
        ObjectMapper result = jacksonObjectMapperBuilder.filters(new SimpleFilterProvider()).build();
        Assertions.assertNotNull(result);
    }

    @Test
    void testSerializers() {
        ObjectMapper result = jacksonObjectMapperBuilder.serializers(new DateSerializer()).build();
        Assertions.assertNotNull(result);
        Assertions.assertThrows(IllegalArgumentException.class,()->{
            jacksonObjectMapperBuilder.serializers(new JsonSerializer(){
                        @Override
                        public Class handledType() {
                            return null;
                        }

                        @Override
                        public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

                        }
                    })
                    .build();
        });
        Assertions.assertThrows(IllegalArgumentException.class,()->{
            jacksonObjectMapperBuilder.serializers(new JsonSerializer(){
                        @Override
                        public Class handledType() {
                            return Object.class;
                        }

                        @Override
                        public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

                        }
                    })
                    .build();
        });
    }

    @Test
    void testSerializerByType() {
        ObjectMapper result = jacksonObjectMapperBuilder.serializerByType(Date.class, new DateSerializer()).build();
        Assertions.assertNotNull(result);
    }

    @Test
    void testDeserializers() {
        ObjectMapper result = jacksonObjectMapperBuilder.deserializers(new NumberDeserializers.BigDecimalDeserializer()).build();
        Assertions.assertNotNull(result);
        Assertions.assertThrows(IllegalArgumentException.class,()->{
            jacksonObjectMapperBuilder.deserializers(new JsonDeserializer(){
                        @Override
                        public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
                            return null;
                        }

                        @Override
                        public Class handledType() {
                            return null;
                        }
                    })
                    .build();
        });
        Assertions.assertThrows(IllegalArgumentException.class,()->{
            jacksonObjectMapperBuilder.deserializers(new JsonDeserializer(){

                        @Override
                        public Class handledType() {
                            return Object.class;
                        }

                        @Override
                        public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
                            return null;
                        }
                    })
                    .build();
        });
    }

    @Test
    void testDeserializerByType() {
        ObjectMapper result = jacksonObjectMapperBuilder.deserializerByType(BigDecimal.class, new NumberDeserializers.BigDecimalDeserializer()).build();
        Assertions.assertNotNull(result);
    }

    @Test
    void testAutoDetectFields() {
        ObjectMapper result = jacksonObjectMapperBuilder.autoDetectFields(true).build();
        Assertions.assertNotNull(result);
    }

    @Test
    void testAutoDetectGettersSetters() {
        ObjectMapper result = jacksonObjectMapperBuilder.autoDetectGettersSetters(true).build();
        Assertions.assertNotNull(result);
    }

    @Test
    void testDefaultViewInclusion() {
        ObjectMapper result = jacksonObjectMapperBuilder.defaultViewInclusion(true).build();
        Assertions.assertNotNull(result);
    }

    @Test
    void testFailOnUnknownProperties() {
        ObjectMapper result = jacksonObjectMapperBuilder.failOnUnknownProperties(true).build();
        Assertions.assertNotNull(result);
    }

    @Test
    void testFailOnEmptyBeans() {
        ObjectMapper result = jacksonObjectMapperBuilder.failOnEmptyBeans(true).build();
        Assertions.assertNotNull(result);
    }

    @Test
    void testIndentOutput() {
        ObjectMapper result = jacksonObjectMapperBuilder.indentOutput(true).build();
        Assertions.assertNotNull(result);
    }

    @Test
    void testDefaultUseWrapper() {
        ObjectMapper result = jacksonObjectMapperBuilder.defaultUseWrapper(true).build();
        Assertions.assertNotNull(result);
    }

    @Test
    void testVisibility() {
        ObjectMapper result = jacksonObjectMapperBuilder.visibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.ANY).build();
        Assertions.assertNotNull(result);
    }

    @Test
    void testFeaturesToEnable() {
        ObjectMapper result = jacksonObjectMapperBuilder.featuresToEnable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .featuresToEnable(JsonParser.Feature.ALLOW_COMMENTS)
                .featuresToEnable(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM)
                .build();
        Assertions.assertNotNull(result);
        Assertions.assertThrows(IllegalStateException.class,() -> {
            jacksonObjectMapperBuilder.featuresToEnable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                    .featuresToEnable(new Object())
                    .build();
        });
    }

    @Test
    void testFeaturesToDisable() {
        ObjectMapper result = jacksonObjectMapperBuilder.featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS).build();
        Assertions.assertNotNull(result);
    }

    @Test
    void testHandlerInstantiator() {
        ObjectMapper result = jacksonObjectMapperBuilder.handlerInstantiator(new HandlerInstantiator() {
            @Override
            public JsonDeserializer<?> deserializerInstance(DeserializationConfig config, Annotated annotated, Class<?> deserClass) {
                return null;
            }

            @Override
            public KeyDeserializer keyDeserializerInstance(DeserializationConfig config, Annotated annotated, Class<?> keyDeserClass) {
                return null;
            }

            @Override
            public JsonSerializer<?> serializerInstance(SerializationConfig config, Annotated annotated, Class<?> serClass) {
                return null;
            }

            @Override
            public TypeResolverBuilder<?> typeResolverBuilderInstance(MapperConfig<?> config, Annotated annotated, Class<?> builderClass) {
                return null;
            }

            @Override
            public TypeIdResolver typeIdResolverInstance(MapperConfig<?> config, Annotated annotated, Class<?> resolverClass) {
                return null;
            }
        }).build();
        Assertions.assertNotNull(result);
    }

    @Test
    void testPostConfigurer() {
        ObjectMapper result = jacksonObjectMapperBuilder.postConfigurer(objectMapper -> {})
                .postConfigurer(objectMapper -> {})
                .build();
        Assertions.assertNotNull(result);
    }

    @Test
    void testBuild() {
        ObjectMapper objectMapper = jacksonObjectMapperBuilder.build();
        Assertions.assertNotNull(objectMapper);
    }

}
