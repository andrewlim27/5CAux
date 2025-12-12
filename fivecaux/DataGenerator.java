package fivecaux;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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

    // CONFIGURATION

    private static final int NUM_USERS = 200;
    private static final int NUM_PLAYS = 10000;
    
    // Start: Jan 1, 2025
    private static final LocalDateTime START_DATE = LocalDateTime.of(2025, Month.JANUARY, 1, 0, 0);
    // End: "Now" (or a fixed date late in 2025)
    private static final LocalDateTime END_DATE = LocalDateTime.now(); 

    private static final String[] SCHOOLS = {"Pomona", "Scripps", "CMC", "Harvey Mudd", "Pitzer"};
    private static final int[] YEARS = {2026, 2027, 2028, 2029};
    
    private static final String[] MAJORS = {
        "Africana Studies", "American Studies", "Anthropology", "Art", "Art History",
        "Asian American Studies", "Asian Studies", "Astronomy", "Biology", "Chemistry",
        "Chicana/o-Latina/o Studies", "Chinese", "Classics", "Cognitive Science",
        "Computer Science", "Dance", "Economics", "English", "Environmental Analysis",
        "French", "Gender & Women's Studies", "Geology", "German Studies", "History",
        "International Relations", "Japanese", "Late Antique-Medieval Studies",
        "Latin American Studies", "Linguistics", "Mathematics & Statistics",
        "Media Studies", "Middle Eastern Studies", "Molecular Biology", "Music",
        "Neuroscience", "Philosophy", "Philosophy, Politics & Economics",
        "Physics & Astronomy", "Politics", "Psychological Science",
        "Public Policy Analysis", "Religious Studies", "Romance Languages & Literatures",
        "Russian", "Russian & Eastern European Studies", "Science, Technology & Society",
        "Sociology", "Spanish", "Theatre"
    };

    private static final String[] FIRST_NAMES = {
        "Emma", "Liam", "Olivia", "Noah", "Ava", "Elijah", "Sophia", "Lucas", "Mia", "Ben", 
        "Charlotte", "Oliver", "Amelia", "James", "Harper", "Alexander", "Evelyn", "William"
    };
    
    private static final String[] LAST_NAMES = {
        "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", 
        "Rodriguez", "Martinez", "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson"
    };

    // Store song data as simple Objects or Arrays for generation
    // ID, Title, Genre, Artist
    private static final Object[][] SONG_DATA = {
        {1, "Espresso", "Pop", "Sabrina Carpenter"},
        {2, "Not Like Us", "Hip Hop", "Kendrick Lamar"},
        {3, "Birds of a Feather", "Pop", "Billie Eilish"},
        {4, "Good Luck, Babe!", "Synth-pop", "Chappell Roan"},
        {5, "A Bar Song (Tipsy)", "Country", "Shaboozey"},
        {6, "I Had Some Help", "Country", "Post Malone ft. Morgan Wallen"},
        {7, "Million Dollar Baby", "Hip Hop", "Tommy Richman"},
        {8, "Too Sweet", "Alternative", "Hozier"},
        {9, "Beautiful Things", "Pop", "Benson Boone"},
        {10, "Lose Control", "Soul", "Teddy Swims"},
        {11, "Like That", "Hip Hop", "Future, Metro Boomin"},
        {12, "Texas Hold 'Em", "Country", "Beyoncé"},
        {13, "We Can't Be Friends", "Pop", "Ariana Grande"},
        {14, "Saturn", "R&B", "SZA"},
        {15, "Carnival", "Hip Hop", "Kanye West"},
        {16, "Greedy", "Pop", "Tate McRae"},
        {17, "Cruel Summer", "Pop", "Taylor Swift"},
        {18, "Stick Season", "Folk", "Noah Kahan"},
        {19, "Paint The Town Red", "Hip Hop", "Doja Cat"},
        {20, "Vampire", "Pop", "Olivia Rodrigo"},
        {21, "Flowers", "Pop", "Miley Cyrus"},
        {22, "Kill Bill", "R&B", "SZA"},
        {23, "Anti-Hero", "Pop", "Taylor Swift"},
        {24, "As It Was", "Pop", "Harry Styles"},
        {25, "Rich Baby Daddy", "Hip Hop", "Drake"},
        {26, "Lovin On Me", "Hip Hop", "Jack Harlow"},
        {27, "Houdini", "Pop", "Dua Lipa"},
        {28, "Agora Hills", "R&B", "Doja Cat"},
        {29, "Snooze", "R&B", "SZA"},
        {30, "Fast Car", "Country", "Luke Combs"},
        {31, "Last Night", "Country", "Morgan Wallen"},
        {32, "Thinkin Bout Me", "Country", "Morgan Wallen"},
        {33, "White Horse", "Country", "Chris Stapleton"},
        {34, "Dance The Night", "Disco", "Dua Lipa"},
        {35, "Barbie World", "Hip Hop", "Nicki Minaj & Ice Spice"},
        {36, "Fukumean", "Hip Hop", "Gunna"},
        {37, "Ella Baila Sola", "Regional Mexican", "Eslabon Armado"},
        {38, "La Bebe", "Reggaeton", "Yng Lvcas & Peso Pluma"},
        {39, "Un x100to", "Regional Mexican", "Grupo Frontera & Bad Bunny"},
        {40, "Monaco", "Latin Trap", "Bad Bunny"},
        {41, "Perro Negro", "Reggaeton", "Bad Bunny & Feid"},
        {42, "Lala", "Reggaeton", "Myke Towers"},
        {43, "Seven", "K-Pop", "Jung Kook"},
        {44, "Super Shy", "K-Pop", "NewJeans"},
        {45, "Cupid", "K-Pop", "Fifty Fifty"},
        {46, "Standing Next to You", "K-Pop", "Jung Kook"},
        {47, "Perfect Night", "K-Pop", "Le Sserafim"},
        {48, "Drama", "K-Pop", "aespa"},
        {49, "Get A Guitar", "K-Pop", "RIIZE"},
        {50, "Baddie", "K-Pop", "IVE"}
    };

    private static final Random random = new Random();

    public static void main(String[] args) {
        System.out.println("Starting Data Generation...");
        generateUsers();
        generateSongs();
        generatePlays();
        System.out.println("SUCCESS! Users.csv, Songs.csv, and SongPlays.csv created.");
    }

    private static void generateUsers() {
        System.out.println("Generating Users.csv...");
        try (PrintWriter writer = new PrintWriter(new FileWriter("Users.csv"))) {
            writer.println("user_id,user_name,user_school,user_year,user_major");
            
            for (int i = 1; i <= NUM_USERS; i++) {
                String name = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)] + " " + 
                              LAST_NAMES[random.nextInt(LAST_NAMES.length)];
                String school = SCHOOLS[random.nextInt(SCHOOLS.length)];
                int year = YEARS[random.nextInt(YEARS.length)];
                String major = MAJORS[random.nextInt(MAJORS.length)];
                
                writer.printf("%d,%s,%s,%d,%s%n", i, name, school, year, major);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateSongs() {
        System.out.println("Generating Songs.csv...");
        try (PrintWriter writer = new PrintWriter(new FileWriter("Songs.csv"))) {
            writer.println("song_id,song_title,song_genre,song_artist");
            
            for (Object[] song : SONG_DATA) {
                // song[0]=id, song[1]=title, song[2]=genre, song[3]=artist
                writer.printf("%s,%s,%s,%s%n", song[0], song[1], song[2], song[3]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generatePlays() {
        System.out.println("Generating SongPlays.csv...");
        List<String> plays = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        long startSeconds = START_DATE.toEpochSecond(ZoneOffset.UTC);
        long endSeconds = END_DATE.toEpochSecond(ZoneOffset.UTC);
        long totalSeconds = endSeconds - startSeconds;

        for (int i = 0; i < NUM_PLAYS; i++) {
            int userId = random.nextInt(NUM_USERS) + 1;
            int songId = random.nextInt(SONG_DATA.length) + 1;
            
            // Generate random seconds to add to start date
            long randomSecsToAdd = ThreadLocalRandom.current().nextLong(totalSeconds);
            LocalDateTime playTime = START_DATE.plusSeconds(randomSecsToAdd);
            
            // Format: 2025-10-03T18:16:45 (truncate nanoseconds for cleaner output)
            String timeString = playTime.truncatedTo(ChronoUnit.SECONDS).format(formatter);
            
            plays.add(userId + "," + songId + "," + timeString);
        }

        // Sort plays by date to make it realistic
        Collections.sort(plays, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                // Extract time string (after second comma)
                String t1 = s1.split(",")[2];
                String t2 = s2.split(",")[2];
                return t1.compareTo(t2);
            }
        });

        try (PrintWriter writer = new PrintWriter(new FileWriter("SongPlays.csv"))) {
            writer.println("user_id,song_id,time");
            for (String line : plays) {
                writer.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}