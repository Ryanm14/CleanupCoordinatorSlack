package controller.actions;

import backend.DataRepositoryInterface;
import backend.models.CleanupHour;
import frontend.SlackInterface;
import frontend.views.AssignCleanupHourMessageBlocks;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class AssignCleanupHoursAction extends ActionRunner.Action {

    private final Set<String> selectedHoursNames;

    public AssignCleanupHoursAction(Set<String> selectedHoursNames) {
        this.selectedHoursNames = selectedHoursNames;
    }

    @Override
    protected void run(DataRepositoryInterface dataRepository, SlackInterface slackInterface) {
        var hours = dataRepository.getCleanupHours().stream().filter(
                cleanupHour -> selectedHoursNames.contains(cleanupHour.getName())
        ).collect(Collectors.toList());


//        var userId = "US4MRGT09";
        var userId = "U02CKAQ8DV2";

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

        for (CleanupHour hour : hours) {
            var assignmentId = UUID.randomUUID().toString();
            var blocks = AssignCleanupHourMessageBlocks.getBlocks(userId, assignmentId, hour);

            slackInterface.sendMessage(userId, "You have been assigned a cleanup hour for this week", blocks);
        }
    }
}
