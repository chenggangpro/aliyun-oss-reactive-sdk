package pro.chenggang.project.reactive.aliyun.oss.entity.model.bucket;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pro.chenggang.project.reactive.aliyun.oss.option.external.AccessControlList;
import pro.chenggang.project.reactive.aliyun.oss.option.external.CommonStatus;
import pro.chenggang.project.reactive.aliyun.oss.option.external.DataRedundancyType;
import pro.chenggang.project.reactive.aliyun.oss.option.external.StorageClass;

import java.time.LocalDateTime;

/**
 * The Bucket.
 */
@Getter
@Setter
@ToString
public class Bucket {

	/**
	 * bucket's create time
	 */
	private LocalDateTime creationDate;
	/**
	 * bucket's extranet domain name
	 */
	private String extranetEndpoint;
	/**
	 * intranet domain name of bucket accessed by ECS in the same region
	 */
	private String intranetEndpoint;
	/**
	 * region id
	 */
	private String region;
	/**
	 * bucket's geographic location
	 */
	private String location;
	/**
	 * the storage type of the Bucket
	 */
	private StorageClass storageClass;
	/**
	 * the bucket's name
	 */
	private String name;
	/**
	 * the bucket's owner
	 */
	private Owner owner;
	/**
	 * the bucket's acl
	 */
	private AccessControlList accessControlList;
	/**
	 * bucket's data tolerance type
	 */
	private DataRedundancyType dataRedundancyType;
	/**
	 * the version control status of the Bucket
	 */
	private CommonStatus versionControlStatus;
	/**
	 * displays the transfer acceleration status of Bucket
	 */
	private CommonStatus transferAcceleration;
	/**
	 * displays the cross-region replication status of the Bucket
	 */
	private CommonStatus crossRegionReplication;
	/**
	 * the bucket's comment
	 */
	private String comment;

}