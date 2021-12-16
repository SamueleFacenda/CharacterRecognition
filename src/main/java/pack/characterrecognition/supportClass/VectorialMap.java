package pack.characterrecognition.supportClass;

import pack.characterrecognition.supportClass.figures.Arch;
import pack.characterrecognition.supportClass.figures.CoorD;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
/**
 * Classe mappa vettoriale, i vertici dei segmenti sono punti della mappa(GraphPoint), con salvati i segmenti che partono e
 * arrivano lí.
 * Questo perché cosí ho fatto i metodi per "abbellire" l'immagine, unisco i segmenti in linea dritte.
 * @author Samuele Facenda
 }
*/
public class VectorialMap extends VectorialImage{
    private LinkedList<GraphPoint> points;
    public VectorialMap(){
        super();
        points=new LinkedList<>();
    }
    public VectorialMap(VectorialImage in){
        super();
        points=new LinkedList<>();
        for(Arch a:in.archList)archList.add(a.getCopy());
        for(Segment a:in.segmentList) segmentList.add(a.getCopy());
    }
    /**
     * abbellisco l'immagine: genero i GraphPoints, unisco i punti vicini e unisco i segmenti contigui
    */
    public void enchant(){
        double height=0,width=0;
        //cerco il punto piú alto e piú largo
        for (Segment v:
                segmentList) {
            if(v.s.x>width)
                width=v.s.x;
            if(v.s.y>height)
                height=v.s.y;
            if(v.e.x>width)
                width=v.e.x;
            if(v.e.y>height)
                height=v.e.y;
        }
        CoorD further;
        //idem, prendo in cosiderazone anche il punto sull'arco piú lontano
        for (Arch v:
                archList) {
            if(v.s.x>width)
                width=v.s.x;
            if(v.s.y>height)
                height=v.s.y;
            if(v.e.x>width)
                width=v.e.x;
            if(v.e.y>height)
                height=v.e.y;
            further=v.getFurtherPoint();
            if(further.x>width)
                width=further.x;
            if(further.y>height)
                height=further.y;
        }
        //angleCoefficent é pari a un ottavo di giro
        double smallCoefficent=5,angleCoefficent=Math.PI*0.25;
        for (Segment v:
                segmentList)
            checkFormForPoints(v,smallCoefficent);
        for (Arch a:
                archList)
            checkFormForPoints(a,smallCoefficent);
        for (GraphPoint gp:
             points)
            checkForLinearVector(gp,angleCoefficent);
    }

    /**
     * controllo le estremitá del segmento, se sono vicine a un punto della mappa, se non lo sono ne faccio uno nuovo apposta
     * @param in segmento da controllare
     * @param smallCoefficent coefficiente di poca distanza: i punti con distanza minore vengono considerati uguali e uniti
     */
    private void checkFormForPoints(Segment in, double smallCoefficent){
        Iterator<GraphPoint> i=points.iterator();
        GraphPoint gp;
        boolean notSFind=true,notEFind=true;
        //cerca in tutti i punti finché o ho finito i punti o ho trovato un punto sia per l'inizio che per la fine
        while(i.hasNext()&&(notSFind||notEFind)){
            gp=i.next();
            //se sono vicini lo aggiunge
            if(notSFind&& CoorD.areNear(in.s,gp,smallCoefficent)){
                gp.addStart(in);
                in.setS(gp);
                notSFind=false;
            }
            if(notEFind&& CoorD.areNear(in.e,gp,smallCoefficent)){
                gp.addEnd(in);
                in.setE(gp);
                notEFind=false;
            }
        }
        //se non ha trovato un punto per l'inizio ne fa uno nuovo e aggiunge nella lista dell'inizio questo segmento
        if(notEFind){
            GraphPoint added=new GraphPoint(in.e);
            added.addEnd(in);
            points.add(added);
        }
        if(notSFind){
            GraphPoint added=new GraphPoint(in.s);
            added.addStart(in);
            points.add(added);
        }
    }

