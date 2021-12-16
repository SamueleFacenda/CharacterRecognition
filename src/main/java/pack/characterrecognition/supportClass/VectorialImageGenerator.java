package pack.characterrecognition.supportClass;

import pack.characterrecognition.supportClass.figures.CoorD;
import pack.characterrecognition.supportClass.other.DoubleUtils;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * classe per la generazione di immagini vettoriali da blob(forme in bianco e nero convertite).
 * usa un approccio ricorsivo, il blob viene prima convertito in griglia booelana, per poter accedere
 * velocemente alle celle, ci sono inoltre tre variabili in cui sono salvati valori utili.
 * Questa classe é il cuore del mio riconoscimento caratteri, ogni riga di codice scritta qui è frutto
 * della mia sola mente, senza aiuti o ispirazione internet.
 * @author Samuele Facenda
 */

public class VectorialImageGenerator extends VectorialImage{
    /**
     * rad360 è il valore in radianti di un giro completo(360º), viene usato molte volte.
     * deltaRad è il valore metà dell'angolo che viene considerato "dietro" un segmento, viene calcolato come arctg di 2/1.1, in un quadrato
     * 5*5 dal centro si riferisce all'area di lato tre perpendicolare al centro.
     * diagonalHop é la lunghezza della diagonale della griglia divisa per due, saltando due celle in diagonale é la distanza lineare massima percorribile.
     * archRadCoefficnt é il valore per cui due segmenti di angolo esterno minore di questo possono essere detti pezzo di un arco:
     * è un po' meno di 90 gradi.
    */
    private final double rad360,deltaRad,diagonalHop,archRadCoefficent;
    private boolean[][] grid;
    private LinkedList<Segment> toRemove;
    public VectorialImageGenerator(Blob in){
        segmentList=new LinkedList<>();
        deltaRad=Math.atan(1.51/2.5);
        archRadCoefficent=Math.toRadians(80);
        rad360=Math.PI*2;
        grid=in.toBooleanGrid();
        diagonalHop=Math.sqrt(Math.pow((double)grid.length/2+2, 2)+Math.pow((double)grid[0].length/2+2,2 ));
        int i=0;
        while(!grid[(i/ grid[0].length)][i%grid[0].length]) i++;
        CoorD start=new CoorD((i/ grid[0].length),i%grid[0].length);
        generateBlobSegmentAtStart(start);
        toRemove=new LinkedList<>();
        generateArchs();
        for (Segment currentRemove:
             toRemove) {
            if(!segmentList.remove(currentRemove)){
                Iterator<Segment> checkSimilar=segmentList.iterator();
                Segment current;
                while(checkSimilar.hasNext()){
                    current=checkSimilar.next();
                    if(Segment.areSimilar(current,currentRemove,0.05*grid.length))
                        segmentList.remove(current);
                }
            }
        }
    }
    private void generateArchs(){
        
    }

    /**
     * metodo ricorsivo che ritorna una lista di segmenti ad arco da una cella in una certa direzione.
     * Cerca tutti i segmenti nella direzione dell'arco, poi richiama sé stesso dalla fine di quei segmenti,
     * ritorna la lista più lunga con l'aggiunta del segmento da  cui ha generato la nuova lista.
     * Se la lista non è abbastanza lunga la genera come arco a parte.
     * @param start cella da cui cercare segmenti
     * @param radDirection angolo in radianti dell'arco
     * @param orarSense se il segmento deve essere in senso antiorario o orario(radainti maggoiri o minori)
     * @return lista di segmenti ad arco a partire dalla cella
     */
    private LinkedList<Segment> findCurveSegment(CoorD start, double radDirection, boolean orarSense){
        //currentList è un buffer per le liste generate da ogni segmento
        LinkedList<Segment> out=null,currentList = null;
        //se il segmento è simile ma al contrario
        boolean isStorto;
        //controlla per ogni segmento
        for (Segment current:
             segmentList) {
            isStorto= CoorD.areNear(start,current.e,0.05*grid.length);
            //se è simile ma storto o se è simile
            if(isStorto || CoorD.areNear(start,current.s,0.05*grid.length)){
                //se è storto lo gira
                if(isStorto)
                    current=new Segment(current.e,current.s);
                //controlla che sia adatto a far parte dell'arco
                if(checkSegmentForContigousCurve(current,radDirection,orarSense)) {
                    //genera la nuova linkedlist a partire dalla fine del segmento
                    currentList = findCurveSegment(current.e, current.getRad(), orarSense);
                    //se la lista generata è più lunga di quella attualmente più lunga(o se questa è ancora vuota), la imposta
                    if(currentList!=null && (out==null || currentList.size() > out.size()-1)){//il -1 serve perchè currentList non contiene il segmento da cui ha iniziato a generare
                        out=currentList;
                        currentList.addFirst(current);
                    //se quella appena generata è null: se anche l'altra è null la setta come lista con solo il segmento corrente, se no controlla che sia lunga solo uno
                    //e che il segmento corrente sia più lungo di quello contenuto nella lista out
                    }else if(currentList == null && (out==null || (out.size()==1 && out.getFirst().getLen()<current.getLen()))){
                        currentList=new LinkedList<>();
                        currentList.addFirst(current);
                        out=currentList;
                    }
                    //controllo se currentList è stata scartata perché è troppo corta, se è così non la elimina ma la genera come arco a parte
                    if(currentList != null && out!=currentList){
                        currentList.addFirst(current);
                        creatArch(currentList);
                    }
                }
            }
        }
        return out;
    }

