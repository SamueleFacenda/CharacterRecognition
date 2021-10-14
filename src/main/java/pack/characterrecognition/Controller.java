package pack.characterrecognition;

import javafx.fxml.FXML;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import pack.characterrecognition.supportClass.Vector;

public class Controller {
    @FXML
    private Canvas canva;
    private GraphicsContext disegno;
    private ColorPicker cpLine = new ColorPicker(Color.BLACK);
    @FXML
    protected void invio() {
        WritableImage writableImage = new WritableImage((int)canva.getWidth(),(int) canva.getHeight());
        canva.snapshot(null, writableImage);
        CharacterRecognizor cr=new CharacterRecognizor(writableImage);
        cr.recognise();
        disegno.clearRect(0, 0, canva.getWidth(), canva.getHeight());
        disegno.setTextAlign(TextAlignment.CENTER);
        disegno.setTextBaseline(VPos.CENTER);
        Vector v=new Vector(0,0,2,2);
        disegno.fillText(
                "Carattere: "+cr.getChar(),
                Math.round(canva.getWidth() /2 ),
                Math.round(canva.getHeight() /2)
        );
    }
    @FXML
    protected void clear(){
        disegno.clearRect(0, 0, canva.getWidth(), canva.getHeight());
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