    /**
     * metodo che cerca il un graphpoint se ci sono segmenti in linea semi retta
     * @param gp punto da controllare
     * @param angleCoefficent coefficiente di scarto in radianti di angolo di pendenza dei due segmenti
     */
    public void checkForLinearVector(GraphPoint gp, double angleCoefficent) {
        //per ogni segmento che iniza da qui
        for (Segment v:
                gp.getStart()){
            //controllo tutti quelli che finiscono qui
            for (Segment second:
                    gp.getStart()){
                //se sono segemnti e non archi e sono in linea retta
                if(v!=second&& !(v instanceof Arch) && !(second instanceof Arch) && Segment.areSemiContigous(v,second,angleCoefficent)){
                    //creo il nuovo segento
                    Segment due=new Segment(v.e,second.e);
                    //muovo il veccho punto sopra a questo nuovo segemnto
                    gp.move(due.getNearestPointOnThis(gp));
                    //tolgo i segemnti vecchi dal vecchio punto
                    gp.getStart().remove(v);
                    gp.getStart().remove(second);
                    //li tolgo anche dalla lista totale di segmenti
                    segmentList.remove(v);
                    segmentList.remove(second);
                    //aggiungo quello nuovo
                    segmentList.add(due);
                    //cerco i punti in cui finivano i due segmenti, li tolgo da lí
                    for (GraphPoint punto:
                         points) {
                        if(punto.getEnd().contains(v)){
                            punto.getEnd().remove(v);
                            punto.getStart().add(due);
                        }
                        if(punto.getEnd().contains(second)){
                            punto.getEnd().remove(second);
                            punto.getEnd().add(due);
                        }
                    }
                }
            }
            //uguele per quelli che finiscono lí
            for (Segment second:
                    gp.getEnd()){
                if(v!=second&& !(v instanceof Arch) && !(second instanceof Arch) && Segment.areSemiContigous(v,second,angleCoefficent)){
                    Segment due=new Segment(v.e,second.s);
                    gp.move(due.getNearestPointOnThis(gp));
                    gp.getEnd().remove(second);
                    gp.getStart().remove(v);
                    segmentList.remove(v);
                    segmentList.remove(second);
                    segmentList.add(due);
                    for (GraphPoint punto:
                            points) {
                        if(punto.getEnd().contains(v)){
                            punto.getEnd().remove(v);
                            punto.getStart().add(due);
                        }
                        if(punto.getStart().contains(second)){
                            punto.getStart().remove(second);
                            punto.getEnd().add(due);
                        }
                    }
                }
            }
        }
        //uguale per i segmenti che finiscono lí
        for (Segment v:
                gp.getEnd()) {
            for (Segment second:
                    gp.getStart()){
                if(v!=second&& !(v instanceof Arch) && !(second instanceof Arch) && Segment.areSemiContigous(v,second,angleCoefficent)){
                    Segment due=new Segment(v.s,second.e);
                    gp.move(due.getNearestPointOnThis(gp));
                    gp.getEnd().remove(v);
                    gp.getStart().remove(second);
                    segmentList.remove(v);
                    segmentList.remove(second);
                    segmentList.add(due);
                    for (GraphPoint punto:
                            points) {
                        if(punto.getStart().contains(v)){
                            punto.getStart().remove(v);
                            punto.getStart().add(due);
                        }
                        if(punto.getEnd().contains(second)){
                            punto.getEnd().remove(second);
                            punto.getEnd().add(due);
                        }
                    }
                }
            }
            for (Segment second:
                    gp.getEnd()){
                if(v!=second&& !(v instanceof Arch) && !(second instanceof Arch) && Segment.areSemiContigous(v,second,angleCoefficent)){
                    Segment due=new Segment(v.s,second.s);
                    gp.move(due.getNearestPointOnThis(gp));
                    gp.getEnd().remove(v);
                    gp.getEnd().remove(second);
                    segmentList.remove(v);
                    segmentList.remove(second);
                    segmentList.add(due);
                    for (GraphPoint punto:
                            points) {
                        if(punto.getStart().contains(v)){
                            punto.getStart().remove(v);
                            punto.getStart().add(due);
                        }
                        if(punto.getStart().contains(second)){
                            punto.getStart().remove(second);
                            punto.getEnd().add(due);
                        }
                    }
                }
            }
        }
    }

    /**
     * scala le dimensioni dell'immagine vettoriale secondo la frazione inserita
     * @param fraction indice di scala(1 é uguale, 2 raddoppia, 0.5 dimezza...)
     */
    public void scale(double fraction){
        //scalo tutti i punti
        for (GraphPoint gp: points)
            gp.move(new CoorD(gp.x*fraction,gp.y*fraction));
    }

    /**
     * scalo in base all'altezza che voglio ottenere
     * @param in altezza che voglio
     */
    public void scalePerHeight(double in){
        scale(in/getHeight());
    }

