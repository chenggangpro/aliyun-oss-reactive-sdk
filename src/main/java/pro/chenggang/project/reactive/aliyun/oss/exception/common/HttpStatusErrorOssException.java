package pro.chenggang.project.reactive.aliyun.oss.exception.common;

import lombok.Getter;
import lombok.ToString;
import pro.chenggang.project.reactive.aliyun.oss.entity.http.MultiValueMap;
import pro.chenggang.project.reactive.aliyun.oss.exception.OssException;

/**
 * Http status error
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@ToString(callSuper = true)
public class HttpStatusErrorOssException extends OssException {

    private static final long serialVersionUID = -3053189144164400618L;

    private final int statusCode;
    private final MultiValueMap<String, String> responseHeaders;

    public HttpStatusErrorOssException(int statusCode, MultiValueMap<String, String> responseHeaders) {
        super(String.valueOf(statusCode), "Oss client response http status error", null, null);
        this.statusCode = statusCode;
        this.responseHeaders = responseHeaders;
    }
}
