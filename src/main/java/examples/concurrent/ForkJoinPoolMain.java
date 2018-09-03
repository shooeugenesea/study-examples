package examples.concurrent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;

public class ForkJoinPoolMain {

    private static AtomicInteger actionCounter = new AtomicInteger();
    public static void main(String[] params) throws ExecutionException, InterruptedException {
        ForkJoinPool pool = new ForkJoinPool();
        System.out.println("main:" + pool.submit(new MyAction(new int[]{1,2,3,4,5,6,7,8,9,10,11})).get());
    }

    private static class MyAction extends RecursiveTask<Integer> {
        // Extends RecursiveAction if no return value required

        private int count;
        private int[] numbers;

        public MyAction(int[] numbers) {
            this.count = actionCounter.incrementAndGet();
            this.numbers = numbers;
        }

        @Override
        protected Integer compute() {
            if (numbers.length > 2) {
                System.out.println(count + " split " + Arrays.toString(numbers) + " to 2 arrays");
                List<MyAction> actions = new ArrayList<>();
                actions.add(new MyAction(Arrays.copyOfRange(numbers, 0, numbers.length / 2)));
                actions.add(new MyAction(Arrays.copyOfRange(numbers, numbers.length / 2, numbers.length)));
                actions.forEach(MyAction::fork);
                return actions.stream().mapToInt(MyAction::join).sum();
            } else if (numbers.length == 2) {
                System.out.println(count + " add " + numbers[0] + " and " + numbers[1]);
                return numbers[0] + numbers[1];
            }
            System.out.println(count + " just return " + numbers[0]);
            return numbers[0];
        }
    }

}
