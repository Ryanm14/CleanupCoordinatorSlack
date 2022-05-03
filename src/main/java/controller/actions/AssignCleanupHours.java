package controller.actions;

import backend.DataRepositoryInterface;
import backend.models.Assignment;
import frontend.SlackInterface;
import frontend.views.AssignCleanupHourMessageBlocks;

import java.util.List;
import java.util.UUID;

public class AssignCleanupHours extends ActionRunner.Action {

    public AssignCleanupHours() {

    }

    @Override
    protected void run(DataRepositoryInterface dataRepository, SlackInterface slackInterface) {
//        var userId = "US4MRGT09";
        var userId = "U01LJCXTYSC";

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
        var assignmentId = UUID.randomUUID().toString();
        var assignment = List.of(new Assignment()).get(0);
        var blocks = AssignCleanupHourMessageBlocks.getBlocks(userId, assignmentId, assignment);

        slackInterface.sendMessage(userId, "You have been assigned a cleanup hour for this week", blocks);
    }
}
