package me.regexmc.skywarsloottracker.utils;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemCategory {
    private static final List<String> armor = new ArrayList<String>() {{
        add("item.helmet");
        add("item.chestplate");
        add("item.leggings");
        add("item.boots");
    }};

    private static final List<String> tools = new ArrayList<String>() {{
        add("item.hatchet");
        add("item.shovel");
        add("item.pickaxe");
        add("item.hoe");
    }};

    private static final List<String> blocks = new ArrayList<String>() {{
        add("tile.stone");
        add("tile.wood");
        add("tile.log");
    }};

    public static Category fromItemStack(ItemStack item) {
        String itemName = item.getItem().getUnlocalizedName().toLowerCase();

        if (itemName.startsWith("item.sword")) {
            return Category.SWORD;
        }

        for (String s : armor) {
            if (itemName.startsWith(s)) return Category.ARMOR;
        }

        for (String s : tools) {
            if (itemName.startsWith(s)) return Category.TOOL;
        }

        for (String s : blocks) {
            if (itemName.startsWith(s)) return Category.BLOCK;
        }

        return Category.OTHER;
    }
}
