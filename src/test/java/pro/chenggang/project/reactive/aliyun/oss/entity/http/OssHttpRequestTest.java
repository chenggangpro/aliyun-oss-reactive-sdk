package pro.chenggang.project.reactive.aliyun.oss.entity.http;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pro.chenggang.project.reactive.aliyun.oss.option.http.OssHttpMethod;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.nio.file.Paths;

import static cn.hutool.http.ContentType.FORM_URLENCODED;
import static cn.hutool.http.Header.CONTENT_TYPE;

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
                .addCookie("cookieName", "cookieValue")
                .newSimpleBody("text/plain")
                .setBodyData("body")
                .and()
                .newFormBody()
                .addFormData("key", "value")
                .and()
                .newFileBody(Paths.get(URI.create("file:/tmp")))
                .newByteBufferBody(Flux.empty())
                .build();
        Assertions.assertNotNull(ossHttpRequest);
    }

    @Test
    void testBuilderException() {
        Assertions.assertThrows(Exception.class, () -> OssHttpRequest.newBuilder("xxx://127.0.0.1", OssHttpMethod.GET));
    }

    @Test
    void testGetUrlWithQuery() {
        String url = "http://127.0.0.1";
        OssHttpRequest simpleRequest = OssHttpRequest.newBuilder(url, OssHttpMethod.GET).build();
        Assertions.assertEquals(url, simpleRequest.getUrlWithQuery());
        OssRequestBuilder ossRequestBuilder = OssHttpRequest.newBuilder(url, OssHttpMethod.GET)
                .addParameter("key", "value");
        String result = ossRequestBuilder.build().getUrlWithQuery();
        Assertions.assertEquals(url + "?key=value", result);
        OssHttpRequest ossHttpRequest = ossRequestBuilder.addHeader(CONTENT_TYPE.getValue(), FORM_URLENCODED.getValue()).build();
        String result2 = ossHttpRequest.getUrlWithQuery();
        Assertions.assertEquals(url + "?key=value", result2);
    }

    @Test
    void testGetContentType() {
        String url = "http://127.0.0.1";
        OssHttpRequest ossHttpRequest = OssHttpRequest.newBuilder(url, OssHttpMethod.GET)
                .addHeader(CONTENT_TYPE.getValue(), FORM_URLENCODED.getValue())
                .build();
        String result = ossHttpRequest.getContentType();
        Assertions.assertEquals(FORM_URLENCODED.getValue(), result);
    }

    @Test
    void testHasFileBody() {
        OssHttpRequest ossHttpRequest = OssHttpRequest.newBuilder("http://127.0.0.1", OssHttpMethod.GET)
                .newFileBody(Paths.get(URI.create("file:/tmp")))
                .build();
        Assertions.assertTrue(ossHttpRequest.hasFileBody());
    }

    @Test
    void testHasByteBufferBody() {
        OssHttpRequest ossHttpRequest = OssHttpRequest.newBuilder("http://127.0.0.1", OssHttpMethod.GET)
                .newByteBufferBody(Flux.empty())
                .build();
        Assertions.assertTrue(ossHttpRequest.hasByteBufferBody());
        ossHttpRequest = OssHttpRequest.newBuilder("http://127.0.0.1", OssHttpMethod.GET)
                .newByteBufferBody(null)
                .build();
        Assertions.assertFalse(ossHttpRequest.hasByteBufferBody());
    }

    @Test
    void testHasFormBody() {
        OssHttpRequest ossHttpRequest = OssHttpRequest.newBuilder("http://127.0.0.1", OssHttpMethod.GET)
                .newFormBody()
                .addFormData("key", "value")
                .and()
                .build();
        Assertions.assertTrue(ossHttpRequest.hasFormBody());
        ossHttpRequest = OssHttpRequest.newBuilder("http://127.0.0.1", OssHttpMethod.GET)
                .newFormBody()
                .and()
                .build();
        Assertions.assertFalse(ossHttpRequest.hasFormBody());
    }

    @Test
    void testHasSimpleBody() {
        OssHttpRequest ossHttpRequest = OssHttpRequest.newBuilder("http://127.0.0.1", OssHttpMethod.GET)
                .newSimpleBody("text/plain")
                .setBodyData("body")
                .and()
                .build();
        Assertions.assertTrue(ossHttpRequest.hasSimpleBody());
        ossHttpRequest = OssHttpRequest.newBuilder("http://127.0.0.1", OssHttpMethod.GET)
                .newSimpleBody("text/plain")
                .setBodyData(null)
                .and()
                .build();
        Assertions.assertFalse(ossHttpRequest.hasSimpleBody());
    }

    @Test
    void testHasNoneBody() {
        OssHttpRequest ossHttpRequest = OssHttpRequest.newBuilder("http://127.0.0.1", OssHttpMethod.GET)
                .build();
        Assertions.assertTrue(ossHttpRequest.hasNoneBody());
        ossHttpRequest = OssHttpRequest.newBuilder("http://127.0.0.1", OssHttpMethod.GET)
                .addParameter("key", "value")
                .newSimpleBody("text/plain")
                .setBodyData("body")
                .and()
                .build();
        Assertions.assertFalse(ossHttpRequest.hasNoneBody());
        ossHttpRequest = OssHttpRequest.newBuilder("http://127.0.0.1", OssHttpMethod.GET)
                .addParameter("key", "value")
                .newFormBody()
                .addFormData("key", "value")
                .and()
                .build();
        Assertions.assertFalse(ossHttpRequest.hasNoneBody());
        ossHttpRequest = OssHttpRequest.newBuilder("http://127.0.0.1", OssHttpMethod.GET)
                .addParameter("key", "value")
                .newFileBody(Paths.get(URI.create("file:/tmp")))
                .build();
        Assertions.assertFalse(ossHttpRequest.hasNoneBody());
        ossHttpRequest = OssHttpRequest.newBuilder("http://127.0.0.1", OssHttpMethod.GET)
                .addParameter("key", "value")
                .newByteBufferBody(Flux.empty())
                .build();
        Assertions.assertFalse(ossHttpRequest.hasNoneBody());

    }
}
