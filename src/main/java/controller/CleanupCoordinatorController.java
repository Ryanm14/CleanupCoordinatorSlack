package controller;

import backend.DataRepository;
import com.slack.api.app_backend.events.payload.EventsApiPayload;
import com.slack.api.model.Message;
import com.slack.api.model.event.MessageEvent;
import controller.actions.*;
import frontend.SlackInterface;

public class CleanupCoordinatorController {

    private final ActionRunner actionRunner;

    public CleanupCoordinatorController(SlackInterface slackInterface) {
        actionRunner = new ActionRunner(new DataRepository(), slackInterface);
    }

    public void handleNoBallsEvent(EventsApiPayload<MessageEvent> payload) {
        var userId = payload.getEvent().getUser();
        actionRunner.runAction(new NoBallsAction(userId));
    }

    public void handleAppHomeOpenedEvent(String userId) {
        actionRunner.runAction(new AppHomeOpenedAction(userId));
    }

    public void handleAssignHoursEvent() {
        actionRunner.runAction(new AssignCleanupHours());
    }

    public void handleReloadSheetsDataEvent() {
        actionRunner.runAction(new ReloadSheetsDataAction());
    }

    public void handleAcceptHourEvent(String userId, String assignmentId, Message message) {
        actionRunner.runAction(new AcceptCleanupHourAction(userId, assignmentId, message));
    }

    public void handleSkipHourEvent(String userId, String assignmentId) {

    }

    public void handleReloadSheetsKeyEvent() {
        actionRunner.runAction(new ReloadSheetsKeysAction());
    }
}
