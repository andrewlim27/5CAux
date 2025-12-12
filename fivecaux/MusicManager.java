package fivecaux;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;


/**
 * MusicManager performs all the basic features of the app:
 * 1. sorting listening history
 * 2. filtering listening history
 * 3. matching users based on taste
 * 
 * @author Julian Chumacero
 * @author Andrew Lim
 * @author Alexander Adhikari
 */

public class MusicManager {
    private final HashMap<Integer, Song> songLibrary; //maps songId to Song objects
    private final HashMap<Integer, User> userLibrary; //maps userId to User objects
    private ArrayList<SongPlay> listeningHistory; //stores listening data in a list of SongPlay objects

    /**
     * reads Songs.csv to create Song objects, create HashMap that maps songId to songs
     * then reads SongPlays.csv and increments plays for a Song each time its Id appears in SongPlays.csv
     */ 
    public MusicManager(){
        this.songLibrary = new HashMap<>();
        this.userLibrary = new HashMap<>();
        this.listeningHistory = new ArrayList<SongPlay>();
        
        //reading the file Songs.csv to build the map of songId to Song objects
        try { 
            FileReader fr = new FileReader("data/Songs.csv"); 
            BufferedReader br = new BufferedReader(fr);
            br.readLine(); //skip header line
            String line = br.readLine();
            while(line!=null) {
                String[] lineArray = line.split(","); //lineArray[0] = songId, lineArray[1] = title, lineArray[2] = genre, lineArray[3] = artist
                int currentSongId = Integer.parseInt(lineArray[0]);
                String currentSongName = lineArray[1];
                String currentSongGenre = lineArray[2];
                String currectSongArtist = lineArray[3];
                this.songLibrary.put(currentSongId, new Song(currentSongId, currentSongName, currentSongGenre, currectSongArtist)); //add songId and Song to map
                line = br.readLine();
            }
            br.close();
        } catch (FileNotFoundException e) { //catch file not found exception
            e.printStackTrace();
        } catch (IOException e) { //catch IO exception
            e.printStackTrace();
        }

        //reading the file Users.csv to build the map of userId to User objects
        try { 
            FileReader fr = new FileReader("data/Users.csv"); 
            BufferedReader br = new BufferedReader(fr);
            br.readLine(); //skip header line
            String line = br.readLine();
            while(line!=null) {
                String[] lineArray = line.split(","); //lineArray[0] = userId, lineArray[1] = user_name, lineArray[2] = user_school, lineArray[3] = user_year, lineArray[4] = user_major
                int currentUserId = Integer.parseInt(lineArray[0]);
                String currentUserName = lineArray[1];
                String currentUserSchool = lineArray[2];
                int currectUserYear = Integer.parseInt(lineArray[3]);
                String currentUserMajor = lineArray[4];
                this.userLibrary.put(currentUserId, new User(currentUserId, currentUserName, currentUserSchool, currectUserYear, currentUserMajor)); //add userId and User to map
                line = br.readLine();
            }
            br.close();
        } catch (FileNotFoundException e) { //catch file not found exception
            e.printStackTrace();
        } catch (IOException e) { //catch IO exception
            e.printStackTrace();
        }


        //reading the file SongPlays.csv to build the list listening history
        try { 
            FileReader fr = new FileReader("data/SongPlays.csv"); 
            BufferedReader br = new BufferedReader(fr);
            br.readLine(); //skip header line
            String line = br.readLine();
            while(line!=null) {
                String[] lineArray = line.split(","); //lineArray[0] = userId, lineArray[1] = songId, lineArray[2] = time
                int currentUserId = Integer.parseInt(lineArray[0]);
                int currentSongId = Integer.parseInt(lineArray[1]);
                String currentTime = lineArray[2];
                listeningHistory.add(new SongPlay(currentUserId, currentSongId, currentTime));
                line = br.readLine();
            }
            br.close();
        } catch (FileNotFoundException e) { //catch file not found exception
            e.printStackTrace();
        } catch (IOException e) { //catch IO exception
            e.printStackTrace();
        }


        //reading the file SongPlays.csv to build the map of songId to number of times played
        try { 
            FileReader fr = new FileReader("data/SongPlays.csv"); 
            BufferedReader br = new BufferedReader(fr);
            br.readLine(); //skip header line
            String line = br.readLine();
            while(line!=null) {
                String[] lineArray = line.split(","); //lineArray[0] = userId, lineArray[1] = songId, lineArray[2] = time
                int currentSongId = Integer.parseInt(lineArray[1]);
                this.songLibrary.get(currentSongId).incrementPlays(); //increment count of song played
                line = br.readLine();
            }
            br.close();
        } catch (FileNotFoundException e) { //catch file not found exception
            e.printStackTrace();
        } catch (IOException e) { //catch IO exception
            e.printStackTrace();
        }
    }

