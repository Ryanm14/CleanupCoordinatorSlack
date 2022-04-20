import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.socket_mode.SocketModeApp;
import io.github.cdimascio.dotenv.Dotenv;

public class SlackCleanupCoordinator {

    public static void main(String[] args) throws Exception {
        Dotenv dotenv = Dotenv.load();
        String botToken = dotenv.get("SLACK_BOT_TOKEN");
        String appToken = dotenv.get("SLACK_APP_TOKEN");

        AppConfig appConfig = AppConfig.builder().singleTeamBotToken(botToken).build();
        App app = new App(appConfig);
        SocketModeApp socketModeApp = new SocketModeApp(appToken, app);

        app.command("/hello", (req, ctx) -> {
            return ctx.ack(":wave: Hello!");
        });

        app.message("hi", (payload, ctx) -> {
            ctx.say("Hello, <@" + payload.getEvent().getUser() + ">");
            return ctx.ack();
        });

        socketModeApp.start();
    }

}
