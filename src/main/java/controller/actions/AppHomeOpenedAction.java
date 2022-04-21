package controller.actions;

import backend.DataRepository;
import frontend.SlackInterface;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.view.Views.view;

public class AppHomeOpenedAction extends ActionRunner.Action {
    public AppHomeOpenedAction(String userId) {
        super(userId);
    }

    @Override
    protected void run(DataRepository dataRepository, SlackInterface slackInterface) {
        var completedHours = dataRepository.getCleanupHours(userId);
        var appHomeView = view(view -> view
                        .type("home")
                        .blocks(asBlocks(
                                section(section -> section.text(markdownText(mt -> mt.text(String.format("Welcome to the Slack Cleanup Coordinator <@%s>.", userId))))),
                                divider(),
                                section(section -> section.text(markdownText(mt -> mt.text(String.format("Currently you have: %d/5 completed cleanup hours", completedHours)))))
//                        actions(actions -> actions
//                                .elements(asElements(
//                                        button(b -> b.text(plainText(pt -> pt.text("Click me!"))).value("button1").actionId("button_1"))
//                                ))
//                        )
                        ))
        );

        slackInterface.publishView(userId, appHomeView);
    }
}
