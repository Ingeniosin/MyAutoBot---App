package me.juan.bot.configuration;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import me.juan.bot.models.LocalData;

public class Configuration {

    public static final String BASE_URL = "http://169.254.173.50:7287";


    private static File getLocalDataFile(Context context) {
        File filesDir = context.getFilesDir();
        return new File(filesDir, "data.json");
    }

    public static LocalData getLocalData(Context context){
        try {
            File file = getLocalDataFile(context);
            if(!file.exists())  return null;
            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();
            br.close();
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(everything, LocalData.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void saveLocalData(Context context, LocalData localData){
        try {
            File file = getLocalDataFile(context);
            if(localData == null && file.exists()){
                file.delete();
                return;
            }
            ObjectMapper mapper = new ObjectMapper();
            if(!file.exists())
                file.createNewFile();
            mapper.writeValue(file, localData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
