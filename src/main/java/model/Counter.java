package model;

import java.util.ArrayList;

public class Counter {
    private static final ArrayList<Counter> allCounters;

    static {
        allCounters = new ArrayList<>();
    }

    private int count;

    public Counter() {
        allCounters.add(this);
    }

    public static ArrayList<Counter> getAllCounters() {
        return allCounters;
    }

    public int getCount() {
        return count;
    }

    public void increaseCount() {//is correct?
        this.count++;
    }
}