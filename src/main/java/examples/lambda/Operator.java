package examples.lambda;


public interface Operator {

    public static Operator PLUS = (a, b) -> ThirdPartyCalculator.plus(a, b);
    public static Operator MINUS = (a, b) -> ThirdPartyCalculator.minus(a, b);
    public static Operator MULTIPLY = (a, b) -> ThirdPartyCalculator.multiply(a, b);
    public static Operator DIVIDE = (a, b) -> ThirdPartyCalculator.divide(a, b);
    
    int num(int a, int b);
    
    public static class ThirdPartyCalculator {
        private static int plus(int a, int b) {
            return a + b;
        }
        private static int minus(int a, int b) {
            return a - b;
        }
        private static int multiply(int a, int b) {
            return a * b;
        }
        private static int divide(int a, int b) {
            return a / b;
        }
    }
    
}
