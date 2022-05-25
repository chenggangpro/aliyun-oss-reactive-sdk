package pro.chenggang.project.reactive.aliyun.oss.exception.client;

import lombok.ToString;
import pro.chenggang.project.reactive.aliyun.oss.exception.OssClientException;

/**
 * Serialize failed Exception
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
@ToString(callSuper = true)
public class SerializeFailedException extends OssClientException {

    private static final long serialVersionUID = 4660946757072100155L;

    private static final String errorCode = "SerializeFailed";

    public SerializeFailedException(String errorMessage) {
        super(errorCode, errorMessage);
    }

    public SerializeFailedException(Throwable cause) {
        super(errorCode, cause.getMessage(), cause);
    }
}
