package pro.chenggang.project.reactive.aliyun.oss.entity.model.bucket;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The Bucket's owner.
 */
@Getter
@Setter
@ToString
public class Owner {

    /**
     * owner id
     */
    private String id;
    /**
     * owner display name
     */
    private String displayName;

}