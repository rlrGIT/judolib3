package dev.russell.judolib3;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class Controller {

    @GetMapping("/")
    public ResponseEntity<String> index() {
        return ResponseEntity.ok("Welcome to judo.lib!");
    }
}
