package pro.chenggang.project.reactive.aliyun.oss.exception.bucket;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import pro.chenggang.project.reactive.aliyun.oss.exception.OssException;

/**
 * Too many buckets
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@ToString(callSuper = true)
public class TooManyBucketsException extends OssException {

    private static final long serialVersionUID = 604551970593836479L;

    @JsonCreator
    public TooManyBucketsException(@JsonProperty("Code") String errorCode, @JsonProperty("Message") String errorMessage, @JsonProperty("RequestId") String requestId, @JsonProperty("HostId") String hostId) {
        super(errorCode, errorMessage, requestId, hostId);
    }
}
