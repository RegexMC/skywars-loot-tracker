package me.regexmc.skywarsloottracker.handlers;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ConfigManager {
    public boolean enabled = true;
    public File configFile = null;

    public void saveConfig() {
        if (configFile != null) {
            try {
                JSONObject object = new JSONObject();
                object.put("enabled", enabled);
                configFile.createNewFile();

                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(configFile), StandardCharsets.UTF_8))) {
                    writer.write(object.toJSONString());
                }
            } catch (Exception e) {
                System.err.println("Failed to write config!");
                e.printStackTrace();
            }
        }

    }

    public void loadConfig() {
        if (configFile != null) {
            try {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(configFile), StandardCharsets.UTF_8))) {
                    JSONObject json = (JSONObject) new JSONParser().parse(reader);
                    enabled = (boolean) json.get("enabled");
                }
            } catch (Exception e) {
                System.err.println("Failed to load config!");
                e.printStackTrace();
            }
        }
    }
}
