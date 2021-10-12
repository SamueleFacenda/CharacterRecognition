package pack.characterrecognition.geometry;

public class Arch extends Vector{
    protected Coor p;//terzo punto dell'arco oltre all'inizio e alla fine
    public Arch(int xS, int yS, int xE, int yE,int pX,int pY) {
        super(xS, yS, xE, yE);
        p=new Coor(pX,pY);
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
    public Coor getFurtherPoit(){
        Coor center=getCenter();
        double radius=getRadius(),radS=Math.asin(Math.abs(s.y-center.y)/radius),radE=Math.asin(Math.abs(e.y-center.y)/radius);
        radS=s.y>center.y?(s.x> center.x?radS:Math.PI-radS):(s.x> center.x?2*Math.PI-radS:Math.PI+radS);
        radE=e.y>center.y?(e.x> center.x?radE:Math.PI-radE):(e.x> center.x?2*Math.PI-radE:Math.PI+radE);
        double medRad=(radS+radE)/2;
        return new Coor(radius*Math.cos(medRad),radius*Math.sin(medRad));
    }
    public double getWidth(){
        return getDistanza(getFurtherPoit());
    }
}
