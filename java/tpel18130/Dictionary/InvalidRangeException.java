package tpel18130.Dictionary;

//Throwbale exception when letter count in a word is less than 6
public class InvalidRangeException extends RuntimeException{
    public InvalidRangeException(){
        super("Invalid dictionary: Contains words with less than 6 letters");
    }
}
