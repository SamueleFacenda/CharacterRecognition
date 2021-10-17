package pack.characterrecognition.supportClass;

import java.util.Objects;

public class Coor {
    protected double x,y;

    public Coor(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
    public static double getDist(Coor uno,Coor due){
        return Math.sqrt(Math.pow(uno.x-due.x,2)+Math.pow(uno.y-due.y,2));
    }
    public static Coor getPMedio(Coor uno,Coor due){
        return new Coor((uno.x+due.x)/2,(uno.y+due.y)/2);
    }
    public Coor getCopy(){
        return new Coor(x,y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coor coor = (Coor) o;
        return Double.compare(coor.x, x) == 0 && Double.compare(coor.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
    @Override
    public String toString(){
        return "x: "+x+",y: "+y;
    }
}
