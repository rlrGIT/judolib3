package dev.russell.judolib3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<String>> getMenuVideoMetadata() {
        /*
            json or xml, we just need to return a view where
            we can show a series thumbnails and titles

            The template can we static and we can just dynamically load
            Dunno how we gonna avoid server side rendering
            WE DEFINITELY WANNA USE HTMX HERE
            so we should return html as a response
         */
        var json = streamService.populateMenu();
        return ResponseEntity.ok(json);
    }
}
