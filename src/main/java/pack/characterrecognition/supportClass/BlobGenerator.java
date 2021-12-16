package pack.characterrecognition.supportClass;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import pack.characterrecognition.supportClass.figures.CoorD;

import java.util.LinkedList;

/**
 * classe per la generazione di blob di coordinate adiacenti(anch everticalmente) da immagine in bianco e nero
 * @author Samuele Facenda
 */
public class BlobGenerator {
    private LinkedList<Blob> lista;
    private PixelReader pix;
    /**
     * mappa booleana che indica se il pixel è già stato aggiunto a un blob
     */
    private boolean[][] added;
    public BlobGenerator(Image in){
        lista=new LinkedList<>();
        pix=in.getPixelReader();
        added=new boolean[(int) in.getHeight()][(int) in.getWidth()];
    }
    private void generateBlobs(){
        //controllo ogni cella
        for(int row=0;row<added.length;row++){
            for(int col=0;col<added[0].length;col++){
                //se non è stata aggiunta è è nera genero un nuovo blob a partire da quella cella
                if(!added[row][col]&&!pix.getColor(col,row).equals(Color.WHITE)){
                    Blob ad=new Blob();
                    ad.add(new CoorD(col,row));
                    added[row][col]=true;
                    lista.add(ad);
                    addAround(ad,col,row);
                }
            }
        }
    }
    public Blob getBigger(){
        generateBlobs();
        Blob bigger=lista.getFirst();
        for (Blob blo:
             lista) {
            if(blo.size()>bigger.size())
                bigger=blo;
        }
        return bigger;
    }

    /**
     * controlla le celle intorno e se va fatto le aggiunge al blob e controlla quelle intorno a quelle aggiunte,
     * metodo ricorsivo
     * @param in
     * @param x
     * @param y
     */
    private void addAround(Blob in,int x,int y){
        boolean notIsX0=x!=0,notIsY0=y!=0,notIsXEnd=x!=added[0].length-1,notIsYEnd=y!= added.length-1;
        //controllo che se controllo le celle vicine non esca dalle coordinate dell'immagine
        if(notIsX0){
            if(notIsY0)
                checkCell(in, x - 1, y - 1);
            if(notIsYEnd)
                checkCell(in,x-1,y+1);
            checkCell(in,x-1,y);
        }
        if(notIsXEnd){
            if(notIsY0)
                checkCell(in,x+1,y-1);
            if(notIsYEnd)
                checkCell(in,x+1,y+1);
            checkCell(in,x+1,y);
        }
        if(notIsY0)
            checkCell(in,x,y-1);
        if(notIsYEnd)
            checkCell(in,x,y+1);
    }
    private void checkCell(Blob in,int x,int y){
        if(!added[y][x]&&!pix.getColor(x,y).equals(Color.WHITE)){
            in.add(new CoorD(x,y));
            added[y][x]=true;
            addAround(in,x,y);
        }
    }
    @Override
    public String toString(){
        String out="BlobGeneratore:\n";
        for (boolean[] row:
                added) {
            for (boolean cell:
                    row) {
                out+=cell?"@":" ";
            }
            out+="\n";
        }
        return out;
    }

}
