package backend;

import backend.sheets.response.TotalHoursSheetsModel;

import java.util.Set;

public interface DataRepositoryInterface {
    void reloadData();

    TotalHoursSheetsModel getCleanupHours(String userId);

    Set<String> getUserIds();

    void reloadKeys();
}
