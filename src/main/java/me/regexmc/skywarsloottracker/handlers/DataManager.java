package me.regexmc.skywarsloottracker.handlers;

import me.regexmc.skywarsloottracker.utils.InsertableItem;
import net.minecraft.util.EnumChatFormatting;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DataManager {
    public File dataFile;

    public void writeItem(InsertableItem record) {
        JSONParser jsonParser = new JSONParser();
        JSONObject dataObject = new JSONObject();

        try (FileReader reader = new FileReader("./skywarsloottracker/stats.json")) {
            dataObject = (JSONObject) jsonParser.parse(reader);
            JSONObject itemObject = (JSONObject) dataObject.get(record.getId());

            if (itemObject == null) {
                itemObject = new JSONObject();
                itemObject.put("count", record.getCount());
                itemObject.put("name", record.getName());
                itemObject.put("category", record.getCategory().toString());
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

    public String export() {
        try (FileReader reader = new FileReader("./skywarsloottracker/stats.json")) {
            File outputFile = new File("./skywarsloottracker/export_" + System.currentTimeMillis() + ".csv");
            outputFile.createNewFile();

            JSONObject data = (JSONObject) new JSONParser().parse(reader);

            FileWriter writer = new FileWriter(outputFile);
            writer.write("Id,Count,Name,Category\n");
            for (Object key : data.keySet()) {
                JSONObject itemData = (JSONObject) data.get(key);
                final String Id = (String) key;
                final String Count = (String) itemData.get("count");
                final String Name = (String) itemData.get("name");
                final String Category = (String) itemData.get("category");
                writer.write(Id + "," + Count + "," + Name + "," + Category + "\n");
            }

            writer.close();

            return EnumChatFormatting.AQUA + "Exported to " + EnumChatFormatting.GOLD + outputFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading stats file");
            return "Something went wrong!";
        } catch (ParseException e) {
            e.printStackTrace();
            return "Something went wrong!";
        }
    }
}
