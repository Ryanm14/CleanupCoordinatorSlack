package frontend;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.view.View;
import util.Log;

import java.util.List;

public class SlackInterface {

    private final String botToken;
    private final MethodsClient client;

    public SlackInterface(String botToken) {
        this.botToken = botToken;
        client = Slack.getInstance().methods();
    }

    public void sendMessage(String id, String text) {
        sendMessage(id, text, null);
    }

    public void sendMessage(String id, String text, List<LayoutBlock> blocks) {
        try {
            client.chatPostMessage(r -> r
                    .token(botToken)
                    .channel(id)
                    .blocks(blocks)
                    .text(text));
        } catch (Exception e) {
            Log.e(String.format("Couldn't send message - error: %s", e.getMessage()), e);
        }
    }

    public void sendMessage(String id, List<LayoutBlock> blocks) {
        sendMessage(id, "", blocks);
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

    public void updateMessage(String id, String text, List<LayoutBlock> blocks, String ts) {
        try {
            client.chatUpdate(r -> r
                    .token(botToken)
                    .asUser(true)
                    .channel(id)
                    .ts(ts)
                    .blocks(blocks)
                    .text(text));
        } catch (Exception e) {
            Log.e(String.format("Couldn't update message - error: %s", e.getMessage()), e);
        }
    }
}
