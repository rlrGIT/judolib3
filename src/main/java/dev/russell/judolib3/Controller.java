package dev.russell.judolib3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class Controller {

    private final VideoMetadataRepository videoRepo;
    private final StreamService streamService;

    @Autowired
    public Controller(
            VideoMetadataRepository videoRepo,
            StreamService streamService
    ) {
        this.videoRepo = videoRepo;
        this.streamService = streamService;
    }

    @GetMapping("/")
    public ResponseEntity<String> index() {
        return ResponseEntity.ok("Welcome to judo.lib!");
    }

    @GetMapping("/watch/{name}")
    public ResponseEntity<VideoMetadata> getVideo(
            @PathVariable(name = "name") String name
    ) {
        /*
            Refactor so that this streams data from the S3
         */
        return ResponseEntity.ok(videoRepo.findById(name)
                .orElseThrow(
                    () -> new RuntimeException(String.format("Video: %s was not found.", name))
                )
        );
    }

    @GetMapping("/videos")
    public ResponseEntity<String> getMenuVideoMetadata() {
        // maybe I need to use xml here, but we'll see
        var json = streamService.populateMenu();
        return ResponseEntity.ok(json);
    }
}
