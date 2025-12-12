package fivecaux;


/**
 * Each object represents a user and their respective ID, name, school, year, and major
 * 
 * @author Julian Chumacero
 * @author Andrew Lim
 * @author Alexander Adhikari
 */
public class User {
    private final int userId; // corresponding integer to a user
    private final String name; // name of the user
    private final String school; // school of user
    private final int year; // year of user
    private final String major; // major of user

    public User(int userId, String name, String school, int year, String major){
        this.userId = userId;
        this.name = name;
        this.school = school;
        this.year = year;
        this.major = major;
    }

    public int getUserId(){
        return userId;
    }

    public String getName(){
        return name;
    }

    public String getSchool(){
        return school;
    }
    
    public int getYear(){
        return year;
    }

    public String getMajor(){
        return major;
    }
}
