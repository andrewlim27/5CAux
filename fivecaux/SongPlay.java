package fivecaux;
import java.time.LocalDateTime;

/**
 * Each SongPlay object represents an instance of a song being played
 * 
 * @author Julian Chumacero
 * @author Andrew Lim
 * @author Alexander Adhikari
 */
public class SongPlay implements Comparable<SongPlay> {
    private final int userId; // corresponding integer to a user
    private final int songId; // corresponding integer to a song
    private final LocalDateTime time; // Date/time the song is played

    public SongPlay(int userId, int songId, String timeString) {
        this.userId = userId;
        this.songId = songId;
        this.time = LocalDateTime.parse(timeString);
    }

    //defines natural order of a play based on time of play
    //comparing such that most recent plays come first when we use Collections.sort
    public int compareTo(SongPlay other) {
        int timeComparison = other.time.compareTo(this.time);
        if (timeComparison != 0) {
            return timeComparison;
        }
        //if times are equal for two songs, break the tie using songId
        return Integer.compare(this.songId, other.songId);
    }

    public int getUserId(){
        return userId;
    }

    public int getSongId(){
        return songId;
    }

    public LocalDateTime getTime(){
        return time;
    }

    public String toString(){
        return "UserID: " + userId + " | SongID: " + songId + " | Time: " + time;
    }
}
