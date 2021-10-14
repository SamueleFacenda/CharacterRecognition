package pack.characterrecognition.supportClass;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import java.util.LinkedList;

public class VectorialImage {
    private LinkedList<Vector> vectorList;
    private LinkedList<Arch> archList;
    public VectorialImage(){
        vectorList=new LinkedList<>();
        archList=new LinkedList<>();
    }
    public void add(Arch in){
        archList.add(in);
    }
    public void add(Vector in){
        vectorList.add(in);
    }
    public Arch getArch(int index){
        return index<archList.size()?archList.get(index):null;
    }
    public Vector getVector(int index){
        return index<vectorList.size()?vectorList.get(index):null;
    }
    public Image toImage(){
        double maxY,maxX,minY,minX;
        if(vectorList.size()>=1){
            maxY=vectorList.get(0).getYE();
            maxX=vectorList.get(0).getXE();
            minX=vectorList.get(0).getXE();
            minY=vectorList.get(0).getXE();
        }else{
            maxY=archList.get(0).getYE();
            maxX=archList.get(0).getXE();
            minX=archList.get(0).getXE();
            minY=archList.get(0).getXE();
        }
        for(Vector v:vectorList){
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
            else{
                if(v.e.y<minY)
                    minY=v.e.y;
            }
            if(v.p.x>maxX)
                maxX=v.p.x;
            else{
                if(v.p.x<minX)
                    minX=v.p.x;
            }
            if(v.p.y>maxY)
                maxY=v.p.y;
            else{
                if(v.p.y<minY)
                    minY=v.p.y;
            }
        }
        ImageFromVector img=new ImageFromVector((int)(maxX-minX+2),(int)(maxY-minY+2));
        for(Arch v:archList)
            img.write(v.moveDownLeft(minX,minY));
        for(Vector v:vectorList)
            img.write(v.moveDownLeft(minX,minY));
        return img;
    }
}
