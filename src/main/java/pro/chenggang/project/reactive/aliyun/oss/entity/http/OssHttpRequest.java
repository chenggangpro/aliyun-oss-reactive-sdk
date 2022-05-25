package pro.chenggang.project.reactive.aliyun.oss.entity.http;

import cn.hutool.core.net.url.UrlQuery;
import cn.hutool.http.ContentType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pro.chenggang.project.reactive.aliyun.oss.option.http.OssHttpMethod;
import reactor.core.publisher.Flux;

import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static cn.hutool.http.ContentType.FORM_URLENCODED;
import static cn.hutool.http.Header.CONTENT_TYPE;
import static lombok.AccessLevel.PROTECTED;

/**
 * The Oss Http Request
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
public class OssHttpRequest {

    private final OssHttpMethod ossHttpMethod;
    private final MultiValueMap<String, String> cookies = new MultiValueMap<>();
    private final MultiValueMap<String, String> headers = new MultiValueMap<>();
    private final MultiValueMap<String, String> parameters = new MultiValueMap<>();
    private final URL url;
    private final FormBody formBody;
    private final SimpleBody simpleBody;
    private final FileBody fileBody;
    private final ByteBufferBody byteBufferBody;

    /**
     * Instantiates a new Oss http request.
     *
     * @param ossHttpMethod  the oss http method
     * @param url            the url
     * @param headers        the headers
     * @param parameters     the parameters
     * @param formBody       the form body
     * @param simpleBody     the simple body
     * @param fileBody       the file body
     * @param byteBufferBody the byte buffer body
     */
    protected OssHttpRequest(OssHttpMethod ossHttpMethod,
                             URL url,
                             MultiValueMap<String, String> cookies,
                             MultiValueMap<String, String> headers,
                             MultiValueMap<String, String> parameters,
                             FormBody formBody,
                             SimpleBody simpleBody,
                             FileBody fileBody,
                             ByteBufferBody byteBufferBody) {
        this.ossHttpMethod = ossHttpMethod;
        this.url = url;
        this.cookies.addAll(cookies);
        this.headers.addAll(headers);
        this.parameters.addAll(parameters);
        this.formBody = formBody;
        this.simpleBody = simpleBody;
        this.fileBody = fileBody;
        this.byteBufferBody = byteBufferBody;
    }

    /**
     * New OssHttpRequest builder.
     *
     * @param url           the target url
     * @param ossHttpMethod the oss http method
     * @return the builder
     */
    public static OssRequestBuilder newBuilder(String url, OssHttpMethod ossHttpMethod) {
        return new OssRequestBuilder(url, ossHttpMethod);
    }

    /**
     * get url with query
     *
     * @return the url with query
     */
    public String getUrlWithQuery() {
        String rawUrl = this.url.toString();
        if (this.parameters.isEmpty()) {
            return rawUrl;
        }
        UrlQuery urlQuery;
        if (FORM_URLENCODED.getValue().equals(this.headers.getFirst(CONTENT_TYPE.getValue()))) {
            urlQuery = new UrlQuery(true);
        } else {
            urlQuery = new UrlQuery();
        }
        Set<Map.Entry<String, List<String>>> entries = this.parameters.getRaw().entrySet();
        for (Map.Entry<String, List<String>> entry : entries) {
            String key = entry.getKey();
            for (String value : entry.getValue()) {
                urlQuery.add(key, value);
            }
        }
        return rawUrl + "?" + urlQuery.build(StandardCharsets.UTF_8);
    }

    /**
     * get content type
     *
     * @return the content type
     */
    public String getContentType() {
        return this.headers.getFirst(CONTENT_TYPE.getValue());
    }

    /**
     * Has file body.
     *
     * @return the boolean
     */
    public boolean hasFileBody() {
        return Objects.nonNull(this.fileBody);
    }

    /**
     * Has byte buffer body.
     *
     * @return the boolean
     */
    public boolean hasByteBufferBody() {
        return Objects.nonNull(this.byteBufferBody)
                && Objects.nonNull(this.byteBufferBody.getByteBufferFlux());
    }

    /**
     * Has form body .
     *
     * @return the boolean
     */
    public boolean hasFormBody() {
        return Objects.nonNull(this.formBody)
                && !formBody.getFormContent().isEmpty();
    }

    /**
     * Has simple body .
     *
     * @return the boolean
     */
    public boolean hasSimpleBody() {
        return Objects.nonNull(simpleBody)
                && Objects.nonNull(simpleBody.getBodyData());
    }

    /**
     * Has none body .
     *
     * @return the boolean
     */
    public boolean hasNoneBody() {
        return !hasSimpleBody()
                && !hasByteBufferBody()
                && !hasFormBody()
                && !hasFileBody();
    }

    /**
     * The Form body.
     */
    @Getter
    public static class FormBody {

        private final Map<String, String> contentType = Collections.singletonMap(CONTENT_TYPE.getValue(), ContentType.MULTIPART.getValue());
        private final Map<String, Object> formContent = new LinkedHashMap<>();

        /**
         * Add form data.
         *
         * @param key   the key
         * @param value the value
         * @return the form body
         */
        public void addFormData(String key, Object value) {
            this.formContent.put(key, value);
        }

    }

    /**
     * The String body.
     */
    @Getter
    public static class SimpleBody {

        private final Map<String, String> contentType;
        private Object bodyData;

        /**
         * Instantiates a new Simple body.
         *
         * @param contentType the content type
         */
        protected SimpleBody(String contentType) {
            this.contentType = Collections.singletonMap(CONTENT_TYPE.getValue(), contentType);
        }

        /**
         * Set body content.
         *
         * @param bodyData the body content
         * @return the body content
         */
        public SimpleBody setBodyData(Object bodyData) {
            this.bodyData = bodyData;
            return this;
        }

    }

    /**
     * The Path body.
     */
    @Getter
    @RequiredArgsConstructor(access = PROTECTED)
    public static class FileBody {

        private final Path path;

    }

    /**
     * The Byte buffer body.
     */
    @Getter
    @RequiredArgsConstructor(access = PROTECTED)
    public static class ByteBufferBody {

        private final Flux<ByteBuffer> byteBufferFlux;

    }

}
