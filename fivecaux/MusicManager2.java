package fivecaux;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;

public class MusicManager2 {
    private final HashMap<Integer, Song> songLibrary; //maps songId to Song objects
    private final HashMap<Integer, User> userLibrary; //maps userId to User objects
    private final ArrayList<SongPlay> listeningHistory;

    /**
     * reads Songs.csv to create Song objects, create HashMap that maps songId to songs
     * then reads SongPlays.csv and increments plays for a Song each time its Id appears in SongPlays.csv
     */ 
    public MusicManager2(){
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

    public void printNumberTimesPlayed() {
        for (Map.Entry<Integer, Song> entry : songLibrary.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
    
    //set number of plays for every song to 0
    private void clearPlays(){
        for (Song s : songLibrary.values()){
            s.resetPlays();
        }
    }

    //counts the number of times a song was played
    //helper for sortByPlays
    private ArrayList<Song> countPlays(ArrayList<SongPlay> data){
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

    //returns a sorted ArrayList of Song objects based on the number of times they have been played in the ArrayList data of SongPlay objects
    public ArrayList<Song> sortByPlays(ArrayList<SongPlay> data){
        //count the data
        ArrayList<Song> sorted = countPlays(data);
        //sort and return the counted data
        Collections.sort(sorted);
        return sorted;
    }

    //returns a sorted ArrayList of SongPlay objects based on the time of play (most recent appear first)
    public ArrayList<SongPlay> sortByDate(ArrayList<SongPlay> data){
        //copy data
        ArrayList<SongPlay> sorted = new ArrayList<>(data);
        //sort and return the copied data
        Collections.sort(sorted);
        return sorted;
    }

    public static void main(String[] args){
        SongLibrary lib = new SongLibrary();
        lib.printNumberTimesPlayed();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("SORTED BELOW HERE");
        System.out.println("------------------------------");
        for (Song x : lib.sort()){
            System.out.println(x);
        }
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("TOP 10 MOST PLAYED SONGS BELOW HERE");
        System.out.println("------------------------------");
        ArrayList<Song> sorted = lib.sort();
        for (int i = 0; i < 10; i++){
            System.out.println(sorted.get(i));
        }
    }
}
