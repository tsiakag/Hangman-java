package tpel18130.Dictionary;

import javax.json.*;
import java.io.*;
import java.net.URL;
import java.util.*;


public class Dictionary {
    private static String ID = "OL31390631M";
    private static String[] dictionary;

    //TODO remove main
    public static void main(String[] args) throws IOException {
        ReadData r = new ReadData(ID);

        String api_result = r.GetJSONdata();

        dictionary = new String[api_result.length()];
        dictionary = api_result.split("\s+");

        System.out.println(Arrays.toString(dictionary));

        WriteToFile w = new WriteToFile(dictionary, ID);
        w.doWrite();
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

            return results; //TODO might remove later and change method type
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

    private List<String> ValidWords() {
        List<String> arr = new ArrayList<String>();
        int count = 0;
        for(String word: words) {
            word = word.replaceAll("[^a-zA-Z]", "");

            if(word.length() < 5 || arr.contains(word)) continue; // we want at least 6 length and unique words

            if(word.length() >= 9) count++; //we count them to see if the dictionary is valid

            arr.add(word);
        }

        //TODO create exceptions (remove statement below maybe)
        if(arr.size() < 20 || ((float)count / (float)(arr.size())) < 0.2f)
            return Collections.emptyList(); //invalid dictionary
        else
            return arr;
    }

    public void doWrite() throws IOException {
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