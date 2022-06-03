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
public class CredentialsNotFoundException extends OssClientException {

    private static final long serialVersionUID = -3431592423373126900L;

    private static final String errorCode = "CredentialsNotFound";

    public CredentialsNotFoundException(String errorMessage) {
        super(errorCode, errorMessage);
    }

    public CredentialsNotFoundException(Throwable cause) {
        super(errorCode, cause.getMessage(), cause);
    }
}
