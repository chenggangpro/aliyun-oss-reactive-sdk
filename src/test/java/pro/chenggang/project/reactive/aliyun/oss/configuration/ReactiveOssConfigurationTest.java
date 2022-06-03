package pro.chenggang.project.reactive.aliyun.oss.configuration;

import org.junit.jupiter.api.Test;
import pro.chenggang.project.reactive.aliyun.oss.auth.manager.DefaultCredentialsProviderManager;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
class ReactiveOssConfigurationTest {

    @Test
    void testReactiveOssConfiguration() {
        ReactiveOssConfiguration reactiveOssConfiguration = new ReactiveOssConfiguration(Duration.ZERO,Duration.ZERO,new DefaultCredentialsProviderManager());
        assertThat(reactiveOssConfiguration, notNullValue());
    }

}