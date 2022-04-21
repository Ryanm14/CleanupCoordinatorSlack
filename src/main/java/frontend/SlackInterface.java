package frontend;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.model.view.View;
import util.Log;

public class SlackInterface {

    private final String botToken;
    private final MethodsClient client;

    public SlackInterface(String botToken) {
        this.botToken = botToken;
        client = Slack.getInstance().methods();
    }

    public void sendMessage(String id, String text) {
        try {
            client.chatPostMessage(r -> r
                    .token(botToken)
                    .channel(id)
                    .text(text));
        } catch (Exception e) {
            Log.e(String.format("Couldn't send message - error: %s", e.getMessage()), e);
        }
    }

    public void publishView(String userId, View view) {
        try {
            client.viewsPublish(r -> r
                    .token(botToken)
                    .userId(userId)
                    .view(view));
        } catch (Exception e) {
            Log.e(String.format("Couldn't publish view - error: %s", e.getMessage()), e);
        }
    }
}
