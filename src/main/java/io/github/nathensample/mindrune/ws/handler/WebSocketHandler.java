package io.github.nathensample.mindrune.ws.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.nathensample.mindrune.ws.model.messages.out.CommandMessage;
import io.github.nathensample.mindrune.ws.model.messages.in.IdentifyMessage;
import io.github.nathensample.mindrune.ws.model.messages.out.OnConnectionMessage;
import io.github.nathensample.mindrune.ws.model.session.SessionMetrics;
import io.github.nathensample.mindrune.ws.model.session.SessionRepository;
import io.github.nathensample.mindrune.ws.model.session.exception.ClientNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private final SessionRepository sessionRepository;
    private final ObjectMapper mapper;

    @Override
    public void afterConnectionEstablished(final WebSocketSession session) throws IOException{
        sessionRepository.registerSession(session);
        session.sendMessage(new TextMessage(mapper.writeValueAsString(new OnConnectionMessage(session.getId()))));
    }

    @Override
    protected void handleTextMessage(final WebSocketSession session, final TextMessage message)
            throws IOException {

        log.info("Received from client: {}", session.getId());

        final String payload = message.getPayload();
        final IdentifyMessage identifyMessage = mapper.readValue(payload, IdentifyMessage.class);

        sessionRepository.identifySession(identifyMessage);
    }

    @Override
    public void afterConnectionClosed(final WebSocketSession session, final CloseStatus status) {
        sessionRepository.removeSession(session.getId());
        log.info("Client disconnected: {}", session.getId());
    }
    public void sendCommand(final String clientId, final CommandMessage command)
            throws IOException {

        final SessionMetrics sessionMetrics = sessionRepository.getSession(clientId)
                .orElseThrow(() -> new ClientNotFoundException(clientId));
        final WebSocketSession session = sessionMetrics.getWebSocketSession();
        if (session.isOpen()) {
            log.info("sendCommand");
            final String json = mapper.writeValueAsString(command);
            session.sendMessage(new TextMessage(json));
        } else {
            log.info("sendCommand failed");
        }

    }
}