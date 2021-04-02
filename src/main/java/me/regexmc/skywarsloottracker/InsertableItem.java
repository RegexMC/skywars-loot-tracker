package me.regexmc.skywarsloottracker;

public class InsertableItem {
    private final String id;
    private final String count;
    private final String name;

    public InsertableItem(String id, String count, String name) {
        this.id = id;
        this.count = count;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getCount() {
        return count;
    }

    public String getName() {
        return name;
    }
}
