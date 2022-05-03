package util;

import backend.models.Assignment;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Collections;
import java.util.List;

public class Constants {
    private static String botToken = "";
    private static String appToken = "";
    private static List<String> houseManagerIds = Collections.singletonList("");
    private static String sheetsFile = "";
    private static String sheetsMemberRange = "";
    private static String sheetsTotalHoursRange = "";

    private static List<Assignment> assignments = Collections.emptyList();

    public static void init() {
        var dotenv = Dotenv.load();
        botToken = dotenv.get("SLACK_BOT_TOKEN");
        appToken = dotenv.get("SLACK_APP_TOKEN");
        houseManagerIds = List.of(dotenv.get("HOUSE_MANAGER_ID").split(","));
        sheetsFile = dotenv.get("SHEETS_FILE");
        sheetsMemberRange = dotenv.get("SHEETS_MEMBERS_RANGE");
        sheetsTotalHoursRange = dotenv.get("SHEETS_TOTAL_HOURS_RANGE");
    }

    public static String getBotToken() {
        return botToken;
    }

    public static String getAppToken() {
        return appToken;
    }

    public static List<String> getHouseManagerIds() {
        return houseManagerIds;
    }

    public static String getSheetsFile() {
        return sheetsFile;
    }

    public static String getSheetsMemberRange() {
        return sheetsMemberRange;
    }

    public static String getSheetsTotalHoursRange() {
        return sheetsTotalHoursRange;
    }

    public static List<Assignment> getAssignments() {
        return assignments;
    }

    public static void setAssignments(List<Assignment> assignments) {
        Constants.assignments = assignments;
    }
}
