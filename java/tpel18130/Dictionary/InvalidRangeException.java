package tpel18130.Dictionary;

//Throwbale exception when letter count in a word is less than 6
public class InvalidRangeException extends RuntimeException{
    public InvalidRangeException(String message, Throwable err){
        super(message, err);
    }
}
