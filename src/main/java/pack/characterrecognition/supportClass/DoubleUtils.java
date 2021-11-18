package pack.characterrecognition.supportClass;

/**
 * classe di utilità per ovviare al problema dei double sui confronti, il
 * fatto che essendo a floating point può succedere che siano un nanesimo(default e-8) più alti o più
 * bassi del valore originale, causando problemi nei confronti i nella conversione a int,
 * perché nel secondo caso ad esempio se il valore si è ridotto anche solo di pochissimo con un cast
 * si avrà il valore diminuito di uno, senza possibilità di controllo. Per fare queste operazioni correte si
 * perde però precisione, con questa classe è possibile impostare la precisione di scarto sotto cui i valori verranno
 * visti come double alterati errati.
 * @author Samuele Facenda
 */
public class DoubleUtils {
    private static double precision=0.00000001;

    /**
     * restituisce true se i due double sono uguali, entro uno scarto dato dalla
     * precisione, quindi anche se sono andati incontro a una variazione da virgola mobile
     * @param uno primo valore
     * @param due secondo valore
     * @return se sono uguali(+-precisione)
     */
    public static boolean areEquals(double uno,double due){
        return Math.abs(uno-due)<precision;
    }

    /**
     * set della precisione, il valore inserito é il numero di zeri dopo la virgola da usare
     * @param in precisione= e-valore, notazione scientifica
     */
    public static void setENegativePrecision(int in){
        precision=Math.pow(10,-in);
    }

    /**
     * converte il valore in int, se per uno scarto di precisione è minore dell`int successivo,
     * ritornerà quello
     * @param in il valore da convertire
     * @return il valore convertito in int
     */
    public static int toInt(double in){
        return in - (int) in > 1.0 - precision ? (int) in + 1 : (int) in;
    }
}
