package controller.actions.assign_hours;

import backend.models.AcceptedStatus;
import backend.models.Assignment;
import backend.models.CleanupHour;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CleanupHourAssignmentProcessor {

    private List<Assignment> currentAssignments;

    private List<CleanupHour> selectedCleanupHours;
    private List<String> users;

    public CleanupHourAssignmentProcessor(List<CleanupHour> cleanupHours, List<String> users) {
        this.selectedCleanupHours = cleanupHours;
        this.users = users;
        this.currentAssignments = new ArrayList<>();
    }

    public CleanupHourAssignmentProcessor() {
        this(List.of(), List.of());
    }

    public ImmutableList<Assignment> createAssignments(List<CleanupHour> selectedCleanupHours) {
        this.selectedCleanupHours = selectedCleanupHours;

        var userId = "US4MRGT09";
        currentAssignments = selectedCleanupHours.stream().limit(1).map(hour -> new Assignment(userId, "Test", hour)).collect(ImmutableList.toImmutableList());

        return getAssignments();
    }

    public ImmutableList<Assignment> getAssignments() {
        return ImmutableList.copyOf(currentAssignments);
    }

    public Optional<Assignment> userAccepted(String slackId) {
        var assignment = currentAssignments.stream()
                .filter(a -> a.getSlackId().equals(slackId))
                .findFirst();

        assignment.ifPresent(a -> a.setStatus(AcceptedStatus.ACCEPTED));
        return assignment;
    }

//    public Assignment userSkippedGetNext(String userId) {
//        return null;
//    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}
