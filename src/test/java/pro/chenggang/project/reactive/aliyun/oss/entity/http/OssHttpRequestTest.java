package pro.chenggang.project.reactive.aliyun.oss.entity.http;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pro.chenggang.project.reactive.aliyun.oss.option.http.OssHttpMethod;
import reactor.core.publisher.Flux;

import java.io.File;
import java.net.URI;
import java.nio.file.Paths;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
class OssHttpRequestTest {

    @Test
    void testNewBuilder() {
        OssRequestBuilder requestBuilder = OssHttpRequest.newBuilder("http://127.0.0.1", OssHttpMethod.GET);
        OssHttpRequest ossHttpRequest = requestBuilder.addHeader("key", "value")
                .addParameter("key", "value")
                .newStringBody("content-type")
                .setBodyContent("body")
                .and()
                .newFormBody()
                .addFormData("key", "value")
                .and()
                .newFileBody(new File(""))
                .newPathBody(Paths.get(URI.create("file:/tmp")))
                .newByteBufferBody(Flux.empty())
                .build();
        Assertions.assertNotNull(ossHttpRequest);
    }

    @Test
    void testBuilderException() {
        Assertions.assertThrows(Exception.class, () -> OssHttpRequest.newBuilder("xxx://127.0.0.1", OssHttpMethod.GET));
    }
}
