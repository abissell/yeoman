package com.abissell.yeoman.tools;

import com.abissell.yeoman.functions.Identity;

public final class Main {
    public static void main(String[] args) {
        var identity = Identity.<String>of();
        System.out.println(identity.apply("yeoman"));
    }
}
