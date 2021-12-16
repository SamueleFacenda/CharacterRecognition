package pack.characterrecognition.supportClass.figures;


import pack.characterrecognition.supportClass.figures.CoorD;

/**
 * classe che indica le coordinate di un punto 2d
 * , con alcuni metodi di utilità
 * @author Samuele Facenda
 */
interface Coor {

    public double getX();

    public double getY() ;

    public void setX(double x);

    public void setY(double y);

    /**
     * calcolo della distanza di due punti
     * @param uno primo punto
     * @param due secondo punto
     * @return la distanza
     */
    public static double getDist(CoorD uno, CoorD due){
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
    public CoorD getCopy();

    @Override
    public boolean equals(Object o) ;

    @Override
    public int hashCode();
    @Override
    public String toString();

    /**
     * restituisce true se la coordinata inserita è compresa in un
     * cerchio di raggio radius da questa coordinata
     * @param in punto da controllare
     * @param radius distanza massima per dire che sono simili
     * @return se sono simili in base al raggio inserito
     */
    public boolean areSimilar(CoorD in, double radius);

    /**
     * metodo statico che controlla se la distanza di due punti è minore di quella
     * inserita da parametro, se sono vicine
     * @param uno primo punto
     * @param due secondo punto
     * @param radius distanza massima per dire che sono vicine
     * @return se sono vicine
     */
    public static boolean areNear(CoorD uno, CoorD due, double radius){
        return getDist(uno,due)<=radius;
    }
    public static double calcRad(CoorD s, CoorD e){
        if(s.equals(e))
            return Math.PI*4;
        else if(s.y>e.y)
            return Math.acos((s.x-e.x)/getDist(s,e));
        else
            return Math.PI*2-Math.acos((s.x-e.x)/getDist(s,e));
    }
    public void scale(double fract);
}
