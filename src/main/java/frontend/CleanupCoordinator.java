package frontend;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.socket_mode.SocketModeApp;
import com.slack.api.model.event.AppHomeOpenedEvent;
import controller.CleanupCoordinatorController;

public class CleanupCoordinator {


    private final App app;
    private final SocketModeApp socketModeApp;
    private final CleanupCoordinatorController controller;

    public CleanupCoordinator(String botToken, String appToken) throws Exception {
        var appConfig = AppConfig.builder().singleTeamBotToken(botToken).build();
        app = new App(appConfig);
        socketModeApp = new SocketModeApp(appToken, app);

        setupListeners();

        var slackInterface = new SlackInterface(botToken);
        controller = new CleanupCoordinatorController(slackInterface);
    }

    private void setupListeners() {
        app.command("/hello", (req, ctx) -> {
            return ctx.ack(":wave: Hello!");
        });

        app.message("hi", (payload, ctx) -> {
            controller.handleHiCommand(payload);
            return ctx.ack();
        });

        app.event(AppHomeOpenedEvent.class, (payload, ctx) -> {
            controller.handleAppHomeOpenedEvent(payload);
            return ctx.ack();
        });
    }

    public void start() throws Exception {
        socketModeApp.start();
    }
}
