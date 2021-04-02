package me.regexmc.skywarsloottracker;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@Mod(modid = SkywarsLootTracker.MODID, version = SkywarsLootTracker.VERSION, clientSideOnly = true)
public class SkywarsLootTracker {
    public static final String MODID = "skywarsloottracker";
    public static final String VERSION = "1.0";
    public static Minecraft mc;

    private File dataFile;

    public static void writeItem(InsertableItem record) {
        JSONParser jsonParser = new JSONParser();
        JSONObject dataObject = new JSONObject();

        try (FileReader reader = new FileReader("./skywarsloottracker/stats.json")) {
            dataObject = (JSONObject) jsonParser.parse(reader);
            JSONObject itemObject = (JSONObject) dataObject.get(record.getId());

            if (itemObject == null) {
                itemObject = new JSONObject();
                itemObject.put("count", record.getCount());
                itemObject.put("name", record.getName());
                dataObject.put(record.getId(), itemObject);
            } else {
                itemObject.replace("count", String.valueOf(Integer.parseInt((String) itemObject.get("count")) + Integer.parseInt(record.getCount())));
                dataObject.replace(record.getId(), itemObject);
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
            System.out.println("Error reading and parsing data");
        }

        try (FileWriter writer = new FileWriter("./skywarsloottracker/stats.json")) {
            writer.write(dataObject.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error writing data");
        }
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        mc = Minecraft.getMinecraft();

        dataFile = new File("./skywarsloottracker/stats.json");
        if (!dataFile.exists()) {
            try {
                File file = new File("./skywarsloottracker/");
                file.mkdir();
                dataFile.createNewFile();

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
