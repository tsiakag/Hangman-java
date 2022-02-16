package tpel18130.Dictionary;

//Throwbale exception when given input is not valid
public class InvalidRangeException extends RuntimeException{
    public InvalidRangeException(){
        super("Invalid dictionary: Contains words with less than 6 letters");
    }
}