    /**
     * metodo che da lista di segmenti genera un arco, quindi se è lungo abbastanza sostituisce tutti
     * i segmenti con un solo arco, li rimuove dalla segmentList(li aggiunge alla lista di segmenti da rimuovere
     * , cosicché possano essere ancora usati durante la generazione di nuovi archi.
     * @param lista
     */
    private void creatArch(LinkedList<Segment> lista){

    }

    /**
     * metodo per il controllo della contiguità angolare di un semgento con una direzone, in un senso
     * @param check segmento da controllare
     * @param radDirection direzoine di partenza
     * @param isOrarSense senso(sinista o destra) del controllo
     * @return
     */
    private boolean checkSegmentForContigousCurve(Segment check, double radDirection,boolean isOrarSense){
        double radCheck= check.getRad();
        if(isOrarSense){
            /*
            se il senso è antiorario perchè possa essere ammissibile l'angolo del segmento deve sempre essere minore dell'angolo
            di controllo, quindi se questo è lo giro di 360° in senso orario(-2PI), poi calcolo la differenza(raDirection adesso è
            sempre maggiore), poi ritorna se la differenza è minore del coefficiente che detenrmina l'angolo ammissibile
             */
            if(radCheck>radDirection)
                radCheck-=rad360;
            radCheck=radDirection-radCheck;
            return radCheck<archRadCoefficent;
        }else{
            //l'opposto del senso orario
            if(radCheck<radDirection)
                radCheck+=rad360;
            radCheck-=radDirection;
            return radCheck<archRadCoefficent;
        }
    }

    /**
     * launcher del metodo ricorsivo per generare segmenti in tutte le direzioni
     * @param s la cella da cui partire
     */
    private void generateBlobSegmentAtStart(CoorD s){
        Iterator<Segment> iter;
        BlobSegment blobSegBuffer;
        //in ogni direzione
        for (CoorD c :
                getAroundDirection(s, 0,true)) {
            //il metodo getArodundDirecton resitituisce null se la cella è fuori dalla griglia o non è nera
            if(c!=null){
                //genera dei segmenti a partire dalla cella s nella direzone corrente
                addAfterCheck(generateSegmentRecursive(new BlobSegment(s,c),diagonalHop).toSegment());
            }
        }
    }

    /**
     * metodo ricorsivo per generare segmenti in una direzione
     * @param bs segmento da cui generare i continui
     * @param ttl time to live, all'inizio è il massimo numero di richiami lineari che possono essere fatti per generare
     *            un segmento, cioè la distanza della diagonale della griglia divisa 2, viene diminuito di uno ogni volta che
     *            viene richiamata questa funzione in modo recorsivo, per evitare un loop infinito o molto lungo di chiamate
     *            ricorsive
     * @return
     */
    private BlobSegment generateSegmentRecursive(BlobSegment bs,double ttl){
        BlobSegment out=bs;
        //controlla se ha superato il limite di hop massimi(distanza da vertice a vertice divisa due nella griglia)
        if(ttl>=0) {
            //calcola la pendenza attuale del segmento
            double radAng= CoorD.calcRad(bs.s,bs.e);
            //creo la lista in cui inseriere i segmenti generati da questo segmento nelle varie direzioni
            LinkedList<BlobSegment> currentList = new LinkedList<>();
            BlobSegment blobSegBuffer;
            //controlla intorno per tutte le coordinate valide
            for (CoorD c :
                 getAroundDirection(bs.e, radAng,false)) {
                //crea un nuovo BlobSegment copia e sposta la fine alla Coor corrente
                blobSegBuffer = bs.getCopy();
                blobSegBuffer.moveEnd(c);
                //se il segmento è più spesso di tre(due hop in diagonale)identifica una curva e lancia un generatore in quella direzione
                if (blobSegBuffer.getMaxDist() > 3)
                    addAfterCheck(generateSegmentRecursive(new BlobSegment(bs.e,c),diagonalHop).toSegment());
                else //aggiunge alla lista corrente il segmento generato da quella cella, diminuisce di uno il ttl per questa generazione
                    currentList.add(generateSegmentRecursive(blobSegBuffer, ttl - 1));
            }
            //cerco il segmento più lungo, se
            if (currentList.size() != 0) {
                Iterator<BlobSegment> iterator = currentList.iterator();
                out = iterator.next();
                while (iterator.hasNext()) {
                    blobSegBuffer = iterator.next();
                    if (blobSegBuffer.getLen() > out.getLen())
                        out = blobSegBuffer;
                }
                //ritorna quello di partenza se è più lungo
                if(out.getLen()<bs.getLen())
                    out=bs;
            }
        }
        return out;
    }

