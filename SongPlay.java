public class SongPlay {
    private final int userId;
    private final int songId;
    private final String time;

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
