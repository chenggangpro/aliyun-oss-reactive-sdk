package pro.chenggang.project.reactive.aliyun.oss.entity.model.bucket;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class OwnerTest {

    @Mock
    Owner owner;

    @Test
    void testSetId() {
        owner.setId("id");
    }

    @Test
    void testSetDisplayName() {
        owner.setDisplayName("displayName");
    }

    @Test
    void testToString() {
        String result = owner.toString();
        assertThat(result, notNullValue());
    }
}