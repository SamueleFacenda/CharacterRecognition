package pack.characterrecognition.test;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pack.characterrecognition.supportClass.*;

import java.io.IOException;

public class ControlleTest {
    @FXML
    private Label lbl1,lbl2,lbl3,lbl4;
    @FXML
    private Group gruppo;
    public void initialize() throws IOException, InterruptedException {
        VectorialImage vi=new VectorialImage();
        vi.add(new Segment(10,20,130,150));
        vi.add(new Segment(135,150,300,300));
        vi.add(new Segment(140,148,300,20));
        VectorialMap vm=new VectorialMap(vi);
        vm.enchant();
        lbl1.setText(vm.toString());
        gruppo.getChildren().add(new ImageView(vm.toImage()));
        ImageView adding=new ImageView(vi.toImage());
        gruppo.getChildren().add(adding);
        adding.setY(adding.getY()+300);
        vm.scale(1.25);
        ImageView adding2=new ImageView(vm.toImage());
        gruppo.getChildren().add(adding2);
        adding2.setX(adding.getX()+300);
        lbl2.setText(vm.toString());
    }
    public void addImg(Image in){
        gruppo.getChildren().add(new ImageView(in));
    }
}
