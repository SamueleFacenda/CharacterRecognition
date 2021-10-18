package pack.characterrecognition.supportClass;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public class DataAccess {
    public static void add(char in,VectorialMap mappa) throws IOException {
        DataClass dc =FileSaverCharacter.read();
        if(dc==null)
            dc=new DataClass();
        CharacterSaves cs=dc.get((int)in-(int)'a');
        if(cs==null)
            cs=new CharacterSaves(in);
        cs.add(mappa);
        FileSaverCharacter.write(dc);
    }
    public static CharacterSaves get(char in) throws FileNotFoundException {
        DataClass dc =FileSaverCharacter.read();
        if(dc==null)
            return null;
        else
            return dc.get((int)in-(int)'a');
    }
}
