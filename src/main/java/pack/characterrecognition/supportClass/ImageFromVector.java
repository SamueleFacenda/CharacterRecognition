package pack.characterrecognition.supportClass;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ImageFromVector extends WritableImage {
    public ImageFromVector(int i, int i1) {
        super(i, i1);
    }
    public void write(Arch in){
        PixelWriter pi=this.getPixelWriter();
        for(Coor c:in.getCoors())
            pi.setArgb((int)c.x,(int)(getHeight()-c.y-1),0);
    }
    public void write(Vector in){
        PixelWriter pi=this.getPixelWriter();
        for(Coor c:in.getCoors())
            pi.setColor((int)c.x,(int)(getHeight()-c.y-1), Color.BLACK);
    }
}
