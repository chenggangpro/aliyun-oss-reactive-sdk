package pro.chenggang.project.reactive.aliyun.oss.http.message;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLResolver;
import java.io.ByteArrayInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * The Jackson object mapper builder.
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
public class JacksonObjectMapperBuilder {

    private final Map<Class<?>, JsonSerializer<?>> serializers = new LinkedHashMap<>();
    private final Map<Class<?>, JsonDeserializer<?>> deserializers = new LinkedHashMap<>();
    private final Map<PropertyAccessor, JsonAutoDetect.Visibility> visibilities = new LinkedHashMap<>();
    private final Map<Object, Boolean> features = new LinkedHashMap<>();
    private final boolean createXmlMapper;
    private final ClassLoader moduleClassLoader = getClass().getClassLoader();
    private DateFormat dateFormat;
    private Locale locale;
    private TimeZone timeZone;
    private AnnotationIntrospector annotationIntrospector;
    private PropertyNamingStrategy propertyNamingStrategy;
    private TypeResolverBuilder<?> defaultTyping;
    private JsonInclude.Value serializationInclusion;
    private FilterProvider filters;
    private HandlerInstantiator handlerInstantiator;
    private Boolean defaultUseWrapper = true;
    private Consumer<ObjectMapper> configurer;

    /**
     * Instantiates a new Jackson object mapper builder.
     */
    private JacksonObjectMapperBuilder() {
        this.createXmlMapper = false;
    }

    /**
     * Instantiates a new Jackson object mapper builder.
     *
     * @param createXmlMapper determine whether to create xml mapper
     */
    private JacksonObjectMapperBuilder(boolean createXmlMapper) {
        this.createXmlMapper = createXmlMapper;
    }

    /**
     * new Jackson2ObjectMapperBuilder instance to build ObjectMapper
     *
     * @return the jackson object mapper builder
     */
    public static JacksonObjectMapperBuilder json() {
        return new JacksonObjectMapperBuilder();
    }

    /**
     * new Jackson2ObjectMapperBuilder instance to build XmlMapper
     *
     * @return the jackson object mapper builder
     */
    public static JacksonObjectMapperBuilder xml() {
        return new JacksonObjectMapperBuilder(true);
    }


