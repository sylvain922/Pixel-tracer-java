package app;

import java.util.List;


public class Command {

    private final String name;
    private final List<String> strs;
    private final List<Integer> ints;

    public Command(String name, List<String> strs, List<Integer> ints) {
        this.name = name;
        this.strs = strs;
        this.ints = ints;
    }

    public String getName() {
        return name;
    }

    public List<String> getStrs() {
        return strs;
    }

    public List<Integer> getInts() {
        return ints;
    }

    public boolean matches(int strCount, int intCount) {
        return strs.size() == strCount && ints.size() == intCount;
    }

    public String toString() {
        return "Command[name=" + name + ", strs=" + strs + ", ints=" + ints + "]";
    }
}
