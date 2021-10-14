package pack.characterrecognition.supportClass;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class ImageFromVector extends WritableImage {
    public ImageFromVector(int i, int i1) {
        super(i, i1);
    }
    public void write(Arch in){
        PixelWriter pi=this.getPixelWriter();
        for(Coor c:in.getCoors())
            pi.setArgb((int)c.x,(int)c.y,0);
    }
    public void write(Vector in){
        PixelWriter pi=this.getPixelWriter();
        for(Coor c:in.getCoors())
            pi.setArgb((int)c.x,(int)c.y,0);
    }
}
