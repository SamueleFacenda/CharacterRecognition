package pack.characterrecognition.geometry;

import java.util.LinkedList;

public class BlobVector extends Vector{
    protected LinkedList<Coor> lista=new LinkedList<>();
    protected Coor u,d;

    public BlobVector(Coor st){
        super(st.getCopy(),st.getCopy());
        lista.add(st.getCopy());
        u=st.getCopy();
        d=st.getCopy();
    }
    public void add(Coor in){
        if(!lista.contains(in)){
            lista.add(in.getCopy());
            e=in.getCopy();
            if(isUpper(in))
                if(getDistanza(in)>getDistanza(u))u=in.getCopy();
            else
                if(getDistanza(in)>getDistanza(d))d=in.getCopy();
        }
    }
    public double width(){
        return getDistanza(u)+getDistanza(d);
    }
    public boolean isExtern(Coor p){
        if(s.x<e.x){
            if(s.y<e.y){

            }else{

            }
        }else{
            if(s.y<e.y){

            }else{

            }
        }
        return false;
    }
}
