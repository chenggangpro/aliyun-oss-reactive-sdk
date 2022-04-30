package pro.chenggang.project.reactive.aliyun.oss.exception;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * The base oss exception
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
public class OssException extends RuntimeException {

    private static final long serialVersionUID = -634090785175445677L;

    protected final String errorCode;
    protected final String errorMessage;
    protected final String requestId;
    protected final String hostId;

    @JsonCreator
    public OssException(@JsonProperty("Code") String errorCode, @JsonProperty("Message") String errorMessage, @JsonProperty("RequestId") String requestId, @JsonProperty("HostId") String hostId) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.requestId = requestId;
        this.hostId = hostId;
    }

    public OssException(String errorCode, String errorMessage, String requestId, String hostId, Throwable cause) {
        super(cause);
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.requestId = requestId;
        this.hostId = hostId;
    }

}
