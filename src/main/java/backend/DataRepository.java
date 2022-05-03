package backend;

import backend.sheets.GoogleSheetsDataSource;
import backend.sheets.response.Result;
import backend.sheets.response.TotalHoursSheetsModel;
import util.Log;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DataRepository implements DataRepositoryInterface {

    private final GoogleSheetsDataSource googleSheetsDataSource;
    private Map<String, String> userIdToNameMap;
    private Map<String, String> nameToUserIdMap;

    private List<TotalHoursSheetsModel> totalHoursList;

    public DataRepository() {
        googleSheetsDataSource = new GoogleSheetsDataSource();
        reloadDataFromSheets();
    }

    private void reloadDataFromSheets() {
        userIdToNameMap = loadData(googleSheetsDataSource.getSlackUserToName(), Collections.emptyMap());
        nameToUserIdMap = userIdToNameMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
        totalHoursList = loadData(googleSheetsDataSource.getTotalHours(), Collections.emptyList());
    }

    private <T> T loadData(Result<T> result, T defaultValue) {
        if (result.isOk()) {
            return result.getValue();
        } else {
            Log.e(result.getError());
        }
        return defaultValue;
    }

    @Override
    public void reloadData() {
        reloadDataFromSheets();
    }

    @Override
    public TotalHoursSheetsModel getCleanupHours(String userId) {
        if (userIdToNameMap.containsKey(userId)) {
            var name = userIdToNameMap.get(userId);
            return totalHoursList.stream()
                    .filter(model -> name.equals(model.getName()))
                    .findFirst()
                    .orElse(TotalHoursSheetsModel.empty());
        } else {
            Log.e(String.format("Error getting cleanup hours for %s", userId));
        }
        return TotalHoursSheetsModel.empty();
    }

    @Override
    public Set<String> getUserIds() {
        return userIdToNameMap.keySet();
    }
}
