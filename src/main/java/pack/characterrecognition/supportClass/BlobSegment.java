package pack.characterrecognition.supportClass;

import java.util.LinkedList;

public class BlobSegment extends Segment {
    protected LinkedList<Coor> lista=new LinkedList<>();

    public BlobSegment(Coor st){
        super(st.getCopy(),st.getCopy());
        lista.add(st.getCopy());
    }
    public void moveEnd(Coor in){
        if(!lista.contains(in)){
            lista.add(in.getCopy());
            e=in.getCopy();
        }
    }
    public double width(){
        return getDistanza(upper())+getDistanza(downer());
    }
    public double getMaxDist(){
        return Double.max(getDistanza(upper()),getDistanza(downer()));
    }
    private Coor upper(){
        double maxDist=getDistanza(lista.get(0));
        int max=0;
        for(int i=1;i<lista.size();i++){
            if(isUpper(lista.get(i))&&getDistanza(lista.get(i))>maxDist){
                max=i;
                maxDist=getDistanza(lista.get(i));
            }
        }
        return lista.get(max).getCopy();
    }
    private Coor downer(){
        double maxDist=getDistanza(lista.get(0));
        int max=0;
        for(int i=1;i<lista.size();i++){
            if(!isUpper(lista.get(i))&&getDistanza(lista.get(i))>maxDist){
                max=i;
                maxDist=getDistanza(lista.get(i));
            }
        }
        return lista.get(max).getCopy();
    }
    public boolean isExtern(Coor p){
        Coor h,d;
        if(s.y>e.y){
            h=s;
            d=e;
        }else{
            h=e;
            d=s;
        }
        if(h.x>d.x)
            return (p.x<d.x||p.y<s.y)&&(p.x>h.x||p.y>h.y);
        else
            return (p.x<h.x||p.y>h.y)&&(p.x>d.x||p.y<d.y);
    }
}