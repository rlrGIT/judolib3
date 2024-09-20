package dev.russell.judolib3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@TestConfiguration
public class JudolibTestConfiguration {
    private static final Logger log = LoggerFactory.getLogger(JudolibTestConfiguration.class);

    @Bean
    public S3Client testClientProvider(
            @Value("${localstack.s3.endpoint}") URI mockEndpoint
    ) {
        final String ACCESS_KEY = "fake-user";
        final String SECRET_KEY = "fake-pass";

        return S3Client.builder()
                .endpointOverride(mockEndpoint)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY)))
                .region(Region.US_EAST_1)
                .build();
    }

    @Bean
    public List<String> s3ObjectProvider() {
        // maybe just implement uploads? rather than injecting starting vals. you may repeat code.
        return Collections.emptyList();
    }

    /*
        Maybe to fit he testcontainer paradigm, these get moved? Or do I use the
        CommandLineRunner with S3Mock?
        TODO: REFACTOR TO BE MONGO TEST CONTAINER, ISOLATE TESTS INTO GROUPS
     */
    @Bean
    public List<VideoMetadata> mongoEntryProvider() {
        var entry0 = new VideoMetadata("seoi-nage", "how to throw seoi nage", "blob/path", "thumb/path");
        var entry1 = new VideoMetadata("uchi-mata", "how to throw uchi mata", "blob/path1", "thumb/path1");
        return List.of(entry0, entry1);
    }

    @Bean
    public CommandLineRunner initMongoWithEntries(
            VideoMetadataRepository repo,
            @Autowired List<VideoMetadata> mockVideos
    ) {
        return args -> {
            for (var video : mockVideos) {
                log.info("Preloading " + repo.save(video));
            }
        };
    }
}
