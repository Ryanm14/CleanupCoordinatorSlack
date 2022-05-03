package controller.actions;

import backend.DataRepositoryInterface;
import com.slack.api.model.Message;
import frontend.SlackInterface;

import static com.slack.api.model.block.Blocks.asBlocks;
import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;

public class AcceptCleanupHourAction extends ActionRunner.UserAction {

    private String assignmentId;
    private Message message;

    public AcceptCleanupHourAction(String userId, String assignmentId, Message message) {
        super(userId);
        this.assignmentId = assignmentId;
        this.message = message;
    }

    @Override
    protected void run(DataRepositoryInterface dataRepository, SlackInterface slackInterface) {
        var ts = message.getTs();
        slackInterface.updateMessage(message.getChannel(), "TEST", asBlocks(
                section(section -> section.text(markdownText(mt -> mt.text("*HEDITED*"))))
        ), ts);
    }
}
