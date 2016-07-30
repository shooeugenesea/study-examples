package examples.lambda;

/**
 * @author <a href="https://github.com/shooeugenesea">isaac</a>
 */
public class Iteration {

    public String toString(Iterable<String> iterable, Character delimiter) {
        StringBuilder result = new StringBuilder();
        for ( String s: iterable ) {
            result.append(s).append(delimiter);
        }
        result.deleteCharAt(result.lastIndexOf(delimiter.toString()));
        return result.toString();
    }

    public String forEachToString(Iterable<String> iterable, Character delimiter) {
        StringBuilder s = new StringBuilder();
        iterable.forEach((x) -> s.append(x).append(delimiter));
        s.deleteCharAt(s.lastIndexOf(delimiter.toString()));
        return s.toString();
    }

}
