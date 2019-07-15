package kai.sample.util;

import org.slf4j.Logger;

public class LogHelper {

    public static void logInfo(Logger logger, String msg) {
        logInfoParam(logger, msg);
    }

    public static void logInfoParam(Logger logger, String msg, Object... params) {
        if (logger != null && logger.isInfoEnabled()) {
            logger.info(msg, params);
        }
    }

    public static void logError(Logger logger, String msg, Throwable throwable) {
        if (logger != null && logger.isErrorEnabled()) {
            logger.error(msg, throwable);
        }
    }

    public static void logError(Logger logger, String msg) {
        logErrorParam(logger, msg);
    }

    public static void logErrorParam(Logger logger, String msg, Object... params) {
        if (logger != null && logger.isErrorEnabled()) {
            logger.error(msg, params);
        }
    }

}
