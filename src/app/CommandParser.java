package app;

import java.util.ArrayList;
import java.util.List;


public final class CommandParser {

    private CommandParser() {
    }

    public static Command parse(String line) {
        if (line == null) {
            return null;
        }
        int hash = line.indexOf('#');
        if (hash >= 0) {
            line = line.substring(0, hash);
        }
        line = line.trim().toLowerCase();
        if (line.isEmpty()) {
            return null;
        }
        String[] tokens = line.split("\\s+");
        String name = tokens[0];
        List<String> strs = new ArrayList<>();
        List<Integer> ints = new ArrayList<>();
        for (int i = 1; i < tokens.length; i++) {
            String t = tokens[i];
            if (isInt(t)) {
                ints.add(Integer.parseInt(t));
            } else {
                strs.add(t);
            }
        }
        return new Command(name, strs, ints);
    }

    private static boolean isInt(String s) {
        if (s.isEmpty()) {
            return false;
        }
        int start = s.charAt(0) == '-' ? 1 : 0;
        if (start == s.length()) {
            return false;
        }
        for (int i = start; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
}