    /**
     * filters each instance of every song listen from listeningHistory by the given school and returns
     * those instances as a new ArrayList
     * 
     * @param data the SongPlay listening history we want to filter
     * @param schoolName the given schoolname to filter by
     * @return schoolList, the list of every instance of a song listened to at a specific school
     */
    public ArrayList<SongPlay> filterSchool(ArrayList<SongPlay> data, String schoolName) {
        ArrayList<SongPlay> schoolList = new ArrayList<SongPlay>(); // creates a new empty ArrayList that will be SongPlay objects of the given school
        
        for(SongPlay x: data) { // loops through the listeningHistory that contains data from SongPlays.csv
            User userObject = userLibrary.get(x.getUserId());
            if(userObject.getSchool().equals(schoolName)) { // checks each userId in listeningHistory to see if they go to the given school
                schoolList.add(x);
            }
        }

        return schoolList;
    }

    /**
     * filters each instance of every song listen from listeningHistory by the given year (Freshman, Sophomore, etc.) 
     * and returns those instances as a new ArrayList
     * 
     * @param data the SongPlay listening history we want to filter
     * @param year the given year to filter by
     * @return yearList, the list of every instance of a song listened to by a specific year
     */
    public ArrayList<SongPlay> filterYear(ArrayList<SongPlay> data, int year) {
        ArrayList<SongPlay> yearList = new ArrayList<SongPlay>(); // creates a new empty ArrayList that will be SongPlay objects of the given year
        
        for(SongPlay x: data) { // loops through the listeningHistory that contains data from SongPlays.csv
            User userObject = userLibrary.get(x.getUserId());
            if(userObject.getYear() == year) { // checks each userId in listeningHistory to see if they are in the given year
                yearList.add(x);
            }
        }

        return yearList;
    }

    /**
     * filters each instance of every song listen from listeningHistory by the given major and returns
     * those instances as a new ArrayList
     * 
     * @param data the SongPlay listening history we want to filter
     * @param major the given schoolname to filter by
     * @return majorList, the list of every instance of a song listened to by a specific major
     */
    public ArrayList<SongPlay> filterMajor(ArrayList<SongPlay> data, String major) {
        ArrayList<SongPlay> majorList = new ArrayList<SongPlay>(); // creates a new empty ArrayList that will be SongPlay objects of the given major
        
        for(SongPlay x: data) { // loops through the listeningHistory that contains data from SongPlays.csv
            User userObject = userLibrary.get(x.getUserId());
            if(userObject.getMajor().equals(major)) { // checks each userId in listeningHistory to see if they study the given major
                majorList.add(x);
            }
        }

        return majorList;
    }

    /**
     * filters each instance of every song listen from listeningHistory by the given genre and returns
     * those instances as a new ArrayList
     * 
     * @param data the SongPlay listening history we want to filter
     * @param genre the given genre to filter by
     * @return genreList, the list of every instance of a song listened from a specific genre
     */
    public ArrayList<SongPlay> filterGenre(ArrayList<SongPlay> data, String genre) {
        ArrayList<SongPlay> genreList = new ArrayList<SongPlay>(); // creates a new empty ArrayList that will be SongPlay objects of the given genre
        
        for(SongPlay x: data) { // loops through the listeningHistory that contains data from SongPlays.csv
            Song songObject = songLibrary.get(x.getSongId());
            if(songObject.getGenre().equals(genre)) { // checks each songId in listeningHistory to see if they study the given major
                genreList.add(x);
            }
        }

        return genreList;
    }
    
    /**
	 * prints the number of times each song was played
	 */
    public void printNumberTimesPlayed(ArrayList<Song> songs) {
        for (Song song : songs){
            System.out.println(song.getTitle() + " -> " + song.getPlays());
        }
    }
    
