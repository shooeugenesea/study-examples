package examples.transformer;

public class TextMessageEmptyConstructor {

    private String text;

    public TextMessageEmptyConstructor() {}
    public TextMessageEmptyConstructor(String text) {
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "=>" + text;
    }
}
