package examples.lambda;


public interface Operator1 {

    // arguments announcement
    public static Operator1 PLUS = (a, b) -> ThirdPartyCalculator.plus(a, b);
    public static Operator1 MINUS = (a, b) -> ThirdPartyCalculator.minus(a, b);
    public static Operator1 MULTIPLY = (a, b) -> ThirdPartyCalculator.multiply(a, b);
    public static Operator1 DIVIDE = (a, b) -> ThirdPartyCalculator.divide(a, b);
    
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
