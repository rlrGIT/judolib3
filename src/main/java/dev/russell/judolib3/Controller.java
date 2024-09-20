package dev.russell.judolib3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class Controller {

    private final VideoMetadataRepository videoRepo;

    public Controller(VideoMetadataRepository videoRepo) {
        this.videoRepo = videoRepo;
    }

    @GetMapping("/")
    public ResponseEntity<String> index() {
        return ResponseEntity.ok("Welcome to judo.lib!");
    }

    @GetMapping("/videos/{name}")
    public ResponseEntity<VideoMetadata> getVideo(
            @PathVariable(name = "name") String name
    ) {
        return ResponseEntity.ok(videoRepo.findById(name)
                .orElseThrow(
                    () -> new RuntimeException(String.format("Video: %s was not found.", name))
                )
        );
    }
}
