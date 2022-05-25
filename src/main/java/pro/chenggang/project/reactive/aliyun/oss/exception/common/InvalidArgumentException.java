package pro.chenggang.project.reactive.aliyun.oss.exception.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import pro.chenggang.project.reactive.aliyun.oss.exception.OssException;

/**
 * Invalid argument
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@ToString(callSuper = true)
public class InvalidArgumentException extends OssException {

    private static final long serialVersionUID = -5862813712023978018L;

    private final String argumentName;
    private final String argumentValue;

    @JsonCreator
    public InvalidArgumentException(@JsonProperty("Code") String errorCode, @JsonProperty("Message") String errorMessage, @JsonProperty("RequestId") String requestId, @JsonProperty("HostId") String hostId, @JsonProperty("ArgumentName") String argumentName, @JsonProperty("ArgumentValue") String argumentValue) {
        super(errorCode, errorMessage, requestId, hostId);
        this.argumentName = argumentName;
        this.argumentValue = argumentValue;
    }
}
