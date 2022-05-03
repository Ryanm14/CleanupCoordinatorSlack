package controller.actions;

import backend.DataRepositoryInterface;
import backend.sheets.response.TotalHoursSheetsModel;

import java.util.Set;

public class FakeDataRepository implements DataRepositoryInterface {
    @Override
    public void reloadData() {

    }

    @Override
    public TotalHoursSheetsModel getCleanupHours(String userId) {
        return null;
    }

    @Override
    public Set<String> getUserIds() {
        return null;
    }
}
