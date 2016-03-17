package examples.lambda;


public interface Operator2 {

    // arguments announcement
    public static Operator2 PLUS = ThirdPartyCalculator::plus;
    public static Operator2 MINUS = ThirdPartyCalculator::minus;
    public static Operator2 MULTIPLY = ThirdPartyCalculator::multiply;
    public static Operator2 DIVIDE = ThirdPartyCalculator::divide;
    
    int num(int a, int b);

    public static class Calculator {
        public static int operateAll(int[] numbers, Operator2 op) {
            int result = 0;
            for ( int num: numbers ) {
                result = op.num(result, num);
            }
            return result;
        }
    }
    
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
