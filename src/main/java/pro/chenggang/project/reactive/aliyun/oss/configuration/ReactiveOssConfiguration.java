package pro.chenggang.project.reactive.aliyun.oss.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Duration;

/**
 * The Reactive Oss configuration
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
public class ReactiveOssConfiguration {

    /**
     * The response timeout when oss download file
     * Default is PT1M
     */
    private Duration downloadResponseTimeout = Duration.ofMinutes(1);

    /**
     * The response timeout when oss upload file
     * Default is PT1M
     */
    private Duration uploadResponseTimeout = Duration.ofMinutes(1);

    /**
     * The oss endpoint
     */
    private String endpoint;

}
