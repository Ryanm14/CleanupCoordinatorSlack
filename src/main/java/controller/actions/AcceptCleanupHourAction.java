package controller.actions;

import backend.DataRepositoryInterface;
import controller.actions.assign_hours.CleanupHourAssignmentProcessor;
import frontend.SlackInterface;
import frontend.views.AssignCleanupHourMessageBlocks;

import java.time.Instant;

public class AcceptCleanupHourAction extends ActionRunner.UserAction {

    private final String channelId;
    private final String ts;
    private final CleanupHourAssignmentProcessor assignmentProcessor;

    public AcceptCleanupHourAction(String slackId, String channelId, String ts, CleanupHourAssignmentProcessor assignmentProcessor) {
        super(slackId);
        this.assignmentProcessor = assignmentProcessor;
        this.channelId = channelId;
        this.ts = ts;
    }

    @Override
    protected void run(DataRepositoryInterface dataRepository, SlackInterface slackInterface) {
        var assignment = assignmentProcessor.userAccepted(slackId);
        dataRepository.updateAssignments(assignmentProcessor.getAssignments());
        assignment.ifPresent(value -> {
            var epochSecond = (int) Instant.now().plusSeconds(30).getEpochSecond();

            slackInterface.updateMessage(channelId, "You have accepted a cleanup hour!", AssignCleanupHourMessageBlocks.getAcceptedBlocks(value), ts);
            slackInterface.scheduleMessage(channelId, String.format("Reminder to complete your cleanup Hour! It is due at %s", value.getCleanupHour().getDueTime()), epochSecond);
        });
    }
}
