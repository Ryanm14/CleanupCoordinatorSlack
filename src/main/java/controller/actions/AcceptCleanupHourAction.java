package controller.actions;

import backend.DataRepositoryInterface;
import controller.actions.assign_hours.CleanupHourAssignmentProcessor;
import frontend.SlackInterface;
import frontend.views.AssignCleanupHourMessageBlocks;

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
        assignment.ifPresent(value -> slackInterface.updateMessage(channelId, "You have accepted a cleanup hour!", AssignCleanupHourMessageBlocks.getAcceptedBlocks(value), ts));
    }
}
