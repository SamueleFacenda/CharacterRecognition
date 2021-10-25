package pack.characterrecognition.supportClass;

public class DataClass {
    protected CharacterSaves[] lista;
    public DataClass(){
        lista=new CharacterSaves[26];
        for(int i=0;i< lista.length;i++)
            lista[i]=new CharacterSaves((char)((int)'a'+1));
    }
    public CharacterSaves get(int i){
        return lista[i];
    }
}
