package pro.chenggang.project.reactive.aliyun.oss.option.external;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The Bucket's common status
 * @author evans
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum CommonStatus {

    /**
     * Enabled
     */
    ENABLED("Enabled"),

    /**
     * Suspended
     */
    SUSPENDED("Suspended"),

    /**
     * Disabled
     */
    Disabled("Disabled"),

    ;

    private final String value;
}
