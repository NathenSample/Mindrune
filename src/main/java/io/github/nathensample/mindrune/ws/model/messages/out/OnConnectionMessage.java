package io.github.nathensample.mindrune.ws.model.messages.out;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.nathensample.mindrune.ws.model.messages.AbstractMessage;
import io.github.nathensample.mindrune.ws.model.types.MessageType;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
public class OnConnectionMessage extends AbstractMessage {

    @JsonCreator
    public OnConnectionMessage(@JsonProperty("clientId") final String clientId) {
        super(clientId);
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.REGISTRATION;
    }
}
