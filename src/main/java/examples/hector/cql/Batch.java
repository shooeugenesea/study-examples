package examples.hector.cql;

import java.util.ArrayList;
import java.util.List;

public class Batch implements CqlCommand {

    private final List<DmlCommand> commands = new ArrayList<>();
    private Long timestamp = null;
    
    public Batch command(DmlCommand command) {
        this.commands.add(command);
        return this;
    }
    
    public Batch timestamp(Long timestamp) {
        this.timestamp = timestamp;
        return this;
    }
    
    @Override
    public String toCQL() {
        StringBuilder cql = new StringBuilder("BEGIN BATCH ");
        if ( timestamp != null ) {
            cql.append("USING TIMESTAMP " + timestamp + " ");
        }
        for ( DmlCommand command: commands ) {
            cql.append(command.toCQL());
        }
        cql.append(" APPLY BATCH;");
        return cql.toString();
    }
    
}
