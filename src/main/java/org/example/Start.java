package org.example;

import org.example.clone.CloneLib;
import org.example.clone.CloneLibInitializer;

import java.util.ArrayList;
import java.util.List;

public class Start {

    public static void main(String[] args) {
        CloneLib cloneLib = CloneLibInitializer.createCloneLib();

        List<String> books = new ArrayList<>(List.of("book 1", "book 2", "book 3"));
        Man man = new Man("just a guy", 39, books);
        Man clone = cloneLib.deepCopy(man);
        System.out.println(clone);
    }

}
