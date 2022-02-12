package tpel18130.Dictionary;

import javax.json.*;
import java.io.*;
import java.net.URL;
import java.util.*;

//"OL31390631M"
public class Dictionary {
    private static String ID;
    private static String[] dictionary;

    public Dictionary(String ID) { this.ID = ID; }

    public void CreateDictionary() throws IOException, UndersizeException, UnbalancedException, InvalidCountException, InvalidCountException {
        ReadData r = new ReadData(ID);
        String api_result = r.GetJSONdata();

        dictionary = new String[api_result.length()];
        dictionary = api_result.split("\s+");

        System.out.println(Arrays.toString(dictionary));

        WriteToFile w = new WriteToFile(dictionary, ID);
        w.doWrite();
    }

    //TODO remove main
    public static void main(String[] args) {

    }
}


class ReadData {
    private String ID; //the ID that we use to connect to the certain book

    ReadData(String ID) { //constructor
        this.ID = ID;
    }

    public String GetJSONdata() throws IOException {
        URL url = new URL("https://openlibrary.org/works/"+ID+".json"); //load the url

        //establish connection
        InputStream is = null;
        try{is = url.openStream();}
        catch(Exception e){ System.out.println("Failed to Connect due to error:\n"+ e); }

        //create json
        JsonReader rdr = Json.createReader(is);
        JsonObject obj = rdr.readObject();

        //get the result of the json
        // nested try-catch blocks in order for the procedure to work smoothly
        String results = "";
        try {
            try { results = obj.getString("description"); }
            catch (ClassCastException E) {
                obj= obj.getJsonObject("description");
                results = obj.getString("value");
            }

            if (results.length() == 0) {
                throw new NullPointerException("no description found");
            }

            return results;
        }
        catch(Exception e){
            System.out.println("No valid object found");
        }
        return "";
    }
}

class WriteToFile{
    private String ID;
    private String words[];

    WriteToFile(String[] input, String ID) {
        words = input.clone();
        this.ID = ID;
    }

    private List<String> ValidWords() throws UndersizeException, UnbalancedException, InvalidCountException, InvalidCountException  {
        List<String> arr = new ArrayList<String>();
        int count = 0;
        for(String word: words) {
            word = word.replaceAll("[^a-zA-Z]", "");

            if(word.length() < 6 || arr.contains(word)) continue; // we want at least 6 length and unique words

            if(word.length() >= 9) count++; //we count them to see if the dictionary is valid

            arr.add(word);
        }

        //exceptions to check if dictionary is valid
        if(arr.size() < 20) throw new UndersizeException();
        if((float)count / (float)(arr.size()) < 0.2f) throw new UnbalancedException();
        List<String> temp = new ArrayList<String>();
        for(String i : arr) {
            if (i.length() < 6) throw new InvalidRangeException();
            if (temp.contains(i)) throw new InvalidCountException();
            temp.add(i);
        }
        return arr;
    }

    public void doWrite() throws IOException, UndersizeException, UnbalancedException, InvalidCountException, InvalidCountException  {
        //add lot of if's
        List<String> validWords = new ArrayList<String>();
        validWords = ValidWords();

        if(validWords.isEmpty()){
            System.out.println("Invalid Dictionary");
            return;
        }

        FileWriter writer = new FileWriter("hangman-"+ID+".txt");
        for(String word: validWords) {
            writer.write(word.toUpperCase() + System.lineSeparator());
        }
        writer.close();
    }
}