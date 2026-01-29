package io.github.nathensample.mindrune.ws.model.messages.in;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.nathensample.mindrune.ws.model.messages.AbstractMessage;
import io.github.nathensample.mindrune.ws.model.types.MessageType;
import lombok.Getter;

@Getter
public class IdentifyMessage extends AbstractMessage {

    private final String username;

    @JsonCreator
    public IdentifyMessage(@JsonProperty("clientId") final String clientId, @JsonProperty("username") final String username) {
        super(clientId);
        this.username = username;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.IDENTIFY;
    }
}
