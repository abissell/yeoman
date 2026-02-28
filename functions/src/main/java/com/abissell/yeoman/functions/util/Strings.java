package com.abissell.yeoman.functions.util;

import org.jspecify.annotations.Nullable;

public enum Strings {
    ; // Enum singleton

    public static String requireNonNullOrEmpty(@Nullable String str) {
        if (str == null) {
            return "";
        }

        return str;
    }
}
