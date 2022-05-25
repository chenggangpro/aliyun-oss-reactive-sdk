package pro.chenggang.project.reactive.aliyun.oss.exception.bucket;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import pro.chenggang.project.reactive.aliyun.oss.exception.OssException;

/**
 * Bucket is not exist
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@ToString(callSuper = true)
public class NoSuchBucketException extends OssException {

    private static final long serialVersionUID = -172303697814607692L;

    private final String bucketName;

    @JsonCreator
    public NoSuchBucketException(@JsonProperty("Code") String errorCode, @JsonProperty("Message") String errorMessage, @JsonProperty("RequestId") String requestId, @JsonProperty("HostId") String hostId, @JsonProperty("BucketName") String bucketName) {
        super(errorCode, errorMessage, requestId, hostId);
        this.bucketName = bucketName;
    }

}
