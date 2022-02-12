package tpel18130.Dictionary;

//Throwbale exception when duplicate words exist in a dictionary
public class InvalidCountException extends RuntimeException{
    public InvalidCountException(String message, Throwable err){
        super(message, err);
    }
}
