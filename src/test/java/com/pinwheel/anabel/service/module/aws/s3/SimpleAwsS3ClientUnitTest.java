package com.pinwheel.anabel.service.module.aws.s3;

import com.pinwheel.anabel.external.category.Unit;
import com.pinwheel.anabel.service.SiteSettingService;
import com.pinwheel.anabel.util.ConvertUtils;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = {
        "/application-test.properties",
        "/application-test-local.properties"
})
@Category(Unit.class)
class SimpleAwsS3ClientUnitTest {
    @Autowired
    private AwsS3Client s3Client;

    @MockBean
    private SiteSettingService siteSettingService;

    @Autowired
    ResourceLoader resourceLoader;

    @AfterEach
    public void cleanBucket() {
        Mockito.doReturn(true).when(siteSettingService).exists("aws.s3.allow-cleaning");
        Mockito.doReturn(true).when(siteSettingService).getValue("aws.s3.allow-cleaning");
        s3Client.cleanBucket();
    }

    @Test
    public void shouldPutFileAndMultipartFile() throws IOException {
        s3Client.put("testObject", resourceLoader.getResource("classpath:aws/text.txt").getFile());
        assertTrue(s3Client.doesObjectExists("testObject"));

        s3Client.put("testObject2", ConvertUtils.fileToMultipartFile(resourceLoader.getResource("classpath:aws/text.txt").getFile()));
        assertTrue(s3Client.doesObjectExists("testObject2"));
    }
}
