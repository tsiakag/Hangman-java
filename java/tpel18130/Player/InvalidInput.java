package tpel18130.Player;

public class InvalidInput extends RuntimeException{
    public InvalidInput(){
        super("Invalid character: Expected character ranging from a-z or from A-Z");
    }
}
