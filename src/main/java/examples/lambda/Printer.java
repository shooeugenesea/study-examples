package examples.lambda;

public interface Printer {

    /* Method reference */
    public static Printer SUFFIXA = APrinter::suffixA;
    public static Printer PREFIXA = APrinter::prefixA;
    
    String print(String...words);
    
    public static class APrinter {
        public static String suffixA(String...words) {
            String s = "";
            for ( String w: words ) {
                s += w + "A";
            }
            return s;
        }
        public static String prefixA(String...words) {
            String s = "";
            for ( String w: words ) {
                s += "A" + w;
            }
            return s;
        }
    }
    
    
    
}
