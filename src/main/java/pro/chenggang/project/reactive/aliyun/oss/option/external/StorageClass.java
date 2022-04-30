package pro.chenggang.project.reactive.aliyun.oss.option.external;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The storage class
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum StorageClass {

    /**
     * Standard
     */
    STANDARD("Standard"),

    /**
     * Infrequent Access
     */
    IA("IA"),

    /**
     * Archive
     */
    ARCHIVE("Archive"),

    /**
     * ColdArchive
     */
    COLD_ARCHIVE("ColdArchive"),

    /**
     * Unknown
     */
    UNKNOWN("Unknown"),

    ;

    private final String value;

}