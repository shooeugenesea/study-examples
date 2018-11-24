package examples.router;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MyMessage {

    private String to;
    private String message;

}
