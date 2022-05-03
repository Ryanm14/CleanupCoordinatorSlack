package backend.models;

public class Assignment {
    private String name;
    private String dueDay;
    private String dueTime;
    private int worth;
    private String link;

    public Assignment(String name, String dueDay, String dueTime, int worth, String link) {
        this.name = name;
        this.dueDay = dueDay;
        this.dueTime = dueTime;
        this.worth = worth;
        this.link = link;
    }

    public Assignment() {

    }

    public String getName() {
        return name;
    }

    public String getDueDay() {
        return dueDay;
    }

    public String getDueTime() {
        return dueTime;
    }

    public int getWorth() {
        return worth;
    }

    public String getLink() {
        return link;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "name='" + name + '\'' +
                ", dueDay='" + dueDay + '\'' +
                ", dueTime='" + dueTime + '\'' +
                ", worth=" + worth +
                ", link='" + link + '\'' +
                '}';
    }
}
