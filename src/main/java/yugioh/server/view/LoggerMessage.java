package view;

import java.util.logging.Logger;

public class LoggerMessage {

    private static final Logger logger = Logger.getLogger(LoggerMessage.class.getName());

    public static void log(String message) {
        logger.info(message);
    }

}
