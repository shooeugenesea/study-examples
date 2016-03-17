package examples.lambda;


public interface Operator3 {

    MyInteger num(MyInteger a, MyInteger b);

    public static class Calculator {
        
        // caller specify Operator3 implementation directly
        public static MyInteger all(MyInteger[] nums, Operator3 op) {
            MyInteger result = null;
            for ( MyInteger num: nums ) {
                if (result == null) {
                    result = num;
                    continue;
                }
                result = op.num(result, num);
            }
            return result == null ? new MyInteger(0) : result;
        }
    }
    
    public static class MyInteger {
        private final int val;
        public MyInteger(int val) {
            this.val = val;
        }
        public MyInteger plus(MyInteger num) {
            return new MyInteger(val + num.val);
        }
        public MyInteger minus(MyInteger num) {
            return new MyInteger(val - num.val);
        }
        public MyInteger multiply(MyInteger num) {
            return new MyInteger(val * num.val);
        }
        public MyInteger divide(MyInteger num) {
            return new MyInteger(val / num.val);
        }
        public int intValue() {
            return val;
        }
    }

    
}
