package controller;

import backend.DataRepository;
import com.slack.api.app_backend.events.payload.EventsApiPayload;
import com.slack.api.model.event.AppHomeOpenedEvent;
import com.slack.api.model.event.MessageEvent;
import controller.actions.ActionRunner;
import controller.actions.AppHomeOpenedAction;
import controller.actions.HiAction;
import frontend.SlackInterface;

public class CleanupCoordinatorController {

    private final ActionRunner actionRunner;

    public CleanupCoordinatorController(SlackInterface slackInterface) {
        actionRunner = new ActionRunner(new DataRepository(), slackInterface);
    }

    public void handleHiCommand(EventsApiPayload<MessageEvent> payload) {
        var userId = payload.getEvent().getUser();
        actionRunner.runAction(new HiAction(userId));
    }

    public void handleAppHomeOpenedEvent(EventsApiPayload<AppHomeOpenedEvent> payload) {
        var userId = payload.getEvent().getUser();
        actionRunner.runAction(new AppHomeOpenedAction(userId));
    }
}
