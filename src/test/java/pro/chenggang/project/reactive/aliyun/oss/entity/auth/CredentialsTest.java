package pro.chenggang.project.reactive.aliyun.oss.entity.auth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
class CredentialsTest {

    @Test
    void of() {
        Credentials credentials = Credentials.of("", "", "");
        assertNotNull(credentials);
    }

    @Test
    void testOf() {
        Credentials credentials = Credentials.of("", "", "","");
        assertNotNull(credentials);
    }

    @Test
    void useSecurityToken() {
        Credentials credentials = Credentials.of("", "", "","");
        assertFalse(credentials.useSecurityToken());
    }
}