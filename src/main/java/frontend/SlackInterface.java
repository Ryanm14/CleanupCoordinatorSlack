package frontend;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import util.Log;

import java.io.IOException;

public class SlackInterface {

    private final String botToken;

    public SlackInterface(String botToken) {
        this.botToken = botToken;
    }

    public void sendMessage(String id, String text) {
        var client = Slack.getInstance().methods();
        try {
            client.chatPostMessage(r -> r
                    // The token you used to initialize your app is stored in the `context` object
                    .token(botToken)
                    // Payload message should be posted in the channel where original message was heard
                    .channel(id)
                    .text(text)
            );
        } catch (IOException | SlackApiException e) {
            Log.e(String.format("error: %s", e.getMessage()), e);
        }
    }
}
