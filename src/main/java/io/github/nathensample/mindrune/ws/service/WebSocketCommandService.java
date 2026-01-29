package io.github.nathensample.mindrune.ws.service;

import io.github.nathensample.mindrune.ws.handler.WebSocketHandler;
import io.github.nathensample.mindrune.ws.model.messages.out.CommandMessage;
import io.github.nathensample.mindrune.ws.model.types.CommandType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
public class WebSocketCommandService {

    @Autowired
    private WebSocketHandler webSocketHandler;

    public void sendExampleCommand(final String clientId) throws IOException {
        log.info("Sending command {}", clientId);
        CommandMessage pickupCommand = new CommandMessage(
                clientId,
                CommandType.PICKUP_ITEM,
                Map.of("x", 6208, "y", 5952, "id", "4151")
        );

        webSocketHandler.sendCommand(clientId, pickupCommand);
    }

}
