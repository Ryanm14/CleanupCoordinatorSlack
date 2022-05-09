package controller.actions.assign_hours;

import backend.models.Assignment;
import backend.models.CleanupHour;
import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Set;

public class CleanupHourAssignmentProcessor {

    private final List<CleanupHour> cleanupHours;
    private final List<String> users;
    private final Set<String> pendingUsers;
    private final Set<String> acceptedUsers;
    private final Set<String> skippedUsers;

    public CleanupHourAssignmentProcessor(List<CleanupHour> cleanupHours, List<String> users, Set<String> pendingUsers, Set<String> acceptedUsers, Set<String> skippedUsers) {
        this.cleanupHours = cleanupHours;
        this.users = users;
        this.pendingUsers = pendingUsers;
        this.acceptedUsers = acceptedUsers;
        this.skippedUsers = skippedUsers;
    }

    public CleanupHourAssignmentProcessor() {
        this(List.of(), List.of(), Set.of(), Set.of(), Set.of());
    }

    public ImmutableList<Assignment> createAssignments(List<CleanupHour> hours, Set<String> userIds) {
        clear(); //Maybe, i'm not sure yet

        var userId = "US4MRGT09";
        return hours.stream().limit(1).map(hour -> new Assignment(userId, "Test", hour)).collect(ImmutableList.toImmutableList());
    }

    public void userAccepted(String userId) {

    }

    public Assignment userSkippedGetNext(String userId) {
        return null;
    }

    private void clear() {
        //clear data
    }
}
