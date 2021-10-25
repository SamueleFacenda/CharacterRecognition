package pack.characterrecognition.supportClass;

import java.util.LinkedList;
import java.util.Objects;

public class VectorialMap extends VectorialImage{
    private LinkedList<Coor> point;
    public VectorialMap(){
        super();
        point=new LinkedList<>();
    }
    public VectorialMap(VectorialImage in){
        super();
        point=new LinkedList<>();
        for(Arch a:in.archList)archList.add(a.getCopy());
        for(Vector a:in.vectorList)vectorList.add(a.getCopy());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        VectorialMap that = (VectorialMap) o;
        return Objects.equals(point, that.point);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), point);
    }
}
