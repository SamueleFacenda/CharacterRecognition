package pack.characterrecognition.supportClass;

import java.util.ArrayList;
import java.util.LinkedList;

public class CharacterSaves {
    private ArrayList<VectorialMap> lista;
    public CharacterSaves(){
        lista=new ArrayList<>();
    }
    public void add(VectorialMap in){
        if(!lista.contains(in)) lista.add(in);
    }
    public VectorialMap get(int index){
        if(index<lista.size()) return lista.get(index);
        else return null;
    }
    public double getSimilarity(VectorialMap in){
        if(lista.size()==0)
            return 0;
        else{
            double val,out=0;
            for (VectorialMap vm:
                 lista) {
                val=VectorialMap.calcSimil(vm,in);
                if(val>0.5)
                    out+=val;
            }
            return out;
        }
    }
}
