package pro.chenggang.project.reactive.aliyun.oss.option.external;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The Access Control List (ACL)
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum AccessControlList {

    /**
     * This is only for object, means the acl inherits the bucket's acl
     */
    DEFAULT("default"),

    /**
     * The owner has the full control, other user does not have access
     */
    PRIVATE("private"),

    /**
     * The owner has the full control, other user have read-only access
     */
    PUBLIC_READ("public-read"),

    /**
     * Both the owner and other user have full control
     */
    PUBLIC_READ_WRITE("public-read-write"),

    ;

    private final String value;

}