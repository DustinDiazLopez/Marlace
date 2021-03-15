package com.example.marlace;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Object> objects = new ArrayList<>();
        objects.add(2);
        objects.add(2f);
        objects.add(2d);
        objects.add("Hey");

        for (Object o : objects) {
            System.err.println(o instanceof String);
        }
    }
}
