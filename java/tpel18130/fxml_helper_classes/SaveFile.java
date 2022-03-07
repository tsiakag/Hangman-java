package tpel18130.fxml_helper_classes;

import java.io.*;
import java.util.List;

public class SaveFile {

    //Todo maybe fill it?
    public SaveFile() {}

    public void WriteToSave (String word, int rounds, String winner) throws IOException {
        FileWriter writer = new FileWriter("./java/tpel18130/fxml_helper_classes/savefile", true);

        //in the word va we need to remove the \n in the end!!!
        String temp = "Word: " + word.replaceAll("[^a-zA-Z]", "")+ ", Rounds: " +String.valueOf(rounds)+ ", Winner: " + winner;
            writer.write(temp + System.lineSeparator());
        writer.close();
    }

    public String ReadFromSave () throws FileNotFoundException {
        File save = new File("./java/tpel18130/fxml_helper_classes/savefile");
        return LastLines(save, 9);
    }

    private String LastLines(File file, int lines) {
        java.io.RandomAccessFile fileHandler = null;
        try {
            fileHandler =
                    new java.io.RandomAccessFile( file, "r" );
            long fileLength = fileHandler.length() - 1;
            StringBuilder sb = new StringBuilder();
            int line = 0;

            for(long filePointer = fileLength; filePointer != -1; filePointer--){
                fileHandler.seek( filePointer );
                int readByte = fileHandler.readByte();

                if( readByte == 0xA ) {
                    if (filePointer < fileLength) {
                        line = line + 1;
                    }
                } else if( readByte == 0xD ) {
                    if (filePointer < fileLength-1) {
                        line = line + 1;
                    }
                }
                if (line >= lines) {
                    break;
                }
                sb.append( ( char ) readByte );
            }

            String lastLine = sb.reverse().toString();
            return lastLine;
        } catch( java.io.FileNotFoundException e ) {
            e.printStackTrace();
            return null;
        } catch( java.io.IOException e ) {
            e.printStackTrace();
            return null;
        }
        finally {
            if (fileHandler != null )
                try {
                    fileHandler.close();
                } catch (IOException e) {
                }
        }
    }
}
