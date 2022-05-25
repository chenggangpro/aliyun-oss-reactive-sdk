package pro.chenggang.project.reactive.aliyun.oss.configuration;

import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.transport.ProxyProvider;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


/**
 * Configuration for the Netty {@link reactor.netty.http.client.HttpClient}.
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
public class ReactiveHttpClientConfiguration {

    /**
     * The http connection connect timeout in millis.
     * Default is 10S
     */
    private Integer connectTimeout = 10_000;

    /**
     * The http connection global response timeout.
     * Default is PT1M
     */
    private Duration responseTimeout = Duration.ofMinutes(5);

    /**
     * Pool configuration for Netty HttpClient
     */
    private Pool pool = new Pool();

    /**
     * Proxy configuration for Reactor Netty HttpClient
     */
    private Proxy proxy = new Proxy();

    /**
     * SSL configuration for Reactor Netty HttpClient
     */
    private Ssl ssl = new Ssl();

    /**
     * Enables wiretap debugging for Reactor Netty HttpClient.
     * Default is false
     */
    private boolean wiretap = false;

    /**
     * Enables compression for Reactor Netty HttpClient.
     * Default is true
     */
    private boolean compression = true;


    /**
     * HttpClient Pool
     */
    @Getter
    @Setter
    @ToString
    public static class Pool {

        /**
         * Type of pool for HttpClient to use, defaults to ELASTIC
         */
        private PoolType type = PoolType.ELASTIC;

        /**
         * The channel pool map name, defaults to proxy.
         */
        private String name = "reactor-netty";

        /**
         * Only for type FIXED, the maximum number of connections before starting pending
         * acquisition on existing ones.
         */
        private Integer maxConnections = ConnectionProvider.DEFAULT_POOL_MAX_CONNECTIONS;

        /**
         * Only for type FIXED, the maximum time in millis to wait for acquiring.
         */
        private Long acquireTimeout = ConnectionProvider.DEFAULT_POOL_ACQUIRE_TIMEOUT;

        /**
         * Time in millis after which the channel will be closed.
         * Default is NULL, there is no max idle time.
         */
        private Duration maxIdleTime = null;

        /**
         * Duration after which the channel will be closed.
         * Default is NULL, there is no max lifetime.
         */
        private Duration maxLifeTime = null;

        /**
         * Perform regular eviction checks in the background at a specified interval.
         * Default is {@link Duration#ZERO}, this will be disabled
         */
        private Duration evictionInterval = Duration.ZERO;


        /**
         * HttpClient Pool Type
         */
        public enum PoolType {

            /**
             * Elastic pool type.
             */
            ELASTIC,

            /**
             * Fixed pool type.
             */
            FIXED,

            /**
             * Disabled pool type.
             */
            DISABLED

        }

    }

    /**
     * HttpClient Proxy
     */
    @Getter
    @Setter
    @ToString
    public static class Proxy {

        /**
         * ProxyType for proxy configuration of Netty HttpClient
         */
        private ProxyProvider.Proxy type = ProxyProvider.Proxy.HTTP;

        /**
         * Hostname for proxy configuration of Netty HttpClient
         */
        private String host;

        /**
         * Port for proxy configuration of Netty HttpClient
         */
        private Integer port;

        /**
         * Username for proxy configuration of Netty HttpClient
         */
        private String username;

        /**
         * Password for proxy configuration of Netty HttpClient
         */
        private String password;

        /**
         * Regular expression (Java) for a configured list of hosts. that should be reached directly, bypassing the proxy
         */
        private String nonProxyHostsPattern;

    }

    /**
     * HttpClient SSL
     */
    @Getter
    @Setter
    @ToString
    public static class Ssl {

        /**
         * Installs the netty InsecureTrustManagerFactory.
         * Default is true,HttpClient will use {@link InsecureTrustManagerFactory#INSTANCE}
         */
        private boolean useInsecureTrustManager = true;

        /**
         * Trusted certificates for verifying the remote endpoint's certificate.
         */
        private List<String> trustedX509Certificates = new ArrayList<>();

        /**
         * SSL handshake timeout.
         * Default to 10000 ms
         */
        private Duration handshakeTimeout = Duration.ofMillis(10000);

        /**
         * SSL close_notify flush timeout.
         * Default to 3000 ms
         */
        private Duration closeNotifyFlushTimeout = Duration.ofMillis(3000);

        /**
         * SSL close_notify read timeout.
         * Default to 0 ms
         */
        private Duration closeNotifyReadTimeout = Duration.ZERO;

        /**
         * Keystore path for Netty HttpClient
         */
        private String keyStore;

        /**
         * Keystore type for Netty HttpClient
         * Default is JKS
         */
        private String keyStoreType = "JKS";

        /**
         * Keystore provider for Netty HttpClient,
         * Default is null
         */
        private String keyStoreProvider = null;

        /**
         * Keystore password
         */
        private String keyStorePassword;

        /**
         * Key password
         * Default is same as keyStorePassword
         */
        private String keyPassword;

    }

}
