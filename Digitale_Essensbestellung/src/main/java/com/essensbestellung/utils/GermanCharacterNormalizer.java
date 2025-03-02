package com.essensbestellung.utils;

import java.util.Map;
import java.util.HashMap;

public class GermanCharacterNormalizer {

    
    public static String normalize(String input) {
        if (input == null) {
            return null;
        }


        Map<String, String> replacement = new HashMap<>();
        replacement.put("ä", "ae");
        replacement.put("ö", "oe");
        replacement.put("ü", "ue");
        replacement.put("ß", "ss");
        replacement.put("Ä", "Ae");
        replacement.put("Ö", "Oe");
        replacement.put("Ü", "Ue");


        for (Map.Entry<String, String> entry : replacement.entrySet()) {
            input = input.replace(entry.getKey(), entry.getValue());
        }

        return input;
    }
}
