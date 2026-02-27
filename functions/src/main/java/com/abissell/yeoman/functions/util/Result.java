package com.abissell.yeoman.functions.util;

public sealed interface Result<T> {
    record Ok<T>(T value) implements Result<T> {}

    record Err<T>(String errorMsg) implements Result<T> {}
}
