package frontend;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.socket_mode.SocketModeApp;
import com.slack.api.model.event.AppHomeOpenedEvent;
import controller.CleanupCoordinatorController;
import util.Constants;
import util.Log;

public class CleanupCoordinator {


    private final App app;
    private final SocketModeApp socketModeApp;
    private final CleanupCoordinatorController controller;

    public CleanupCoordinator() throws Exception {
        var appConfig = AppConfig.builder().singleTeamBotToken(Constants.getBotToken()).build();
        app = new App(appConfig);
        socketModeApp = new SocketModeApp(Constants.getAppToken(), app);

        setupListeners();

        var slackInterface = new SlackInterface(Constants.getBotToken());
        Log.setSlackInterface(slackInterface); //Must be before the next one
        controller = new CleanupCoordinatorController(slackInterface);
    }

    public static boolean isHouseManagerId(String userId) {
        return Constants.getHouseManagerIds().contains(userId);
    }

    public void start() throws Exception {
        socketModeApp.start();
    }

    private void setupListeners() {
        app.command("/hello", (req, ctx) -> {
            return ctx.ack(":wave: Hello!");
        });

        app.message("no balls", (payload, ctx) -> {
            controller.handleNoBallsEvent(payload);
            return ctx.ack();
        });

        app.event(AppHomeOpenedEvent.class, (payload, ctx) -> {
            var userId = payload.getEvent().getUser();
            controller.handleAppHomeOpenedEvent(userId);
            return ctx.ack();
        });

        app.blockAction("assign_hours_btn", (req, ctx) -> {
            controller.handleAssignHoursEvent();
            return ctx.ack();
        });

        app.blockAction("reload_sheets_data_btn", (req, ctx) -> {
            controller.handleReloadSheetsDataEvent();
            return ctx.ack();
        });

        app.blockAction("accept_hour_btn", (req, ctx) -> {
            var userId = ctx.getRequestUserId();
            var assignmentId = req.getPayload().getActions().get(0).getValue();
            var message = req.getPayload().getMessage();
            controller.handleAcceptHourEvent(userId, assignmentId, message);
            return ctx.ack();
        });

        app.blockAction("skip_hour_btn", (req, ctx) -> {
            var userId = ctx.getRequestUserId();
            var assignmentId = req.getPayload().getActions().get(1).getValue();
            controller.handleSkipHourEvent(userId, assignmentId);
            return ctx.ack();
        });
    }
}
