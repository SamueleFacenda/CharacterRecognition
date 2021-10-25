package pack.characterrecognition.supportClass;

import java.util.Objects;

public class Vector {
    protected Coor s,e;//start e end

    public Vector(int xS, int yS, int xE, int yE) {
        s=new Coor(xS,yS);
        e=new Coor(xE,yE);
    }
    public Vector(Coor s,Coor e){
        this.s=s.getCopy();
        this.e=e.getCopy();
    }
    public void setS(Coor s){
        this.s=s.getCopy();
    }
    public void setE(Coor e){
        this.e=e.getCopy();
    }
    public Coor getS(){
        return e.getCopy();
    }
    public Coor getE(){
        return e.getCopy();
    }
    public void setXS(int x){
        s.x=x;
    }
    public void setYS(int y){
        s.y=y;
    }
    public void setXE(int x){
        e.x=x;
    }
    public void setYE(int y){
        e.y=y;
    }
    public double getXS(){
        return s.x;
    }
    public double getYS(){
        return s.y;
    }
    public double getXE(){
        return e.x;
    }
    public double getYE(){
        return e.y;
    }
    public double getLen(){
        return Coor.getDist(s,e);
    }
    public double getDistanza(Coor p){
        return Math.abs(getPendenza()*p.x-p.y+getYAxis())/Math.sqrt(Math.pow(getPendenza(),2)+1);
    }
    public double getDistanza(int x,int y){
        return Math.abs(getPendenza()*x-y+getYAxis())/Math.sqrt(Math.pow(getPendenza(),2)+1);
    }
    public double getPendenza(){
        return Vector.getPendenzaDuePunti(s,e);
    }
    public double getYAxis(){
        return s.y-(getPendenza()*s.x);
    }
    public Coor getPMedio(){
        return Coor.getPMedio(s,e);
    }
    public static double getPendenzaDuePunti(Coor uno,Coor due ){
        return (uno.y-due.y)/(uno.x-due.x);
    }
    public boolean isUpper(Coor p){
        return getPendenza()*p.x-p.y+getYAxis()>0;
    }
    public Coor[] getCoors(){
        Coor[] out=new Coor[(int)Math.abs(s.x-e.x)];
        double m=getPendenza(),q=getYAxis(),start=Double.min(s.x,e.x);
        for(int i=(int)start;i<start+out.length;i++)
            out[(int) (i-start)]=new Coor(i,Math.round(i*m+q));
        return out;
    }
    public Vector moveDownLeft(double deltaX,double deltaY){
        return new Vector(new Coor(s.x-deltaX,s.y-deltaY),new Coor(e.x-deltaX,e.y-deltaY));
    }
    public Vector(){}
    public Vector getCopy(){
        return new Vector(s,e);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Objects.equals(s, vector.s) && Objects.equals(e, vector.e);
    }

    @Override
    public int hashCode() {
        return Objects.hash(s, e);
    }
}
