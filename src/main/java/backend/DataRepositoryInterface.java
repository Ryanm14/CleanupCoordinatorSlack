package backend;

import backend.models.CleanupHour;
import backend.sheets.response.TotalHoursSheetsModel;

import java.util.List;
import java.util.Set;

public interface DataRepositoryInterface {
    void reloadData();

    TotalHoursSheetsModel getUsersHourCount(String userId);

    List<CleanupHour> getCleanupHours();

    Set<String> getUserIds();

    void reloadKeys();
}
