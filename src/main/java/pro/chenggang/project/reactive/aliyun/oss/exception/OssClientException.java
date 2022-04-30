package pro.chenggang.project.reactive.aliyun.oss.exception;

import lombok.Getter;

/**
 * The base oss client exception
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
public class OssClientException extends RuntimeException {

    private static final long serialVersionUID = 3640507403836147683L;

    protected final String errorCode;
    protected final String errorMessage;
    protected final String requestId;

    public OssClientException(String errorCode, String errorMessage) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.requestId = "unknown";
    }

    public OssClientException(String errorCode, String errorMessage, Throwable cause) {
        super(cause);
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.requestId = "unknown";
    }

    public OssClientException(String errorCode, String errorMessage, String requestId) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.requestId = requestId;
    }

    public OssClientException(String errorCode, String errorMessage, String requestId, Throwable cause) {
        super(cause);
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.requestId = requestId;
    }
}
