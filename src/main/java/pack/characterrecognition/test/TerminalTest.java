package pack.characterrecognition.test;

import pack.characterrecognition.supportClass.Blob;
import pack.characterrecognition.supportClass.CoorD;
import pack.characterrecognition.supportClass.VectorialImageGenerator;
import pack.characterrecognition.supportClass.VectorialMap;

public class TerminalTest {
    public static void main(String[] args) {
        Blob b=new Blob();
        b.add(new CoorD(0,0));
        b.add(new CoorD(0,2));
        b.add(new CoorD(2,3));
        b.add(new CoorD(3,5));
        b.add(new CoorD(4,7));
        b.add(new CoorD(4,9));
        b.add(new CoorD(5,5));
        b.add(new CoorD(6,10));
        b.add(new CoorD(7,6));
        b.add(new CoorD(8,4));
        b.add(new CoorD(9,2));
        b.add(new CoorD(10,0));

        VectorialImageGenerator v=new VectorialImageGenerator(b);
        VectorialMap vi=new VectorialMap(v);
        vi.enchant();
        vi.scale(10);
        System.out.println(vi.ascii());
    }
}
