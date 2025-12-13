package fivecaux;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * DataGenerator generated our sample data for this project
 * Generates Users.csv (200 lines), Songs.csv, SongPlays.csv (10000 lines)
 * Next steps for this project would involve importing users Spotify/Apple Music listening history
 * and possibly incorporating live data integration through APIs
 * 
 * @author Google Gemini
 * We wanted to generate data directly using Gemini, however the desired number of rows (10000) was too many, thus Gemini had to create a class to generate our data
 */

public class DataGenerator {

    // --- Configuration ---
    private static final int NUM_PLAYS = 10000;
    private static final int NUM_USERS_TO_GENERATE = 200;
    
    // Start date remains fixed at Jan 1, 2025
    private static final LocalDateTime START_DATE = LocalDateTime.of(2025, Month.JANUARY, 1, 0, 0, 0);

    // --- Data Sources (Same as before) ---
    private static final String[] FIRST_NAMES = {
        "Emma", "Liam", "Olivia", "Noah", "Ava", "Elijah", "Sophia", "Lucas", "Mia", "Ben",
        "Charlotte", "Oliver", "Amelia", "James", "Harper", "Alexander", "Evelyn", "William"
    };
    
    private static final String[] LAST_NAMES = {
        "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis",
        "Rodriguez", "Martinez", "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson"
    };

    private static final String[] SCHOOLS = {"Pomona", "Scripps", "CMC", "Harvey Mudd", "Pitzer"};
    private static final int[] YEARS = {2026, 2027, 2028, 2029};

    private static final String[] MAJORS = {
        "Africana Studies", "American Studies", "Anthropology", "Art", "Art History",
        "Asian American Studies", "Asian Studies", "Astronomy", "Biology", "Chemistry",
        "Chicana/o-Latina/o Studies", "Chinese", "Classics", "Cognitive Science",
        "Computer Science", "Dance", "Economics", "English", "Environmental Analysis",
        "French", "Gender & Women's Studies", "Geology", "German Studies", "History",
        "International Relations", "Japanese", "Late Antique-Medieval Studies",
        "Latin American Studies", "Linguistics", "Mathematics & Statistics", "Media Studies",
        "Middle Eastern Studies", "Molecular Biology", "Music", "Neuroscience", "Philosophy",
        "Philosophy, Politics & Economics", "Physics & Astronomy", "Politics",
        "Psychological Science", "Public Policy Analysis", "Religious Studies",
        "Romance Languages & Literatures", "Russian", "Russian & Eastern European Studies",
        "Science, Technology & Society", "Sociology", "Spanish", "Theatre"
    };

    // --- Inner Classes ---
    static class User {
        int id;
        String name;
        String school;
        int year;
        String major;

        public User(int id, String name, String school, int year, String major) {
            this.id = id;
            this.name = name;
            this.school = school;
            this.year = year;
            this.major = major;
        }
    }

    static class Song {
        int id;
        String title;
        String genre;
        String artist;

        public Song(int id, String title, String genre, String artist) {
            this.id = id;
            this.title = title;
            this.genre = genre;
            this.artist = artist;
        }
    }

    static class Play {
        int userId;
        int songId;
        LocalDateTime time;

        public Play(int userId, int songId, LocalDateTime time) {
            this.userId = userId;
            this.songId = songId;
            this.time = time;
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting Data Generation...");
        
        Random random = new Random();

        // 1. Define Time Range (Start of 2025 -> NOW)
        LocalDateTime endDate = LocalDateTime.now(); 
        
        // Validation: If current date is before 2025, default to end of 2025 to prevent errors
        if (endDate.isBefore(START_DATE)) {
            endDate = LocalDateTime.of(2025, 12, 31, 23, 59);
        }

        // 2. Generate Songs
        List<Song> songs = getRealSongs();

        // 3. Generate Users
        List<User> users = new ArrayList<>();
        Set<String> generatedNames = new HashSet<>();

        while (users.size() < NUM_USERS_TO_GENERATE) {
            String fname = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
            String lname = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
            String fullName = fname + " " + lname;

            if (generatedNames.contains(fullName)) continue;
            generatedNames.add(fullName);

            User u = new User(
                users.size() + 1,
                fullName,
                SCHOOLS[random.nextInt(SCHOOLS.length)],
                YEARS[random.nextInt(YEARS.length)],
                MAJORS[random.nextInt(MAJORS.length)]
            );
            users.add(u);
        }

        // 4. Generate Song Plays
        List<Play> plays = new ArrayList<>();
        long startEpoch = START_DATE.toEpochSecond(ZoneOffset.UTC);
        long endEpoch = endDate.toEpochSecond(ZoneOffset.UTC);
        
        // Ensure we don't crash if range is 0 (very unlikely)
        long epochDiff = endEpoch - startEpoch;
        if (epochDiff <= 0) epochDiff = 1;

        for (int i = 0; i < NUM_PLAYS; i++) {
            User randomUser = users.get(random.nextInt(users.size()));
            Song randomSong = songs.get(random.nextInt(songs.size()));

            // Generate random time between Start 2025 and NOW
            long randomSecond = startEpoch + (long)(random.nextDouble() * epochDiff);
            LocalDateTime randomTime = LocalDateTime.ofEpochSecond(randomSecond, 0, ZoneOffset.UTC);

            plays.add(new Play(randomUser.id, randomSong.id, randomTime));
        }

        // Sort plays by time
        Collections.sort(plays, Comparator.comparing(p -> p.time));

        // 5. Write CSV Files
        writeUsersCsv(users);
        writeSongsCsv(songs);
        writeSongPlaysCsv(plays);

        System.out.println("Done! Files created with data spanning from " + START_DATE.toLocalDate() + " to " + endDate);
    }

