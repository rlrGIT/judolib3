package dev.russell.judolib3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.util.List;

@Service
public class StreamService {
    private static final Logger log = LoggerFactory.getLogger(StreamService.class);

    private final String bucketName = "judo-blob-3";

    @Autowired
    private S3Client blobClient;

    public List<String> populateMenu() {
        // maybe this should return xml?
        try {
            var listResponse = blobClient.listObjectsV2(
                    ListObjectsV2Request.builder()
                            .bucket(bucketName)
                            .build()
            );

            return listResponse.contents()
                    .stream()
                    .map(content -> content.key() + ": size = " + content.size())
                    .toList();

        } catch (S3Exception unexpected) {
            log.info(unexpected.getMessage());
            throw new RuntimeException(unexpected.getMessage());
        }
    }
}
