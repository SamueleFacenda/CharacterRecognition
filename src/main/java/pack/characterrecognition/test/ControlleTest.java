package pack.characterrecognition.test;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pack.characterrecognition.supportClass.*;
import pack.characterrecognition.supportClass.figures.CoorD;

import java.io.IOException;

public class ControlleTest {
    @FXML
    private Label lbl1,lbl2,lbl3,lbl4;
    @FXML
    private Group gruppo;
    public void initialize() throws IOException, InterruptedException {
        Blob b=new Blob();
        b.add(new CoorD(0,0));
        b.add(new CoorD(0,2));
        b.add(new CoorD(2,3));
        b.add(new CoorD(3,5));
        b.add(new CoorD(4,7));
        b.add(new CoorD(4,9));
        b.add(new CoorD(5,5));
        b.add(new CoorD(6,10));
        b.add(new CoorD(7,6));
        b.add(new CoorD(8,4));
        b.add(new CoorD(9,2));
        b.add(new CoorD(10,0));

        VectorialImageGenerator v=new VectorialImageGenerator(b);
        VectorialMap vi=new VectorialMap(v);
        vi.enchant();
        vi.scale(10);
        gruppo.getChildren().add(new ImageView(vi.toImage()));
    }
    public void addImg(Image in){
        //gruppo.getChildren().add(new ImageView(in));
        Blob b=new BlobGenerator(in).getBigger();
        b=Blob.removeMargin(b);
        VectorialImage vi=new VectorialImageGenerator(b);
        System.out.println(vi);
        vi.scale(10);
        try{
            gruppo.getChildren().add(new ImageView(vi.toImage()));
        }catch (IndexOutOfBoundsException e){
            System.out.println("non ci sono punti");
        }
    }
}
