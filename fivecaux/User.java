package fivecaux;
public class User {
    private final int userId;
    private final String name;
    private final String school;
    private final int year;
    private final String major;

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
