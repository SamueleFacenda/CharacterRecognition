package pack.characterrecognition.supportClass;

public class DataClass {
    protected CharacterSaves[] lista;
    public DataClass(){
        lista=new CharacterSaves[256];
        for(int i=0;i< lista.length;i++)
            lista[i]=new CharacterSaves();
    }
    public CharacterSaves get(int i){
        return lista[i];
    }
}
