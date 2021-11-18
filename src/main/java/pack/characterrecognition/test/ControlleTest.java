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
    }
    public void addImg(Image in){
        //gruppo.getChildren().add(new ImageView(in));
        Blob b=new BlobGenerator(in).getBigger();
        b=Blob.removeMargin(b);
        VectorialImage vi=new VectorialImageGenerator(b);
        System.out.println(vi);
        vi.scale(10);
        gruppo.getChildren().add(new ImageView(vi.toImage()));
    }
}
