package guava;

import com.google.common.base.MoreObjects;

import java.util.Objects;

public final class NumbersObject {

    public final int a;
    public final int b;
    public final int c;

    public NumbersObject(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("a",a)
                .add("b",b)
                .add("c",c)
                .toString();
    }
}
