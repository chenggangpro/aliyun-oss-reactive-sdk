package pro.chenggang.project.reactive.aliyun.oss.configuration;

import org.junit.jupiter.api.Test;

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
        ReactiveOssConfiguration reactiveOssConfiguration = new ReactiveOssConfiguration();
        assertThat(reactiveOssConfiguration,notNullValue());
    }

}