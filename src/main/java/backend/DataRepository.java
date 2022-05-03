package backend;

import backend.sheets.CleanupCoordinatorSheetsAPI;
import backend.sheets.CleanupCoordinatorSheetsDataSource;
import backend.sheets.response.TotalHoursSheetsModel;
import util.Constants;
import util.Log;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DataRepository implements DataRepositoryInterface {

    private CleanupCoordinatorSheetsDataSource googleSheetsDataSource;
    private Map<String, String> userIdToNameMap;
    private Map<String, String> nameToUserIdMap;

    private List<TotalHoursSheetsModel> totalHoursList;

    public DataRepository() {
        try {
            googleSheetsDataSource = new CleanupCoordinatorSheetsDataSource(new CleanupCoordinatorSheetsAPI());
            reloadKeysFromSheets();
            reloadDataFromSheets();
        } catch (Exception e) {
            Log.e(e.getMessage(), e);
        }
    }

    private void reloadKeysFromSheets() {
        if (googleSheetsDataSource == null) {
            logGoogleSheetsNullError();
            return;
        }

        var keys = googleSheetsDataSource.getKeys();
        Constants.setKeys(keys);
    }

    private void reloadDataFromSheets() {
        if (googleSheetsDataSource == null) {
            logGoogleSheetsNullError();
            return;
        }

        userIdToNameMap = googleSheetsDataSource.getSlackUserToName();
        nameToUserIdMap = userIdToNameMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
        totalHoursList = googleSheetsDataSource.getTotalHours();
    }

    @Override
    public void reloadData() {
        if (googleSheetsDataSource == null) {
            logGoogleSheetsNullError();
            return;
        }

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

    @Override
    public void reloadKeys() {
        reloadKeysFromSheets();
    }

    private void logGoogleSheetsNullError() {
        Log.e("Google Sheets is not initialized - check credentials.json file");
    }
}
