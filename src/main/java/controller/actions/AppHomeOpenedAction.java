package controller.actions;

import backend.DataRepositoryInterface;
import com.slack.api.model.view.View;
import frontend.CleanupCoordinator;
import frontend.SlackInterface;
import frontend.views.HouseManagerAppHomeView;
import frontend.views.UserAppHomeView;
import util.Log;

import static com.slack.api.model.block.Blocks.asBlocks;
import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.view.Views.view;

public class AppHomeOpenedAction extends ActionRunner.UserAction {
    public AppHomeOpenedAction(String userId) {
        super(userId);
    }

    @Override
    final protected void run(DataRepositoryInterface dataRepository, SlackInterface slackInterface) {
        if (isInvalidUserId(dataRepository, userId)) {
            slackInterface.publishView(userId, getInvalidUserIdView());
            Log.e(String.format("Unknown User Accessed the Home Screen. userId: %s", userId), null);
            return;
        }

        if (CleanupCoordinator.isHouseManagerId(userId)) {
            slackInterface.publishView(userId, HouseManagerAppHomeView.getView(userId));
        } else {
            var totalHoursSheetsModel = dataRepository.getCleanupHours(userId);
            slackInterface.publishView(userId, UserAppHomeView.getView(totalHoursSheetsModel));
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
}
