package pack.characterrecognition.test;

import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pack.characterrecognition.supportClass.*;

import java.io.IOException;

public class View2 {
    @FXML
    private Label lbl1,lbl2,lbl3,lbl4;
    @FXML
    private Group gruppo;
    public void initialize() throws IOException, InterruptedException {
        Vector v=new Vector(new Coor(0,0),new Coor(100,100)),v1=new Vector(10,10,10,100),v2=new Vector(10,110,10,200);
        VectorialImage vi=new VectorialImage();
        Arch a=new Arch(new Coor(100,200),new Coor(0,0),new Coor(30,120));
        vi.add(a);
        vi.add(v);
        vi.add(v1);
        vi.add(v2);
        DataAccess.add('b',new VectorialMap(vi));
        FileSaverCharacter.read();
        VectorialMap vm=new VectorialMap(vi);
        vm.enchant();
        lbl1.setText(vm.toString());
        gruppo.getChildren().add(new ImageView(vi.toImage()));
    }
    public void addImg(Image in){
        gruppo.getChildren().add(new ImageView(in));
    }
}
