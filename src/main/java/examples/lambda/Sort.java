package examples.lambda;

import java.util.Arrays;
import java.util.Comparator;

public class Sort {

    public static String[] jdk7Sort(String...names) {
        Arrays.sort(names, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        });
        return names;
    }
    
    public static String[] lambdaSort1(String...names) {
        Comparator<String> c = (String o1, String o2) -> o1.length() - o2.length();
        Arrays.sort(names, c);
        return names;
    }
    
    public static String[] lambdaSort2(String...names) {
        Comparator<String> c = (o1,o2) -> o1.length() - o2.length();
        Arrays.sort(names, c);
        return names;
    }
    
}
