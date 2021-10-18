package pack.characterrecognition.primaryGUI;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import pack.characterrecognition.CharApp;
import pack.characterrecognition.CharacterRecognizor;

import java.io.IOException;

public class ChoosePage {
    @FXML
    Label lbl;
    @FXML
    Button btn1,btn2;
    private CharacterRecognizor cr;
    public void passa(ActionEvent actionEvent) {
        btn1.setText("esci");
        btn1.setOnAction(e->{
            Platform.exit();
            System.exit(0);
        });
        btn2.setText("inserisci nuovo valore");
        btn2.setOnAction(e->{
            Stage stage= (Stage) lbl.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(CharApp.class.getResource("view1.fxml"));
            try {
                Scene scene = new Scene(fxmlLoader.load(), 300, 400);
                stage.setTitle("Character Recognition");
                scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
                stage.setScene(scene);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
    public void setImg(WritableImage in){
        cr=new CharacterRecognizor(in);
        lbl.setText(cr.recognise()?"carattere: "+cr.getChar():"carattere sconosciuto");
    }

    public void inserisci(ActionEvent actionEvent) {
        GridPane grid= (GridPane) btn1.getParent();
        TextField in=new TextField("lettera");
        Button btn=new Button("invia");
        Label llb=new Label("inserisci la lettera che hai disegnato");
        grid.getChildren().clear();
        GridPane.setHalignment(btn, HPos.CENTER);
        GridPane.setHalignment(llb, HPos.CENTER);
        GridPane.setHalignment(in, HPos.CENTER);
        btn.setOnAction(e->{
            String input=in.getText().toUpperCase();
            if(input.length()!=1||((int)input.charAt(0)>64&&(int)input.charAt(0)<91)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("errore");
                alert.setHeaderText("input non valido");
                alert.setContentText("inserire solo una lettera");
                alert.showAndWait();
            }else{
                try {
                    cr.set(input.charAt(0));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        grid.add(llb,0,0);
        grid.add(in,1,0);
        grid.add(btn,2,0);
    }
}















