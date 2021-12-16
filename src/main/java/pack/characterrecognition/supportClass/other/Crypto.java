package pack.characterrecognition.supportClass.other;

public class Crypto {
    public static String code(String frase,String key){
        String out="";
        for(int i=0;i<frase.length();i++)
            out+=(char)(((int)frase.charAt(i)+(int)key.charAt(i%key.length()))%255);
        return out;
    }
    public static String decode(String frase,String key){
        String out="";
        for(int i=0;i<frase.length();i++)
            out+=(char)(((int)frase.charAt(i)-(int)key.charAt(i%key.length())+255)%255);
        return out;
    }
    public static String randomKey(int len){
        String out="";
        for(int i=0;i<len;i++)
            out+=(char)(int)(Math.random()*255);
        return out;
    }
}
