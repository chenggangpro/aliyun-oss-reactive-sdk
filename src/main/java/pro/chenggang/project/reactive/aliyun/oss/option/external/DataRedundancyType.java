package pro.chenggang.project.reactive.aliyun.oss.option.external;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Data redundancy type
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum DataRedundancyType {
    /**
     * LRS
     */
    LRS("LRS"),

    /**
     * ZRS
     */
    ZRS("ZRS"),

    ;

    private final String value;

}