package tpel18130.Dictionary;

//Throwbale exception when duplicate words exist in a dictionary
public class InvalidCountException extends RuntimeException{
    public InvalidCountException(){
        super("Invalid dictionary: duplicate words found");
    }
}
