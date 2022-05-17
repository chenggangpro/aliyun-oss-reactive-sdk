package pro.chenggang.project.reactive.aliyun.oss;

import pro.chenggang.project.reactive.aliyun.oss.entity.model.bucket.AccessControlPolicy;
import pro.chenggang.project.reactive.aliyun.oss.entity.model.bucket.Bucket;
import pro.chenggang.project.reactive.aliyun.oss.entity.model.bucket.BucketListResult;
import pro.chenggang.project.reactive.aliyun.oss.exception.bucket.BucketAlreadyExistsException;
import pro.chenggang.project.reactive.aliyun.oss.exception.bucket.BucketNotEmptyException;
import pro.chenggang.project.reactive.aliyun.oss.exception.bucket.InvalidBucketNameException;
import pro.chenggang.project.reactive.aliyun.oss.exception.bucket.NoSuchBucketException;
import pro.chenggang.project.reactive.aliyun.oss.exception.bucket.TooManyBucketsException;
import pro.chenggang.project.reactive.aliyun.oss.exception.common.AccessDeniedException;
import pro.chenggang.project.reactive.aliyun.oss.option.external.AccessControlList;
import pro.chenggang.project.reactive.aliyun.oss.option.external.DataRedundancyType;
import pro.chenggang.project.reactive.aliyun.oss.option.external.StorageClass;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

/**
 * Bucket Operations
 * <p>
 * Entry point interface of Alibaba Cloud's OSS (Object Store Service) Bucket Operations
 * </p>
 *
 * @author Gang Cheng
 * @since 1.0.0
 */
public interface BucketOperations {

    /**
     * Get all buckets which meet the conditions specified
     *
     * @param prefix  the prefix filter for the buckets to return,all buckets returned must start with this prefix.
     * @param marker  the maker filter for the buckets to return,all buckets returned must be greater than the marker
     * @param maxKeys the max number of buckets to return , by default ,it is 100
     * @return the {@link BucketListResult}
     */
    Mono<BucketListResult> listBuckets(String prefix, String marker, Integer maxKeys);

    /**
     * Get all buckets
     *
     * @return A list of {@link Bucket} instances. If there's no buckets, the flux will be empty
     */
    default Flux<Bucket> listBuckets() {
        return listBuckets(null, null, null)
                .expand(bucketList -> {
                    if (!bucketList.isTruncated()) {
                        return Mono.empty();
                    }
                    return listBuckets(null, bucketList.getNextMarker(), null);
                })
                .flatMap(bucketList -> Mono.justOrEmpty(bucketList)
                        .map(BucketListResult::getBucketList)
                        .filter(buckets -> !buckets.isEmpty())
                        .flatMapIterable(Function.identity())
                );
    }

    /**
     * Get bucket access control policy by target bucket name
     *
     * @param bucketName the target bucket name
     * @return Returns {@link AccessControlPolicy} when the bucket exists otherwise returns an error signal
     * @throws NoSuchBucketException if the target bucket is not exist
     * @throws AccessDeniedException if the target bucket without permission to access
     */
    Mono<AccessControlPolicy> getBucketAcl(String bucketName);

    /**
     * Put bucket access control policy with bucket name and {@link AccessControlList}
     *
     * @param bucketName        the target bucket name
     * @param accessControlList the target {@link AccessControlList} to put
     * @return nothing if success
     * @throws AccessDeniedException if the target bucket without permission to set acl
     */
    Mono<Void> putBucketAcl(String bucketName, AccessControlList accessControlList);

    /**
     * Get bucket info by target bucket name
     *
     * @param bucketName the target bucket name
     * @return Returns {@link Bucket} when the bucket exists otherwise returns an error signal
     * @throws NoSuchBucketException if the target bucket is not exist
     * @throws AccessDeniedException if the target bucket without permission to access
     */
    Mono<Bucket> getBucketInfo(String bucketName);

