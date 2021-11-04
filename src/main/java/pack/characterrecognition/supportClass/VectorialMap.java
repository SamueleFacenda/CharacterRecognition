package pack.characterrecognition.supportClass;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;

public class VectorialMap extends VectorialImage{
    private LinkedList<GraphPoint> points;
    public VectorialMap(){
        super();
        points=new LinkedList<>();
    }
    public VectorialMap(VectorialImage in){
        super();
        points=new LinkedList<>();
        for(Arch a:in.archList)archList.add(a.getCopy());
        for(Segment a:in.segmentList) segmentList.add(a.getCopy());
    }
    public void enchant(){
        double height=0,width=0;
        for (Segment v:
                segmentList) {
            if(v.s.x>width)
                width=v.s.x;
            if(v.s.y>height)
                height=v.s.y;
            if(v.e.x>width)
                width=v.e.x;
            if(v.e.y>height)
                height=v.e.y;
        }
        Coor further;
        for (Arch v:
                archList) {
            if(v.s.x>width)
                width=v.s.x;
            if(v.s.y>height)
                height=v.s.y;
            if(v.e.x>width)
                width=v.e.x;
            if(v.e.y>height)
                height=v.e.y;
            further=v.getFurtherPoint();
            if(further.x>width)
                width=further.x;
            if(further.y>height)
                height=further.y;
        }
        points.clone();
        double smallCoefficent=height/7,angleCoefficent=Math.PI*0.25;
        for (Segment v:
                segmentList)
            checkFormForPoints(v,smallCoefficent);
        for (Arch a:
                archList)
            checkFormForPoints(a,smallCoefficent);
        for (GraphPoint gp:
             points)
            checkForLinearVector(gp,angleCoefficent);
    }
    private void checkFormForPoints(Segment in, double smallCoefficent){
        Iterator<GraphPoint> i=points.iterator();
        GraphPoint gp;
        boolean notSFind=true,notEFind=true;
        while(i.hasNext()&&(notSFind||notEFind)){
            gp=i.next();
            if(notSFind&&Coor.areNear(in.s,gp,smallCoefficent)){
                gp.addStart(in);
                in.setS(gp.getCopy());
                notSFind=false;
            }
            if(notEFind&&Coor.areNear(in.e,gp,smallCoefficent)){
                gp.addStart(in);
                in.setE(gp.getCopy());
                notEFind=false;
            }
        }
        if(notEFind){
            GraphPoint added=new GraphPoint(in.e);
            added.addEnd(in);
            points.add(added);
        }
        if(notSFind){
            GraphPoint added=new GraphPoint(in.s);
            added.addStart(in);
            points.add(added);
        }
    }
    public void checkForLinearVector(GraphPoint gp, double angleCoefficent) {
        for (Segment v:
                gp.getStart()){
            for (Segment second:
                    gp.getStart()){
                if(v!=second&& !(v instanceof Arch) && !(second instanceof Arch) && Segment.areSemiContigous(v,second,angleCoefficent)){
                    Segment due=new Segment(v.e,second.e);
                    gp.move(due.getNearestPointOnThis(gp));
                    gp.getStart().remove(v);
                    gp.getStart().remove(second);
                    segmentList.remove(v);
                    segmentList.remove(second);
                    segmentList.add(due);
                    for (GraphPoint punto:
                         points) {
                        if(punto.getEnd().contains(v)){
                            punto.getEnd().remove(v);
                            punto.getStart().add(due);
                        }
                        if(punto.getEnd().contains(second)){
                            punto.getEnd().remove(second);
                            punto.getEnd().add(due);
                        }
                    }
                }
            }
            for (Segment second:
                    gp.getEnd()){
                if(v!=second&& !(v instanceof Arch) && !(second instanceof Arch) && Segment.areSemiContigous(v,second,angleCoefficent)){
                    Segment due=new Segment(v.e,second.s);
                    gp.move(due.getNearestPointOnThis(gp));
                    gp.getEnd().remove(second);
                    gp.getStart().remove(v);
                    segmentList.remove(v);
                    segmentList.remove(second);
                    segmentList.add(due);
                    for (GraphPoint punto:
                            points) {
                        if(punto.getEnd().contains(v)){
                            punto.getEnd().remove(v);
                            punto.getStart().add(due);
                        }
                        if(punto.getStart().contains(second)){
                            punto.getStart().remove(second);
                            punto.getEnd().add(due);
                        }
                    }
                }
            }
        }
        for (Segment v:
                gp.getEnd()) {
            for (Segment second:
                    gp.getStart()){
                if(v!=second&& !(v instanceof Arch) && !(second instanceof Arch) && Segment.areSemiContigous(v,second,angleCoefficent)){
                    Segment due=new Segment(v.s,second.e);
                    gp.move(due.getNearestPointOnThis(gp));
                    gp.getEnd().remove(v);
                    gp.getStart().remove(second);
                    segmentList.remove(v);
                    segmentList.remove(second);
                    segmentList.add(due);
                    for (GraphPoint punto:
                            points) {
                        if(punto.getStart().contains(v)){
                            punto.getStart().remove(v);
                            punto.getStart().add(due);
                        }
                        if(punto.getEnd().contains(second)){
                            punto.getEnd().remove(second);
                            punto.getEnd().add(due);
                        }
                    }
                }
            }
            for (Segment second:
                    gp.getEnd()){
                if(v!=second&& !(v instanceof Arch) && !(second instanceof Arch) && Segment.areSemiContigous(v,second,angleCoefficent)){
                    Segment due=new Segment(v.s,second.s);
                    gp.move(due.getNearestPointOnThis(gp));
                    gp.getEnd().remove(v);
                    gp.getEnd().remove(second);
                    segmentList.remove(v);
                    segmentList.remove(second);
                    segmentList.add(due);
                    for (GraphPoint punto:
                            points) {
                        if(punto.getStart().contains(v)){
                            punto.getStart().remove(v);
                            punto.getStart().add(due);
                        }
                        if(punto.getStart().contains(second)){
                            punto.getStart().remove(second);
                            punto.getEnd().add(due);
                        }
                    }
                }
            }
        }
    }
    public void scale(double fraction){
        for (GraphPoint gp: points)
            gp.move(new Coor(gp.x*fraction,gp.y*fraction));
        for (GraphPoint gp: points)
            System.out.println(gp);
    }
    static public double calcSimil(VectorialMap uno,VectorialMap due){
        uno.scale(100.0/uno.getHeight());
        due.scale(100.0/due.getHeight());
        double minCoefficent=20,similVal=1;
    }
    public GraphPoint getHigher(){
        if(points.size()==0)
            return null;
        else{
            GraphPoint max=points.getFirst();
            for(GraphPoint gp:points) if(gp.y>max.y) max=gp;
            return max;
        }
    }
    public GraphPoint getLefter(){
        if(points.size()==0)
            return null;
        else{
            GraphPoint max=points.getFirst();
            for(GraphPoint gp:points) if(gp.x<max.x) max=gp;
            return max;
        }
    }
    public GraphPoint getDowner(){
        if(points.size()==0)
            return null;
        else{
            GraphPoint max=points.getFirst();
            for(GraphPoint gp:points) if(gp.y<max.y) max=gp;
            return max;
        }
    }
    public GraphPoint getRighter(){
        if(points.size()==0)
            return null;
        else{
            GraphPoint max=points.getFirst();
            for(GraphPoint gp:points) if(gp.x>max.x) max=gp;
            return max;
        }
    }
    public double getHeight(){
        return getHigher().y-getDowner().y;
    }
    public double getWidth(){
        return getRighter().x-getLefter().x;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        VectorialMap that = (VectorialMap) o;
        return Objects.equals(points, that.points);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), points);
    }
}
