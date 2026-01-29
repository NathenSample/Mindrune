package io.github.nathensample.mindrune.ws.model.session;

import jakarta.websocket.Session;
import lombok.*;
import org.springframework.web.socket.WebSocketSession;

import java.time.Instant;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionMetrics {
    private final Instant start;
    private final String clientId;
    private final WebSocketSession webSocketSession;
    @Setter
    private String username;

    public static final class Builder {

        private final Instant start;
        private final String clientId;
        private final WebSocketSession webSocketSession;
        private String username = null;

        public Builder(final Instant start, final String clientId, final WebSocketSession webSocketSession) {
            this.start = start;
            this.clientId = clientId;
            this.webSocketSession = webSocketSession;
        }

        public Builder withUsername(final String username) {
            this.username = username;
            return this;
        }

        public SessionMetrics build() {
            return new SessionMetrics(start, clientId, webSocketSession, username);
        }
    }
}
