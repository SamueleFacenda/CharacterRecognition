package pack.characterrecognition.supportClass.saves;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import pack.characterrecognition.supportClass.VectorialMap;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class BufferedSaver {
    private String location;
    private Gson gson;
    private DataClass data;
    public BufferedSaver() throws FileNotFoundException {
        gson = new GsonBuilder().setPrettyPrinting().create();
        location= "saves.json";
        File doc=new File(location);
        InputStreamReader isReader;
        isReader = new InputStreamReader(new FileInputStream(doc), StandardCharsets.UTF_8);
        JsonReader myReader = new JsonReader(isReader);
        data=gson.fromJson(myReader, DataClass.class);
        if(data==null)
            data=new DataClass();
    }
    public void add(char c, VectorialMap added) throws IOException {
        data.get(c).add(added);
    }
    public void writeFileAndClose() throws IOException {
        String stringa=gson.toJson(data);
        FileWriter fw=new FileWriter(location);
        fw.write(stringa);
        fw.close();
        gson=null;
        data=null;
        location=null;
    }
    public CharacterSaves read(char c) {
        try{
            return data.get(c);
        }catch (Exception e){
            return  null;
        }
    }
    public void clear(){
        System.out.println("confermare svuotamento dati? Y/n :");
        Scanner in=new Scanner(System.in);
        if(in.nextLine().equals("Y")){
            data=new DataClass();
            String stringa=gson.toJson(data);
            try {
                FileWriter fw = new FileWriter(location);
                fw.write(stringa);
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
