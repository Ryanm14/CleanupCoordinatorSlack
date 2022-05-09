package util;

public class Util {

    public static int parseIntSafely(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static boolean isHouseManagerId(String userId) {
        return Constants.getHouseManagerIds().contains(userId);
    }
}
