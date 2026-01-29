package io.github.nathensample.mindrune.ws.model.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.nathensample.mindrune.ws.model.messages.in.IdentifyMessage;
import io.github.nathensample.mindrune.ws.model.messages.out.CommandMessage;
import io.github.nathensample.mindrune.ws.model.types.MessageType;
import lombok.Getter;

import java.util.UUID;


@Getter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "messageType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CommandMessage.class, name = "COMMAND"),
        @JsonSubTypes.Type(value = IdentifyMessage.class, name = "IDENTIFY"),

})
public abstract class AbstractMessage {
    @JsonProperty("clientId")
    private final String clientId;
    private final String messageId = UUID.randomUUID().toString();

    @JsonCreator
    protected AbstractMessage(final String clientId) {
        this.clientId = clientId;
    }

    public abstract MessageType getMessageType();

    public final String getMessageId() {
        return messageId;
    }

}
