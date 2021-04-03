package me.regexmc.skywarsloottracker.utils;

public class InsertableItem {
    private final String id;
    private final String count;
    private final String name;
    private final Category category;

    public InsertableItem(String id, String count, String name, Category category) {
        this.id = id;
        this.count = count;
        this.name = name;
        this.category = category;
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

    public Category getCategory() {
        return category;
    }
}
