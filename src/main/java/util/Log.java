package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {
    private static final Logger logger = LoggerFactory.getLogger("cleanup-coordinator-slack");

    private Log() {

    }

    public static void e(String message, Throwable t) {
        logger.error(message, t);
    }
}
