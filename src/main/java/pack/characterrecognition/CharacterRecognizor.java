package pack.characterrecognition;

import javafx.scene.image.WritableImage;

public class CharacterRecognizor {
    private WritableImage img;
    private boolean[][] grid;
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
}
