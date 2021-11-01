package pack.characterrecognition.supportClass;

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
        for(Vector a:in.vectorList)vectorList.add(a.getCopy());
    }
    public void enchant(){
        double height=0,width=0;
        for (Vector v:
             vectorList) {
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
        double smallCoefficent=height/7,angleCoefficent=Math.PI*0.5;
        for (Vector v:
                vectorList)
            checkFormForPoints(v,smallCoefficent);
        for (Arch a:
                archList) {
            checkFormForPoints(a,smallCoefficent);
        }
        for (GraphPoint gp:
             points) {
            checkForLinearVector(gp,angleCoefficent);
        }
    }
    private void checkFormForPoints(Vector in,double smallCoefficent){
        for (GraphPoint gp:
             points) {
            if(Coor.areNear(in.s,gp,smallCoefficent)){
                gp.addStart(in);
                in.setS(gp.getCopy());
            }else{
                GraphPoint added=new GraphPoint(in.s);
                added.addStart(in);
                points.add(added);
            }
            if(Coor.areNear(in.e,gp,smallCoefficent)){
                gp.addEnd(in);
                in.setE(gp.getCopy());
            }else{
                GraphPoint added=new GraphPoint(in.e);
                added.addEnd(in);
                points.add(added);
            }
        }
    }
    public void checkForLinearVector(GraphPoint gp, double angleCoefficent) {
        for (Vector v:
                gp.getStart()){
            for (Vector second:
                    gp.getStart()){
                if(v!=second&& !(v instanceof Arch) && !(second instanceof Arch) && Vector.areSemiParallel(v,second,angleCoefficent)){
                    Vector due=new Vector(v.e,second.e);
                    gp.move(due.getNearestPointOnThis(gp));
                    for (GraphPoint punto:
                         points) {
                        if(punto.getEnd().contains(v)){
                            punto.getEnd().remove(v);
                            punto.getEnd().add(due);
                        }
                        if(punto.getEnd().contains(second)){
                            punto.getEnd().remove(second);
                            punto.getEnd().add(due);
                        }
                    }
                }
            }
            for (Vector second:
                    gp.getEnd()){
                if(v!=second&& !(v instanceof Arch) && !(second instanceof Arch) && Vector.areSemiParallel(v,second,angleCoefficent)){
                    Vector due=new Vector(v.e,second.s);
                    gp.move(due.getNearestPointOnThis(gp));
                    for (GraphPoint punto:
                            points) {
                        if(punto.getEnd().contains(v)){
                            punto.getEnd().remove(v);
                            punto.getEnd().add(due);
                        }
                        if(punto.getStart().contains(second)){
                            punto.getStart().remove(second);
                            punto.getStart().add(due);
                        }
                    }
                }
            }
        }
        for (Vector v:
                gp.getEnd()) {
            for (Vector second:
                    gp.getStart()){
                if(v!=second&& !(v instanceof Arch) && !(second instanceof Arch) && Vector.areSemiParallel(v,second,angleCoefficent)){
                    Vector due=new Vector(v.s,second.e);
                    gp.move(due.getNearestPointOnThis(gp));
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
            for (Vector second:
                    gp.getEnd()){
                if(v!=second&& !(v instanceof Arch) && !(second instanceof Arch) && Vector.areSemiParallel(v,second,angleCoefficent)){
                    Vector due=new Vector(v.s,second.s);
                    gp.move(due.getNearestPointOnThis(gp));
                    for (GraphPoint punto:
                            points) {
                        if(punto.getStart().contains(v)){
                            punto.getStart().remove(v);
                            punto.getStart().add(due);
                        }
                        if(punto.getStart().contains(second)){
                            punto.getStart().remove(second);
                            punto.getStart().add(due);
                        }
                    }
                }
            }
        }
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
