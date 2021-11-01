package pack.characterrecognition.supportClass;

import javafx.scene.image.WritableImage;
import pack.characterrecognition.supportClass.DataAccess;
import pack.characterrecognition.supportClass.VectorialMap;

import java.io.IOException;

public class CharacterRecognizor {
    private Blob img;
    private VectorialMap map;
    private char carattere;
    public CharacterRecognizor(WritableImage img){
        this.img=new BlobGenerator(img).getBigger();
    }
    public char getChar(){
        return carattere;
    }
    public boolean recognise(){
        System.out.println(img);
        carattere='H';
        return false;
    }
    public void set(char c) throws IOException {
        DataAccess.add(c,map);
    }
}
