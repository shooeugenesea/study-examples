package examples.router;

import org.springframework.integration.annotation.Router;
import org.springframework.stereotype.Component;

@Component
public class MessageRouter {

    @Router
    public String route(MyMessage msg) {
        System.out.println("msg.to:" + msg.getTo());
        return msg.getTo();
    }


}
