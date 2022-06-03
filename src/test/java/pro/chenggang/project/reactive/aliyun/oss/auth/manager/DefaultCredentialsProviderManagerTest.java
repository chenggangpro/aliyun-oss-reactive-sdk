package pro.chenggang.project.reactive.aliyun.oss.auth.manager;

import org.junit.jupiter.api.Test;
import pro.chenggang.project.reactive.aliyun.oss.auth.provider.CredentialsProvider;
import pro.chenggang.project.reactive.aliyun.oss.auth.provider.MultiCredentialsProvider;
import pro.chenggang.project.reactive.aliyun.oss.auth.provider.SingleCredentialsProvider;
import pro.chenggang.project.reactive.aliyun.oss.entity.auth.Credentials;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
class DefaultCredentialsProviderManagerTest {

    DefaultCredentialsProviderManager credentialsProviderManager = new DefaultCredentialsProviderManager();

    @Test
    void registerCredentialsProvider() {
        credentialsProviderManager.registerCredentialsProvider(new SingleCredentialsProvider("","",""));
    }

    @Test
    void getCredentialsProvider() {
        credentialsProviderManager.registerCredentialsProvider(new SingleCredentialsProvider("","",""));
        Optional<CredentialsProvider> optionalCredentialsProvider = credentialsProviderManager.getCredentialsProvider("");
        assertTrue(optionalCredentialsProvider.isPresent());
    }

    @Test
    void getCredentialsProvider2() {
        credentialsProviderManager.registerCredentialsProvider(new SingleCredentialsProvider("","",""));
        Optional<CredentialsProvider> optionalCredentialsProvider = credentialsProviderManager.getCredentialsProvider("");
        assertTrue(optionalCredentialsProvider.isPresent());
    }

    @Test
    void getCredentialsProvider3() {
        credentialsProviderManager.registerCredentialsProvider(new MultiCredentialsProvider());
        credentialsProviderManager.setDefaultCredentialsProvider(new SingleCredentialsProvider("","",""));
        Optional<CredentialsProvider> optionalCredentialsProvider = credentialsProviderManager.getCredentialsProvider("");
        assertFalse(optionalCredentialsProvider.isPresent());
    }

    @Test
    void getCredentialsProvider4() {
        credentialsProviderManager.registerCredentialsProvider(new MultiCredentialsProvider());
        credentialsProviderManager.setDefaultCredentialsProvider(new SingleCredentialsProvider("","",""));
        Optional<CredentialsProvider> optionalCredentialsProvider = credentialsProviderManager.getCredentialsProvider("",true);
        assertTrue(optionalCredentialsProvider.isPresent());
    }

    @Test
    void getCredentialsProvider5() {
        MultiCredentialsProvider multiCredentialsProvider = new MultiCredentialsProvider();
        multiCredentialsProvider.addCredentials("", Credentials.of("","",""));
        credentialsProviderManager.registerCredentialsProvider(multiCredentialsProvider);
        credentialsProviderManager.setDefaultCredentialsProvider(new SingleCredentialsProvider("","",""));
        Optional<CredentialsProvider> optionalCredentialsProvider = credentialsProviderManager.getCredentialsProvider("x",true);
        assertTrue(optionalCredentialsProvider.isPresent());
    }

    @Test
    void getCredentialsProvider6() {
        MultiCredentialsProvider multiCredentialsProvider = new MultiCredentialsProvider();
        multiCredentialsProvider.addCredentials("", Credentials.of("","",""));
        credentialsProviderManager.registerCredentialsProvider(multiCredentialsProvider);
        credentialsProviderManager.setDefaultCredentialsProvider(new SingleCredentialsProvider("","",""));
        Optional<CredentialsProvider> optionalCredentialsProvider = credentialsProviderManager.getCredentialsProvider("",true);
        assertTrue(optionalCredentialsProvider.isPresent());
    }

    @Test
    void getCredentialsProvider7() {
        MultiCredentialsProvider multiCredentialsProvider = new MultiCredentialsProvider();
        multiCredentialsProvider.addCredentials("", Credentials.of("","",""));
        credentialsProviderManager.registerCredentialsProvider(multiCredentialsProvider);
        credentialsProviderManager.setDefaultCredentialsProvider(new SingleCredentialsProvider("","",""));
        Optional<CredentialsProvider> optionalCredentialsProvider = credentialsProviderManager.getCredentialsProvider("x");
        assertFalse(optionalCredentialsProvider.isPresent());
    }

    @Test
    void getCredentialsProvider8() {
        credentialsProviderManager.setDefaultCredentialsProvider(new SingleCredentialsProvider("","",""));
        Optional<CredentialsProvider> optionalCredentialsProvider = credentialsProviderManager.getCredentialsProvider("x",true);
        assertTrue(optionalCredentialsProvider.isPresent());
    }
}