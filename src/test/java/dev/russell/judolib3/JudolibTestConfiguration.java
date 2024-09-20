package dev.russell.judolib3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.List;

@TestConfiguration
public class JudolibTestConfiguration {
    private static final Logger log = LoggerFactory.getLogger(JudolibTestConfiguration.class);

    @Bean
    public List<VideoMetadata> initWithTestValues() {
        var entry0 = new VideoMetadata("seoi-nage", "how to throw seoi nage", "blob/path", "thumb/path");
        var entry1 = new VideoMetadata("uchi-mata", "how to throw uchi mata", "blob/path1", "thumb/path1");
        return List.of(entry0, entry1);
    }

    @Bean
    public CommandLineRunner initRepo(
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
