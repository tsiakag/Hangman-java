package tpel18130.main_code;

import tpel18130.Dictionary.Dictionary;
import tpel18130.Dictionary.UnbalancedException;
import tpel18130.Dictionary.UndersizeException;

import java.io.IOException;


public class TestMain {
    public static void main(String[] args) throws UndersizeException, UnbalancedException, IOException {
        Dictionary d = new Dictionary("OL31390631M");
        d.CreateDictionary();

        System.out.println(d.getRandomWord());
    }
}
