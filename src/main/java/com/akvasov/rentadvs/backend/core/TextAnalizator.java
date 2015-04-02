package com.akvasov.rentadvs.backend.core;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by akvasov on 07.08.14.
 */
@Component
@Scope(value = "singleton")
public class TextAnalizator {

    private TextAnalizator() {

    }

    public Boolean isValid(String text){
        String t = text.toUpperCase();
        return t.contains("СДАМ") || t.contains("USER");
    }
}
