package controller.actions;

import backend.DataRepositoryInterface;
import backend.sheets.response.TotalHoursSheetsModel;
import com.slack.api.model.block.SectionBlock;
import com.slack.api.model.view.View;
import frontend.CleanupCoordinator;
import frontend.SlackInterface;
import util.Log;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.asElements;
import static com.slack.api.model.block.element.BlockElements.button;
import static com.slack.api.model.view.Views.view;

public class AppHomeOpenedAction extends ActionRunner.UserAction {
    public AppHomeOpenedAction(String userId) {
        super(userId);
    }

    @Override
    protected void run(DataRepositoryInterface dataRepository, SlackInterface slackInterface) {
        if (isInvalidUserId(dataRepository, userId)) {
            slackInterface.publishView(userId, getInvalidUserIdView());
            Log.e(String.format("Unknown User Accessed the Home Screen. userId: %s", userId), null);
            return;
        }

        var totalHoursSheetsModel = dataRepository.getCleanupHours(userId);
        if (CleanupCoordinator.isHouseManagerId(userId)) {
            slackInterface.publishView(userId, getHouseManagerView());
        } else {
            slackInterface.publishView(userId, getUserView(totalHoursSheetsModel));
        }
    }

    private boolean isInvalidUserId(DataRepositoryInterface dataRepository, String userId) {
        return !dataRepository.getUserIds().contains(userId);
    }

    private View getInvalidUserIdView() {
        return view(view -> view
                .type("home")
                .blocks(asBlocks(
                        section(section -> section.text(markdownText(mt -> mt.text("*YOUR ID IS NOT IN THE SYSTEM!!! Please Contact the Housing manager.*"))))
                ))
        );
    }

    private View getUserView(TotalHoursSheetsModel totalHoursSheetsModel) {
        SectionBlock completedSection;
        if (totalHoursSheetsModel.isEmpty()) {
            completedSection = section(section -> section.text(markdownText(mt -> mt.text("*ERROR YOUR NAME IS NOT IN THE HOURS SHEETS. Please Contact the Housing manager.*"))));
        } else {
            completedSection = section(section -> section.text(markdownText(mt -> mt.text(String.format("Currently you have: %d/%d completed cleanup hours",
                    totalHoursSheetsModel.getCompletedHours(),
                    totalHoursSheetsModel.getRequiredHours())))));
        }

        return view(view -> view
                .type("home")
                .blocks(asBlocks(
                        section(section -> section.text(markdownText(mt -> mt.text(String.format("Welcome to the Slack Cleanup Coordinator %s!.", totalHoursSheetsModel.getName()))))),
                        divider(),
                        completedSection
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
                                        button(b -> b.text(plainText(pt -> pt.text("Assign Hours"))).value("button1").actionId("assign_hours_btn")),
                                        button(b -> b.text(plainText(pt -> pt.text("Reload Sheets Data"))).value("reloadDataBtn").actionId("reload_sheets_data_btn"))
                                ))
                        )
                ))
        );
    }
}
