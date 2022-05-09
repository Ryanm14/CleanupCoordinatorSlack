package backend;

import backend.models.Assignment;
import backend.models.CleanupHour;
import backend.sheets.response.TotalHoursSheetsModel;
import com.google.common.collect.ImmutableList;

import java.util.Set;

public interface DataRepositoryInterface {
    void reloadData();

    TotalHoursSheetsModel getUsersHourCount(String userId);

    ImmutableList<CleanupHour> getCleanupHours();

    Set<String> getUserIds();

    void reloadKeys();

    void setNewAssignedHours(ImmutableList<Assignment> assignments);
}
