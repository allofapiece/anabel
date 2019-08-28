package com.pinwheel.anabel.service.module.aws.s3;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @version 1.0.0
 */
interface AwsS3Client {
    String put(String path, MultipartFile file);

    String put(String path, File file);

    boolean doesObjectExists(String objectName);

    void cleanBucket();

    boolean isAllowedCleaning();
}
