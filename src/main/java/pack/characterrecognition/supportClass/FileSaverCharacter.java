package pack.characterrecognition.supportClass;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.*;
import java.nio.charset.StandardCharsets;
public class FileSaverCharacter {
    private static final String location= "pack/characterrecognition/supportClass/saves.txt";
    private static final Gson gson = new Gson();
    public static void write(DataClass in) throws IOException {
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
    public static DataClass read() throws FileNotFoundException {
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
