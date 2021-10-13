package pack.characterrecognition.geometry;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.*;
import java.nio.charset.StandardCharsets;
public class CharacterMapStorage {
    private static final String location="pack/characterrecognition/geometry/saves.txt";
    private static final Gson gson = new Gson();
    public static void write(CharacterSaves in) throws IOException {
        String data=gson.toJson(in);
        File doc=new File(location);
        if(doc.exists()){
            FileWriter writer;
            writer = new FileWriter(doc.getAbsoluteFile(), true);
            BufferedWriter bufferWriter = new BufferedWriter(writer);
            bufferWriter.write(data);
            bufferWriter.close();
        }
    }
    public static CharacterSaves read() throws FileNotFoundException {
        File doc=new File(location);
        if(doc.exists()){
            InputStreamReader isReader;
            isReader = new InputStreamReader(new FileInputStream(doc), StandardCharsets.UTF_8);
            JsonReader myReader = new JsonReader(isReader);
            return gson.fromJson(myReader, CharacterMapStorage.class);
        }else
            return null;
    }
}
