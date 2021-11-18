package pack.characterrecognition.supportClass;

import javafx.scene.image.WritableImage;
import pack.characterrecognition.supportClass.VectorialMap;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CharacterRecognizor {
    private VectorialMap map;
    private char carattere;
    private BufferedSaver bs;
    public CharacterRecognizor(WritableImage img) throws FileNotFoundException {
        map=new VectorialMap(new VectorialImageGenerator(Blob.removeMargin(new BlobGenerator(img).getBigger())));
        //map.scalePerHeight(100);
        bs=new BufferedSaver();
    }
    public char getChar(){
        return carattere;
    }
    public boolean recognise(){
        double max=0,val;
        char charMax=0;
        for (int i = 0; i < 255; i++) {
            val=bs.read((char)i).getSimilarity(map);
            if(val>max){
                max=val;
                charMax=(char) i;
            }
        }
        if(Double.compare(max,0.0)==0)
            return false;
        else{
            carattere=charMax;
            return true;
        }
    }
    public void set(char c) throws IOException {
        if(c!=(char) 0){
            bs.add(c,map);
            bs.writeFileAndClose();
        }
    }
}
