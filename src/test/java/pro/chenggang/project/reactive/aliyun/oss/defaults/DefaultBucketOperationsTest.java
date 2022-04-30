package pro.chenggang.project.reactive.aliyun.oss.defaults;

import org.junit.jupiter.api.Test;
import pro.chenggang.project.reactive.aliyun.oss.entity.model.bucket.AccessControlPolicy;
import pro.chenggang.project.reactive.aliyun.oss.entity.model.bucket.Bucket;
import pro.chenggang.project.reactive.aliyun.oss.entity.model.bucket.BucketListResult;
import pro.chenggang.project.reactive.aliyun.oss.option.external.AccessControlList;
import pro.chenggang.project.reactive.aliyun.oss.option.external.DataRedundancyType;
import pro.chenggang.project.reactive.aliyun.oss.option.external.StorageClass;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * DefaultBucketOperationsTest
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 */
class DefaultBucketOperationsTest {

    DefaultBucketOperations defaultBucketOperations = new DefaultBucketOperations();

    @Test
    void testListBuckets() {
        Mono<BucketListResult> result = defaultBucketOperations.listBuckets("prefix", "marker", 0);
        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void testGetBucketAcl() {
        Mono<AccessControlPolicy> result = defaultBucketOperations.getBucketAcl("bucketName");
        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void testPutBucketAcl() {
        Mono<Void> result = defaultBucketOperations.putBucketAcl("bucketName", AccessControlList.DEFAULT);
        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void testGetBucketInfo() {
        Mono<Bucket> result = defaultBucketOperations.getBucketInfo("bucketName");
        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void testGetBucketLocation() {
        Mono<String> result = defaultBucketOperations.getBucketLocation("bucketName");
        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void testPutBucket() {
        Mono<Void> result = defaultBucketOperations.putBucket("bucketName", AccessControlList.DEFAULT, StorageClass.STANDARD, DataRedundancyType.LRS);
        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void testDeleteBucket() {
        Mono<Void> result = defaultBucketOperations.deleteBucket("bucketName");
        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void testListBuckets2() {
        Flux<Bucket> result = defaultBucketOperations.listBuckets();
        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void testDoesBucketExist() {
        Mono<Boolean> result = defaultBucketOperations.doesBucketExist("bucketName");
        StepVerifier.create(result)
                .expectNext(false)
                .verifyComplete();
    }

    @Test
    void testPutBucket2() {
        Mono<Void> result = defaultBucketOperations.putBucket("bucketName", AccessControlList.DEFAULT, StorageClass.STANDARD);
        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void testPutBucket3() {
        Mono<Void> result = defaultBucketOperations.putBucket("bucketName", AccessControlList.DEFAULT);
        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void testPutBucket4() {
        Mono<Void> result = defaultBucketOperations.putBucket("bucketName");
        StepVerifier.create(result)
                .verifyComplete();
    }
}