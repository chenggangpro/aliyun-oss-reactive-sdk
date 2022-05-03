package pro.chenggang.project.reactive.aliyun.oss.http;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.text.CharSequenceUtil;
import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.RequiredArgsConstructor;
import pro.chenggang.project.reactive.aliyun.oss.configuration.ReactiveHttpClientConfiguration;
import reactor.netty.http.Http11SslContextSpec;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.transport.ProxyProvider;

import javax.net.ssl.KeyManagerFactory;
import java.io.IOException;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchProviderException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static pro.chenggang.project.reactive.aliyun.oss.configuration.ReactiveHttpClientConfiguration.Pool.PoolType.DISABLED;
import static pro.chenggang.project.reactive.aliyun.oss.configuration.ReactiveHttpClientConfiguration.Pool.PoolType.FIXED;

/**
 * Reactor netty HttpClient Factory
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class ReactorNettyHttpClientFactory {

    private final ReactiveHttpClientConfiguration reactiveHttpClientConfiguration;
    private final List<HttpClientCustomizer> httpClientCustomizers;

    /**
     * New instance http client.
     *
     * @return the http client
     */
    public HttpClient newInstance() {
        ConnectionProvider connectionProvider = this.buildConnectionProvider(this.reactiveHttpClientConfiguration);
        HttpClient httpClient = HttpClient.create(connectionProvider);
        if (this.reactiveHttpClientConfiguration.getConnectTimeout() != null) {
            httpClient = httpClient.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, this.reactiveHttpClientConfiguration.getConnectTimeout());
        }
        httpClient = this.configureProxy(httpClient);
        httpClient = this.configureSsl(httpClient);
        if (this.reactiveHttpClientConfiguration.isWiretap()) {
            httpClient = httpClient.wiretap(true);
        }
        if (this.reactiveHttpClientConfiguration.isCompression()) {
            httpClient = httpClient.compress(true);
        }
        if (CollectionUtil.isNotEmpty(this.httpClientCustomizers)) {
            for (HttpClientCustomizer httpClientCustomizer : this.httpClientCustomizers) {
                httpClient = httpClientCustomizer.customize(httpClient);
            }
        }
        return httpClient;
    }

    /**
     * Configure ssl http client.
     *
     * @param httpClient the http client
     * @return the http client
     */
    protected HttpClient configureSsl(HttpClient httpClient) {
        ReactiveHttpClientConfiguration.Ssl ssl = this.reactiveHttpClientConfiguration.getSsl();
        Http11SslContextSpec sslContext = Http11SslContextSpec.forClient();
        if (ssl.isUseInsecureTrustManager()) {
            httpClient = httpClient.secure(sslContextSpec -> {
                sslContext.configure(sslContextBuilder -> sslContextBuilder.trustManager(InsecureTrustManagerFactory.INSTANCE));
                sslContextSpec.sslContext(sslContext)
                        .handshakeTimeout(ssl.getHandshakeTimeout())
                        .closeNotifyFlushTimeout(ssl.getCloseNotifyFlushTimeout())
                        .closeNotifyReadTimeout(ssl.getCloseNotifyReadTimeout());
            });
            return httpClient;
        }
        boolean hasSslSettings = false;
        X509Certificate[] trustedX509Certificates = this.getTrustedX509CertificatesForTrustManager();
        if (trustedX509Certificates.length > 0) {
            sslContext.configure(sslContextBuilder -> sslContextBuilder.trustManager(trustedX509Certificates));
            hasSslSettings = true;
        }
        if (CharSequenceUtil.isNotEmpty(ssl.getKeyStore())) {
            sslContext.configure(sslContextBuilder -> sslContextBuilder.keyManager(this.getKeyManagerFactory()));
            hasSslSettings = true;
        }
        if (hasSslSettings) {
            httpClient = httpClient.secure(sslContextSpec -> {
                sslContextSpec.sslContext(sslContext)
                        .handshakeTimeout(ssl.getHandshakeTimeout())
                        .closeNotifyFlushTimeout(ssl.getCloseNotifyFlushTimeout())
                        .closeNotifyReadTimeout(ssl.getCloseNotifyReadTimeout());
            });
        }
        return httpClient;
    }

    /**
     * Configure proxy http client.
     *
     * @param httpClient the http client
     * @return the http client
     */
    protected HttpClient configureProxy(HttpClient httpClient) {
        if (CharSequenceUtil.isNotBlank(this.reactiveHttpClientConfiguration.getProxy().getHost())) {
            ReactiveHttpClientConfiguration.Proxy proxy = this.reactiveHttpClientConfiguration.getProxy();
            httpClient = httpClient
                    .proxy(proxySpec -> {
                        ProxyProvider.Builder builder = proxySpec.type(proxy.getType())
                                .host(proxy.getHost());
                        if (null != proxy.getPort()) {
                            builder.port(proxy.getPort());
                        }
                        if (CharSequenceUtil.isNotBlank(proxy.getUsername())) {
                            builder.username(proxy.getUsername());
                        }
                        if (CharSequenceUtil.isNotBlank(proxy.getPassword())) {
                            builder.password(username -> proxy.getPassword());
                        }
                        if (CharSequenceUtil.isNotBlank(proxy.getNonProxyHostsPattern())) {
                            builder.nonProxyHosts(proxy.getNonProxyHostsPattern());
                        }
                    });
        }
        return httpClient;
    }

    /**
     * Get trusted x 509 certificates for trust manager x 509 certificate [ ].
     *
     * @return the x 509 certificate []
     */
    protected X509Certificate[] getTrustedX509CertificatesForTrustManager() {
        ReactiveHttpClientConfiguration.Ssl ssl = this.reactiveHttpClientConfiguration.getSsl();
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            ArrayList<Certificate> allCerts = new ArrayList<>();
            for (String trustedCert : ssl.getTrustedX509Certificates()) {
                URL url = ResourceUtil.getResource(trustedCert);
                if(null != url){
                    try {
                        Collection<? extends Certificate> certs = certificateFactory.generateCertificates(url.openStream());
                        allCerts.addAll(certs);
                    } catch (IOException e) {
                        throw new RuntimeException("Could not load certificate '" + trustedCert + "'", e);
                    }
                }
            }
            return allCerts.toArray(new X509Certificate[allCerts.size()]);
        } catch (CertificateException ex) {
            throw new RuntimeException("Could not load CertificateFactory X.509", ex);
        }
    }

    /**
     * Gets key manager factory.
     *
     * @return the key manager factory
     */
    protected KeyManagerFactory getKeyManagerFactory() {
        ReactiveHttpClientConfiguration.Ssl ssl = this.reactiveHttpClientConfiguration.getSsl();
        try {
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            char[] keyPassword = ssl.getKeyPassword() != null ? ssl.getKeyPassword().toCharArray() : null;
            if (keyPassword == null && ssl.getKeyStorePassword() != null) {
                keyPassword = ssl.getKeyStorePassword().toCharArray();
            }
            keyManagerFactory.init(this.createKeyStore(), keyPassword);
            return keyManagerFactory;
        } catch (Exception e) {
            throw new RuntimeException("Could not load key manager factory", e);
        }
    }

    /**
     * Create key store .
     *
     * @return the key store
     */
    protected KeyStore createKeyStore() {
        ReactiveHttpClientConfiguration.Ssl ssl = this.reactiveHttpClientConfiguration.getSsl();
        try {
            KeyStore store = ssl.getKeyStoreProvider() != null
                    ? KeyStore.getInstance(ssl.getKeyStoreType(), ssl.getKeyStoreProvider())
                    : KeyStore.getInstance(ssl.getKeyStoreType());
            try {
                URL url = ResourceUtil.getResource(ssl.getKeyStore());
                store.load(url.openStream(), ssl.getKeyStorePassword() != null ? ssl.getKeyStorePassword().toCharArray() : null);
            } catch (Exception e) {
                throw new RuntimeException("Could not load key store ' " + ssl.getKeyStore() + "'", e);
            }
            return store;
        } catch (KeyStoreException | NoSuchProviderException e) {
            throw new RuntimeException("Could not load KeyStore for given type and provider", e);
        }
    }

    /**
     * Build connection provider .
     *
     * @param reactiveHttpClientConfiguration the reactive http client configuration
     * @return the connection provider
     */
    protected ConnectionProvider buildConnectionProvider(ReactiveHttpClientConfiguration reactiveHttpClientConfiguration) {
        ReactiveHttpClientConfiguration.Pool pool = reactiveHttpClientConfiguration.getPool();
        if (DISABLED.equals(pool.getType())) {
            return ConnectionProvider.newConnection();
        }
        ConnectionProvider.Builder builder = ConnectionProvider.builder(pool.getName());
        if (FIXED.equals(pool.getType())) {
            builder.maxConnections(pool.getMaxConnections())
                    .pendingAcquireMaxCount(-1)
                    .pendingAcquireTimeout(Duration.ofMillis(pool.getAcquireTimeout()));
        } else {
            // ELASTIC
            builder.maxConnections(Integer.MAX_VALUE)
                    .pendingAcquireTimeout(Duration.ofMillis(0))
                    .pendingAcquireMaxCount(-1);
        }
        if (pool.getMaxIdleTime() != null) {
            builder.maxIdleTime(pool.getMaxIdleTime());
        }
        if (pool.getMaxLifeTime() != null) {
            builder.maxLifeTime(pool.getMaxLifeTime());
        }
        if(pool.getEvictionInterval() != null){
            builder.evictInBackground(pool.getEvictionInterval());
        }
        return builder.build();
    }

}
