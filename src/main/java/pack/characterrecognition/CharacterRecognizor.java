package pack.characterrecognition;

import javafx.scene.image.WritableImage;
import pack.characterrecognition.supportClass.CartesianGridBoolean;
import pack.characterrecognition.supportClass.DataAccess;
import pack.characterrecognition.supportClass.VectorialMap;

import java.io.IOException;

public class CharacterRecognizor {
    private WritableImage img;
    private VectorialMap map;
    private char carattere;
    public CharacterRecognizor(WritableImage img){
        this.img=img;
    }
    public char getChar(){
        return carattere;
    }
    public boolean recognise(){
        carattere='H';
        return false;
    }
    public void set(char c) throws IOException {
        DataAccess.add(c,map);
    }
}
