import frontend.CleanupCoordinator;
import util.Constants;

public class SlackCleanupCoordinatorApp {

    public static void main(String[] args) throws Exception {
        Constants.init();

        var cleanupCoordinator = new CleanupCoordinator();
        cleanupCoordinator.start();

    }

}
