package pro.chenggang.project.reactive.aliyun.oss.entity.model.bucket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class BucketListResultTest {

    @Mock
    Owner owner;
    @Mock
    List<Bucket> bucketList;
    @InjectMocks
    BucketListResult bucketListResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSetPrefix() {
        bucketListResult.setPrefix("prefix");
    }

    @Test
    void testSetMarker() {
        bucketListResult.setMarker("marker");
    }

    @Test
    void testSetMaxKeys() {
        bucketListResult.setMaxKeys(0);
    }

    @Test
    void testSetTruncated() {
        bucketListResult.setTruncated(true);
    }

    @Test
    void testSetNextMarker() {
        bucketListResult.setNextMarker("nextMarker");
    }

    @Test
    void testSetOwner() {
        bucketListResult.setOwner(new Owner());
    }

    @Test
    void testSetBucketList() {
        bucketListResult.setBucketList(Collections.singletonList(new Bucket()));
    }

    @Test
    void testToString() {
        String result = bucketListResult.toString();
        assertThat(result, notNullValue());
    }
}