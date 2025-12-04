package fivecaux;

/**
 * Each object Song represents a unique instance of a song
 */
public class Song {
    private final int songId; // corresponding integer to a song
    private final String title; // title of song
    private final String genre; // genre of song
    private final String artist; // artist of song

    
    public Song(int songId, String title, String genre, String artist) {
        this.songId = songId;
        this.title = title;
        this.genre = genre;
        this.artist = artist;
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
