public class User {
    private int id;
    private String username;
    private int school;
    private int year;
    private String major;
    static int num_users;

    public User(String username, int school, int year){
        num_users++;
        id = num_users;
        this.username = username;
        this.school = school;
        this.year = year;
        major = null;
    }

    public User(String username, int school, int year, String major){
        num_users++;
        id = num_users;
        this.username = username;
        this.school = school;
        this.year = year;
        this.major = major;
    }

    public int getId(){
        return id;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public int getSchool(){
        return school;
    }

    public void setSchool(int school){
        this.school = school;
    }

    public int getYear(){
        return year;
    }

    public void setYear(int year){
        this.year = year;
    }

    public String getMajor(){
        return major;
    }

    public void setMajor(String major){
        this.major = major;
    }
}