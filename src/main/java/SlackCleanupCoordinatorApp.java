import frontend.CleanupCoordinator;
import io.github.cdimascio.dotenv.Dotenv;

public class SlackCleanupCoordinatorApp {

    public static void main(String[] args) throws Exception {
        var dotenv = Dotenv.load();
        String botToken = dotenv.get("SLACK_BOT_TOKEN");
        String appToken = dotenv.get("SLACK_APP_TOKEN");
        String houseManagerId = dotenv.get("HOUSE_MANAGER_ID");

        var cleanupCoordinator = new CleanupCoordinator(botToken, appToken, houseManagerId);
        cleanupCoordinator.start();
    }

}
