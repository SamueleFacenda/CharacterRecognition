package pack.characterrecognition.supportClass;

import pack.characterrecognition.supportClass.figures.CoorD;

import java.util.LinkedList;

/**
 * classe di utilità per le vectorialmap, un punto così è caratterizzato oltre che dalle coordinate
 * dai segmenti che partono e arrivano al punto
 * @author Samuele Facenda
 */
public class GraphPoint extends CoorD {
    private LinkedList<Segment> start,end;
    public GraphPoint(CoorD in) {
        super(in.x, in.y);
        start=new LinkedList<>();
        end=new LinkedList<>();
    }
    public void addStart(Segment in){
        if(!start.contains(in)) start.add(in);
    }
    public void addEnd(Segment in){
        if(!end.contains(in)) end.add(in);
    }
    public LinkedList<Segment> getStart(){
        return start;
    }
    public LinkedList<Segment> getEnd(){
        return end;
    }

    /**
     * sposta il punto e tutti i vertici dei segmenti che arrivano al punto alla coordinata inserita
     * @param in punto su cui spostare questo incrocio
     */
    public void move(CoorD in){
        for (Segment v:
                start)
            v.setS(in);
        for (Segment a:
                end) {
            a.setE(in);
        }
        x=in.x;
        y=in.y;
    }
    @Override public String toString(){
        String out="GraphPoint: "+super.toString();
        for (Segment s:
             start)
            out+="\nstart: "+s.toString();
        for (Segment s:
                end)
            out+="\nend: "+s.toString();
        return out;
    }

}