    /**
     * Get bucket location
     *
     * @param bucketName the target bucket name
     * @return the bucket's LocationConstraint
     * @throws AccessDeniedException if the target bucket without permission to access
     */
    Mono<String> getBucketLocation(String bucketName);

    /**
     * Checks if the bucket exists
     *
     * @param bucketName the target bucket name
     * @return Returns true if the bucket exists and false if not.
     * @throws AccessDeniedException if the target bucket without permission to access
     */
    default Mono<Boolean> doesBucketExist(String bucketName) {
        return getBucketAcl(bucketName)
                .flatMap(accessControlPolicy -> Mono.just(Objects.nonNull(accessControlPolicy.getOwner())))
                .onErrorResume(NoSuchBucketException.class, throwable -> Mono.just(false))
                .switchIfEmpty(Mono.just(false));
    }

    /**
     * Create bucket
     *
     * @param bucketName         the bucket name
     * @param accessControlList  the {@link AccessControlList}
     * @param storageClass       the {@link StorageClass}
     * @param dataRedundancyType the {@link DataRedundancyType}
     * @return nothing if success
     * @throws AccessDeniedException        if the target bucket without permission to access
     * @throws InvalidBucketNameException   if the bucket name is invalid
     * @throws TooManyBucketsException      if the bucket's count matches the largest allowed bucket's count
     * @throws BucketAlreadyExistsException if the bucket already exist
     */
    Mono<Void> putBucket(String bucketName, AccessControlList accessControlList, StorageClass storageClass, DataRedundancyType dataRedundancyType);

    /**
     * Create bucket
     *
     * @param bucketName        the bucket name
     * @param accessControlList the {@link AccessControlList}
     * @param storageClass      the {@link StorageClass}
     * @return nothing if success
     * @throws AccessDeniedException        if the target bucket without permission to access
     * @throws InvalidBucketNameException   if the bucket name is invalid
     * @throws TooManyBucketsException      if the bucket's count matches the largest allowed bucket's count
     * @throws BucketAlreadyExistsException if the bucket already exist
     */
    default Mono<Void> putBucket(String bucketName, AccessControlList accessControlList, StorageClass storageClass) {
        return putBucket(bucketName, accessControlList, storageClass, DataRedundancyType.LRS);
    }

    /**
     * Create bucket
     *
     * @param bucketName        the bucket name
     * @param accessControlList the {@link AccessControlList}
     * @return nothing if success
     * @throws AccessDeniedException        if the target bucket without permission to access
     * @throws InvalidBucketNameException   if the bucket name is invalid
     * @throws TooManyBucketsException      if the bucket's count matches the largest allowed bucket's count
     * @throws BucketAlreadyExistsException if the bucket already exist
     */
    default Mono<Void> putBucket(String bucketName, AccessControlList accessControlList) {
        return putBucket(bucketName, accessControlList, StorageClass.STANDARD, DataRedundancyType.LRS);
    }

    /**
     * Create bucket
     *
     * @param bucketName the bucket name
     * @return nothing if success
     * @throws AccessDeniedException        if the target bucket without permission to access
     * @throws InvalidBucketNameException   if the bucket name is invalid
     * @throws TooManyBucketsException      if the bucket's count matches the largest allowed bucket's count
     * @throws BucketAlreadyExistsException if the bucket already exist
     */
    default Mono<Void> putBucket(String bucketName) {
        return putBucket(bucketName, AccessControlList.PRIVATE, StorageClass.STANDARD, DataRedundancyType.LRS);
    }

    /**
     * Delete bucket
     *
     * @param bucketName the bucket name
     * @return nothing if success
     * @throws AccessDeniedException   if the target bucket without permission to access
     * @throws NoSuchBucketException   if the target bucket does not exist
     * @throws BucketNotEmptyException if the target bucket is not empty
     */
    Mono<Void> deleteBucket(String bucketName);

    //TODO listObjects
    //TODO listObjectsV2
}