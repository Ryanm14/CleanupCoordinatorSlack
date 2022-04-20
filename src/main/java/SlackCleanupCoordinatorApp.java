import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.socket_mode.SocketModeApp;
import frontend.CleanupCoordinator;
import io.github.cdimascio.dotenv.Dotenv;

public class SlackCleanupCoordinatorApp {

    public static void main(String[] args) throws Exception {
        var dotenv = Dotenv.load();
        String botToken = dotenv.get("SLACK_BOT_TOKEN");
        String appToken = dotenv.get("SLACK_APP_TOKEN");

        var cleanupCoordinator = new CleanupCoordinator(botToken, appToken);
        cleanupCoordinator.start();
    }

}
