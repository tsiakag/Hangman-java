package tpel18130.Dictionary;

//Throwable exception when less than 20% of words have more letters >= 9
public class UnbalancedException extends Exception{
    public UnbalancedException(){
        super("Invalid dictionary: less than 20% of words have >= 9 leters");
    }
}
