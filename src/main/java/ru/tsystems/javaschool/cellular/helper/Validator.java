package ru.tsystems.javaschool.cellular.helper;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ferh on 25.10.14.
 */
public class Validator {
    static Logger logger = Logger.getLogger(Validator.class);

    public static String checkPassword(String src) {
        StringBuilder builder = new StringBuilder();
        if (src.length() < 4) {
            logger.error("Password must be at least 4 symbols.");
            builder.append("Password must be at least 4 symbols.");
        }
        if (src.length() > 255) {
            logger.error("Too big password.");
            builder.append("Too big password.");
        }
        return builder.length() > 0 ? builder.toString() : "";
    }

    public static String checkString(String src) {
        StringBuilder builder = new StringBuilder();
        if (src.length() > 255) {
            logger.error("Too big string.");
            builder.append("Too big string.");
        }
        return builder.length() > 0 ? builder.toString() : "";
    }

    public static String checkNumber(String src) {
        StringBuilder builder = new StringBuilder();
        if (src.length() > 6) {
            logger.error("Too big value.");
            builder.append("Too big value.");
        }
        if (Double.parseDouble(src) < 0) {
            logger.error("Negative value isn't allowed here.");
            builder.append("Negative value isn't allowed here.");
        }
        return builder.length() > 0 ? builder.toString() : "";
    }

    public static String checkPhoneNumber(String src) {
        StringBuilder builder = new StringBuilder();
        if (src.length() > 10) {
            logger.error("Phone number must be in format: XXXYYYZZZZ.");
            builder.append("Phone number must be in format: XXXYYYZZZZ.");
        }
        try {
            Double.parseDouble(src);
        } catch (NumberFormatException nfe) {
            logger.error("Phone number can contain only numbers.");
            builder.append("Phone number can contain only numbers.");
        }
        return builder.length() > 0 ? builder.toString() : "";
    }

    public static String checkEmail(String src) {
        StringBuilder builder = new StringBuilder();
        if (src.startsWith("@")) {
            logger.error("Email can't start with @.");
            builder.append("Email can't start with @.");
        }
        if (!src.contains("@")) {
            logger.error("Email must contain @.");
            builder.append("Email must contain @.");
        }
        if (src.endsWith("@")) {
            logger.error("Email can't end with @.");
            builder.append("Email can't end with @.");
        }
        return builder.length() > 0 ? builder.toString() : "";
    }

    public static String checkDate(String src) {
        Date currentDate = new Date();
        StringBuilder builder = new StringBuilder();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(src);
            if (date.compareTo(currentDate) > 0) {
                logger.error(" Wrong date!.");
                builder.append(" Wrong date!.");
            }
        } catch (ParseException e) {
            logger.error(" Wrong date format!.");
            builder.append(" Wrong date format!.");
        }
        return builder.length() > 0 ? builder.toString() : "";
    }
}