    /**
     * metodo che ritorna le coordinate delle celle a distanza di due da quella inserita,
     * se queste non sono esterne alla griglia e fanno parte della figura.
     * Controlla inoltre la direzione delle celle da restituire in riferimento a quella inserita da parametro,
     * se la cella da inserire è "indietro" rispetto alla direzione, questa non verrà inserita, ecco un esempio:
     * radAng=PI/2(verticale in su)
     * ok | ok | ok | ok | ok
     * ----------------------
     * ok |    |    |    | ok
     * ----------------------
     * ok |    | () |    | ok
     * ----------------------
     * ok |    |    |    | ok
     * ----------------------
     * ok | no | no | no | ok
     * @param center cella centrale da cui prendere quelle intorno
     * @param radAng pendenza in radianti della direzione da prendere
     * @param isAllOk bypassa il check dei radianti, così vengono ritornate le coordinate a 360º
     * @return le coordinate accettabili e presenti nella griglia intorno alla cella inserita da parametro
     */
    private LinkedList<CoorD> getAroundDirection(CoorD center, double radAng, boolean isAllOk){
        //delta rad è il valore di metà zona non accettabile, prendo le coordinate opposte(mezzo giro dopo, +PI), aggiungendo e togliendo deltaRad
        double startNoZone=radAng+Math.PI-deltaRad,endNoZone=radAng+Math.PI+deltaRad;
        LinkedList<CoorD> out=new LinkedList<>();
        CoorD check;
        for (double i = center.x-2; i < center.x+3; i++){
            check=new CoorD(i,center.y+2);
            if(checkCoorInDirecton(center,check,startNoZone,endNoZone,isAllOk))
                out.add(check);
            check=new CoorD(i,center.y-2);
            if(checkCoorInDirecton(center,check,startNoZone,endNoZone,isAllOk))
                out.add(check);
        }
        for (double i = center.y-1; i < center.y+2; i++){
            check=new CoorD(center.x-2,i);
            if(checkCoorInDirecton(center,check,startNoZone,endNoZone,isAllOk))
                out.add(check);
            check=new CoorD(center.x+2,i);
            if(checkCoorInDirecton(center,check,startNoZone,endNoZone,isAllOk))
                out.add(check);
        }
        return out;
    }

    /**
     * in base a quello inserito da parametro,
     * controlla che non siano già presenti segmenti simili nella lista di segmenti, se non è così lo aggiunge
     * @param blobSegBuffer
     */
    private void addAfterCheck(Segment blobSegBuffer){
        Iterator<Segment> iter=segmentList.iterator();
        while(iter.hasNext() && !Segment.areSimilar(iter.next(), blobSegBuffer,0.05*grid.length));
        if(!iter.hasNext())
            segmentList.add(blobSegBuffer);
    }

    /**
     * controlla la coordinata in base alle richieste del metodo getAroundDirection, quindi che la coordinata sia compresa nella griglia
     * e che non sia compresa nella sua pendenza tra i radianti che limitano la zona in cui non accettare coordinate
     * @param center cella centrale del cerchio, per il calcolo della pendenza delle cella
     * @param check cella da controllare
     * @param startNoZone radianti di inizio della zona di non accettabilità
     * @param endNoZone radianti di fine della zona di non accettabilità
     * @param isAllOk booleano per il bypass del controllo sui radianti
     * @return se la cella soddisfa tutti i criteri richiesti
     */
    private boolean checkCoorInDirecton(CoorD center, CoorD check, double startNoZone, double endNoZone, boolean isAllOk){
        double radCheck= CoorD.calcRad(center,check);
        //controllo che sia compreso nella griglia e la cella si piena(pixel nero)
        boolean out=check.x>=0 && check.x<grid[0].length && check.y>=0 && check.y< grid.length && grid[DoubleUtils.toInt(check.y)][DoubleUtils.toInt(check.x)];
        return out && (isAllOk || !isGhegenuber(radCheck,startNoZone,endNoZone));
    }

    /**
     * metodo per il controllo dei radianti, ritorna true se il valore in radianti radCheck é compreso tra start e end,
     * controlla che non sia compresa invece tra i radianti ma "un giro dopo", in radianti
     * @param radCheck radiante da controllare
     * @param startZone inizio della zona da controllare
     * @param endZone fine della zona da controllare
     * @return se l`angolo inserito è compreso tra gli altri due
     */
    private boolean isGhegenuber(double radCheck,double startZone,double endZone){
        //se è minore di tutti e due faccoi il controllo "un giro dopo"
        if(radCheck<startZone && radCheck < endZone)
            radCheck+=rad360;
        return radCheck > startZone && radCheck < endZone;
    }
}