    /**
     * calcolo la similitudine di due mappe vettoriali(indice di ritorno double)
     * @param uno prima mappa da confrontare
     * @param due seconda mappa da confrontare
     * @return indice in double di similitudine(max 1)
     */
    static public double calcSimil(VectorialMap uno,VectorialMap due){
        /*uno.scale(100.0/uno.getHeight());
        due.scale(100.0/due.getHeight());
        double minCoefficent=20;
        if(confrontGrahpGrid(generateGrid(uno.points),generateGrid(due.points)));{
            double out=1;
            Iterator<Segment> itUno=uno.segmentList.iterator(),itDue=due.segmentList.iterator();
            double diff;
            while(itUno.hasNext()){
                diff=Math.abs(itUno.next().getLen()-itDue.next().getLen());
                if(diff>minCoefficent){
                    diff-=minCoefficent;
                    diff
                }
            }
        }*/
        //return confrontGrahpGrid(generateGrid(uno.points),generateGrid(due.points))?1:0;
        double val=0,minCoefficent=5;
        Iterator<Segment> segUno=uno.segmentList.iterator(),segDue=due.segmentList.iterator();
        //controllo se tutti i segmenti sono simili(se le immagini sono simili l'ordine dovrebbe essere uguale
        while(segUno.hasNext()&&segDue.hasNext())
            if(Segment.areSimilar(segUno.next(), segDue.next(), minCoefficent)) val++;
        Iterator<Arch> arcUno=uno.archList.iterator(),arcDue=due.archList.iterator();
        //idem per gli archi
        while(arcUno.hasNext()&&arcDue.hasNext())
            if(Segment.areSimilar(segUno.next(), segDue.next(), minCoefficent)) val++;
        //proporzione con il numero totale di segemnti e arch
        return  val/(Integer.max(uno.segmentList.size(),due.segmentList.size())+Integer.max(uno.archList.size(),due.archList.size()));
    }
    private static boolean haveSameBridges(LinkedList<GraphPoint> uno,LinkedList<GraphPoint> due){
        if(uno.size()== due.size()){
            return equalsBooleanGrid(generateGrid(uno),generateGrid(due));
        }else
            return false;
    }

    private static int getBridge(boolean[] in){
        int i=0;
        for (boolean boo:
             in) {
            if(boo)
                i++;
        }
        return i;
    }
    private static boolean[][] generateGrid(LinkedList<GraphPoint> in){
        boolean[][] out=new boolean[in.size()][in.size()];
        int i=0,secondI;
        for (GraphPoint gp:
             in) {
            secondI=0;
            for (GraphPoint second:
                    in) {
                for (Segment s:
                     gp.getStart()) {
                    if (second.getEnd().contains(s)) {
                        out[i][secondI] = true;
                        break;
                    }
                }
                for (Segment s:
                        gp.getEnd()) {
                    if (second.getStart().contains(s)) {
                        out[i][secondI] = true;
                        break;
                    }
                }
                secondI++;
            }
            i++;
        }
        return out;
    }
    private static boolean equalsBooleanGrid(boolean[][] uno,boolean[][] due){
        if(uno.length==0 &&due.length==0)
            return true;
        else if(uno.length== due.length&&uno[0].length==due[0].length){
            int row=0,col;
            do{
                col=0;
                while (col<uno[0].length && uno[row][col]==due[row][col])
                    col++;
                row++;
            }while(row<uno.length && col==uno[0].length);
            return col==uno[0].length;
        }else
            return false;
    }
    public GraphPoint getHigher(){
        if(points.size()==0)
            return null;
        else{
            GraphPoint max=points.getFirst();
            for(GraphPoint gp:points) if(gp.y>max.y) max=gp;
            return max;
        }
    }
    public GraphPoint getLefter(){
        if(points.size()==0)
            return null;
        else{
            GraphPoint max=points.getFirst();
            for(GraphPoint gp:points) if(gp.x<max.x) max=gp;
            return max;
        }
    }
    public GraphPoint getDowner(){
        if(points.size()==0)
            return null;
        else{
            GraphPoint max=points.getFirst();
            for(GraphPoint gp:points) if(gp.y<max.y) max=gp;
            return max;
        }
    }
    public GraphPoint getRighter(){
        if(points.size()==0)
            return null;
        else{
            GraphPoint max=points.getFirst();
            for(GraphPoint gp:points) if(gp.x>max.x) max=gp;
            return max;
        }
    }
    public double getHeight(){
        return getHigher().y-getDowner().y;
    }
    public double getWidth(){
        return getRighter().x-getLefter().x;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        VectorialMap that = (VectorialMap) o;
        return Objects.equals(points, that.points);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), points);
    }
    private void removeSegment(Segment in){
        segmentList.remove(in);
        for (GraphPoint gp :
                points) {
            gp.getStart().remove(in);
            gp.getEnd().remove(in);
        }
    }
}
