package examples.filter;

import examples.MyMessage;
import org.springframework.integration.core.MessageSelector;
import org.springframework.messaging.Message;

public class MyMessageFilter implements MessageSelector {

    @Override
    public boolean accept(Message<?> message) {
        return MyMessage.class.isInstance(message.getPayload());
    }

}
