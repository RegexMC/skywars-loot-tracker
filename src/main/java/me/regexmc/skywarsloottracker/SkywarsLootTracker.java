package me.regexmc.skywarsloottracker;

import me.regexmc.skywarsloottracker.commands.SkywarsLootTrackerCommand;
import me.regexmc.skywarsloottracker.handlers.ConfigManager;
import me.regexmc.skywarsloottracker.handlers.DataManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Mod(modid = SkywarsLootTracker.MODID, version = SkywarsLootTracker.VERSION, clientSideOnly = true)
public class SkywarsLootTracker {
    public static final String MODID = "skywarsloottracker";
    public static final String VERSION = "1.0";

    public static Minecraft mc;
    public static ConfigManager configManager;
    public static DataManager dataManager;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configManager = new ConfigManager();
        dataManager = new DataManager();
        mc = Minecraft.getMinecraft();
        ClientCommandHandler.instance.registerCommand(new SkywarsLootTrackerCommand());

        File configDir = new File(event.getModConfigurationDirectory(), "skywarsloottracker");
        configDir.mkdirs();
        configManager.configFile = new File(configDir, "config.json");
        configManager.loadConfig();

        dataManager.dataFile = new File("./skywarsloottracker/stats.json");
        if (!dataManager.dataFile.exists()) {
            try {
                File file = new File("./skywarsloottracker/");
                file.mkdir();
                dataManager.dataFile.createNewFile();

                FileWriter writer = new FileWriter("./skywarsloottracker/stats.json");
                writer.write("{}");
                writer.close();
            } catch (IOException e) {
                System.out.println("Error creating the data file.");
                e.printStackTrace();
                mc.shutdown();
            }
        }
    }
}
