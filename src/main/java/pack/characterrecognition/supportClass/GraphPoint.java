package pack.characterrecognition.supportClass;

import java.util.LinkedList;

public class GraphPoint extends Coor{
    private LinkedList<Segment> start,end;
    public GraphPoint(Coor in) {
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
    public void move(Coor in){
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
