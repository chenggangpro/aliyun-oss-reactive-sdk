package pro.chenggang.project.reactive.aliyun.oss.entity.model.bucket;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * The list all buckets result
 */
@Getter
@Setter
@ToString
public class BucketListResult {

    /**
     * the query result's prefix
     */
    private String prefix;
    /**
     * the query's starting point
     */
    private String marker;
    /**
     * the query result's max key count
     */
    private Integer maxKeys;
    /**
     * whether all results have been returned
     */
    private boolean isTruncated;
    /**
     * Used to assign a value to marker when continuing a query
     */
    private String nextMarker;
    /**
     * the bucket's owner
     */
    private Owner owner;
    /**
     * the query result's bucket list
     */
    private List<Bucket> bucketList;

}