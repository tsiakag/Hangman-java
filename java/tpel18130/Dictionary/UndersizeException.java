package tpel18130.Dictionary;

//Exception when dictionary haw less than 20 Words
public class UndersizeException extends Exception{
    public UndersizeException(){
        super("Invalid dictionary: Less than 20 words");
    }
}
