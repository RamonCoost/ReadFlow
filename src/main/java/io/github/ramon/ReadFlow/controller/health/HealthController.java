package io.github.ramon.ReadFlow.controller.health;

import io.github.ramon.ReadFlow.business.dto.health.HealthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping
    ResponseEntity<HealthResponse> health(){
        return ResponseEntity.ok(new HealthResponse("UP"));
    }
}
