package pro.chenggang.project.reactive.aliyun.oss.exception.bucket;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import pro.chenggang.project.reactive.aliyun.oss.exception.OssException;

/**
 * Invalid argument 
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
public class InvalidBucketNameException extends OssException {

    private static final long serialVersionUID = -5862813712023978018L;

    @JsonCreator
    public InvalidBucketNameException(@JsonProperty("Code") String errorCode, @JsonProperty("Message") String errorMessage, @JsonProperty("RequestId") String requestId, @JsonProperty("HostId") String hostId) {
        super(errorCode, errorMessage, requestId, hostId);
    }
}
