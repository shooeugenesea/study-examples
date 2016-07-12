package examples.lambda.blog;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

/**
 * @author <a href="https://github.com/shooeugenesea">isaac</a>
 */
public class FunctionalInterface {

    public static void main(String[] params) {
        List<String> list = new ArrayList<>();
        for ( int i = 0; i < 100; i++ ) {
            list.add(UUID.randomUUID().toString());
        }
        printFiltered(list, (n) -> n.startsWith("a"));
    }

    private static <T> void printFiltered(List<T> list, Predicate<T> filter) {
        for (T t: list) {
            if (filter.test(t)) {
                System.out.println(t);
            }
        }
    }

}
