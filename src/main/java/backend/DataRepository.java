package backend;

import backend.sheets.GoogleSheetsDataSource;

import java.util.Map;
import java.util.Random;

public class DataRepository {

    private GoogleSheetsDataSource googleSheetsDataSource;
    private Map<String, String> userIdToNameMap;

    public DataRepository() {
        googleSheetsDataSource = new GoogleSheetsDataSource();
        userIdToNameMap = googleSheetsDataSource.getSlackUserToName();
    }

    public int getCleanupHours(String userId) {
        return new Random().nextInt(5);
    }

    public boolean isInvalidUserId(String userId) {
        return !userIdToNameMap.containsKey(userId);
    }
}
