package dev.russell.judolib3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;

@Service
public class StreamService {

    @Autowired
    private S3Client blobClient;

    public String populateMenu() {
        return "";
    }
}
