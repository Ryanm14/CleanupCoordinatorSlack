package backend.sheets;

import backend.models.Assignment;
import backend.models.CleanupHour;
import backend.sheets.response.TotalHoursSheetsModel;
import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Map;

public interface SheetsDataSource {

    Map<String, String> getKeys();

    List<TotalHoursSheetsModel> getTotalHours();

    Map<String, String> getSlackUserToName();

    List<CleanupHour> getCleanupHours();

    void createNewAssignment(ImmutableList<Assignment> assignedHours);

    void updateAssignments(ImmutableList<Assignment> assignments);
}
