package pack.characterrecognition.supportClass;

public class DoubleCompare {
    private static double precision=0.000000001;
    public static boolean areEquals(double uno,double due){
        return Math.abs(uno-due)<precision;
    }
}
