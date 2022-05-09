package backend;

import backend.models.Assignment;
import backend.models.CleanupHour;
import backend.sheets.CleanupCoordinatorSheetsAPI;
import backend.sheets.CleanupCoordinatorSheetsDataSource;
import backend.sheets.response.TotalHoursSheetsModel;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import util.Constants;
import util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataRepository implements DataRepositoryInterface {

    private CleanupCoordinatorSheetsDataSource googleSheetsDataSource;
    private ImmutableMap<String, String> userIdToNameMap;
    private ImmutableMap<String, String> nameToUserIdMap;

    private ImmutableList<TotalHoursSheetsModel> totalHoursList;
    private ImmutableList<CleanupHour> cleanupHours;
    private List<Assignment> assignedHours;

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
        Constants.setSettings(keys);
    }

    private void reloadDataFromSheets() {
        if (googleSheetsDataSource == null) {
            logGoogleSheetsNullError();
            return;
        }

        userIdToNameMap = googleSheetsDataSource.getSlackUserToName();
        nameToUserIdMap = userIdToNameMap.entrySet().stream().collect(ImmutableMap.toImmutableMap(Map.Entry::getValue, Map.Entry::getKey));
        totalHoursList = googleSheetsDataSource.getTotalHours();
        cleanupHours = googleSheetsDataSource.getCleanupHours();
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
    public TotalHoursSheetsModel getUsersHourCount(String userId) {
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
    public ImmutableList<CleanupHour> getCleanupHours() {
        return cleanupHours;
    }

    @Override
    public ImmutableSet<String> getUserIds() {
        return userIdToNameMap.keySet();
    }

    @Override
    public void reloadKeys() {
        reloadKeysFromSheets();
    }

    @Override
    public void setNewAssignedHours(ImmutableList<Assignment> assignedHours) {
        this.assignedHours = new ArrayList(assignedHours);
        googleSheetsDataSource.createNewAssignment(assignedHours);
    }

    private void logGoogleSheetsNullError() {
        Log.e("Google Sheets is not initialized - check credentials.json file");
    }
}
