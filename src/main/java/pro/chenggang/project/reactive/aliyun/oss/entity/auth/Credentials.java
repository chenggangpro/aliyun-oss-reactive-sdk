package pro.chenggang.project.reactive.aliyun.oss.entity.auth;

import cn.hutool.core.util.StrUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * The Credentials.
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Credentials {

    /**
     * The secret access key .
     */
    public final String secretAccessKey;
    /**
     * The security token .
     */
    public final String securityToken;
    /**
     * The oss endpoint
     */
    private final String endpoint;
    /**
     * The access key ID f.
     */
    private final String accessKeyId;

    /**
     * new credentials.
     *
     * @param endpoint        the endpoint
     * @param accessKeyId     the access key id
     * @param secretAccessKey the secret access key
     * @return the credentials
     */
    public static Credentials of(String endpoint, String accessKeyId, String secretAccessKey) {
        return new Credentials(endpoint, accessKeyId, secretAccessKey, null);
    }

    /**
     * new credentials.
     *
     * @param endpoint        the endpoint
     * @param accessKeyId     the access key id
     * @param secretAccessKey the secret access key
     * @param securityToken   the security token
     * @return the credentials
     */
    public static Credentials of(String endpoint, String accessKeyId, String secretAccessKey, String securityToken) {
        return new Credentials(endpoint, accessKeyId, secretAccessKey, securityToken);
    }

    /**
     * whether to use security token for http requests.
     *
     * @return the boolean
     */
    public boolean useSecurityToken() {
        return StrUtil.isNotBlank(securityToken);
    }
}
