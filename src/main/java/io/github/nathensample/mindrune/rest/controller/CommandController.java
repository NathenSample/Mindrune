package io.github.nathensample.mindrune.rest.controller;

import io.github.nathensample.mindrune.ws.service.WebSocketCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/commands")
@RequiredArgsConstructor
public class CommandController {

    private final WebSocketCommandService commandService;

    @PostMapping("/{clientId}")
    public ResponseEntity<Void> sendCommand(@PathVariable final String clientId) {
        try {
            commandService.sendExampleCommand(clientId);
            return ResponseEntity.accepted().build();
        } catch (Exception e) {
            log.error("Failed to send command to client {}", clientId, e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
