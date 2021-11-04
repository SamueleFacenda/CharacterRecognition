package pack.characterrecognition.supportClass;

import java.util.Objects;

public class Segment {
    protected Coor s,e;//start e end

    public Segment(int xS, int yS, int xE, int yE) {
        s=new Coor(xS,yS);
        e=new Coor(xE,yE);
    }
    public Segment(Coor s, Coor e){
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
        return Segment.getPendenzaDuePunti(s,e);
    }
    public double getYAxis(){
        return s.y-(getPendenza()*s.x);
    }
    public Coor getPMedio(){
        return Coor.getPMedio(s,e);
    }
    public static double getPendenzaDuePunti(Coor uno,Coor due ){
        if(Double.compare(uno.x,due.x)==0)
            return Double.MAX_VALUE;
        else
            return (uno.y-due.y)/(uno.x-due.x);
    }
    public boolean isUpper(Coor p){
        return getPendenza()*p.x-p.y+getYAxis()>0;
    }
    public Coor[] getCoorsX(){
        double m=getPendenza(),q=getYAxis(),start=Double.min(s.x,e.x);
        if(Double.compare(e.x,s.x)==0)
            return new Coor[]{s.getCopy(),e.getCopy()};
        else if(Double.compare(e.y,s.y)==0){
            Coor[] out = new Coor[(int) Math.abs(s.x - e.x)];
            for (int i = (int) start; i < start + out.length; i++)
                out[(int) (i - start)] = new Coor(i, s.y);
            return out;
        }else{
            Coor[] out = new Coor[(int) Math.abs(s.x - e.x)];
            for (int i = (int) start; i < start + out.length; i++)
                out[(int) (i - start)] = new Coor(i, Math.round(i * m + q));
            return out;
        }
    }
    public Coor[] getCoorsY(){
        double m=getPendenza(),q=getYAxis(),start=Double.min(s.y,e.y);
        if(Double.compare(e.y,s.y)==0)
            return new Coor[]{s.getCopy(),e.getCopy()};
        else if(Double.compare(e.x,s.x)==0){
            Coor[] out = new Coor[(int) Math.abs(s.y - e.y)];
            for (int i = (int) start; i < start + out.length; i++)
                out[(int) (i - start)] = new Coor(s.x,i);
            return out;
        }else{
            Coor[] out = new Coor[(int) Math.abs(s.y - e.y)];
            for (int i = (int) start; i < start + out.length; i++)
                out[(int) (i - start)] = new Coor(Math.round((i - q) / m), i);
            return out;
        }
    }
    public Segment moveDownLeft(double deltaX, double deltaY){
        return new Segment(new Coor(s.x-deltaX,s.y-deltaY),new Coor(e.x-deltaX,e.y-deltaY));
    }
    public Segment(){}
    public Segment getCopy(){
        return new Segment(s,e);
    }
    public static boolean areSemiContigous(Segment uno, Segment due, double radMax){
        Coor center,p1,p2;
        if(uno.s.equals(due.s)){
            center=uno.s;
            p1=uno.e;
            p2=due.e;
        }else if(uno.s.equals(due.e)){
            center=uno.s;
            p1=uno.e;
            p2=due.s;
        }else if(uno.e.equals(due.e)){
            center=uno.e;
            p1=uno.s;
            p2=due.s;
        }else{
            center=uno.e;
            p1=uno.e;
            p2=due.e;
        }
        double radP1=Math.acos((center.x-p1.x)/Coor.getDist(center,p1)),radP2=Math.acos((center.x-p2.x)/Coor.getDist(center,p2));
        if(p1.y< center.y)
            radP1=Math.PI*2-radP1;
        if(p2.y<center.y)
            radP2=Math.PI*2-radP2;
        return Math.abs(radP1-Math.PI-radP2)<=radMax || Math.abs(radP1+Math.PI-radP2)<=radMax;
    }
    public Coor getNearestPointOnThis(Coor in){
        if(Double.compare(e.y,s.y)==0)
            return new Coor(in.x,s.y);
        else if(Double.compare(e.x,s.x)==0){
            return new Coor(s.x,in.y);
        }else{
            double pendenza=getPendenza(),pendenzaStorta=pendenza==0?Double.MAX_VALUE:-1.0/pendenza;
            double yPointStorto=in.y-(pendenzaStorta*in.x),yPoint=getYAxis();
            double x=(yPointStorto-yPoint)/(pendenza-pendenzaStorta);
            return new Coor(x,pendenza*x+yPoint);
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Segment segment = (Segment) o;
        return Objects.equals(s, segment.s) && Objects.equals(e, segment.e);
    }

    @Override
    public int hashCode() {
        return Objects.hash(s, e);
    }
    @Override
    public String toString(){
        return "Segmento: \n"+"start: "+s+"\nend: "+e;
    }
}
