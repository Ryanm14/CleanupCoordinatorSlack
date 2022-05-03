package backend.sheets;

import backend.models.CleanupHour;
import backend.sheets.response.Result;
import backend.sheets.response.TotalHoursSheetsModel;
import com.google.api.services.sheets.v4.model.ValueRange;
import util.Util;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CleanupCoordinatorSheetsDataSource implements SheetsDataSource {

    private SheetsAPI sheetsAPI;

    public CleanupCoordinatorSheetsDataSource(SheetsAPI sheetsAPI) {
        this.sheetsAPI = sheetsAPI;
    }

    @Override
    public Map<String, String> getSlackUserToName() {
        Result<ValueRange> response = sheetsAPI.getMembersSheet();

        if (response.isError()) {
            return Map.of();
        }

        var values = response.getValue().getValues();

        return values.stream().collect(Collectors.toMap(
                row -> getStringFromRowSafely(row, 1), //Slack User ID
                row -> getStringFromRowSafely(row, 0) //Name
        ));
    }

    @Override
    public List<CleanupHour> getCleanupHours() {
        Result<ValueRange> response = sheetsAPI.getCleanupHours();

        if (response.isError()) {
            return List.of();
        }

        var values = response.getValue().getValues();
        return values.stream().map(row -> {
            var name = getStringFromRowSafely(row, 0);
            var dueDay = getStringFromRowSafely(row, 1);
            var dueTime = getStringFromRowSafely(row, 2);

            var worthStr = getStringFromRowSafely(row, 3);
            var worth = Util.parseIntSafely(worthStr);

            var link = getStringFromRowSafely(row, 4);

            return new CleanupHour(name, dueDay, dueTime, worth, link);
        }).collect(Collectors.toList());
    }

    @Override
    public List<TotalHoursSheetsModel> getTotalHours() {
        Result<ValueRange> response = sheetsAPI.getTotalHoursSheet();

        if (response.isError()) {
            return List.of();
        }

        var values = response.getValue().getValues();

        return values.stream().map(row -> {
            var name = getStringFromRowSafely(row, 0);

            var completedHoursStr = getStringFromRowSafely(row, 1);
            var completedHours = Util.parseIntSafely(completedHoursStr);

            var requiredHoursStr = getStringFromRowSafely(row, 2);
            var requiredHours = Util.parseIntSafely(requiredHoursStr);

            return new TotalHoursSheetsModel(name, completedHours, requiredHours);
        }).collect(Collectors.toList());
    }

    @Override
    public Map<String, String> getKeys() {
        Result<ValueRange> response = sheetsAPI.getKeysSheet();

        if (response.isError()) {
            return Map.of();
        }

        var values = response.getValue().getValues();

        return values.stream().collect(Collectors.toMap(
                row -> getStringFromRowSafely(row, 0), //Key Name
                row -> getStringFromRowSafely(row, 1) //Key Value
        ));
    }

    private String getStringFromRowSafely(List<Object> row, int position) {
        if (position >= row.size()) {
            return "";
        } else {
            return row.get(position).toString();
        }
    }
}
