package examples.lambda;

public interface DrawCommand {

    public static DrawCommand LINE = () -> "draw line";
    public static DrawCommand CIRCLE = () -> "draw circle";
    
    String draw();
    
    public static class DrawPanel {
        public String draw(DrawCommand...commands) {
            String result = "";
            for (DrawCommand c: commands) {
                result += c.draw();
            }
            return result;
        }
    }
    
}
