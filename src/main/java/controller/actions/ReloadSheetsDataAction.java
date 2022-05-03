package controller.actions;

import backend.DataRepositoryInterface;
import frontend.SlackInterface;

public class ReloadSheetsDataAction extends ActionRunner.Action {
    @Override
    protected void run(DataRepositoryInterface dataRepository, SlackInterface slackInterface) {
        dataRepository.reloadData();
    }
}
