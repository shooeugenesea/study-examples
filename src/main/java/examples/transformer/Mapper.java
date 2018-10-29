package examples.transformer;

import org.springframework.integration.annotation.Transformer;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Mapper {

    @Transformer
    public TextMessage map(Map<String, String> message) {
        System.out.println("transform from " + message + " to TextMessage");
        return new TextMessage(message.get("text"));
    }

    @Transformer
    public TextMessage map(String message) {
        System.out.println("transform from string to TextMessage");
        return new TextMessage(message);
    }

}