    // --- CSV Writers ---

    private static void writeUsersCsv(List<User> users) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("Users.csv"))) {
            writer.println("user_id,user_name,user_school,user_year,user_major");
            for (User u : users) {
                writer.printf("%d,%s,%s,%d,%s%n", u.id, u.name, u.school, u.year, u.major);
            }
            System.out.println("Wrote Users.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeSongsCsv(List<Song> songs) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("Songs.csv"))) {
            writer.println("song_id,song_title,song_genre,song_artist");
            for (Song s : songs) {
                String safeArtist = s.artist.contains(",") ? "\"" + s.artist + "\"" : s.artist;
                writer.printf("%d,%s,%s,%s%n", s.id, s.title, s.genre, safeArtist);
            }
            System.out.println("Wrote Songs.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeSongPlaysCsv(List<Play> plays) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        
        try (PrintWriter writer = new PrintWriter(new FileWriter("SongPlays.csv"))) {
            writer.println("user_id,song_id,time");
            for (Play p : plays) {
                String timeStr = p.time.truncatedTo(java.time.temporal.ChronoUnit.SECONDS).format(formatter);
                writer.printf("%d,%d,%s%n", p.userId, p.songId, timeStr);
            }
            System.out.println("Wrote SongPlays.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // --- Data Population ---
    private static List<Song> getRealSongs() {
        List<Song> songs = new ArrayList<>();
        String[][] rawData = {
            {"Espresso", "Pop", "Sabrina Carpenter"},
            {"Not Like Us", "Hip Hop", "Kendrick Lamar"},
            {"Birds of a Feather", "Pop", "Billie Eilish"},
            {"Good Luck, Babe!", "Pop", "Chappell Roan"},
            {"Texas Hold 'Em", "Country", "Beyoncé"},
            {"Beautiful Things", "Pop", "Benson Boone"},
            {"Too Sweet", "Alternative", "Hozier"},
            {"Like That", "Hip Hop", "Future, Metro Boomin"},
            {"Lose Control", "Soul", "Teddy Swims"},
            {"Cruel Summer", "Pop", "Taylor Swift"},
            {"Greedy", "Pop", "Tate McRae"},
            {"I Remember Everything", "Country", "Zach Bryan ft. Kacey Musgraves"},
            {"Paint The Town Red", "Hip Hop", "Doja Cat"},
            {"Water", "Afrobeats", "Tyla"},
            {"Agora Hills", "R&B", "Doja Cat"},
            {"Stick Season", "Folk", "Noah Kahan"},
            {"Fast Car", "Country", "Luke Combs"},
            {"Last Night", "Country", "Morgan Wallen"},
            {"Vampire", "Pop", "Olivia Rodrigo"},
            {"Kill Bill", "R&B", "SZA"},
            {"Flowers", "Pop", "Miley Cyrus"},
            {"Anti-Hero", "Pop", "Taylor Swift"},
            {"As It Was", "Pop", "Harry Styles"},
            {"Bad Habit", "R&B", "Steve Lacy"},
            {"About Damn Time", "Pop", "Lizzo"},
            {"Heat Waves", "Indie", "Glass Animals"},
            {"Stay", "Pop", "The Kid LAROI & Justin Bieber"},
            {"Drivers License", "Pop", "Olivia Rodrigo"},
            {"Blinding Lights", "Synth-pop", "The Weeknd"},
            {"Don't Start Now", "Disco", "Dua Lipa"},
            {"Levitating", "Pop", "Dua Lipa"},
            {"Peaches", "R&B", "Justin Bieber"},
            {"Montero (Call Me By Your Name)", "Hip Hop", "Lil Nas X"},
            {"Good 4 U", "Rock", "Olivia Rodrigo"},
            {"Kiss Me More", "R&B", "Doja Cat ft. SZA"},
            {"Industry Baby", "Hip Hop", "Lil Nas X & Jack Harlow"},
            {"Butter", "K-Pop", "BTS"},
            {"Easy On Me", "Ballad", "Adele"},
            {"Shivers", "Pop", "Ed Sheeran"},
            {"Cold Heart", "Disco", "Elton John & Dua Lipa"},
            {"Super Shy", "K-Pop", "NewJeans"},
            {"Seven", "Pop", "Jung Kook"},
            {"Rich Men North of Richmond", "Country", "Oliver Anthony"},
            {"Fukumean", "Hip Hop", "Gunna"},
            {"Calm Down", "Afrobeats", "Rema & Selena Gomez"},
            {"Creepin'", "R&B", "Metro Boomin, The Weeknd, 21 Savage"},
            {"Rich Flex", "Hip Hop", "Drake & 21 Savage"},
            {"Cuff It", "R&B", "Beyoncé"},
            {"Unholy", "Pop", "Sam Smith & Kim Petras"},
            {"Made You Look", "Pop", "Meghan Trainor"}
        };

        for (int i = 0; i < rawData.length; i++) {
            songs.add(new Song(i + 1, rawData[i][0], rawData[i][1], rawData[i][2]));
        }
        return songs;
    }
}