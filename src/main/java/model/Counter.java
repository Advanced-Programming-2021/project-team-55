package model;

import java.util.ArrayList;

public class Counter {
    private int count;
    private static ArrayList<Counter> allCounters;

    static {
        allCounters = new ArrayList<>();
    }

    public Counter() {
        allCounters.add(this);
    }

    public int getCount() {
        return count;
    }

    public void increaseCount() {//is correct?
        this.count++;
    }

    public static ArrayList<Counter> getAllCounters() {
        return allCounters;
    }
}