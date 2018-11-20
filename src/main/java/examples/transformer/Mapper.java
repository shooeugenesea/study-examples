package examples.transformer;

import org.apache.camel.Header;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.handler.annotation.Headers;
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

    @Transformer
    public TextMessage mapMyKey(@Header("mykey") String myval, @Headers Map<String,String> headerMap, String message) {
        System.out.println("headerMap:" + headerMap);
        return new TextMessage(myval);
    }

}
