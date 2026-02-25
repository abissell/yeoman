package com.abissell.yeoman.functions;

import java.util.function.UnaryOperator;

public final class Identity {
    private Identity() {}

    public static <T> UnaryOperator<T> of() {
        return t -> t;
    }
}