    /**
     * Customize Date format .
     *
     * @param dateFormat the date format
     * @return the jackson object mapper builder
     */
    public JacksonObjectMapperBuilder dateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
        return this;
    }

    /**
     * Customize Simple date format .
     *
     * @param format the format
     * @return the jackson object mapper builder
     */
    public JacksonObjectMapperBuilder simpleDateFormat(String format) {
        this.dateFormat = new SimpleDateFormat(format);
        return this;
    }

    /**
     * Customize Locale.
     *
     * @param locale the locale
     * @return the jackson object mapper builder
     */
    public JacksonObjectMapperBuilder locale(Locale locale) {
        this.locale = locale;
        return this;
    }

    /**
     * Customize Time zone.
     *
     * @param timeZone the time zone
     * @return the jackson object mapper builder
     */
    public JacksonObjectMapperBuilder timeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
        return this;
    }

    /**
     * Customize Annotation introspector .
     *
     * @param annotationIntrospector the annotation introspector
     * @return the jackson object mapper builder
     */
    public JacksonObjectMapperBuilder annotationIntrospector(AnnotationIntrospector annotationIntrospector) {
        this.annotationIntrospector = annotationIntrospector;
        return this;
    }

    /**
     * Customize Property naming strategy .
     *
     * @param propertyNamingStrategy the property naming strategy
     * @return the jackson object mapper builder
     */
    public JacksonObjectMapperBuilder propertyNamingStrategy(PropertyNamingStrategy propertyNamingStrategy) {
        this.propertyNamingStrategy = propertyNamingStrategy;
        return this;
    }

    /**
     * Customize  Default typing.
     *
     * @param typeResolverBuilder the type resolver builder
     * @return the jackson object mapper builder
     */
    public JacksonObjectMapperBuilder defaultTyping(TypeResolverBuilder<?> typeResolverBuilder) {
        this.defaultTyping = typeResolverBuilder;
        return this;
    }

    /**
     * Customize Serialization inclusion .
     *
     * @param inclusion the inclusion
     * @return the jackson object mapper builder
     */
    public JacksonObjectMapperBuilder serializationInclusion(JsonInclude.Include inclusion) {
        return serializationInclusion(JsonInclude.Value.construct(inclusion, inclusion));
    }

    /**
     * Customize Serialization inclusion .
     *
     * @param serializationInclusion the serialization inclusion
     * @return the jackson object mapper builder
     */
    public JacksonObjectMapperBuilder serializationInclusion(JsonInclude.Value serializationInclusion) {
        this.serializationInclusion = serializationInclusion;
        return this;
    }

    /**
     * Customize Filters .
     *
     * @param filters the filters
     * @return the jackson object mapper builder
     */
    public JacksonObjectMapperBuilder filters(FilterProvider filters) {
        this.filters = filters;
        return this;
    }

    /**
     * Configure custom serializers.
     *
     * @param serializers the serializers
     * @return the jackson object mapper builder
     */
    public JacksonObjectMapperBuilder serializers(JsonSerializer<?>... serializers) {
        for (JsonSerializer<?> serializer : serializers) {
            Class<?> handledType = serializer.handledType();
            if (handledType == null || handledType == Object.class) {
                throw new IllegalArgumentException("Unknown handled type in " + serializer.getClass().getName());
            }
            this.serializers.put(serializer.handledType(), serializer);
        }
        return this;
    }

    /**
     * Configure custom serializers by type .
     *
     * @param type       the type
     * @param serializer the serializer
     * @return the jackson object mapper builder
     */
    public JacksonObjectMapperBuilder serializerByType(Class<?> type, JsonSerializer<?> serializer) {
        this.serializers.put(type, serializer);
        return this;
    }

    /**
     * Configure custom deserializers .
     *
     * @param deserializers the deserializers
     * @return the jackson object mapper builder
     */
    public JacksonObjectMapperBuilder deserializers(JsonDeserializer<?>... deserializers) {
        for (JsonDeserializer<?> deserializer : deserializers) {
            Class<?> handledType = deserializer.handledType();
            if (handledType == null || handledType == Object.class) {
                throw new IllegalArgumentException("Unknown handled type in " + deserializer.getClass().getName());
            }
            this.deserializers.put(deserializer.handledType(), deserializer);
        }
        return this;
    }

    /**
     * Configure custom deserializer by type .
     *
     * @param type         the type
     * @param deserializer the deserializer
     * @return the jackson object mapper builder
     */
    public JacksonObjectMapperBuilder deserializerByType(Class<?> type, JsonDeserializer<?> deserializer) {
        this.deserializers.put(type, deserializer);
        return this;
    }


    /**
     * Shortcut for {@link MapperFeature#AUTO_DETECT_FIELDS} option.
     *
     * @param autoDetectFields whether auto-detect fields
     * @return the jackson object mapper builder
     */
    public JacksonObjectMapperBuilder autoDetectFields(boolean autoDetectFields) {
        this.features.put(MapperFeature.AUTO_DETECT_FIELDS, autoDetectFields);
        return this;
    }

    /**
     * Shortcut for {@link MapperFeature#AUTO_DETECT_SETTERS}/
     * {@link MapperFeature#AUTO_DETECT_GETTERS}/{@link MapperFeature#AUTO_DETECT_IS_GETTERS}
     * options.
     *
     * @param autoDetectGettersSetters the auto detect getters setters
     * @return the jackson object mapper builder
     */
    public JacksonObjectMapperBuilder autoDetectGettersSetters(boolean autoDetectGettersSetters) {
        this.features.put(MapperFeature.AUTO_DETECT_GETTERS, autoDetectGettersSetters);
        this.features.put(MapperFeature.AUTO_DETECT_SETTERS, autoDetectGettersSetters);
        this.features.put(MapperFeature.AUTO_DETECT_IS_GETTERS, autoDetectGettersSetters);
        return this;
    }

    /**
     * Shortcut for {@link MapperFeature#DEFAULT_VIEW_INCLUSION} option.
     *
     * @param defaultViewInclusion the default view inclusion
     * @return the jackson object mapper builder
     */
    public JacksonObjectMapperBuilder defaultViewInclusion(boolean defaultViewInclusion) {
        this.features.put(MapperFeature.DEFAULT_VIEW_INCLUSION, defaultViewInclusion);
        return this;
    }

    /**
     * Shortcut for {@link DeserializationFeature#FAIL_ON_UNKNOWN_PROPERTIES} option.
     *
     * @param failOnUnknownProperties the fail on unknown properties
     * @return the jackson object mapper builder
     */
    public JacksonObjectMapperBuilder failOnUnknownProperties(boolean failOnUnknownProperties) {
        this.features.put(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, failOnUnknownProperties);
        return this;
    }

    /**
     * Shortcut for {@link SerializationFeature#FAIL_ON_EMPTY_BEANS} option.
     *
     * @param failOnEmptyBeans the fail on empty beans
     * @return the jackson object mapper builder
     */
    public JacksonObjectMapperBuilder failOnEmptyBeans(boolean failOnEmptyBeans) {
        this.features.put(SerializationFeature.FAIL_ON_EMPTY_BEANS, failOnEmptyBeans);
        return this;
    }

    /**
     * Shortcut for {@link SerializationFeature#INDENT_OUTPUT} option.
     *
     * @param indentOutput the indent output
     * @return the jackson object mapper builder
     */
    public JacksonObjectMapperBuilder indentOutput(boolean indentOutput) {
        this.features.put(SerializationFeature.INDENT_OUTPUT, indentOutput);
        return this;
    }

    /**
     * Define if a wrapper will be used for indexed (List, array) properties or not by
     * default (only applies to {@link XmlMapper}).
     *
     * @param defaultUseWrapper the default use wrapper
     * @return the jackson object mapper builder
     */
    public JacksonObjectMapperBuilder defaultUseWrapper(Boolean defaultUseWrapper) {
        this.defaultUseWrapper = defaultUseWrapper;
        return this;
    }

    /**
     * Specify visibility to limit what kind of properties are auto-detected.
     *
     * @param accessor   the accessor
     * @param visibility the visibility
     * @return the jackson object mapper builder
     * @see com.fasterxml.jackson.annotation.PropertyAccessor
     * @see com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility
     */
    public JacksonObjectMapperBuilder visibility(PropertyAccessor accessor, JsonAutoDetect.Visibility visibility) {
        this.visibilities.put(accessor, visibility);
        return this;
    }

    /**
     * Specify features to enable.
     *
     * @param featuresToEnable the features to enable
     * @return the jackson object mapper builder
     * @see com.fasterxml.jackson.core.JsonParser.Feature
     * @see com.fasterxml.jackson.core.JsonGenerator.Feature
     * @see com.fasterxml.jackson.databind.SerializationFeature
     * @see com.fasterxml.jackson.databind.DeserializationFeature
     * @see com.fasterxml.jackson.databind.MapperFeature
     */
    public JacksonObjectMapperBuilder featuresToEnable(Object... featuresToEnable) {
        for (Object feature : featuresToEnable) {
            this.features.put(feature, Boolean.TRUE);
        }
        return this;
    }

    /**
     * Specify features to disable.
     *
     * @param featuresToDisable the features to disable
     * @return the jackson object mapper builder
     * @see com.fasterxml.jackson.core.JsonParser.Feature
     * @see com.fasterxml.jackson.core.JsonGenerator.Feature
     * @see com.fasterxml.jackson.databind.SerializationFeature
     * @see com.fasterxml.jackson.databind.DeserializationFeature
     * @see com.fasterxml.jackson.databind.MapperFeature
     */
    public JacksonObjectMapperBuilder featuresToDisable(Object... featuresToDisable) {
        for (Object feature : featuresToDisable) {
            this.features.put(feature, Boolean.FALSE);
        }
        return this;
    }

    /**
     * Handler instantiator jackson object mapper builder.
     *
     * @param handlerInstantiator the handler instantiator
     * @return the jackson object mapper builder
     */
    public JacksonObjectMapperBuilder handlerInstantiator(HandlerInstantiator handlerInstantiator) {
        this.handlerInstantiator = handlerInstantiator;
        return this;
    }

    /**
     * Post configurer jackson object mapper builder.
     *
     * @param configurer the configurer
     * @return the jackson object mapper builder
     */
    public JacksonObjectMapperBuilder postConfigurer(Consumer<ObjectMapper> configurer) {
        this.configurer = (this.configurer != null ? this.configurer.andThen(configurer) : configurer);
        return this;
    }

    /**
     * Build a new {@link ObjectMapper} instance.
     *
     * @param <T> the type parameter
     * @return the newly built ObjectMapper
     */
    @SuppressWarnings("unchecked")
    public <T extends ObjectMapper> T build() {
        ObjectMapper mapper;
        if (this.createXmlMapper) {
            mapper = (this.defaultUseWrapper != null ?
                    new XmlObjectMapperInitializer().create(this.defaultUseWrapper) :
                    new XmlObjectMapperInitializer().create());
        } else {
            mapper = new ObjectMapper();
        }
        configure(mapper);
        return (T) mapper;
    }

    /**
     * Configure an existing {@link ObjectMapper} instance with this builder's
     * settings. This can be applied to any number of {@code ObjectMappers}.
     *
     * @param objectMapper the ObjectMapper to configure
     */
    private void configure(ObjectMapper objectMapper) {

        ObjectMapper.findModules(this.moduleClassLoader)
                .forEach(objectMapper::registerModule);

        if (this.dateFormat != null) {
            objectMapper.setDateFormat(this.dateFormat);
        }
        if (this.locale != null) {
            objectMapper.setLocale(this.locale);
        }
        if (this.timeZone != null) {
            objectMapper.setTimeZone(this.timeZone);
        }

        if (this.annotationIntrospector != null) {
            objectMapper.setAnnotationIntrospector(this.annotationIntrospector);
        }
        if (this.propertyNamingStrategy != null) {
            objectMapper.setPropertyNamingStrategy(this.propertyNamingStrategy);
        }
        if (this.defaultTyping != null) {
            objectMapper.setDefaultTyping(this.defaultTyping);
        }
        if (this.serializationInclusion != null) {
            objectMapper.setDefaultPropertyInclusion(this.serializationInclusion);
        }

        if (this.filters != null) {
            objectMapper.setFilterProvider(this.filters);
        }

        if (!this.serializers.isEmpty() || !this.deserializers.isEmpty()) {
            SimpleModule module = new SimpleModule();
            addSerializers(module);
            addDeserializers(module);
            objectMapper.registerModule(module);
        } else {
            objectMapper.registerModule(new SimpleModule());
        }

        this.visibilities.forEach(objectMapper::setVisibility);

        customizeDefaultFeatures(objectMapper);
        this.features.forEach((feature, enabled) -> configureFeature(objectMapper, feature, enabled));

        if (this.handlerInstantiator != null) {
            objectMapper.setHandlerInstantiator(this.handlerInstantiator);
        }

        if (this.configurer != null) {
            this.configurer.accept(objectMapper);
        }
    }

    private void customizeDefaultFeatures(ObjectMapper objectMapper) {
        if (!this.features.containsKey(MapperFeature.DEFAULT_VIEW_INCLUSION)) {
            configureFeature(objectMapper, MapperFeature.DEFAULT_VIEW_INCLUSION, false);
        }
        if (!this.features.containsKey(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)) {
            configureFeature(objectMapper, DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> void addSerializers(SimpleModule module) {
        this.serializers.forEach((type, serializer) ->
                module.addSerializer((Class<? extends T>) type, (JsonSerializer<T>) serializer));
    }

    @SuppressWarnings("unchecked")
    private <T> void addDeserializers(SimpleModule module) {
        this.deserializers.forEach((type, deserializer) ->
                module.addDeserializer((Class<T>) type, (JsonDeserializer<? extends T>) deserializer));
    }

    @SuppressWarnings("deprecation")
    private void configureFeature(ObjectMapper objectMapper, Object feature, boolean enabled) {
        if (feature instanceof JsonParser.Feature) {
            objectMapper.configure((JsonParser.Feature) feature, enabled);
        } else if (feature instanceof JsonGenerator.Feature) {
            objectMapper.configure((JsonGenerator.Feature) feature, enabled);
        } else if (feature instanceof SerializationFeature) {
            objectMapper.configure((SerializationFeature) feature, enabled);
        } else if (feature instanceof DeserializationFeature) {
            objectMapper.configure((DeserializationFeature) feature, enabled);
        } else if (feature instanceof MapperFeature) {
            objectMapper.configure((MapperFeature) feature, enabled);
        } else {
            throw new IllegalStateException("Unknown feature class: " + feature.getClass().getName());
        }
    }

    private static class XmlObjectMapperInitializer {

        private static final XMLResolver NO_OP_XML_RESOLVER =
                (publicID, systemID, base, ns) -> new ByteArrayInputStream(new byte[0]);

        /**
         * Create defensive input factory xml input factory.
         *
         * @return the xml input factory
         */
        public static XMLInputFactory createDefensiveInputFactory() {
            return createDefensiveInputFactory(XMLInputFactory::newInstance);
        }

        /**
         * Create defensive input factory t.
         *
         * @param <T>              the type parameter
         * @param instanceSupplier the instance supplier
         * @return the t
         */
        public static <T extends XMLInputFactory> T createDefensiveInputFactory(Supplier<T> instanceSupplier) {
            T inputFactory = instanceSupplier.get();
            inputFactory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
            inputFactory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
            inputFactory.setXMLResolver(NO_OP_XML_RESOLVER);
            return inputFactory;
        }

        /**
         * Create object mapper.
         *
         * @return the object mapper
         */
        public ObjectMapper create() {
            return new XmlMapper(createDefensiveInputFactory());
        }

        /**
         * Create object mapper.
         *
         * @param defaultUseWrapper the default use wrapper
         * @return the object mapper
         */
        public ObjectMapper create(boolean defaultUseWrapper) {
            JacksonXmlModule module = new JacksonXmlModule();
            return new XmlMapper(new XmlFactory(createDefensiveInputFactory()), module);
        }
    }

}
