package backend.models;

public class Assignment {
    private String slackId;
    private String userName;
    private CleanupHour cleanupHour;

    public Assignment(String slackId, String userName, CleanupHour cleanupHour) {
        this.slackId = slackId;
        this.userName = userName;
        this.cleanupHour = cleanupHour;
    }

    public String getSlackId() {
        return slackId;
    }

    public void setSlackId(String slackId) {
        this.slackId = slackId;
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
