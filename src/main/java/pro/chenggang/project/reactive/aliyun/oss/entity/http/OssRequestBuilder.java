package pro.chenggang.project.reactive.aliyun.oss.entity.http;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import pro.chenggang.project.reactive.aliyun.oss.entity.http.OssHttpRequest.ByteBufferBody;
import pro.chenggang.project.reactive.aliyun.oss.entity.http.OssHttpRequest.FileBody;
import pro.chenggang.project.reactive.aliyun.oss.entity.http.OssHttpRequest.FormBody;
import pro.chenggang.project.reactive.aliyun.oss.entity.http.OssHttpRequest.SimpleBody;
import pro.chenggang.project.reactive.aliyun.oss.option.http.OssHttpMethod;
import reactor.core.publisher.Flux;

import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

import static cn.hutool.http.Header.CONTENT_TYPE;
import static lombok.AccessLevel.PRIVATE;

/**
 * The OssHttpRequestBuilder
 */
public class OssRequestBuilder {

    private final OssHttpMethod ossHttpMethod;
    private final URL url;
    private final MultiValueMap<String,String> headers = new MultiValueMap<>();
    private final MultiValueMap<String, String> cookies = new MultiValueMap<>();
    private final MultiValueMap<String,String> parameters = new MultiValueMap<>();
    private FormBody formBody;
    private SimpleBody simpleBody;
    private FileBody fileBody;
    private ByteBufferBody byteBufferBody;

    /**
     * Instantiates a new Builder.
     *
     * @param url           the url
     * @param ossHttpMethod the oss http method
     */
    @SneakyThrows
    public OssRequestBuilder(String url, OssHttpMethod ossHttpMethod) {
        this.url = new URL(url);
        this.ossHttpMethod = ossHttpMethod;
    }

    /**
     * Add header.
     *
     * @param headerKey   the header key
     * @param headerValue the header value
     * @return the builder
     */
    public OssRequestBuilder addHeader(String headerKey, String headerValue) {
        this.headers.add(headerKey, headerValue);
        return this;
    }

    /**
     * Add parameter.
     *
     * @param parameterKey   the parameter key
     * @param parameterValue the parameter value
     * @return the builder
     */
    public OssRequestBuilder addParameter(String parameterKey, String parameterValue) {
        this.parameters.add(parameterKey, parameterValue);
        return this;
    }

    /**
     * Add cookie .
     *
     * @param cookieKey   the cookie key
     * @param cookieValue the cookie value
     * @return the builder
     */
    public OssRequestBuilder addCookie(String cookieKey, String cookieValue) {
        this.cookies.add(cookieKey, cookieValue);
        return this;
    }

    /**
     * New form body.
     *
     * @return the form body
     */
    public FormBodyBuilder newFormBody() {
        this.formBody = null;
        return new FormBodyBuilder(this);
    }

    /**
     * New string body.
     *
     * @param contentType the content type
     * @return the string body
     */
    public SimpleBodyBuilder newSimpleBody(String contentType) {
        this.simpleBody = null;
        return new SimpleBodyBuilder(this, contentType);
    }

    /**
     * New file body.
     *
     * @param file the file
     * @return the builder
     */
    public OssRequestBuilder newFileBody(Path file) {
        this.fileBody = new FileBody(file);
        return this;
    }

    /**
     * New byte buffer body.
     *
     * @param byteBufferFlux the byte buffer flux
     * @return the builder
     */
    public OssRequestBuilder newByteBufferBody(Flux<ByteBuffer> byteBufferFlux) {
        this.byteBufferBody = new ByteBufferBody(byteBufferFlux);
        return this;
    }

    /**
     * Build oss http request.
     *
     * @return the oss http request
     */
    public OssHttpRequest build() {
        return new OssHttpRequest(ossHttpMethod,
                url,
                cookies,
                headers,
                parameters,
                formBody,
                simpleBody,
                fileBody,
                byteBufferBody
        );
    }

    /**
     * The Form body.
     */
    @RequiredArgsConstructor(access = PRIVATE)
    public static class FormBodyBuilder {

        private final Map<String, Object> formContent = new LinkedHashMap<>();
        private final OssRequestBuilder ossRequestBuilder;

        /**
         * Add form data.
         *
         * @param key   the key
         * @param value the value
         * @return the form body
         */
        public FormBodyBuilder addFormData(String key, Object value) {
            this.formContent.put(key, value);
            return this;
        }

        /**
         * Then OssHttpRequest builder.
         *
         * @return the builder
         */
        public OssRequestBuilder and() {
            FormBody formBody = new FormBody();
            this.formContent.forEach(formBody::addFormData);
            this.ossRequestBuilder.formBody = formBody;
            return ossRequestBuilder;
        }
    }

    /**
     * The Simple body.
     */
    public static class SimpleBodyBuilder {

        private final OssRequestBuilder ossRequestBuilder;
        private final String contentType;
        private Object bodyData;

        private SimpleBodyBuilder(OssRequestBuilder ossRequestBuilder, String contentType) {
            this.ossRequestBuilder = ossRequestBuilder;
            this.contentType = contentType;
            this.ossRequestBuilder.headers.set(CONTENT_TYPE.getValue(), contentType);
        }

        /**
         * Set body content.
         *
         * @param bodyData the body content
         * @return the body content
         */
        public SimpleBodyBuilder setBodyData(Object bodyData) {
            this.bodyData = bodyData;
            return this;
        }

        /**
         * Then OssHttpRequest builder.
         *
         * @return the builder
         */
        public OssRequestBuilder and() {
            this.ossRequestBuilder.simpleBody = new SimpleBody(contentType).setBodyData(bodyData);
            return ossRequestBuilder;
        }
    }

}