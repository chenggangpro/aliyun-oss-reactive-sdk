package pro.chenggang.project.reactive.aliyun.oss.exception.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.ToString;
import pro.chenggang.project.reactive.aliyun.oss.entity.http.MultiValueMap;
import pro.chenggang.project.reactive.aliyun.oss.exception.OssException;

/**
 * Unidentified oss exception
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@ToString(callSuper = true)
public class UnidentifiedOssException extends OssException {

    private static final long serialVersionUID = -5462262515967361520L;

    private final int statusCode;
    private final MultiValueMap<String, String> responseHeaders;
    private final String responseBody;

    @JsonCreator
    public UnidentifiedOssException(int statusCode, MultiValueMap<String, String> responseHeaders, String responseBody) {
        super(String.valueOf(statusCode), "Unidentified oss error", null, null);
        this.statusCode = statusCode;
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
    }
}
