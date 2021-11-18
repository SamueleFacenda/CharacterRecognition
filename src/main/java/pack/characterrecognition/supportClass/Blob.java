package pack.characterrecognition.supportClass;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

import java.util.LinkedList;

/**
 * array di coordinate con metodi di utilità, autoesplicativi
 * @author Samuele Facenda
 */
public class Blob {
    protected LinkedList<Coor> lista;
    public Blob(){
        lista=new LinkedList<>();
    }
    public void add(Coor in){
        lista.add(in.getCopy());
    }
    public int size(){
        return lista.size();
    }
    public boolean contains(Coor in){
        return lista.contains(in);
    }
    public int getUpper(){
        int max=0;
        for(Coor c:lista)
            if(c.y>max) max=DoubleUtils.toInt(Math.ceil(c.y));
         return max;
    }
    public int getDowner(){
        int min=DoubleUtils.toInt(lista.get(0).y);
        for(Coor c:lista)
            if(c.y<min) min=DoubleUtils.toInt(c.y);
        return min;
    }
    public int getRigther(){
        int max=0;
        for(Coor c:lista)
            if(c.x>max) max=DoubleUtils.toInt(Math.ceil(c.x));
        return max;
    }
    public int getLefter(){
        int min=DoubleUtils.toInt(lista.get(0).x);
        for(Coor c:lista)
            if(c.x<min) min=DoubleUtils.toInt(c.x);
        return min;
    }
    public int getHeight(){
        return getUpper()-getDowner();
    }
    public int getWidth(){
        return getRigther()-getLefter();
    }
    public boolean containsSimilar(Coor in){
        for(Coor c:lista){
            if((int)in.x==(int)Math.round(c.x)&&(int)in.y==(int)Math.round(c.y))
                return true;
        }
        return false;
    }
    public static Blob merge(Blob uno,Blob due){
        Blob out=new Blob();
        out.lista.addAll(uno.lista);
        out.lista.addAll(due.lista);
        return out;
    }

    /**
     * sposta tutte le coordinate in modo che ogni asse cartesiano venga toccato da almeno un punto
     * @param in
     * @return
     */
    public static Blob removeMargin(Blob in){
        in=in.getCopy();
        int minX= in.getLefter(),minY= in.getDowner();
        for (Coor current:
             in.lista) {
            current.x-=minX;
            current.y-=minY;
        }
        return in;
    }
    public Blob getCopy(){
        Blob out=new Blob();
        for (Coor current:
                lista) out.add(current);
        return out;
    }
    @Override
    public String toString(){
        int left=getLefter(),down=getDowner();
        boolean[][] grid=new boolean[getUpper()-down+1][getRigther()-left+1];
        for (Coor c:
             lista) {
            grid[DoubleUtils.toInt(c.y-down)][DoubleUtils.toInt(c.x-left)]=true;
        }
        String out="Blob:\n";
        for (boolean[] row:
             grid) {
            for (boolean cell:
                 row) {
                out+=cell?"@":" ";
            }
            out+="\n";
        }
        return out;
    }
    public boolean[][] toBooleanGrid(){
        boolean[][] out=new boolean[getUpper()+1][getRigther()+1];
        for (Coor c:
             lista)
            out[DoubleUtils.toInt(Math.round(c.y))][DoubleUtils.toInt(Math.round(c.x))]=true;
        return out;
    }
}
