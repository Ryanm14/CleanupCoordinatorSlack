package backend;

import java.util.Random;

public class DataRepository {

    public int getCleanupHours(String userId) {
        return new Random().nextInt(5);
    }
}
