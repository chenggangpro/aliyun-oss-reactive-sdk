package pro.chenggang.project.reactive.aliyun.oss.exception.bucket;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import pro.chenggang.project.reactive.aliyun.oss.exception.OssException;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
public class BucketNotEmptyException extends OssException {

    private static final long serialVersionUID = 8635159509039614347L;

    private final String bucketName;

    @JsonCreator
    public BucketNotEmptyException(@JsonProperty("Code") String errorCode, @JsonProperty("Message") String errorMessage, @JsonProperty("RequestId") String requestId, @JsonProperty("HostId") String hostId, @JsonProperty("BucketName") String bucketName) {
        super(errorCode, errorMessage, requestId, hostId);
        this.bucketName = bucketName;
    }
}
