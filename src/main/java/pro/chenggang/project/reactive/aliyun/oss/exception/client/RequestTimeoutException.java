package pro.chenggang.project.reactive.aliyun.oss.exception.client;

import lombok.ToString;
import pro.chenggang.project.reactive.aliyun.oss.exception.OssClientException;

/**
 * Request Timeout Exception
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
@ToString(callSuper = true)
public class RequestTimeoutException extends OssClientException {

    private static final long serialVersionUID = 494628771798809992L;

    private static final String errorCode = "RequestTimeout";
    private static final String errorMessage = "Http client request timeout";

    public RequestTimeoutException() {
        super(errorCode, errorMessage);
    }

    public RequestTimeoutException(Throwable cause) {
        super(errorCode, errorMessage, cause);
    }
}
