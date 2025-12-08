package fivecaux;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;

public class SongLibrary {
    private final HashMap<Integer, Song> songLibrary; //maps songId to Song objects

    /**
     * reads Songs.csv to create Song objects, create HashMap that maps songId to songs
     * then reads SongPlays.csv and increments plays for a Song each time its Id appears in SongPlays.csv
     */ 
    public SongLibrary(){
        this.songLibrary = new HashMap<>();
        
        // reading the file Songs.csv to build the map of songId to Song objects
        try { 
            FileReader fr = new FileReader("data/Songs.csv"); 
            BufferedReader br = new BufferedReader(fr);
            br.readLine(); //skip header line
            String line = br.readLine();
            while(line!=null) {
                String[] lineArray = line.split(","); //lineArray[0] = songId, lineArray[1] = title, lineArray[2] = genre, lineArray[3] = artist
                int currentSongId = Integer.parseInt(lineArray[0]);
                String currentSongName = lineArray[1];
                String currentSongGenre = lineArray[2];
                String currectSongArtist = lineArray[3];
                this.songLibrary.put(currentSongId, new Song(currentSongId, currentSongName, currentSongGenre, currectSongArtist)); // add songId and title to map
                line = br.readLine();
            }
            br.close();
        } catch (FileNotFoundException e) { //catch file not found exception
            e.printStackTrace();
        } catch (IOException e) { //catch IO exception
            e.printStackTrace();
        }

        //reading the file SongPlays.csv to build the map of songId to number of times played
        try { 
            FileReader fr = new FileReader("data/SongPlays.csv"); 
            BufferedReader br = new BufferedReader(fr);
            br.readLine(); //skip header line
            String line = br.readLine();
            while(line!=null) {
                String[] lineArray = line.split(","); //lineArray[0] = userId, lineArray[1] = songId, lineArray[2] = time
                int currentSongId = Integer.parseInt(lineArray[1]);
                this.songLibrary.get(currentSongId).incrementPlays(); //increment count of song played
                line = br.readLine();
            }
            br.close();
        } catch (FileNotFoundException e) { //catch file not found exception
            e.printStackTrace();
        } catch (IOException e) { //catch IO exception
            e.printStackTrace();
        }
    }

    public void printNumberTimesPlayed() {
        for (Map.Entry<Integer, Song> entry : songLibrary.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    public ArrayList<Song> sort(){
        ArrayList<Song> sorted = new ArrayList<Song>();
        for (Map.Entry<Integer, Song> entry : songLibrary.entrySet()){
            sorted.add(entry.getValue());
        }
        Collections.sort(sorted);
        return sorted;
    }

    public static void main(String[] args){
        SongLibrary lib = new SongLibrary();
        lib.printNumberTimesPlayed();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("SORTED BELOW HERE");
        System.out.println("------------------------------");
        for (Song x : lib.sort()){
            System.out.println(x);
        }
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("TOP 10 MOST PLAYED SONGS BELOW HERE");
        System.out.println("------------------------------");
        ArrayList<Song> sorted = lib.sort();
        for (int i = 0; i < 10; i++){
            System.out.println(sorted.get(i));
        }
    }
}
