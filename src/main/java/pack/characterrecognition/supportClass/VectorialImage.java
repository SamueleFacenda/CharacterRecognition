package pack.characterrecognition.supportClass;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.Objects;

/**
 * classe immagine vettoriale, è formata da segmenti e archi, ha metodi di utilità come quello
 * che la converte in immagine(javafx), utlile per la calsse figlio veectorialmap
 */
public class VectorialImage {
    protected LinkedList<Segment> segmentList;


    protected LinkedList<Arch> archList;
    public VectorialImage(){
        segmentList =new LinkedList<>();
        archList=new LinkedList<>();
    }
    public void add(Arch in){
        archList.add(in);
    }
    public void add(Segment in){
        segmentList.add(in);
    }
    public Arch getArch(int index){
        return index<archList.size()?archList.get(index):null;
    }
    public Segment getVector(int index){
        return index< segmentList.size()? segmentList.get(index):null;
    }

    /**
     * ritorn un'immagine javafx che rappresenta questa immagine vettoriale, scrive un pixel nero
     * nelle coordinate dei segemnti e archi, con i metodi getCoors(), x e y.
     * @return
     */
    public Image toImage(){
        double maxY,maxX,minY,minX;
        if(segmentList.size()>=1){
            maxY= segmentList.get(0).getYE();
            maxX= segmentList.get(0).getXE();
            minX= segmentList.get(0).getXE();
            minY= segmentList.get(0).getXE();
        }else{
            maxY=archList.get(0).getYE();
            maxX=archList.get(0).getXE();
            minX=archList.get(0).getXE();
            minY=archList.get(0).getXE();
        }
        //trovo gli angoli dell'immagine(naggiore x e y tra tutti i vertici
        for(Segment v: segmentList){
            if(v.s.x>maxX)
                maxX=v.s.x;
            else{
                if(v.s.x<minX)
                    minX=v.s.x;
            }
            if(v.s.y>maxY)
                maxY=v.s.y;
            else{
                if(v.s.y<minY)
                    minY=v.s.y;
            }
            if(v.e.x>maxX)
                maxX=v.e.x;
            else{
                if(v.e.x<minX)
                    minX=v.e.x;
            }
            if(v.e.y>maxY)
                maxY=v.e.y;
            else{
                if(v.e.y<minY)
                    minY=v.e.y;
            }
        }
        CoorD further;
        for(Arch v:archList){
            if(v.s.x>maxX)
                maxX=v.s.x;
            else{
                if(v.s.x<minX)
                    minX=v.s.x;
            }
            if(v.s.y>maxY)
                maxY=v.s.y;
            else{
                if(v.s.y<minY)
                    minY=v.s.y;
            }
            if(v.e.x>maxX)
                maxX=v.e.x;
            else{
                if(v.e.x<minX)
                    minX=v.e.x;
            }
            if(v.e.y>maxY)
                maxY=v.e.y;
            else {
                if (v.e.y < minY)
                    minY = v.e.y;
            }
            further=v.getFurtherPoint();
            if(further.x>maxX)
                maxX=further.x;
            else{
                if(further.x<minX)
                    minX=further.x;
            }
            if(further.y>maxY)
                maxY=further.y;
            else{
                if(further.y<minY)
                    minY=further.y;
            }
        }
        WritableImage img=new WritableImage((int)(maxX-minX+2),(int)(maxY-minY+2));
        PixelWriter pi=img.getPixelWriter();
        for(Arch in:archList) copyCoors(minY, minX, pi, in);
        for(Segment in: segmentList) copyCoors(minY, minX, pi, in);
        return img;
    }

