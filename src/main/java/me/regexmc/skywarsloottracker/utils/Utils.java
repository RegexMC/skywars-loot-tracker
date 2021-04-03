package me.regexmc.skywarsloottracker.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

import java.util.regex.Pattern;

public class Utils {
    private static final Pattern hypixelIP = Pattern.compile("^(.+.)?hypixel.net$", Pattern.CASE_INSENSITIVE);

    public static boolean onHypixel(Minecraft minecraft) {
        ServerData serverData = minecraft.getCurrentServerData();
        if (serverData == null) {
            return false;
        }

        return hypixelIP.matcher(serverData.serverIP).find();
    }
}
