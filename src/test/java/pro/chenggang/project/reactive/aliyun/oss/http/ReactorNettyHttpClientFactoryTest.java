package pro.chenggang.project.reactive.aliyun.oss.http;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pro.chenggang.project.reactive.aliyun.oss.configuration.ReactiveHttpClientConfiguration;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;

import java.util.Collections;
import java.util.List;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
class ReactorNettyHttpClientFactoryTest {

    ReactiveHttpClientConfiguration reactiveHttpClientConfiguration;

    List<HttpClientCustomizer> httpClientCustomizers;

    ReactorNettyHttpClientFactory reactorNettyHttpClientFactory;

    @BeforeEach
    void setUp() {
        reactiveHttpClientConfiguration = new ReactiveHttpClientConfiguration();
        httpClientCustomizers = Collections.singletonList(httpClient -> httpClient);
        reactorNettyHttpClientFactory = new ReactorNettyHttpClientFactory(reactiveHttpClientConfiguration,httpClientCustomizers);
    }

    @Test
    void testNewInstance() {
        HttpClient result = reactorNettyHttpClientFactory.newInstance();
        Assertions.assertNotNull(result);
    }

    @Test
    void testNewInstanceWithDisabledPoolType() {
        reactiveHttpClientConfiguration.getPool().setType(ReactiveHttpClientConfiguration.Pool.PoolType.DISABLED);
        HttpClient result = reactorNettyHttpClientFactory.newInstance();
        Assertions.assertNotNull(result);
    }

    @Test
    void testNewInstanceWithFixedPoolType() {
        reactiveHttpClientConfiguration.getPool().setType(ReactiveHttpClientConfiguration.Pool.PoolType.FIXED);
        HttpClient result = reactorNettyHttpClientFactory.newInstance();
        Assertions.assertNotNull(result);
    }

    @Test
    void testConfigureSsl() {
        ReactiveHttpClientConfiguration.Ssl ssl = reactiveHttpClientConfiguration.getSsl();
        ssl.setUseInsecureTrustManager(false);
        ssl.setKeyStore("keystore.jks");
        ssl.setTrustedX509Certificates(Collections.singletonList("single-cert.pem"));
        ssl.setKeyPassword("keyscg1234");
        ssl.setKeyStorePassword("scg1234");
        ssl.setKeyStoreType("JKS");
        HttpClient result = reactorNettyHttpClientFactory.newInstance();;
        Assertions.assertNotNull(result);
    }

    @Test
    void testConfigureSslWithNotExistFile() {
        ReactiveHttpClientConfiguration.Ssl ssl = reactiveHttpClientConfiguration.getSsl();
        ssl.setUseInsecureTrustManager(false);
        ssl.setKeyStore("keystore-not-exist.jks");
        ssl.setTrustedX509Certificates(Collections.singletonList("single-cert-not-exist.pem"));
        ssl.setKeyPassword("keyscg12345");
        ssl.setKeyStorePassword("scg12345");
        ssl.setKeyStoreType("JKS");
        Assertions.assertThrows(Exception.class,() -> {
            HttpClient result = reactorNettyHttpClientFactory.newInstance();;
            Assertions.assertNull(result);
        });

    }

    @Test
    void testConfigureSslWithErrorFile() {
        ReactiveHttpClientConfiguration.Ssl ssl = reactiveHttpClientConfiguration.getSsl();
        ssl.setUseInsecureTrustManager(false);
        ssl.setKeyStore("keystore-error.jks");
        ssl.setTrustedX509Certificates(Collections.singletonList("single-cert-error.pem"));
        ssl.setKeyPassword("keyscg12345");
        ssl.setKeyStorePassword("scg12345");
        ssl.setKeyStoreType("JKS");
        Assertions.assertThrows(Exception.class,() -> {
            HttpClient result = reactorNettyHttpClientFactory.newInstance();;
            Assertions.assertNull(result);
        });

    }

    @Test
    void testConfigureProxy() {
        ReactiveHttpClientConfiguration.Proxy proxy = reactiveHttpClientConfiguration.getProxy();
        proxy.setHost("localhost");
        proxy.setPort(80);
        proxy.setNonProxyHostsPattern("127.0.0.1");
        proxy.setUsername("username");
        proxy.setPassword("password");
        proxy.setType(ProxyProvider.Proxy.HTTP);
        HttpClient result = reactorNettyHttpClientFactory.newInstance();;
        Assertions.assertNotNull(result);
    }

    @Test
    void testBuildConnectionProvider() {
        HttpClient result = reactorNettyHttpClientFactory.newInstance();
        Assertions.assertNotNull(result);
    }
}