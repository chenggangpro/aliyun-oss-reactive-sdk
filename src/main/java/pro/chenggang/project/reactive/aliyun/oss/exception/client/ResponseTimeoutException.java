package pro.chenggang.project.reactive.aliyun.oss.exception.client;

import lombok.ToString;
import pro.chenggang.project.reactive.aliyun.oss.exception.OssClientException;

import java.time.Duration;

/**
 * Response Timeout Exception
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
@ToString(callSuper = true)
public class ResponseTimeoutException extends OssClientException {

    private static final long serialVersionUID = -5493695320684468872L;

    private static final String errorCode = "ResponseTimeout";
    private static final String errorMessage = "Http client get response timeout";

    public ResponseTimeoutException(Duration timeout) {
        super(errorCode, errorMessage + ":" + timeout);
    }

    public ResponseTimeoutException(Throwable cause) {
        super(errorCode, errorMessage, cause);
    }
}
