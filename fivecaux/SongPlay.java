package fivecaux;

/**
 * Each object SongPlay represents an instance of a song being played
 */
public class SongPlay {
    private final int userId; // corresponding integer to a user
    private final int songId; // corresponding integer to a song
    private final String time; // Date/time the song is played

    public SongPlay(int userId, int songId, String time) {
        this.userId = userId;
        this.songId = songId;
        this.time = time;
    }

    public int getUserId(){
        return userId;
    }

    public int getSongId(){
        return songId;
    }

    public String getTime(){
        return time;
    }
}
