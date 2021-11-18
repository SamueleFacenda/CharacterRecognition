package pack.characterrecognition.supportClass;

import java.util.Objects;

/**
 * classe che rappresenta un segmento, sapendo i vertici
 * , con molti metodi di utilità e servizi alle altre classi del package
 */
public class Segment {
    /**
     * le coordinate dei vertici dei segmenti
     */
    protected Coor s,e;//start e end

    /**
     * costruttore con coordinate divise in x e y(solo int)
     * @param xS
     * @param yS
     * @param xE
     * @param yE
     */
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

    /**
     * calcolo della lunghezza del segmento
     * @return lunghezza del segmento
     */
    public double getLen(){
        return Coor.getDist(s,e);
    }

    /**
     * calclolo della distanza di un punto dal segmento(lunghezza della proiezione sulla retta-segmento)
     * @param p punto da usare per il calcolo
     * @return la distanza del punto dal segmento
     */
    public double getDistanza(Coor p){
        return Math.abs(getPendenza()*p.x-p.y+getYAxis())/Math.sqrt(Math.pow(getPendenza(),2)+1);
    }
    public double getDistanza(int x,int y){
        return Math.abs(getPendenza()*x-y+getYAxis())/Math.sqrt(Math.pow(getPendenza(),2)+1);
    }

    /**
     * calcolo della pendenza della retta che attrtaversa il segmento
     * @return m
     */
    public double getPendenza(){
        return Segment.getPendenzaDuePunti(s,e);
    }

    /**
     * calcolo del punto di intersezione con l'asse y della retta che attraversa il segmento
     * @return q
     */
    public double getYAxis(){
        return s.y-(getPendenza()*s.x);
    }

    /**
     * calcolo del punto medio del segmento
     * @return punto medio del segmento
     */
    public Coor getPMedio(){
        return Coor.getPMedio(s,e);
    }

    /**
     * metodo di utilità statico che calcola la pendenza di due coordinate
     * @param uno
     * @param due
     * @return
     */
    public static double getPendenzaDuePunti(Coor uno,Coor due ){
        if(DoubleUtils.areEquals(uno.x,due.x))
            return Double.MAX_VALUE;
        else
            return (uno.y-due.y)/(uno.x-due.x);
    }

    /**
     * restituisce true se il punto inserito è sopra la retta che attraversa il segmento
     * @param p punto da confrontare
     * @return se è sopra(y>)
     */
    public boolean isUpper(Coor p){
        return getPendenza()*p.x-p.y+getYAxis()>0;
    }

    /**
     * restituisce un array di coordinate com x intere per il disegno del segmento
     * , controlla che non sia verticale o orizzontale
     * @return coordinate x del segmento
     */
    public Coor[] getCoorsX(){
        double m=getPendenza(),q=getYAxis(),start=Double.min(s.x,e.x);
        if(DoubleUtils.areEquals(e.x,s.x))
            return new Coor[]{s.getCopy(),e.getCopy()};
        else if(DoubleUtils.areEquals(e.y,s.y)){
            Coor[] out = new Coor[(int) Math.abs(s.x - e.x)];
            for (int i = (int) start; i < start + out.length; i++)
                out[(int) (i - start)] = new Coor(i, s.y);
            return out;
        }else{
            Coor[] out = new Coor[(int) Math.abs(s.x - e.x)];
            //applico l'equazoine della retta
            for (int i = (int) start; i < start + out.length; i++)
                out[(int) (i - start)] = new Coor(i, Math.round(i * m + q));
            return out;
        }
    }
    /**
     * restituisce un array di coordinate com y intere per il disegno del segmento
     * , controlla che non sia orizzontale o verticale
     * @return coordinate y del segmento
     */
    public Coor[] getCoorsY(){
        double m=getPendenza(),q=getYAxis(),start=Double.min(s.y,e.y);
        if(DoubleUtils.areEquals(e.y,s.y))
            return new Coor[]{s.getCopy(),e.getCopy()};
        else if(DoubleUtils.areEquals(e.x,s.x)){
            Coor[] out = new Coor[(int) Math.abs(s.y - e.y)];
            for (int i = (int) start; i < start + out.length; i++)
                out[(int) (i - start)] = new Coor(s.x,i);
            return out;
        }else{
            Coor[] out = new Coor[(int) Math.abs(s.y - e.y)];
            //applico l'equazoine della retta inversa
            for (int i = (int) start; i < start + out.length; i++)
                out[(int) (i - start)] = new Coor(Math.round((i - q) / m), i);
            return out;
        }
    }

    /**
     * restituisce un segmento spostato in basso a sinistra della distanza inserita da parametro
     * @param deltaX spostamento verso sinistra
     * @param deltaY spostamento verso destra
     * @return segmento spostato
     */
    public Segment moveDownLeft(double deltaX, double deltaY){
        return new Segment(new Coor(s.x-deltaX,s.y-deltaY),new Coor(e.x-deltaX,e.y-deltaY));
    }
    public Segment(){}
    public Segment getCopy(){
        return new Segment(s,e);
    }

    /**
     * se i due segmenti hanno un vertice in comune restituisce true se la differenza della loro pendenza
     * è minore di quella inserita da parametro(radianti), quindi se sono contigui con tolleranza angolare radMax
     * , funziona solo se hanno un vertice in comune
     * @param uno segmento uno
     * @param due segmento due
     * @param radMax tolleranza massima per la contiguità(in radianti)
     * @return se sono contigui nei termini della tolleranza
     */
    public static boolean areSemiContigous(Segment uno, Segment due, double radMax){
        //cerco il vertice in comune e quelli non
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
        //calcolo del'angolo in radianti dei segmenti
        double radP1=Math.acos((center.x-p1.x)/Coor.getDist(center,p1)),radP2=Math.acos((center.x-p2.x)/Coor.getDist(center,p2));
        if(p1.y< center.y)
            radP1=Math.PI*2-radP1;
        if(p2.y<center.y)
            radP2=Math.PI*2-radP2;
        //se la differenza è minore di quella angolare massima
        return Math.abs(radP1-Math.PI-radP2)<=radMax || Math.abs(radP1+Math.PI-radP2)<=radMax;
    }

    /**
     * ritorna la coordinata della proiezione del punto sulla retta che attravers ail semgneto
     * @param in punto da calcolare
     * @return proezione del punto sulla retta
     */
    public Coor getNearestPointOnThis(Coor in){
        if(DoubleUtils.areEquals(e.y,s.y))
            return new Coor(in.x,s.y);
        else if(DoubleUtils.areEquals(e.x,s.x)){
            return new Coor(s.x,in.y);
        }else{
            double pendenza=getPendenza(),pendenzaStorta=pendenza==0?Double.MAX_VALUE:-1.0/pendenza;
            double yPointStorto=in.y-(pendenzaStorta*in.x),yPoint=getYAxis();
            double x=(yPointStorto-yPoint)/(pendenza-pendenzaStorta);
            return new Coor(x,pendenza*x+yPoint);
        }
    }
    public void scale(double fract){
        s.scale(fract);
        e.scale(fract);
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
    public static boolean areSimilar(Segment uno,Segment due,double minValue){
        if(Coor.areNear(uno.s,due.s,minValue))
            return Coor.areNear(uno.e,due.e,minValue);
        else
            return Coor.areNear(uno.e,due.s,minValue)&&Coor.areNear(uno.s,due.e,minValue);
    }
    public double getRad(){
        return Coor.calcRad(s,e);
    }
}
