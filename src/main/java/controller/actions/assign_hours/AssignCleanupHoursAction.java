package controller.actions.assign_hours;

import backend.DataRepositoryInterface;
import backend.models.Assignment;
import controller.actions.ActionRunner;
import frontend.SlackInterface;
import frontend.views.AssignCleanupHourMessageBlocks;

import java.util.Set;
import java.util.stream.Collectors;

public class AssignCleanupHoursAction extends ActionRunner.Action {

    private final Set<String> selectedHoursNames;
    private final CleanupHourAssignmentProcessor cleanupHourAssignmentProcessor;

    public AssignCleanupHoursAction(CleanupHourAssignmentProcessor cleanupHourAssignmentProcessor, Set<String> selectedHoursNames) {
        this.cleanupHourAssignmentProcessor = cleanupHourAssignmentProcessor;
        this.selectedHoursNames = selectedHoursNames;
    }

    @Override
    protected void run(DataRepositoryInterface dataRepository, SlackInterface slackInterface) {
        var hours = dataRepository.getCleanupHours().stream().filter(
                cleanupHour -> selectedHoursNames.contains(cleanupHour.getName())
        ).collect(Collectors.toList());


//        var userId = "US4MRGT09";
        var userId = "U0198GBP91A";
//        var userId = "U02CKAQ8DV2";

//        reaction = event["reaction"]
//        if reaction == "calendar":
//        blocks = [{
//            "type": "section",
//                    "text": {"type": "mrkdwn", "text": "Pick a date for me to remind you"},
//            "accessory": {
//                "type": "datepicker",
//                        "action_id": "datepicker_remind",
//                        "initial_date": "2020-05-04",
//                        "placeholder": {"type": "plain_text", "text": "Select a date"}
//            }
//        }]
//        say(
//                blocks=blocks,
//                text="Pick a date for me to remind you"
//        )

        var assignments = cleanupHourAssignmentProcessor.createAssignments(hours);
        dataRepository.saveNewAssignedHours(assignments);

        for (Assignment assignment : assignments) {
            sendAssignment(slackInterface, assignment);
        }
    }

    private void sendAssignment(SlackInterface slackInterface, Assignment assignment) {
        var blocks = AssignCleanupHourMessageBlocks.getBlocks(assignment);
        slackInterface.sendMessage(assignment.getSlackId(), "You have been assigned a cleanup hour for this week", blocks);
    }
}
