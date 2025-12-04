package fivecaux;
import java.util.HashMap;

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

    
    public Song(int songId, String title, String genre, String artist, int plays, HashMap<Song, Integer> numberTimesPlayed) {
        this.songId = songId;
        this.title = title;
        this.genre = genre;
        this.artist = artist;
        this.plays = plays;

        
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
}
