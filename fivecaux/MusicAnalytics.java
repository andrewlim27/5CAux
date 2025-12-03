package fivecaux;

import java.util.List;
import java.util.Map;

public interface MusicAnalytics {

    //data loading
    void addUser(User user);
    void addSong(Song song);
    void addSongPlay(SongPlay play);

    //basic lookups
    User getUser(int userId);
    Song getSong(int songId);

    /**
     * Returns the top k most-played songs overall, as a list of Song objects, sorted from most-played to least
     */
    List<Song> getTopSongsOverall(int k);

    /**
     * Returns the top k songs for a given class year (e.g. 2028)
     */
    List<Song> getTopSongsByYear(int classYear, int k);

    /**
     * Returns the top k songs for a given school (e.g., "Pomona")
     */
    List<Song> getTopSongsBySchool(String school, int k);

    /**
     * Returns a map from Song -> play count for a particular user
     */
    Map<Song, Integer> getUserSongCounts(int userId);

    /**
     * Overall genre counts: genre -> total plays
     */
    Map<String, Integer> getGenreCountsOverall();

    /**
     * Genre counts for a given class year: genre -> plays
     */
    Map<String, Integer> getGenreCountsByYear(int classYear);

    /**
     * Genre counts for a given school: genre -> plays
     */
    Map<String, Integer> getGenreCountsBySchool(String school);
}
