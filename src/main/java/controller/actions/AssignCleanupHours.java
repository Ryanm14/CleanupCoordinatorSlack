package controller.actions;

import backend.DataRepositoryInterface;
import backend.models.Assignment;
import frontend.SlackInterface;
import util.Constants;

import java.util.UUID;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.asElements;
import static com.slack.api.model.block.element.BlockElements.button;

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
        var assignment = Constants.getAssignments().get(0);
        var blocks = asBlocks(
                section(section -> section.text(markdownText(mt -> mt.text(String.format("*Howdy <@%s>! You have been assigned a cleanup hour for this week:*", userId))))),
                divider(),
                section(section -> section.text(markdownText(mt -> mt.text(getAssignmentMessageText(assignmentId, assignment))))),
                actions(actions -> actions
                        .elements(asElements(
                                button(b -> b.text(plainText(pt -> pt.text("Accept Hour"))).value(assignmentId).actionId("accept_hour_btn")),
                                button(b -> b.text(plainText(pt -> pt.text("Skip Hour"))).value(assignmentId).actionId("skip_hour_btn"))
                        ))
                ));
        slackInterface.sendMessage(userId, "You have been assigned a cleanup hour for this week", blocks);
    }

    private String getAssignmentMessageText(String assignmentId, Assignment assignment) {
        return String.format("*Assignment*: %s\n", assignment.getName()) +
                String.format("Due Date: %s at %s\n", assignment.getDueDay(), assignment.getDueTime()) +
                String.format("Worth: %d Hour\n", assignment.getWorth()) +
                String.format("Link: %s\n", assignment.getLink()) +
                String.format("Assignment ID: %s", assignmentId);
    }
}
