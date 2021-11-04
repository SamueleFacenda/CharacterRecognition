package pack.characterrecognition.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import pack.characterrecognition.CharApp;

import java.io.IOException;

public class MainTest extends Application {
    @Override
    public void start(Stage ps) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CharApp.class.getResource("view2.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 700);
        ps.setTitle("Character Recognition");
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        ps.setScene(scene);
        ps.show();
    }
    public static void main(String[] args){
        launch();
    }
}
