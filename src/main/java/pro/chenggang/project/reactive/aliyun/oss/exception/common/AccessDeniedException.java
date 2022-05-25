package pro.chenggang.project.reactive.aliyun.oss.exception.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import pro.chenggang.project.reactive.aliyun.oss.exception.OssException;

/**
 * Access denied
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@ToString(callSuper = true)
public class AccessDeniedException extends OssException {

    private static final long serialVersionUID = 354977138181040042L;

    @JsonCreator
    public AccessDeniedException(@JsonProperty("Code") String errorCode, @JsonProperty("Message") String errorMessage, @JsonProperty("RequestId") String requestId, @JsonProperty("HostId") String hostId) {
        super(errorCode, errorMessage, requestId, hostId);
    }
}
