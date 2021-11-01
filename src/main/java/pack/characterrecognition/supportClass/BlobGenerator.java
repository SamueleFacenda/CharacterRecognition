package pack.characterrecognition.supportClass;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

import java.util.LinkedList;

public class BlobGenerator {
    private LinkedList<Blob> lista;
    private PixelReader pix;
    private boolean[][] added;
    public BlobGenerator(Image in){
        lista=new LinkedList<>();
        pix=in.getPixelReader();
        added=new boolean[(int) in.getHeight()][(int) in.getWidth()];
    }
    private void generateBlobs(){
        for(int row=0;row<added.length;row++){
            for(int col=0;col<added[0].length;col++){
                if(!added[row][col]&&!pix.getColor(col,row).equals(Color.WHITE)){
                    Blob ad=new Blob();
                    ad.add(new Coor(col,row));
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
    private void addAround(Blob in,int x,int y){
        boolean notIsX0=x!=0,notIsY0=y!=0,notIsXEnd=x!=added[0].length-1,notIsYEnd=y!= added.length-1;
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
            in.add(new Coor(x,y));
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
