package pack.characterrecognition.supportClass;

import java.util.LinkedList;
import java.util.Objects;

/**
 * class eestensione di segment, aggiunge un terzo punto per la creazione di un arco
 * @author Samuele Facenda
 */
public class Arch extends Segment {
    protected Coor p;//terzo punto dell'arco oltre all'inizio e alla fine
    /**
     * dynamic programming variabile
     */
    private double radE,radS;
    public Arch(int xS, int yS, int xE, int yE,int pX,int pY) {
        super(xS, yS, xE, yE);
        p=new Coor(pX,pY);
        calcRad();
    }

    /**
     * quando viene chiamato il costruttore calcolo i radianti dei punti e li salvo in modo che siano contigui
     * in senso antiorario(senso dei radianti), con prima s(start), poi p(terzo punto) e poi e(end)
     * @param s
     * @param e
     * @param p
     */
    public Arch(Coor s,Coor e,Coor p){
        super(s,e);
        this.p=p.getCopy();
        Coor center=getCenter();
        double raggio=getRadius(),sDeltaX= s.x-center.x,radS= s.y> center.y?Math.acos(sDeltaX/raggio):Math.PI*2-Math.acos(sDeltaX/raggio);
        double eDeltaX= e.x-center.x,radE= e.y> center.y?Math.acos(eDeltaX/raggio):Math.PI*2-Math.acos(eDeltaX/raggio);
        double pDeltaX= p.x-center.x,radP= p.y> center.y?Math.acos(pDeltaX/raggio):Math.PI*2-Math.acos(pDeltaX/raggio);
        //controllo e correggo i radianti in modo che siano con radianti continui, con il terzo punto in mezzo
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
            Coor swap=s;
            this.s=e.getCopy();
            this.e=swap.getCopy();
            this.radE=radS;
            this.radS=radE;
        }else{
            this.s=s.getCopy();
            this.e=e.getCopy();
            this.radE=radE;
            this.radS=radS;
        }
    }

    public Arch() {
        super();
    }

    /**
     * calcolo del centro dell'arco, intersezione degli assi dei segmenti che collegano i vertici al terzo punto
     * @return centro
     */
    public Coor getCenter(){
        Coor m1=Coor.getPMedio(s,p),m2=Coor.getPMedio(p,e);
        double p1=-1.0/ Segment.getPendenzaDuePunti(s,p),p2=-1.0/ Segment.getPendenzaDuePunti(p,e);
        double yA1=m1.y-(p1*m1.x),yA2= m2.y-(p2*m2.x);
        double x=(yA2-yA1)/(p1-p2);
        return new Coor(x,p1*x+yA1);
    }

    /**
     * raggio dell'arco, distanz adi uno dei tre punti dal centro
     * @return
     */
    public double getRadius(){
        return Coor.getDist(s,getCenter());
    }

    /**
     * calcolo del punto dell'arco più lontano dalla retta che attraversa il segmento
     * @return
     */
    public Coor getFurtherPoint(){
        Coor center=getCenter();
        double raggio=getRadius(),medRadFrat=(radS+radE)/2.0/Math.PI,outX,outY;
        //calcolo radianti a metà tra l'inizio e la fine
        if((medRadFrat>0&&medRadFrat<1)||(medRadFrat>2&&medRadFrat<3)||(medRadFrat>-4&&medRadFrat<-2))
            outY= center.y+ raggio*Math.sin((radS+radE)/2.0);
        else
            outY= center.y-raggio*Math.sin((radS+radE)/2.0);
        if((medRadFrat>1&&medRadFrat<3)||(medRadFrat>5&&medRadFrat<7)||(medRadFrat>-3&&medRadFrat<-1))
            outX= center.x-raggio*Math.cos((radS+radE)/2.0);
        else
            outX= center.x+raggio*Math.cos((radS+radE)/2.0);
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
    public Coor[] getCoorsX(){
        LinkedList<Coor> lista=new LinkedList<>();
        Coor center=getCenter();
        double raggio=getRadius(),start= center.x-raggio, deltaY,radSu;
        for(int i=(int)start;i<start+raggio*2;i++){
            deltaY=Math.sqrt(Math.pow(raggio,2)-Math.pow(i-center.x,2));
            radSu=Math.asin(deltaY/raggio);
            if(i<start+raggio)
                radSu=Math.PI-radSu;
            if(isRadInThisPossible(radSu))lista.add(new Coor(i,Math.round(deltaY+center.y)));
            if(isRadInThisPossible(-radSu))lista.add(new Coor(i,Math.round(center.y-deltaY)));
        }
        return lista.toArray(new Coor[0]);
    }
    @Override
    public Coor[] getCoorsY(){
        LinkedList<Coor> lista=new LinkedList<>();
        Coor center=getCenter();
        double raggio=getRadius(),start= center.y-raggio, deltaX,radLato,radLato2;
        for(int i=(int)start;i<start+raggio*2;i++){
            deltaX=Math.sqrt(Math.pow(raggio,2)-Math.pow(i-center.y,2));
            radLato=Math.acos(deltaX/raggio);
            if(i<start+raggio){
                radLato=Math.PI*1.5+radLato;
                radLato2=Math.PI*1.5-radLato;
            }else
                radLato2=Math.PI-radLato;
            if(isRadInThisPossible(radLato))lista.add(new Coor(Math.round(deltaX+center.x),i));
            if(isRadInThisPossible(radLato2))lista.add(new Coor(Math.round(center.x-deltaX),i));
        }
        return lista.toArray(new Coor[0]);
    }
    private boolean isRadInThisPossible(double radSu){
        return (radS-Math.PI*2<=radSu&&radSu<=radE-Math.PI*2)||(radS<=radSu&&radSu<=radE)|(radS+Math.PI*2<=radSu&&radSu<=radE+Math.PI*2);
    }
    @Override
    public Arch moveDownLeft(double deltaX,double deltaY){
        return new Arch(new Coor(s.x-deltaX,s.y-deltaY),new Coor(e.x-deltaX,e.y-deltaY),new Coor(p.x-deltaX,p.y-deltaY));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Arch arch = (Arch) o;
        return Objects.equals(p, arch.p);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), p, radE, radS);
    }

    @Override
    public Arch getCopy(){
        Arch out=new Arch();
        out.s=s.getCopy();
        out.e=e.getCopy();
        out.p=p.getCopy();
        out.radS=radS;
        out.radE=radE;
        return out;
    }

    /**
     * ricalcolo dei radianti dei punti comenel costruttore principale, usato se modifico i vertici
     */
    private void calcRad(){
        Coor center=getCenter();
        double raggio=getRadius(),sDeltaX= s.x-center.x,radS= s.y> center.y?Math.acos(sDeltaX/raggio):Math.PI*2-Math.acos(sDeltaX/raggio);
        double eDeltaX= e.x-center.x,radE= e.y> center.y?Math.acos(eDeltaX/raggio):Math.PI*2-Math.acos(eDeltaX/raggio);
        double pDeltaX= p.x-center.x,radP= p.y> center.y?Math.acos(pDeltaX/raggio):Math.PI*2-Math.acos(pDeltaX/raggio);
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
            Coor swap=s;
            this.s=e.getCopy();
            this.e=swap.getCopy();
            this.radE=radS;
            this.radS=radE;
        }else{
            this.s=s.getCopy();
            this.e=e.getCopy();
            this.radE=radE;
            this.radS=radS;
        }
    }
    @Override
    public void setS(Coor in){
        s=in.getCopy();
        calcRad();
    }
    @Override
    public void setE(Coor in){
        e=in.getCopy();
        calcRad();
    }
    @Override
    public void scale(double fract){
        s.scale(fract);
        e.scale(fract);
        p.scale(fract);
    }
    @Override
    public String toString(){
        return "Arco: \n"+"start: "+s+"\nend: "+e+"\n3rd point: "+p;
    }
    public void setP(Coor in){
        p=in.getCopy();
        calcRad();
    }
}
