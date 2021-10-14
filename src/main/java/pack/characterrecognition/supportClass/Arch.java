package pack.characterrecognition.supportClass;

import java.util.LinkedList;

public class Arch extends Vector{
    protected Coor p;//terzo punto dell'arco oltre all'inizio e alla fine
    public Arch(int xS, int yS, int xE, int yE,int pX,int pY) {
        super(xS, yS, xE, yE);
        p=new Coor(pX,pY);
    }
    public Arch(Coor s,Coor e,Coor p){
        super(s,e);
        this.p=p.getCopy();
    }
    public Coor getCenter(){
        Coor m1=Coor.getPMedio(s,p),m2=Coor.getPMedio(p,e);
        double p1=-1/Vector.getPendenzaDuePunti(s,p)*m1.x,p2=-1/Vector.getPendenzaDuePunti(p,e);
        double yA1=m1.y-(p1*m1.x),yA2= m2.y-(p2*m2.x);
        double x=(p1-p2)/(yA2-yA1);
        return new Coor(x,p1*x+yA1);
    }
    public double getRadius(){
        return Coor.getDist(s,getCenter());
    }
    public Coor getFurtherPoint(){
        Coor center=getCenter();
        double radius=getRadius(),radS=Math.asin(Math.abs(s.y-center.y)/radius),radE=Math.asin(Math.abs(e.y-center.y)/radius);
        radS=s.y>center.y?(s.x> center.x?radS:Math.PI-radS):(s.x> center.x?2*Math.PI-radS:Math.PI+radS);
        radE=e.y>center.y?(e.x> center.x?radE:Math.PI-radE):(e.x> center.x?2*Math.PI-radE:Math.PI+radE);
        double medRad=(radS+radE)/2;
        return new Coor(radius*Math.cos(medRad),radius*Math.sin(medRad));
    }
    public double getWidth(){
        return getDistanza(getFurtherPoint());
    }
    public Arch moveDown(double n){
        return new Arch(new Coor(s.x,s.y-n),new Coor(e.x,e.y-n),new Coor(p.x,p.y-n));
    }
    public Arch moveUp(double n){
        return new Arch(new Coor(s.x,s.y+n),new Coor(e.x,e.y+n),new Coor(p.x,p.y+n));
    }
    public Arch moveLeft(double n){
        return new Arch(new Coor(s.x-n,s.y),new Coor(e.x-n,e.y),new Coor(p.x-n,p.y));
    }
    public Arch moveRight(double n){
        return new Arch(new Coor(s.x+n,s.y),new Coor(e.x+n,e.y),new Coor(p.x+n,p.y));
    }
    @Override
    public Coor[] getCoors(){
        LinkedList<Coor> lista=new LinkedList<>();
        Coor center=getCenter();
        double m=getPendenza(),q=getYAxis(),start=Double.min(s.x,e.x),val,radius=getRadius(),delta,radS=Math.asin(Math.abs(s.y-center.y)/radius),radE=Math.asin(Math.abs(e.y-center.y)/radius),radSu,radGiu;
        radS=s.y>center.y?(s.x> center.x?radS:Math.PI-radS):(s.x> center.x?2*Math.PI-radS:Math.PI+radS);
        radE=e.y>center.y?(e.x> center.x?radE:Math.PI-radE):(e.x> center.x?2*Math.PI-radE:Math.PI+radE);
        for(int i=(int)start;i<start+(int)Math.abs(s.x-e.x);i++){
            delta=Math.sqrt(Math.pow(radius,2)-Math.pow(center.x-i,2));
            radSu=Math.asin(delta/radius);
            radGiu=Math.asin(-delta/radius);
            if(radSu>=radS&&radSu<=radE)lista.add(new Coor(i,Math.round(delta+center.y)));
            if(radGiu>=radS&&radGiu<=radE)lista.add(new Coor(i,Math.round(center.y-delta)));
        }
        return lista.toArray(new Coor[0]);
    }
    public Arch moveDownLeft(double deltaX,double deltaY){
        return new Arch(new Coor(s.x-deltaX,s.y-deltaY),new Coor(e.x-deltaX,e.y-deltaY),new Coor(p.x-deltaX,p.y-deltaY));
    }
}
