package com.essensbestellung.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LocationNameExtractor {

    public static List<String> extractUniqueLocationNames(List<String[]> csvRows) {
        Set<String> uniqueNames = new HashSet<>();

        return csvRows.stream()
                .map(row -> row[2].trim())
                .filter(name -> !name.isEmpty())
                .filter(uniqueNames::add)
                .collect(Collectors.toList());
    }
}