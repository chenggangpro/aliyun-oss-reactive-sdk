package pro.chenggang.project.reactive.aliyun.oss.entity.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * The credentials context
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@ToString
@RequiredArgsConstructor
public class CredentialsContext {

    private final String credentialsIdentity;
    private final boolean fallbackWithDefault;
}
