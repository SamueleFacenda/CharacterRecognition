package pack.characterrecognition;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class Controller {
    @FXML
    private Canvas canva;
    private GraphicsContext disegno;
    private ColorPicker cpLine = new ColorPicker(Color.BLACK);
    @FXML
    protected void onButtonClick() {

    }
    public void initialize(){
        disegno= canva.getGraphicsContext2D();
        canva.setOnMousePressed(e->{
            disegno.setStroke(cpLine.getValue());
            disegno.beginPath();
            disegno.lineTo(e.getX(), e.getY());
        });
        canva.setOnMouseDragged(e->{
            disegno.lineTo(e.getX(), e.getY());
            disegno.stroke();
        });
    }
}