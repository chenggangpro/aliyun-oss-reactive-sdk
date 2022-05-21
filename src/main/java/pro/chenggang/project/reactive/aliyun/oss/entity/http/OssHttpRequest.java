package pro.chenggang.project.reactive.aliyun.oss.entity.http;

import cn.hutool.http.ContentType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pro.chenggang.project.reactive.aliyun.oss.option.http.OssHttpMethod;
import reactor.core.publisher.Flux;

import java.io.File;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

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
    private final MultiValueMap<String,String> headers = new MultiValueMap<>();
    private final MultiValueMap<String,String> parameters = new MultiValueMap<>();
    private final URL url;
    private final FormBody formBody;
    private final SimpleBody simpleBody;
    private final FileBody fileBody;
    private final PathBody pathBody;
    private final ByteBufferBody byteBufferBody;

    protected OssHttpRequest(OssHttpMethod ossHttpMethod,
                             URL url,
                             MultiValueMap<String,String> headers,
                             MultiValueMap<String,String> parameters,
                             FormBody formBody,
                             SimpleBody simpleBody,
                             FileBody fileBody,
                             PathBody pathBody,
                             ByteBufferBody byteBufferBody) {
        this.ossHttpMethod = ossHttpMethod;
        this.url = url;
        this.headers.addAll(headers);
        this.parameters.addAll(parameters);
        this.formBody = formBody;
        this.simpleBody = simpleBody;
        this.fileBody = fileBody;
        this.pathBody = pathBody;
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
        public FormBody addFormData(String key, Object value) {
            this.formContent.put(key, value);
            return this;
        }

    }

    /**
     * The String body.
     */
    @Getter
    public static class SimpleBody {

        private final Map<String, String> contentType;
        private Object bodyData;

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
     * The File body.
     */
    @Getter
    @RequiredArgsConstructor(access = PROTECTED)
    public static class FileBody {

        private final File file;

    }

    /**
     * The Path body.
     */
    @Getter
    @RequiredArgsConstructor(access = PROTECTED)
    public static class PathBody {

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
