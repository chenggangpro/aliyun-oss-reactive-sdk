package pro.chenggang.project.reactive.aliyun.oss.entity.model.bucket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.chenggang.project.reactive.aliyun.oss.option.external.AccessControlList;
import pro.chenggang.project.reactive.aliyun.oss.option.external.CommonStatus;
import pro.chenggang.project.reactive.aliyun.oss.option.external.DataRedundancyType;
import pro.chenggang.project.reactive.aliyun.oss.option.external.StorageClass;

import java.time.LocalDateTime;
import java.time.Month;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class BucketTest {

    @Mock
    Owner owner;
    @InjectMocks
    Bucket bucket;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSetCreationDate() {
        bucket.setCreationDate(LocalDateTime.of(2022, Month.APRIL, 30, 15, 2, 39));
    }

    @Test
    void testSetExtranetEndpoint() {
        bucket.setExtranetEndpoint("extranetEndpoint");
    }

    @Test
    void testSetIntranetEndpoint() {
        bucket.setIntranetEndpoint("intranetEndpoint");
    }

    @Test
    void testSetRegion() {
        bucket.setRegion("region");
    }

    @Test
    void testSetLocation() {
        bucket.setLocation("location");
    }

    @Test
    void testSetStorageClass() {
        bucket.setStorageClass(StorageClass.STANDARD);
    }

    @Test
    void testSetName() {
        bucket.setName("name");
    }

    @Test
    void testSetOwner() {
        bucket.setOwner(new Owner());
    }

    @Test
    void testSetAccessControlList() {
        bucket.setAccessControlList(AccessControlList.DEFAULT);
    }

    @Test
    void testSetDataRedundancyType() {
        bucket.setDataRedundancyType(DataRedundancyType.LRS);
    }

    @Test
    void testSetVersionControlStatus() {
        bucket.setVersionControlStatus(CommonStatus.ENABLED);
    }

    @Test
    void testSetTransferAcceleration() {
        bucket.setTransferAcceleration(CommonStatus.ENABLED);
    }

    @Test
    void testSetCrossRegionReplication() {
        bucket.setCrossRegionReplication(CommonStatus.ENABLED);
    }

    @Test
    void testSetComment() {
        bucket.setComment("comment");
    }

    @Test
    void testToString() {
        String result = bucket.toString();
        assertThat(result, notNullValue());
    }
}