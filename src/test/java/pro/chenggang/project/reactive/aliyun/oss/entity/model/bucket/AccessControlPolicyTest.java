package pro.chenggang.project.reactive.aliyun.oss.entity.model.bucket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.chenggang.project.reactive.aliyun.oss.option.external.AccessControlList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class AccessControlPolicyTest {

    @Mock
    Owner owner;

    @InjectMocks
    AccessControlPolicy accessControlPolicy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSetOwner() {
        accessControlPolicy.setOwner(new Owner());
    }

    @Test
    void testSetAccessControlList() {
        accessControlPolicy.setAccessControlList(AccessControlList.DEFAULT);
    }

    @Test
    void testToString() {
        String result = accessControlPolicy.toString();
        assertThat(result,notNullValue());
    }
}