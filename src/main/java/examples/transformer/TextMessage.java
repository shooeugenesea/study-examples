package examples.transformer;

import java.io.Serializable;
import java.util.UUID;

public class TextMessage implements Serializable {

    private final String text;
    private final transient String localId;

    public TextMessage(String text) {
        this(text, UUID.randomUUID().toString());
    }

    public TextMessage(String text, String localId) {
        this.text = text;
        this.localId = localId;
    }

    @Override
    public String toString() {
        return localId + ":" + text;
    }
}
