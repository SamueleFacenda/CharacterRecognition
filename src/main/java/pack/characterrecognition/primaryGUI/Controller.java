package pack.characterrecognition.primaryGUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import pack.characterrecognition.CharApp;
import pack.characterrecognition.test.ControlleTest;

import java.io.IOException;

public class Controller {
    @FXML
    private Canvas canva;
    private GraphicsContext disegno;
    @FXML
    protected void invio() throws IOException {
        WritableImage writableImage = new WritableImage((int)canva.getWidth(),(int) canva.getHeight());
        canva.snapshot(null, writableImage);
        Stage ps= (Stage) canva.getScene().getWindow();
        nuova();
        FXMLLoader fxmlLoader = new FXMLLoader(CharApp.class.getResource("choosePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        ((ChoosePage)(fxmlLoader.getController())).setImg(writableImage);
        ps.setTitle("Character Recognition");
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        ps.setScene(scene);
    }
    @FXML
    protected void clear(){
        disegno.clearRect(0, 0, canva.getWidth(), canva.getHeight());
    }
    public void initialize(){
        disegno= canva.getGraphicsContext2D();
        disegno.setStroke(Color.BLACK);
        canva.setOnMousePressed(e->{
            disegno.beginPath();
            disegno.lineTo(e.getX(), e.getY());
        });
        canva.setOnMouseDragged(e->{
            disegno.lineTo(e.getX(), e.getY());
            disegno.stroke();
        });
    }
    private void nuova() throws IOException {
        WritableImage writableImage = new WritableImage((int)canva.getWidth(),(int) canva.getHeight());
        canva.snapshot(null, writableImage);
        FXMLLoader fxmlLoader = new FXMLLoader(CharApp.class.getResource("view2.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 400);
        Stage ps= new Stage();
        ((ControlleTest)fxmlLoader.getController()).addImg(writableImage);
        ps.setTitle("Character Recognition");
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        ps.setScene(scene);
        ps.show();
    }
}