package pack.characterrecognition.supportClass;

import java.util.LinkedList;

public class CharacterSaves {
    private    LinkedList<VectorialMap> lista;
    public CharacterSaves(){
        lista=new LinkedList<>();
    }
    public void add(VectorialMap in){
        if(!lista.contains(in)) lista.add(in);
    }
    public VectorialMap get(int index){
        if(index<lista.size()) return lista.get(index);
        else return null;
    }
}
