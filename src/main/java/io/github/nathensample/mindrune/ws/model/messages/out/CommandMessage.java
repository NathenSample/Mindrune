package io.github.nathensample.mindrune.ws.model.messages.out;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.nathensample.mindrune.ws.model.messages.AbstractMessage;
import io.github.nathensample.mindrune.ws.model.types.CommandType;
import io.github.nathensample.mindrune.ws.model.types.MessageType;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
public class CommandMessage extends AbstractMessage {

    @JsonProperty("commandType")
    private final CommandType commandType;

    @JsonProperty("payload")
    private final Object payload;

    @JsonCreator
    public CommandMessage(@JsonProperty("clientId") final String clientId, @JsonProperty("commandType") final CommandType commandType, @JsonProperty("payload") final Object payload) {
        super(clientId);
        this.commandType = commandType;
        this.payload = payload;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.COMMAND;
    }
}