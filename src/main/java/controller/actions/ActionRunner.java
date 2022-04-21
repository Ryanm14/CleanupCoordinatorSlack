package controller.actions;

import backend.DataRepository;
import frontend.SlackInterface;

public class ActionRunner {
    protected DataRepository dataRepository;
    protected SlackInterface slackInterface;

    public ActionRunner(DataRepository dataRepository, SlackInterface slackInterface) {
        this.dataRepository = dataRepository;
        this.slackInterface = slackInterface;
    }

    public void runAction(Action action) {
        action.run(dataRepository, slackInterface);
    }

    public abstract static class Action {

        protected final String userId;

        public Action(String userId) {
            this.userId = userId;
        }

        protected abstract void run(DataRepository dataRepository, SlackInterface slackInterface);
    }

}