    /**
	 * sets number of plays for every song to 0
	 */
    private void clearPlays(){
        for (Song s : songLibrary.values()){
            s.resetPlays();
        }
    }

    /**
     * @return listeningHistory
     */
    public ArrayList<SongPlay> getListeningHistory(){
        return new ArrayList<>(listeningHistory);
    }

    /**
     * @param songs the list of Song objects to take from
     * @param num the number of Song objects to take from the top/front of songs
     * @return topSongs, list of Song objects at the top of the list
     */
    public ArrayList<Song> head(ArrayList<Song> songs, int num){
        ArrayList<Song> topSongs = new ArrayList<Song>();
        int i = 0;
        while (i < num && i <= songs.size()){
            topSongs.add(songs.get(i));
            i++;
        }
        return topSongs;
    }

    /**
	 * @param data the SongPlay listening history we want to count from
	 * @return ArrayList of Song objects with instance variable plays modified to reflect the  number of times they have been played in the ArrayList data of SongPlay objects
     * does not return any Song objects that do not appear in data
	 */
    public ArrayList<Song> countPlays(ArrayList<SongPlay> data){
        //reset song play counts to 0
        clearPlays();

        //count songs
        for (SongPlay p : data){
            songLibrary.get(p.getSongId()).incrementPlays();
        }

        //store and return only songs that were played at least once
        ArrayList<Song> countedSongs = new ArrayList<Song>();
        for (Song s : songLibrary.values()){
            if (s.getPlays() > 0){
                countedSongs.add(s);
            }
        }
        return countedSongs;
    }

    /**
	 * @param data the SongPlay listening history we want to sort
	 * @return sorted ArrayList of SongPlay objects based on the number of times they have been played in the ArrayList data of SongPlay objects
	 */
    public ArrayList<Song> sortByPlays(ArrayList<SongPlay> data){
        //count the data
        ArrayList<Song> sorted = countPlays(data);
        //sort and return the counted data
        Collections.sort(sorted);
        return sorted;
    }

    /**
	 * @param data the SongPlay listening history we want to sort
	 * @return sorted ArrayList of SongPlay objects based on the time of play (most recent appear first)
	 */
    public ArrayList<SongPlay> sortByDate(ArrayList<SongPlay> data){
        //copy data
        ArrayList<SongPlay> sorted = new ArrayList<>(data);
        //sort and return the copied data
        Collections.sort(sorted);
        return sorted;
    }

    /**
	 * @param data the SongPlay listening history we want to sort and filter
	 * @return ArrayList of songs played in the past 24 hrs, sorted most-played to least
	 */
    public ArrayList<Song> topSongsDay(ArrayList<SongPlay> data){
        ArrayList<SongPlay> recent = new ArrayList<SongPlay>();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime cutoff = now.minusHours(24);
        for (SongPlay p : sortByDate(data)){
            //if time is in the past 24 hrs
            if (p.getTime().isAfter(cutoff) && !p.getTime().isAfter(now)){
                recent.add(p);
            }
        }
        return sortByPlays(recent);
    }

    /**
	 * @param data the SongPlay listening history we want to sort and filter
	 * @return ArrayList of songs played this calendar year, sorted most-played to least
	 */
    public ArrayList<Song> topSongsYear(ArrayList<SongPlay> data){
        ArrayList<SongPlay> recent = new ArrayList<SongPlay>();
        LocalDateTime now = LocalDateTime.now();
        //gets the start of current calendar year
        LocalDateTime cutoff = LocalDate.now().withDayOfYear(1).atStartOfDay();
        for (SongPlay p : sortByDate(data)){
            //if time is in this calendar year
            if (p.getTime().isAfter(cutoff) && !p.getTime().isAfter(now)){
                recent.add(p);
            }
        }
        return sortByPlays(recent);
    }

