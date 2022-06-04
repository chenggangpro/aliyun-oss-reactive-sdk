package pro.chenggang.project.reactive.aliyun.oss.defaults;

import lombok.RequiredArgsConstructor;
import pro.chenggang.project.reactive.aliyun.oss.BucketOperations;
import pro.chenggang.project.reactive.aliyun.oss.auth.AbstractSwitchableCredentialsOperation;
import pro.chenggang.project.reactive.aliyun.oss.auth.manager.CredentialsProviderManager;
import pro.chenggang.project.reactive.aliyun.oss.configuration.ReactiveOssConfiguration;
import pro.chenggang.project.reactive.aliyun.oss.entity.model.bucket.AccessControlPolicy;
import pro.chenggang.project.reactive.aliyun.oss.entity.model.bucket.Bucket;
import pro.chenggang.project.reactive.aliyun.oss.entity.model.bucket.BucketListResult;
import pro.chenggang.project.reactive.aliyun.oss.http.OssHttpClient;
import pro.chenggang.project.reactive.aliyun.oss.option.external.AccessControlList;
import pro.chenggang.project.reactive.aliyun.oss.option.external.DataRedundancyType;
import pro.chenggang.project.reactive.aliyun.oss.option.external.StorageClass;
import reactor.core.publisher.Mono;

/**
 * The default BucketOperations implementation
 *
 * @author Gang Cheng
 * @version 1.0.0
 * @since 1.0.0
 * //TODO unfinished
 */
@RequiredArgsConstructor
class DefaultBucketOperations extends AbstractSwitchableCredentialsOperation implements BucketOperations {

    private final ReactiveOssConfiguration reactiveOssConfiguration;
    private final OssHttpClient ossHttpClient;

    @Override
    protected CredentialsProviderManager getCredentialsProviderManager() {
        return this.reactiveOssConfiguration.getCredentialsProviderManager();
    }

    @Override
    public Mono<BucketListResult> listBuckets(String prefix, String marker, Integer maxKeys) {
        return Mono.empty();
    }

    @Override
    public Mono<AccessControlPolicy> getBucketAcl(String bucketName) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> putBucketAcl(String bucketName, AccessControlList accessControlList) {
        return Mono.empty();
    }

    @Override
    public Mono<Bucket> getBucketInfo(String bucketName) {
        return Mono.empty();
    }

    @Override
    public Mono<String> getBucketLocation(String bucketName) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> putBucket(String bucketName, AccessControlList accessControlList, StorageClass storageClass, DataRedundancyType dataRedundancyType) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> deleteBucket(String bucketName) {
        return Mono.empty();
    }

}
