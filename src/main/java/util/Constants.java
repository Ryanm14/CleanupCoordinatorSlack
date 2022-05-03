package util;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Constants {
    private static String SHEETS_DATA_FILE_ID_KEY = "";
    private static String HOUSE_MANAGER_KEY = "";
    private static String SHEETS_MEMBERS_RANGE_KEY = "";
    private static String SHEETS_MEMBERS_RANGE_KEY_DEFAULT = "";
    private static String SHEETS_TOTAL_HOURS_RANGE_KEY = "";
    private static String SHEETS_TOTAL_HOURS_RANGE_KEY_DEFAULT = "";


    private static String botToken = "";
    private static String appToken = "";

    private static String sheetsCredentialsFilePath = "";
    private static String sheetsAppName = "";
    private static Map<String, String> keys = Map.of();
    private static String sheetsKeyRange = "";
    private static String sheetsKeyFileId = "";

    public static void init() {
        var dotenv = Dotenv.load();
        botToken = dotenv.get("SLACK_BOT_TOKEN");
        appToken = dotenv.get("SLACK_APP_TOKEN");

        sheetsCredentialsFilePath = dotenv.get("SHEETS_CREDENTIALS_FILE_PATH");
        sheetsAppName = dotenv.get("SHEETS_APP_NAME");

        sheetsKeyRange = dotenv.get("SHEETS_KEYS_RANGE");
        sheetsKeyFileId = dotenv.get("SHEETS_KEY_FILE_ID");

        loadKeyNamesAndDefaults(dotenv);
    }

    private static void loadKeyNamesAndDefaults(Dotenv dotenv) {
        SHEETS_DATA_FILE_ID_KEY = dotenv.get("SHEETS_DATA_FILE_ID_KEY");

        HOUSE_MANAGER_KEY = dotenv.get("HOUSE_MANAGER_KEY");

        SHEETS_MEMBERS_RANGE_KEY = dotenv.get("SHEETS_MEMBERS_RANGE_KEY");
        SHEETS_MEMBERS_RANGE_KEY_DEFAULT = dotenv.get("SHEETS_MEMBERS_RANGE_KEY_DEFAULT");

        SHEETS_TOTAL_HOURS_RANGE_KEY = dotenv.get("SHEETS_TOTAL_HOURS_RANGE_KEY");
        SHEETS_TOTAL_HOURS_RANGE_KEY_DEFAULT = dotenv.get("SHEETS_TOTAL_HOURS_RANGE_KEY_DEFAULT");
    }

    public static String getBotToken() {
        return botToken;
    }

    public static String getAppToken() {
        return appToken;
    }

    public static Set<String> getHouseManagerIds() {
        var rawIds = keys.getOrDefault(HOUSE_MANAGER_KEY, "");
        return Stream.of(rawIds.split(","))
                .map(String::trim)
                .collect(Collectors.toSet());
    }

    public static String getSheetsDataFileId() {
        return keys.getOrDefault(SHEETS_DATA_FILE_ID_KEY, "");
    }

    public static String getSheetsMemberRange() {
        return keys.getOrDefault(SHEETS_MEMBERS_RANGE_KEY, SHEETS_MEMBERS_RANGE_KEY_DEFAULT);
    }

    public static String getSheetsTotalHoursRange() {
        return keys.getOrDefault(SHEETS_TOTAL_HOURS_RANGE_KEY, SHEETS_TOTAL_HOURS_RANGE_KEY_DEFAULT);
    }

    public static String getSheetsKeyRange() {
        return sheetsKeyRange;
    }

    public static void setKeys(Map<String, String> keys) {
        Constants.keys = keys;
    }

    public static String getSheetsKeyFileId() {
        return sheetsKeyFileId;
    }

    public static String getSheetsCredentialsFilePath() {
        return sheetsCredentialsFilePath;
    }

    public static String getSheetsAppName() {
        return sheetsAppName;
    }
}
