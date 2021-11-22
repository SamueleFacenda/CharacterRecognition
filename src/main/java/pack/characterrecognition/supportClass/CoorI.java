package pack.characterrecognition.supportClass;

import java.util.Objects;

/**
 * classe che indica le coordinate di un punto 2d
 * , con alcuni metodi di utilità,
 * int
 * @author Samuele Facenda
 */
public class CoorI {
    /**
     * la posizione sull'asse x o y del punto
     */
    protected int x,y;

    /**
     * costruttore semplice
     * @param x posizione sull'asse x
     * @param y posizione sull'asse y
     */
    public CoorI(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public CoorI(CoorD in){
        x=DoubleUtils.toInt(Math.floor(in.x));
        y=DoubleUtils.toInt(Math.floor(in.y));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    /**
     * calcolo della distanza di due punti
     * @param uno primo punto
     * @param due secondo punto
     * @return la distanza
     */
    public static double getDist(CoorI uno, CoorI due){
        return Math.sqrt(Math.pow(uno.x-due.x,2)+Math.pow(uno.y-due.y,2));
    }

    /**
     * calcola il punto in mezzo a due punti(punto medio del segmento
     * che ha come vertici i due parametri)
     * @param uno vertice uno del segmento
     * @param due vertice due del segmento
     * @return punto medio del semgento
     */
    public static CoorD getPMedio(CoorD uno, CoorD due){
        return new CoorD((uno.x+due.x)/2,(uno.y+due.y)/2);
    }
    public CoorI getCopy(){
        return new CoorI(x,y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoorI coor = (CoorI) o;
        return coor.x==x && coor.y==y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
    @Override
    public String toString(){
        return "x: "+x+",y: "+y;
    }

    /**
     * restituisce true se la coordinata inserita è compresa in un
     * cerchio di raggio radius da questa coordinata
     * @param in punto da controllare
     * @param radius distanza massima per dire che sono simili
     * @return se sono simili in base al raggio inserito
     */
    public boolean areSimilar(CoorI in, double radius){
        return getDist(this,in)<=radius;
    }

    /**
     * metodo statico che controlla se la distanza di due punti è minore di quella
     * inserita da parametro, se sono vicine
     * @param uno primo punto
     * @param due secondo punto
     * @param radius distanza massima per dire che sono vicine
     * @return se sono vicine
     */
    public static boolean areNear(CoorI uno, CoorI due, double radius){
        return getDist(uno,due)<=radius;
    }
    public static double calcRad(CoorI s, CoorI e){
        if(s.equals(e))
            return Math.PI*4;
        else if(s.y>e.y)
            return Math.acos((s.x-e.x)/getDist(s,e));
        else
            return Math.PI*2-Math.acos((s.x-e.x)/getDist(s,e));
    }
    public void scale(double fract){
        x=(int)Math.floor(x*fract);
        y=(int)Math.floor(y*fract);
    }
}
