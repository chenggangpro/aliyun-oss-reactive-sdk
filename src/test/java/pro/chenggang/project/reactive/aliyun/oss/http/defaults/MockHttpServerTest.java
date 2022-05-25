package pro.chenggang.project.reactive.aliyun.oss.http.defaults;

import cn.hutool.core.net.url.UrlQuery;
import cn.hutool.http.ContentType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.reactivestreams.Publisher;
import pro.chenggang.project.reactive.aliyun.oss.entity.http.MultiValueMap;
import pro.chenggang.project.reactive.aliyun.oss.http.message.JacksonObjectMapperBuilder;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.netty.http.server.HttpServer;
import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;

import java.io.IOException;
import java.net.URI;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.hutool.http.Header.CONTENT_TYPE;

public class MockHttpServerTest {

    protected String errorUri = "/mock-error";
    protected String getUri = "/mock-get";
    protected String getFileUri = "/mock-file";
    protected String getWithParamUri = "/mock-get-param";
    protected String postForm = "/mock-post-form";
    protected String postData = "/mock-post-data";
    protected String postFile = "/mock-post-file";
    protected int port = 9999;
    protected String host = "127.0.0.1";
    protected String httpBaseUrl = "http://" + host + ":" + port;
    protected Path filePath;
    protected ObjectMapper objectMapper = JacksonObjectMapperBuilder.json().build();

    Disposable monoServer;
    String successXml;
    String errorXml;

    @BeforeEach
    public void setup() throws Exception {
        this.errorXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<Error>\n" +
                "  <Code>AccessDenied</Code>\n" +
                "  <Message>The specified key does not exist.</Message>\n" +
                "  <RequestId>5CAC0FEADE0170*****</RequestId>\n" +
                "  <HostId>versioning-get.oss-cn-hangzhou.aliyun*****</HostId>\n" +
                "  <Key>example</Key>\n" +
                "</Error>";
        this.successXml = "<ListAllMyBucketsResult>\n" +
                "  <Prefix>my</Prefix>\n" +
                "  <Marker>mybucket</Marker>\n" +
                "  <MaxKeys>10</MaxKeys>\n" +
                "  <IsTruncated>true</IsTruncated>\n" +
                "  <NextMarker>mybucket10</NextMarker>\n" +
                "  <Owner>\n" +
                "    <ID>ut_test_put_bucket</ID>\n" +
                "    <DisplayName>ut_test_put_bucket</DisplayName>\n" +
                "  </Owner>\n" +
                "  <Buckets>\n" +
                "    <Bucket>\n" +
                "      <CreationDate>2014-05-14T11:18:32.000Z</CreationDate>\n" +
                "      <ExtranetEndpoint>oss-cn-hangzhou.aliyuncs.com</ExtranetEndpoint>\n" +
                "      <IntranetEndpoint>oss-cn-hangzhou-internal.aliyuncs.com</IntranetEndpoint>\n" +
                "      <Location>oss-cn-hangzhou</Location>\n" +
                "      <Name>mybucket01</Name>\n" +
                "      <Region>cn-hangzhou</Region>\n" +
                "      <StorageClass>Standard</StorageClass>\n" +
                "    </Bucket>\n" +
                "  </Buckets>\n" +
                "</ListAllMyBucketsResult>";
        this.filePath = Paths.get(this.getClass().getClassLoader().getResource("keystore.jks").toURI());
    }

    @BeforeEach
    void initHttpServer() {
        System.setProperty("io.netty.leakDetection.level","PARANOID");
        monoServer = HttpServer.create()
                .host(host)
                .port(port)
                .route(httpServerRoutes -> httpServerRoutes
                        .get(getUri,this::registerGet)
                        .get(errorUri,this::registerGetError)
                        .get(getFileUri,this::registerGetFile)
                        .get(getWithParamUri,this::registerGetWithParam)
                        .post(postForm,this::registerPostForm)
                        .post(postData,this::registerPostData)
                        .post(postFile,this::registerPostFile)
                )
                .bind()
                .publishOn(Schedulers.parallel())
                .subscribeOn(Schedulers.parallel())
                .subscribe();
    }

