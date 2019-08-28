package com.pinwheel.anabel.service.module.aws.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.pinwheel.anabel.service.SiteSettingService;
import com.pinwheel.anabel.util.ConvertUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class SimpleAwsS3Client implements AwsS3Client {
    private final SiteSettingService siteSettingService;

    private final String bucketName;

    private final AmazonS3 client;

    @Value("${amazon.permission.s3.clean}")
    private boolean allowedCleaning;

    public String put(String path, MultipartFile multipartFile) {
        return put(path, ConvertUtils.multipartFileToFile(multipartFile));
    }

    @Override
    public String put(String path, File file) {
        client.putObject(new PutObjectRequest(bucketName, path, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        return client.getUrl(bucketName, path).toString();
    }

    @Override
    public boolean doesObjectExists(String objectName) {
        return this.client.doesObjectExist(bucketName, objectName);
    }

    @Override
    public void cleanBucket() {
        if (!isAllowedCleaning()) {
            throw new NotAllowedOperation("Cleaning of the bucket not allowed.");
        }

        ObjectListing objectListing = client.listObjects(bucketName);

        while (true) {
            objectListing.getObjectSummaries()
                    .forEach((summary) -> client.deleteObject(bucketName, summary.getKey()));

            if (objectListing.isTruncated()) {
                objectListing = client.listNextBatchOfObjects(objectListing);
            } else {
                break;
            }
        }
    }

    @Override
    public boolean isAllowedCleaning() {
        return siteSettingService.exists("aws.s3.allow-cleaning")
                && (boolean) siteSettingService.getValue("aws.s3.allow-cleaning")
                && allowedCleaning;
    }
}
