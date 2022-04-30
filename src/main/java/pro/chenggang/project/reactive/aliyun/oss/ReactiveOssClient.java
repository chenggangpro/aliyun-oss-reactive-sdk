package pro.chenggang.project.reactive.aliyun.oss;

/**
 * Reactive Oss Client
 *
 * @author Gang Cheng
 * @since 1.0.0
 * @version 1.0.0
 */
public interface ReactiveOssClient {

    /**
     * Operations for bucket.
     *
     * @return the {@link BucketOperations}
     */
    BucketOperations bucketOperations();

}
