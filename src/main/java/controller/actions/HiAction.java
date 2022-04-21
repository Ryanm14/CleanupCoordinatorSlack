package controller.actions;

import backend.DataRepository;
import frontend.SlackInterface;

public class HiAction extends ActionRunner.Action {

    public HiAction(String userId) {
        super(userId);
    }

    @Override
    protected void run(DataRepository dataRepository, SlackInterface slackInterface) {
        slackInterface.sendMessage(userId, String.format("Hello, <@%s>", userId));
    }
}
