package fivecaux;

/**
 * Each object Song represents a unique song
 * 
 * @author Julian Chumacero
 * @author Andrew Lim
 * @author Alexander Adhikari
 */

public class Song implements Comparable<Song> {
    private final int songId; // corresponding integer to a song
    private final String title; // title of song
    private final String genre; // genre of song
    private final String artist; // artist of song
    private int plays; // number of times the song was played
    
    /**
     * initializes instance variables
     */
    public Song(int songId, String title, String genre, String artist) {
        this.songId = songId;
        this.title = title;
        this.genre = genre;
        this.artist = artist;
        plays = 0;
    }

    //defines natural order of songs based on # of plays
    //comparing such that highest songs come first when we use Collections.sort
    public int compareTo(Song other) {
        int playComparison = Integer.compare(other.plays, this.plays);
        if (playComparison != 0) {
            return playComparison;
        }
        //if plays are equal for two songs, break the tie using title alphabetically
        return this.title.compareTo(other.title);
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

    public void incrementPlays(){
        plays++;
    }

    public int getPlays(){
        return plays;
    }

    public void resetPlays(){
        plays = 0;
    }

    public String toString(){
        return "Title: " + title + " | Artist: " + artist + " | Genre: " + genre + " | Plays: " + plays;
    }

    public static void main(String[] args) {
        // create a Song object
        Song s = new Song(0, "none", "none", "none");
        s.incrementPlays();
        s.incrementPlays();
        System.out.println(s.getPlays());
        s.incrementPlays();
        System.out.println(s);
    }
}
