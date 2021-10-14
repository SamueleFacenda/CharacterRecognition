package pack.characterrecognition.supportClass;

public class DataClass {
    private CharacterSaves[] lista;
    public DataClass(){
        lista=new CharacterSaves[26];
    }
    public CharacterSaves get(int i){
        return lista[i];
    }
}