    private void copyCoors(double minY, double minX, PixelWriter pi, Segment in) {
        Segment niuV;
        niuV=in.moveDownLeft(minX,minY);
        for(CoorD c:niuV.getCoorsX()) pi.setColor((int)(c.x<0?0:c.x),(int)(c.y<0?0:c.y), Color.BLACK);
        for(CoorD c:niuV.getCoorsY()) pi.setColor((int)(c.x<0?0:c.x),(int)(c.y<0?0:c.y),Color.BLACK);
    }
    private void copyCoorsBool(double minY,double minX,boolean[][] matrix,Segment in){
        Segment niuV;
        niuV=in.moveDownLeft(minX,minY);
        for(CoorD c:niuV.getCoorsX())matrix[(int)(c.x<0?0:c.x)][(int)(c.y<0?0:c.y)]=true;
        for(CoorD c:niuV.getCoorsY())matrix[(int)(c.x<0?0:c.x)][(int)(c.y<0?0:c.y)]=true;
    }
    public void scale(double frac){
        for(Arch in:archList) in.scale(frac);
        for(Segment in: segmentList) in.scale(frac);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VectorialImage that = (VectorialImage) o;
        return Objects.equals(segmentList, that.segmentList) && Objects.equals(archList, that.archList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(segmentList, archList);
    }
    @Override
    public String toString(){
        String out="VectorialImage: ";
        for(Arch in:archList) {
            out+="\n"+in;
        }
        for(Segment in: segmentList) {
            out+="\n"+in;
        }
        return out;
    }
    public String ascii(){
        double maxY,maxX,minY,minX;
        if(segmentList.size()>=1){
            maxY= segmentList.get(0).getYE();
            maxX= segmentList.get(0).getXE();
            minX= segmentList.get(0).getXE();
            minY= segmentList.get(0).getXE();
        }else{
            maxY=archList.get(0).getYE();
            maxX=archList.get(0).getXE();
            minX=archList.get(0).getXE();
            minY=archList.get(0).getXE();
        }
        //trovo gli angoli dell'immagine(naggiore x e y tra tutti i vertici
        for(Segment v: segmentList){
            if(v.s.x>maxX)
                maxX=v.s.x;
            else{
                if(v.s.x<minX)
                    minX=v.s.x;
            }
            if(v.s.y>maxY)
                maxY=v.s.y;
            else{
                if(v.s.y<minY)
                    minY=v.s.y;
            }
            if(v.e.x>maxX)
                maxX=v.e.x;
            else{
                if(v.e.x<minX)
                    minX=v.e.x;
            }
            if(v.e.y>maxY)
                maxY=v.e.y;
            else{
                if(v.e.y<minY)
                    minY=v.e.y;
            }
        }
        CoorD further;
        for(Arch v:archList){
            if(v.s.x>maxX)
                maxX=v.s.x;
            else{
                if(v.s.x<minX)
                    minX=v.s.x;
            }
            if(v.s.y>maxY)
                maxY=v.s.y;
            else{
                if(v.s.y<minY)
                    minY=v.s.y;
            }
            if(v.e.x>maxX)
                maxX=v.e.x;
            else{
                if(v.e.x<minX)
                    minX=v.e.x;
            }
            if(v.e.y>maxY)
                maxY=v.e.y;
            else {
                if (v.e.y < minY)
                    minY = v.e.y;
            }
            further=v.getFurtherPoint();
            if(further.x>maxX)
                maxX=further.x;
            else{
                if(further.x<minX)
                    minX=further.x;
            }
            if(further.y>maxY)
                maxY=further.y;
            else{
                if(further.y<minY)
                    minY=further.y;
            }
        }
        boolean[][] img=new boolean[(int)(maxX-minX+2)][(int)(maxY-minY+2)];
        for(Arch in:archList) copyCoorsBool(minY, minX, img, in);
        for(Segment in: segmentList) copyCoorsBool(minY, minX, img, in);
        String out="";
        for (int i = 0; i < img.length; i++) {
            for (int j = 0; j < img[0].length; j++) {
                out+=img[i][j]?(char)178:(char)176;
            }
        }
        return out;
    }
}
