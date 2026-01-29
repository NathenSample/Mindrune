package io.github.nathensample.mindrune.ws.model.session.exception;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(final String clientId) {
        super("No session found in repository for [%s]".formatted(clientId));
    }
}
