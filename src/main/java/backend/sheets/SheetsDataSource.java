package backend.sheets;

import backend.models.CleanupHour;
import backend.sheets.response.TotalHoursSheetsModel;

import java.util.List;
import java.util.Map;

public interface SheetsDataSource {

    Map<String, String> getKeys();

    List<TotalHoursSheetsModel> getTotalHours();

    Map<String, String> getSlackUserToName();

    List<CleanupHour> getCleanupHours();
}
