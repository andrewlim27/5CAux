package fivecaux;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Runs the main program and dislays the user interface
 * 
 * @author Andrew Lim
 * @author Julian Chumacero
 * @author Alexander Adhikari
 */
public class Main {

    //store state of active filters
    //Null or -1 means filter is turned off
    private static String activeSchool = null;
    private static int activeYear = -1;
    private static String activeMajor = null;
    private static String activeGenre = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Initializing Music Manager and loading data...");
        MusicManager manager = new MusicManager();

        boolean running = true;

        while (running) {
            printMenu();
            System.out.print(">> Select an option: ");
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    showTopSongsAllTime(manager);
                    break;
                case "2":
                    showTopSongsYear(manager);
                    break;
                case "3":
                    showTopSongsSemester(manager);
                    break;
                case "4":
                    showTopSongs24Hours(manager);
                    break;
                case "5":
                    modifyFilters(scanner);
                    break;
                case "6":
                    clearFilters();
                    System.out.println("\n[!] All filters cleared.");
                    break;
                case "7":
                    findMatch(scanner, manager);
                    break;
                case "8":
                    running = false;
                    System.out.println("Exiting application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid selection. Please try again.");
            }

            if (running) {
                System.out.println("\n(Press Enter to continue)");
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    /**
     * starts with the full history, passes the data through every active filter in a chain
     * allows for filters to be added and removed without having to clear all filters
     */
    private static ArrayList<SongPlay> getCurrentFilteredData(MusicManager manager) {
        //start with complete raw history
        ArrayList<SongPlay> pipeline = manager.getListeningHistory();

        //if School filter active, pipe data through it
        if (activeSchool != null) {
            pipeline = manager.filterSchool(pipeline, activeSchool);
        }

        //if Year filter is active, pipe data through it
        if (activeYear != -1) {
            pipeline = manager.filterYear(pipeline, activeYear);
        }

        //if Major filter is active, pipe data through it
        if (activeMajor != null) {
            pipeline = manager.filterMajor(pipeline, activeMajor);
        }

        //if Genre filter is active, pipe data through it
        if (activeGenre != null) {
            pipeline = manager.filterGenre(pipeline, activeGenre);
        }

        return pipeline;
    }

    //display methods
    private static void showTopSongsAllTime(MusicManager manager) {
        System.out.println("\n--- TOP SONGS (All Time) ---");
        //get filtered data
        ArrayList<SongPlay> data = getCurrentFilteredData(manager);
        //sort by plays
        ArrayList<Song> ranked = manager.sortByPlays(data);
        printList(ranked);
    }

    private static void showTopSongsYear(MusicManager manager) {
        System.out.println("\n--- TOP SONGS (This Year) ---");
        //get filtered data
        ArrayList<SongPlay> data = getCurrentFilteredData(manager);
        //filter by year
        ArrayList<Song> ranked = manager.topSongsYear(data);
        printList(ranked);
    }

    private static void showTopSongsSemester(MusicManager manager) {
        System.out.println("\n--- TOP SONGS (This Semester) ---");
        //get filtered data
        ArrayList<SongPlay> data = getCurrentFilteredData(manager);
        //filter by semester
        ArrayList<Song> ranked = manager.topSongsSemester(data);
        printList(ranked);
    }

    private static void showTopSongs24Hours(MusicManager manager) {
        System.out.println("\n--- TOP SONGS (Past 24 Hrs) ---");
        //get filtered data
        ArrayList<SongPlay> data = getCurrentFilteredData(manager);
        //filter by 24hrs
        ArrayList<Song> ranked = manager.topSongsDay(data);
        printList(ranked);
    }

    private static void printList(ArrayList<Song> songs) {
        if (songs.isEmpty()) {
            System.out.println("No songs found matching the current criteria.");
            return;
        }
        
        int limit = Math.min(10, songs.size());
        for (int i = 0; i < limit; i++) {
            Song s = songs.get(i);
            System.out.printf("%d. %s (%d plays) [%s - %s]%n", 
                (i + 1), s.getTitle(), s.getPlays(), s.getArtist(), s.getGenre());
        }
    }

    private static void findMatch(Scanner scanner, MusicManager manager){
        System.out.print("Enter userId: ");
        int userId = -1;
        try {
            userId = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid userId entered.");
        }
        manager.printTopMatchForUser(userId);
    }

    //menu methods
    private static void printMenu() {
        System.out.println("\n========================================");
        System.out.println("      5C MUSIC ANALYTICS DASHBOARD      ");
        System.out.println("========================================");
        System.out.println("Current Filters:");
        System.out.println("   School : " + (activeSchool == null ? "ALL" : activeSchool));
        System.out.println("   Year   : " + (activeYear == -1 ? "ALL" : activeYear));
        System.out.println("   Major  : " + (activeMajor == null ? "ALL" : activeMajor));
        System.out.println("   Genre  : " + (activeGenre == null ? "ALL" : activeGenre));
        System.out.println("----------------------------------------");
        System.out.println("1. View Top Songs (All Time)");
        System.out.println("2. View Top Songs (This Year)");
        System.out.println("3. View Top Songs (This Semester)");
        System.out.println("4. View Top Songs (Last 24 Hrs)");
        System.out.println("5. Add / Change Filter");
        System.out.println("6. Clear All Filters");
        System.out.println("7. Find Your Match");
        System.out.println("8. Exit");
        System.out.println("========================================");
    }

    private static void modifyFilters(Scanner scanner) {
        System.out.println("\n--- FILTER SETTINGS ---");
        System.out.println("1. Set School");
        System.out.println("2. Set Year");
        System.out.println("3. Set Major");
        System.out.println("4. Set Genre");
        System.out.print("Which filter to change? ");
        
        String choice = scanner.nextLine();
        
        switch (choice) {
            case "1":
                System.out.print("Enter School (Pomona, Scripps, CMC, Harvey Mudd, Pitzer): ");
                activeSchool = scanner.nextLine().trim();
                break;
            case "2":
                System.out.print("Enter Year (e.g. 2026): ");
                try {
                    activeYear = Integer.parseInt(scanner.nextLine().trim());
                } catch (Exception e) {
                    System.out.println("Invalid year entered.");
                }
                break;
            case "3":
                System.out.print("Enter Major (e.g. Computer Science): ");
                activeMajor = scanner.nextLine().trim();
                break;
            case "4":
                System.out.print("Enter Genre (e.g. Pop): ");
                activeGenre = scanner.nextLine().trim();
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private static void clearFilters() {
        activeSchool = null;
        activeYear = -1;
        activeMajor = null;
        activeGenre = null;
    }
}