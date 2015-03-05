package com.akvasov.rentadvs.backend.core;

/**
 * Created by akvasov on 07.08.14.
 */
public class TextAnalizator {

    private static TextAnalizator ourInstance = new TextAnalizator();

    public static TextAnalizator getInstance() {
        return ourInstance;
    }

    private TextAnalizator() {

    }

    public Boolean isValid(String text){
        String t = text.toUpperCase();
        return t.contains("СДАМ") || t.contains("USER");
    }
}
