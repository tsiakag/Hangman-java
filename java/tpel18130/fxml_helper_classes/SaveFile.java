package tpel18130.fxml_helper_classes;

import java.io.*;

public class SaveFile {
    /**
     * A small class for implementing a <b>savefile system</b> for the gui so as for the user
     * to be able to see the last 5 games played.
     * The class is responsible for writing the game results to a file named as <b>savefile</b> and retrieve the data
     * from the last 5 played game from it
     */

    /**
     *default constructor for the SaveFile class
     */
    public SaveFile() {}

    //

    /**
     * writes to the file "save" the required saved state of the game
     * @param word a String that indicates the selected word in that game
     * @param rounds an Integer indicating how many
     * @param winner a String indicating if the player won or the Computer (we use only those two values)
     * @throws IOException handle expresions during writing on the savefile
     */
    public void WriteToSave (String word, int rounds, String winner) throws IOException {
        FileWriter writer = new FileWriter("./java/tpel18130/fxml_helper_classes/savefile", true);

        //in the word va we need to remove the \n in the end!!!
        String temp = "Word: " + word.replaceAll("[^a-zA-Z]", "")+ ", Rounds: " +String.valueOf(rounds)+ ", Winner: " + winner;
            writer.write(temp + System.lineSeparator());
        writer.close();
    }

    //reads the last 5 games from the file "savefile" saved there

    /**
     * reads the last 5 games from the file "savefile" saved there.
     * Uses the private method "LastLines" in order to print the 5 last lines
     * <i>Notice: Lastlines takes as argument n=9 because it reads '\n' characters</i>
     * @return the last 5 lines of the "savefile"
     * @throws FileNotFoundException for handling if the file doesn't exist, something that
     * will happen if the "savefile" is deleted
     */
    public String ReadFromSave () throws FileNotFoundException {
        File save = new File("./java/tpel18130/fxml_helper_classes/savefile");
        return LastLines(save, 9); //for the last 5 lines we count 9 '\n' characters
    }

    //used for printing the last lines of a file
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
