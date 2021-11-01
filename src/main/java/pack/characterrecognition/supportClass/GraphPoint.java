package pack.characterrecognition.supportClass;

import java.util.LinkedList;

public class GraphPoint extends Coor{
    private LinkedList<Vector> start,end;
    public GraphPoint(Coor in) {
        super(in.x, in.y);
        start=new LinkedList<>();
        end=new LinkedList<>();
    }
    public void addStart(Vector in){
        if(!start.contains(in)) start.add(in);
    }
    public void addEnd(Vector in){
        if(!end.contains(in)) end.add(in);
    }
    public LinkedList<Vector> getStart(){
        return start;
    }
    public LinkedList<Vector> getEnd(){
        return end;
    }
    public void move(Coor in){
        for (Vector v:
                start)
            v.setS(in);
        for (Vector a:
                end) {
            a.setE(in);
        }
    }

}
