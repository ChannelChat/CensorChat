package feildmaster.module.censorchat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Filter {
    private static final Pattern letters = Pattern.compile("[A-Za-z]"); // This part was an idea from NodinChan
    private Pattern pattern;

    public Filter(String regex) {
        pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
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
