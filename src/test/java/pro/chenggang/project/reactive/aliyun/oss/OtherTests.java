package pro.chenggang.project.reactive.aliyun.oss;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import pro.chenggang.project.reactive.aliyun.oss.configuration.ReactiveHttpClientConfiguration;
import pro.chenggang.project.reactive.aliyun.oss.http.netty.ReactorNettyHttpClientFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.netty.http.client.HttpClient;
import reactor.test.StepVerifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Collections;

import static pro.chenggang.project.reactive.aliyun.oss.auth.SwitchableCredentialsOperation.CREDENTIALS_CONTEXT_KEY;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
public class OtherTests {

//    @Test
    public void testDownload() throws Exception{
        //-Dio.netty.leakDetection.level=advanced
        System.setProperty("io.netty.leakDetection.level","PARANOID");
        ReactiveHttpClientConfiguration reactiveHttpClientConfiguration = new ReactiveHttpClientConfiguration();
        ReactorNettyHttpClientFactory reactorNettyHttpClientFactory = new ReactorNettyHttpClientFactory(reactiveHttpClientConfiguration, Collections.emptyList());
        HttpClient httpClient = reactorNettyHttpClientFactory.newReactorNettyHttpClient();
        File file = new File("/Users/evans/GitHub/aliyun-oss-reactive-sdk/ttt.mkv");
        FileChannel fileChannel = new FileOutputStream(file).getChannel();
        httpClient.post()
                .uri("http://127.0.0.1:9222/test/download")
                .response((httpClientResponse, byteBufFlux) -> {
                    System.out.println(httpClientResponse.responseHeaders());
                    return byteBufFlux.asByteBuffer()
                            .flatMap(byteBuffer -> {
                                try {
                                    fileChannel.write(byteBuffer);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                return Mono.just(true);
                            })
                            .publishOn(Schedulers.boundedElastic())
                            .doFinally(s -> {
                                try {
                                    fileChannel.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });

                })
                .blockLast();
    }

//    @Test
    public void testContext () {
        String credentialsIdentityKey = "TEST";
        log.info("Test log");
        Mono<Void> mono = Mono.justOrEmpty(credentialsIdentityKey)
                .filter(StrUtil::isNotBlank)
                .switchIfEmpty(Mono.error(new RuntimeException()))
                .flatMap(key -> Mono.just(key)
                        .contextWrite(context -> context
                                .put(CREDENTIALS_CONTEXT_KEY, key)
                        )
                        .then()
                );
        StepVerifier.create(mono)
                .expectAccessibleContext()
                .matches(context -> context.hasKey(CREDENTIALS_CONTEXT_KEY) && context.get(CREDENTIALS_CONTEXT_KEY).equals(credentialsIdentityKey))
                .then()
                .verifyComplete();
    }
}
