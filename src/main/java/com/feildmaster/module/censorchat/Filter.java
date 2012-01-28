package com.feildmaster.module.censorchat;

import java.util.regex.*;

public class Filter {
    private static final Pattern letters = Pattern.compile("[A-Za-z]"); // This part was an idea from NodinChan
    private Pattern pattern;
    private String match;

    public Filter(String regex) {
        pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    }

    public Filter(String regex, String replace) {
        pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        match = replace;
    }

    public String replaceAll(String message) {
        StringBuilder filtered = new StringBuilder(message);
        Matcher m = pattern.matcher(message);

        while(m.find()) {
            filtered.replace(m.start(), m.end(), letters.matcher(m.group()).replaceAll("*"));
        }

        return filtered.toString();
    }
}