    /**
	 * @param data the SongPlay listening history we want to sort and filter
	 * @return ArrayList of songs played this semester, sorted most-played to least
	 */
    public ArrayList<Song> topSongsSemester(ArrayList<SongPlay> data){
        ArrayList<SongPlay> recent = new ArrayList<SongPlay>();
        LocalDateTime now = LocalDateTime.now();
        //gets the start of current calendar year
        LocalDateTime cutoff = LocalDateTime.of(2025, 8, 25, 0, 0);
        for (SongPlay p : sortByDate(data)){
            //if time is in this calendar year
            if (p.getTime().isAfter(cutoff) && !p.getTime().isAfter(now)){
                recent.add(p);
            }
        }
        return sortByPlays(recent);
    }

    /**
	 * @param userId the user whose listened songs we want to find
	 * @return uniqueSongs, a hashSet of songIds the user has listened to 
	 */
    private Set<Integer> getUniqueSongIdsForUser(int userId) {
        Set<Integer> uniqueSongs = new HashSet<>();
        
        for (SongPlay play : this.listeningHistory) {
            if (play.getUserId() == userId) {
                uniqueSongs.add(play.getSongId());
            }
        }
        return uniqueSongs;
    }

    /**
     * compares two users, returns a score between 0.0 and 1.0
     * 1.0 means they have listened to the exact same set of songs
     * 0.0 means they share no songs in common
     * @param user1Id one user we want to compare
     * @param user2Id other user we want to compare to
	 * @return score, indicating how similar taste the two users have 
     */
    public double getMatchScore(int user1Id, int user2Id) {
        Set<Integer> user1Songs = getUniqueSongIdsForUser(user1Id);
        Set<Integer> user2Songs = getUniqueSongIdsForUser(user2Id);

        //check if either user has 0 plays
        if (user1Songs.isEmpty() || user2Songs.isEmpty()) {
            return 0.0;
        }

        //calculate intersection (number of shared songs)
        Set<Integer> intersection = new HashSet<>(user1Songs);
        intersection.retainAll(user2Songs); 

        //calculate union (total number of unique songs listened to)
        Set<Integer> union = new HashSet<>(user1Songs);
        union.addAll(user2Songs);

        //calculate score
        return (double) intersection.size() / union.size();
    }

    /**
     * scans entire database to find user with closest match to target user, prints best match and similarity
     * @param targetUserId the user whose match we want to find
     */
    public void printTopMatchForUser(int targetUserId) {
        if (targetUserId == -1){
            return;
        }
        //check if user exists
        if (!userLibrary.containsKey(targetUserId)) {
            System.out.println("User ID " + targetUserId + " not found.");
            return;
        }
        
        String targetName = userLibrary.get(targetUserId).getName();
        System.out.println("Finding matches for: " + targetName + " (ID: " + targetUserId + ")");
        
        //iterate over every other user in the library
        double bestScore = -1.0;
        int bestMatchId = -1;

        for (int otherUserId : userLibrary.keySet()) {
            //skip comparing target user to themselves
            if (otherUserId == targetUserId) continue;

            double score = getMatchScore(targetUserId, otherUserId);

            // //print high matches (> 30%)
            // if (score > 0.30) {
            //     String otherName = userLibrary.get(otherUserId).getName();
            //     System.out.printf("   Match Found: %s (Score: %.2f%%)%n", otherName, score * 100);
            // }

            //track the best match
            if (score > bestScore) {
                bestScore = score;
                bestMatchId = otherUserId;
            }
        }

        //print best match
        if (bestMatchId != -1) {
            System.out.println("------------------------------------------------");
            System.out.printf("BEST MATCH: %s with %.2f%% compatibility!%n", 
                            userLibrary.get(bestMatchId).getName(), bestScore * 100);
        } else {
            System.out.println("No significant matches found.");
        }
    }

    public static void main(String[] args){
        MusicManager lib = new MusicManager();
        //lib.printNumberTimesPlayed();
        System.out.println(lib.filterGenre(lib.getListeningHistory(), "K-pop"));
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("SORTED BELOW HERE");
        System.out.println("------------------------------");
        for (Song x : lib.sortByPlays(lib.getListeningHistory())){
            System.out.println(x);
        }
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("TOP 10 MOST PLAYED SONGS BELOW HERE");
        System.out.println("------------------------------");
        ArrayList<Song> sorted = lib.sortByPlays(lib.getListeningHistory());
        for (int i = 0; i < 10; i++){
            System.out.println(sorted.get(i));
        }
        lib.printTopMatchForUser(2);
    }
}
