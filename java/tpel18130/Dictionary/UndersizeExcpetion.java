package tpel18130.Dictionary;

//Exception when dictionary haw less than 20 Words
public class UndersizeExcpetion extends RuntimeException{
    public UndersizeExcpetion(String message, Throwable err){
        super(message, err);
    }
}
