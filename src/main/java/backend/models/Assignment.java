package backend.models;

public class Assignment {
    private String userId;
    private String userName;
    private CleanupHour cleanupHour;

    public Assignment(String userId, String userName, CleanupHour cleanupHour) {
        this.userId = userId;
        this.userName = userName;
        this.cleanupHour = cleanupHour;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public CleanupHour getCleanupHour() {
        return cleanupHour;
    }

    public void setCleanupHour(CleanupHour cleanupHour) {
        this.cleanupHour = cleanupHour;
    }
}
