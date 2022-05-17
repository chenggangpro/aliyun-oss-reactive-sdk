package pro.chenggang.project.reactive.aliyun.oss.entity.model.bucket;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pro.chenggang.project.reactive.aliyun.oss.option.external.AccessControlList;

/**
 * The Access control policy.
 */
@Getter
@Setter
@ToString
public class AccessControlPolicy {

    /**
     * the bucket's owner
     */
    private Owner owner;
    /**
     * the bucket's acl
     */
    private AccessControlList accessControlList;

}