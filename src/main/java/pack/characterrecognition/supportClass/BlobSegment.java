package pack.characterrecognition.supportClass;

import pack.characterrecognition.supportClass.figures.CoorD;

import java.util.LinkedList;

public class BlobSegment extends Segment {
    protected LinkedList<CoorD> lista=new LinkedList<>();

    public BlobSegment(CoorD st){
        super(st,st);
        lista.add(st.getCopy());
    }

    public BlobSegment(CoorD s, CoorD e) {
        super(s,e);
        lista.add(s.getCopy());
        if(!lista.contains(e))
            lista.add(e.getCopy());
    }

    public void moveEnd(CoorD in){
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
    private CoorD upper(){
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
    private CoorD downer(){
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
    public boolean isExtern(CoorD p){
        CoorD h,d;
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
    public BlobSegment getCopy(){
        BlobSegment out=new BlobSegment(s);
        out.moveEnd(e);
        out.lista.addAll(lista);
        return out;
    }
    public Segment toSegment(){
        return new Segment(s,e);
    }
}
