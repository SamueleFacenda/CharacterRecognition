package pack.characterrecognition.supportClass;

import java.util.LinkedList;

public class Blob {
    private LinkedList<Coor> lista;
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
            if(c.y>max) max=(int)Math.ceil(c.y);
         return max;
    }
    public int getDowner(){
        int min=(int)lista.get(0).y;
        for(Coor c:lista)
            if(c.y<min) min=(int)c.y;
        return min;
    }
    public int getRigther(){
        int max=0;
        for(Coor c:lista)
            if(c.x>max) max=(int)Math.ceil(c.x);
        return max;
    }
    public int getLefter(){
        int min=(int)lista.get(0).x;
        for(Coor c:lista)
            if(c.x<min) min=(int)c.x;
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
}
