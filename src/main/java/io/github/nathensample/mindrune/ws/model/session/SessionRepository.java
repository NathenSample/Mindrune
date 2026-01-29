package io.github.nathensample.mindrune.ws.model.session;

import io.github.nathensample.mindrune.ws.model.messages.in.IdentifyMessage;
import io.github.nathensample.mindrune.ws.model.session.exception.ClientNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.time.Clock;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionRepository {

    private final Clock clock;
    private final Map<String, SessionMetrics> clientIdToSessionMetrics = new HashMap<>();

    public SessionMetrics registerSession(final WebSocketSession webSocketSession) {
        final SessionMetrics metrics =  new SessionMetrics.Builder(clock.instant(), webSocketSession.getId(), webSocketSession).build();
        clientIdToSessionMetrics.put(webSocketSession.getId(), metrics);
        log.info("Successfully registered session: {}", webSocketSession.getId());
        return metrics;
    }

    public SessionMetrics identifySession(final IdentifyMessage identifyMessage) {
        final SessionMetrics sessionMetrics = getSession(identifyMessage.getClientId())
                .orElseThrow(() -> new ClientNotFoundException(identifyMessage.getClientId()));

        sessionMetrics.setUsername(identifyMessage.getUsername());
        log.info("Successfully identified session: {} with username: {}", sessionMetrics.getClientId(), sessionMetrics.getUsername());
        return sessionMetrics;
    }

    public Optional<SessionMetrics> getSession(final String clientId) {
        log.info("getSession: {}", clientId);
        return Optional.ofNullable(clientIdToSessionMetrics.get(clientId));
    }

    /**
     * Attempts to remove the session for the given client Id
     * @param clientId client which we're disconnecting
     */
    public void removeSession(final String clientId) {
        final SessionMetrics metrics = clientIdToSessionMetrics.remove(clientId);
        if (metrics == null) {
            return;
        }
        try {
            if (metrics.getWebSocketSession().isOpen()) {
                metrics.getWebSocketSession().close();
            }
            log.info("Successfully cleaned up session with client: {}", clientId);
        } catch (IOException e) {
            log.warn("Failed to close connection to client: {}", clientId);
        }
    }
}
