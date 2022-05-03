package frontend.views;

import backend.models.CleanupHour;
import com.slack.api.model.block.LayoutBlock;

import java.util.List;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.asElements;
import static com.slack.api.model.block.element.BlockElements.button;

public class AssignCleanupHourMessageBlocks {
    public static List<LayoutBlock> getBlocks(String userId, String assignmentId, CleanupHour assignment) {
        return asBlocks(
                section(section -> section.text(markdownText(mt -> mt.text(String.format("*Howdy <@%s>! You have been assigned a cleanup hour for this week:*", userId))))),
                divider(),
                section(section -> section.text(markdownText(mt -> mt.text(getAssignmentMessageText(assignmentId, assignment))))),
                actions(actions -> actions
                        .elements(asElements(
                                button(b -> b.text(plainText(pt -> pt.text("Accept Hour"))).value(assignmentId).actionId("accept_hour_btn")),
                                button(b -> b.text(plainText(pt -> pt.text("Skip Hour"))).value(assignmentId).actionId("skip_hour_btn"))
                        ))
                ));
    }

    private static String getAssignmentMessageText(String assignmentId, CleanupHour assignment) {
        return String.format("*Assignment*: %s\n", assignment.getName()) +
                String.format("Due Date: %s at %s\n", assignment.getDueDay(), assignment.getDueTime()) +
                String.format("Worth: %d Hour\n", assignment.getWorth()) +
                String.format("Link: %s\n", assignment.getLink()) +
                String.format("Assignment ID: %s", assignmentId);
    }
}
