package fivecaux;
import java.util.HashMap;
import java.util.Map;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Each object Song represents a unique instance of a song
 */
public class Song {
    private final int songId; // corresponding integer to a song
    private final String title; // title of song
    private final String genre; // genre of song
    private final String artist; // artist of song
    private final int plays; // number of times the song was played
    private final HashMap<Integer, Integer> numberTimesPlayed; // maps songId to number of times played
    private final HashMap<Integer, String> songTitles; // maps songId to song title
    

    /**
     * initializes instance variables, then reads the file SongPlays.csv, to 
     * create a HashMap thatmaps songId to the number of times it appears in the file, 
     * and reads the file Songs.csv to create a HashMap that maps songId to song title
     * 
     */
    public Song(int songId, String title, String genre, String artist, int plays) {
        this.songId = songId;
        this.title = title;
        this.genre = genre;
        this.artist = artist;
        this.plays = plays;
        this.numberTimesPlayed = new HashMap<>();
        this.songTitles = new HashMap<>();


        // reading the file SongPlays.csv to build the map of songId to number of times played
        try { 
            FileReader fr = new FileReader("data/SongPlays.csv"); 
            BufferedReader br = new BufferedReader(fr);
            br.readLine(); // skip header line
            String line = br.readLine();
            while(line!=null) {
                String[] lineArray = line.split(","); // lineArray[0] = userId, lineArray[1] = songId, lineArray[2] = time
                int currentUserId = Integer.parseInt(lineArray[0]); 
                int currentSongId = Integer.parseInt(lineArray[1]);
                if(this.numberTimesPlayed.containsKey(currentSongId)) { // check if songId is already in map
                    this.numberTimesPlayed.put(currentSongId, this.numberTimesPlayed.get(currentSongId) + 1); // increment count by 1
                } else {
                     this.numberTimesPlayed.put(currentSongId, 1); // add songId to map with count 1
                }
                line = br.readLine();
            }
            br.close();
        } catch (FileNotFoundException e) { // catch file not found exception
            e.printStackTrace();
        } catch (IOException e) { // catch IO exception
            e.printStackTrace();
        }
        

        // reading the file Songs.csv to build the map of songId to song title
        try { 
            FileReader fr = new FileReader("data/Songs.csv"); 
            BufferedReader br = new BufferedReader(fr);
            br.readLine(); // skip header line
            String line = br.readLine();
            while(line!=null) {
                String[] lineArray = line.split(","); // lineArray[0] = songId, lineArray[1] = title, lineArray[2] = genre, lineArray[3] = artist
                int currentSongId = Integer.parseInt(lineArray[0]);
                String currentSongName = lineArray[1];
                this.songTitles.put(currentSongId, currentSongName); // add songId and title to map
                line = br.readLine();
            }
            br.close();
        } catch (FileNotFoundException e) { // catch file not found exception
            e.printStackTrace();
        } catch (IOException e) { // catch IO exception
            e.printStackTrace();
        }
       

        
    }

    public int getSongId(){
        return songId;
    }

    public String getTitle(){
        return title;
    }

    public String getGenre(){
        return genre;
    }

    public String getArtist(){
        return artist;
    }

    public void printNumberTimesPlayed() {
        for (Map.Entry<Integer, Integer> entry : numberTimesPlayed.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    public void printSongTitles() {
        for (Map.Entry<Integer, String> entry : songTitles.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        // create a Song object
        Song s = new Song(0, "none", "none", "none", 0);
        //s.printNumberTimesPlayed();
        //s.printSongTitles();
    }
}
