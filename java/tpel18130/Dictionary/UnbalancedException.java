package tpel18130.Dictionary;

//Throwable exception when less than 20% of words have more letters >= 9
public class UnbalancedException extends RuntimeException{
    public UnbalancedException(String message, Throwable err){
        super(message, err);
    }
}
