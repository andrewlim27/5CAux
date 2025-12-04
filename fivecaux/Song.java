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
    private final HashMap<Integer, Integer> numberTimesPlayed;
    

    /**
     * initializes instance variables, then reads the file SongPlays.csv, to 
     * create a HashMap thatmaps songId to the number of times it appears in the file
     * 
     * @param songId
     * @param title
     * @param genre
     * @param artist
     * @param plays
     * @param numberTimesPlayed
     */
    public Song(int songId, String title, String genre, String artist, int plays, HashMap<Integer, Integer> numberTimesPlayed) {
        this.songId = songId;
        this.title = title;
        this.genre = genre;
        this.artist = artist;
        this.plays = plays;
        this.numberTimesPlayed = new HashMap<>();

        try { 
            FileReader fr = new FileReader("data/SongPlays.csv"); 
            BufferedReader br = new BufferedReader(fr);
            br.readLine();
            String line = br.readLine();
            while(line!=null) {
                String[] lineArray = line.split(",");
                int currentUserId = Integer.parseInt(lineArray[0]);
                int currentSongId = Integer.parseInt(lineArray[1]);
                if(this.numberTimesPlayed.containsKey(currentSongId)) {
                    this.numberTimesPlayed.put(currentSongId, this.numberTimesPlayed.get(currentSongId) + 1);
                } else {
                     this.numberTimesPlayed.put(currentSongId, 1);
                }
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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

    public static void main(String[] args) {
        // create ONE Song object just to trigger the file read + map build
        Song s = new Song(0, "dummy", "none", "none", 0,null);
        s.printNumberTimesPlayed();
    }
}