    @AfterEach
    void stop(){
        if(!monoServer.isDisposed()){
            monoServer.dispose();
        }
    }

    private Publisher<Void> registerGet(HttpServerRequest request, HttpServerResponse response){
        return response.addHeader(CONTENT_TYPE.getValue(),ContentType.XML.getValue())
                .status(HttpResponseStatus.OK)
                .sendString(Mono.just(this.successXml));
    }

    private Publisher<Void> registerGetError(HttpServerRequest request,HttpServerResponse response){
        return response.addHeader(CONTENT_TYPE.getValue(),ContentType.XML.getValue())
                .status(HttpResponseStatus.BAD_REQUEST)
                .sendString(Mono.just(this.errorXml));
    }

    private Publisher<Void> registerGetFile(HttpServerRequest request,HttpServerResponse response){
        return response.addHeader(CONTENT_TYPE.getValue(),ContentType.OCTET_STREAM.getValue())
                .status(HttpResponseStatus.OK)
                .sendFile(filePath);
    }

    @SneakyThrows
    private Publisher<Void> registerGetWithParam(HttpServerRequest request,HttpServerResponse response){
        String uri = request.uri();
        Map<CharSequence, CharSequence> queryMap = UrlQuery.of(URI.create(uri).getQuery(), StandardCharsets.UTF_8).getQueryMap();
        MultiValueMap<String,String> multiValueMap = new MultiValueMap<>();
        queryMap.forEach((k,v) -> {
            if(null == k && null == v){
                multiValueMap.add(null, null);
                return;
            }
            if(null == k){
                multiValueMap.add(null, v.toString());
                return;
            }
            if(null == v){
                multiValueMap.add(k.toString(), null);
                return;
            }
            multiValueMap.add(k.toString(), v.toString());
        });
        return response.addHeader(CONTENT_TYPE.getValue(),ContentType.TEXT_PLAIN.getValue())
                .status(HttpResponseStatus.OK)
                .sendString(Mono.just(objectMapper.writeValueAsString(multiValueMap)));
    }

    private Publisher<Void> registerPostForm(HttpServerRequest request, HttpServerResponse response){
        return request.receiveForm()
                .flatMap(httpData -> {
                    HttpDataType httpDataType = httpData.getHttpDataType();
                    if(HttpDataType.Attribute.equals(httpDataType)){
                        return Mono.fromCallable(() -> {
                            try {
                                return httpData.getString();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }
                    if(HttpDataType.FileUpload.equals(httpDataType)){
                        return Mono.just(String.valueOf(httpData.definedLength()));
                    }
                    throw new UnsupportedOperationException("unsupported http data type: " + httpDataType);
                })
                .collectList()
                .flatMap(formData -> Mono.from(response.addHeader(CONTENT_TYPE.getValue(),ContentType.TEXT_PLAIN.getValue())
                        .status(HttpResponseStatus.OK)
                        .sendString(Mono.fromCallable(() -> {
                            try {
                                return objectMapper.writeValueAsString(formData);
                            }catch (JsonProcessingException e){
                                throw new RuntimeException(e);
                            }
                        }))));
    }

    private Publisher<Void> registerPostData(HttpServerRequest request,HttpServerResponse response){
        return request.receive()
                .aggregate()
                .asString(StandardCharsets.UTF_8)
                .flatMap(data -> response
                        .addHeader(CONTENT_TYPE.getValue(),ContentType.TEXT_PLAIN.getValue())
                        .status(HttpResponseStatus.OK)
                        .sendString(Mono.just(data))
                        .then()
                );
    }

    private Publisher<Void> registerPostFile(HttpServerRequest request,HttpServerResponse response){
        return request.receive()
                .asByteBuffer()
                .collect(Collectors.summarizingLong(Buffer::capacity))
                .map(LongSummaryStatistics::getSum)
                .flatMap(data -> response
                        .addHeader(CONTENT_TYPE.getValue(),ContentType.TEXT_PLAIN.getValue())
                        .status(HttpResponseStatus.OK)
                        .sendString(Mono.just(String.valueOf(data)))
                        .then()
                );
    }
}