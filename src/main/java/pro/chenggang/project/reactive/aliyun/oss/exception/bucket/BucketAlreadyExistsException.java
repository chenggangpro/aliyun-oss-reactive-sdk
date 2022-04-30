package pro.chenggang.project.reactive.aliyun.oss.exception.bucket;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import pro.chenggang.project.reactive.aliyun.oss.exception.OssException;

/**
 * The bucket already exists
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
public class BucketAlreadyExistsException extends OssException {

    private static final long serialVersionUID = 6142056760272003558L;

    @JsonCreator
    public BucketAlreadyExistsException(@JsonProperty("Code") String errorCode, @JsonProperty("Message") String errorMessage, @JsonProperty("RequestId") String requestId, @JsonProperty("HostId") String hostId) {
        super(errorCode, errorMessage, requestId, hostId);
    }
}
