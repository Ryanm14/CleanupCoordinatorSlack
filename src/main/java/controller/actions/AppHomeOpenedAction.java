package controller.actions;

import backend.DataRepository;
import com.slack.api.model.view.View;
import frontend.CleanupCoordinator;
import frontend.SlackInterface;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.asElements;
import static com.slack.api.model.block.element.BlockElements.button;
import static com.slack.api.model.view.Views.view;

public class AppHomeOpenedAction extends ActionRunner.Action {
    public AppHomeOpenedAction(String userId) {
        super(userId);
    }

    @Override
    protected void run(DataRepository dataRepository, SlackInterface slackInterface) {
        var completedHours = dataRepository.getCleanupHours(userId);
        if (CleanupCoordinator.isHouseManagerId(userId)) {
            slackInterface.publishView(userId, getHouseManagerView());
        } else {
            slackInterface.publishView(userId, getUserView(completedHours));
        }
    }

    private View getUserView(int completedHours) {
        return view(view -> view
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
    }

    private View getHouseManagerView() {
        return view(view -> view
                .type("home")
                .blocks(asBlocks(
                        section(section -> section.text(markdownText(mt -> mt.text(String.format("Welcome to the Slack Cleanup Coordinator House Mananger! <@%s>.", userId))))),
                        divider(),
                        actions(actions -> actions
                                .elements(asElements(
                                        button(b -> b.text(plainText(pt -> pt.text("Assign Hours"))).value("button1").actionId("assign_hours_btn"))
                                ))
                        )
                ))
        );
    }
}
