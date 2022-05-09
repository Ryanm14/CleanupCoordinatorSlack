package controller.actions;

import backend.DataRepositoryInterface;
import controller.actions.assign_hours.CleanupHourAssignmentProcessor;
import frontend.SlackInterface;

import java.util.ArrayList;

public class ReloadSheetsDataAction extends ActionRunner.Action {
    private final CleanupHourAssignmentProcessor cleanupHourAssignmentProcessor;

    public ReloadSheetsDataAction(CleanupHourAssignmentProcessor cleanupHourAssignmentProcessor) {
        this.cleanupHourAssignmentProcessor = cleanupHourAssignmentProcessor;
    }

    @Override
    protected void run(DataRepositoryInterface dataRepository, SlackInterface slackInterface) {
        dataRepository.reloadData();
        var users = dataRepository.getUserIds();

        cleanupHourAssignmentProcessor.setUsers(new ArrayList<>(users));
    }
}
