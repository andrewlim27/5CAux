public class Song {
    private final int songId;
    private final String title;
    private final String genre;
    private final String artist;

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
