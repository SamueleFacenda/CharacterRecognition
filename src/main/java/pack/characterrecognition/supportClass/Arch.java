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
        Coor center=getCenter();
        double raggio=getRadius(),sDeltaX= center.x-s.x,radS= s.y> center.y?Math.acos(sDeltaX/raggio):Math.PI*2-Math.acos(sDeltaX/raggio);
        double eDeltaX= center.x-e.x,radE= e.y> center.y?Math.acos(eDeltaX/raggio):Math.PI*2-Math.acos(eDeltaX/raggio);
        double pDeltaX= center.x-p.x,radP= p.y> center.y?Math.acos(pDeltaX/raggio):Math.PI*2-Math.acos(pDeltaX/raggio);
        if(radE>radP&&radS>radP){
            if(radE>radS)
                radE-=2.0*Math.PI;
            else
                radS-=2.0*Math.PI;
        }else if(radE<radP&&radS<radP){
            if(radE<radS)
                radE+=2.0*Math.PI;
            else
                radS+=2.0*Math.PI;
        }
        if(radE<radS){
            double swap=radE;
            radE=radS;
            radS=swap;
        }
    }
    public Coor getCenter(){
        Coor m1=Coor.getPMedio(s,p),m2=Coor.getPMedio(p,e);
        double p1=-1.0/Vector.getPendenzaDuePunti(s,p),p2=-1.0/Vector.getPendenzaDuePunti(p,e);
        double yA1=m1.y-(p1*m1.x),yA2= m2.y-(p2*m2.x);
        double x=(yA2-yA1)/(p1-p2);
        return new Coor(x,p1*x+yA1);
    }
    public double getRadius(){
        return Coor.getDist(s,getCenter());
    }
    public Coor getFurtherPoint(){
        Coor center=getCenter();
        double radius=getRadius(),sDeltaX= center.x-s.x,radS= s.y> center.y?Math.acos(sDeltaX/radius):Math.PI*2-Math.acos(sDeltaX/radius);
        double eDeltaX= center.x-e.x,radE= e.y> center.y?Math.acos(eDeltaX/radius):Math.PI*2-Math.acos(eDeltaX/radius);
        double pDeltaX= center.x-p.x,radP= p.y> center.y?Math.acos(pDeltaX/radius):Math.PI*2-Math.acos(pDeltaX/radius);
        if(radE>radP&&radS>radP){
            if(radE>radS)
                radE-=2.0*Math.PI;
            else
                radS-=2.0*Math.PI;
        }else if(radE<radP&&radS<radP){
            if(radE<radS)
                radE+=2.0*Math.PI;
            else
                radS+=2.0*Math.PI;
        }
        System.out.println(radE/Math.PI);
        System.out.println(radS/Math.PI);
        double medRadFrat=(radS+radE)/2.0/Math.PI,outX,outY;
        if((medRadFrat>0&&medRadFrat<1)||(medRadFrat>2&&medRadFrat<3)||(medRadFrat>-4&&medRadFrat<-2))
            outY= center.y+ radius*Math.sin((radS+radE)/2.0);
        else
            outY= center.y-radius*Math.sin((radS+radE)/2.0);
        if((medRadFrat>1&&medRadFrat<3)||(medRadFrat>5&&medRadFrat<7)||(medRadFrat>-3&&medRadFrat<-1))
            outX= center.x-radius*Math.cos((radS+radE)/2.0);
        else
            outX= center.x+radius*Math.cos((radS+radE)/2.0);
        return new Coor(outX,outY);
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
        double m=getPendenza(),q=getYAxis(),start=Double.min(s.x,e.x),val,delta,radSu,radGiu;
        double radius=getRadius(),sDeltaX= center.x-s.x,radS= s.y> center.y?Math.acos(sDeltaX/radius):Math.PI*2-Math.acos(sDeltaX/radius);
        double eDeltaX= center.x-e.x,radE= e.y> center.y?Math.acos(eDeltaX/radius):Math.PI*2-Math.acos(eDeltaX/radius);
        double pDeltaX= center.x-p.x,radP= p.y> center.y?Math.acos(pDeltaX/radius):Math.PI*2-Math.acos(pDeltaX/radius);
        if(radE>radP&&radS>radP){
            if(radE>radS)
                radE-=2.0*Math.PI;
            else
                radS-=2.0*Math.PI;
        }else if(radE<radP&&radS<radP){
            if(radE<radS)
                radE+=2.0*Math.PI;
            else
                radS+=2.0*Math.PI;
        }
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
    @Override
    public String toString(){
        return "punto piú lontano: "+getFurtherPoint();
    }
}
