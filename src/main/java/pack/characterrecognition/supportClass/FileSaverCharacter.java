package pack.characterrecognition.supportClass;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;

public class FileSaverCharacter {
    private static final String location= "saves.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static void write(DataClass in) throws IOException {
        String data=gson.toJson(in);
        FileWriter fw=new FileWriter(location);
        fw.write(data);
        fw.close();
    }
    public static DataClass read() throws IOException {
        File doc=new File(location);
        if(doc.exists()){
            InputStreamReader isReader;
            isReader = new InputStreamReader(new FileInputStream(doc), StandardCharsets.UTF_8);
            JsonReader myReader = new JsonReader(isReader);
            return gson.fromJson(myReader, DataClass.class);
        }else
            return null;
    }
}
