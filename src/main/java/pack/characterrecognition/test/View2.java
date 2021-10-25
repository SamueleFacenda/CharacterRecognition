package pack.characterrecognition.test;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import pack.characterrecognition.supportClass.*;

import java.io.IOException;

public class View2 {
    @FXML
    private Label lbl1,lbl2,lbl3,lbl4;
    @FXML
    private Group gruppo;
    public void initialize() throws IOException {
        Vector v=new Vector(new Coor(0,0),new Coor(100,100));
        VectorialImage vi=new VectorialImage();
        Arch a=new Arch(new Coor(100,200),new Coor(0,0),new Coor(30,120));
        vi.add(a);
        vi.add(v);
        gruppo.getChildren().add(new ImageView(vi.toImage()));
        DataAccess.add('a',new VectorialMap());
    }
}
