package controller;

import com.slack.api.app_backend.events.payload.EventsApiPayload;
import com.slack.api.model.event.MessageEvent;
import frontend.SlackInterface;

public class CleanupCoordinatorController {

    private final SlackInterface slackInterface;

    public CleanupCoordinatorController(SlackInterface slackInterface) {
        this.slackInterface = slackInterface;
    }

    public void handleHiCommand(EventsApiPayload<MessageEvent> payload) {
        var userId = payload.getEvent().getUser();
        slackInterface.sendMessage(userId, "Hello, <@" + payload.getEvent().getUser() + ">");
    }
}
