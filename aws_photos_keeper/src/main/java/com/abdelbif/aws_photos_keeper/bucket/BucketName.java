package com.abdelbif.aws_photos_keeper.bucket;

public enum BucketName {
    PROFILE_IMAGE("springreactbucket123");

    private final String bucketName;
    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}
