package utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DirectoryUtils {

    private static final Logger LOGGER = Logger.getLogger(DirectoryUtils.class.getName());

    public static boolean directoryExists(String directoryAbsolutePath) {
        File directory = new File(directoryAbsolutePath);
        return directory.exists();
    }

    public static boolean deleteDirectory(String directoryAbsolutePath) {
        File directory = new File(directoryAbsolutePath);
        if (directory.exists()) {
            clearDirectory(directory);
        }
        return directory.delete();
    }

    public static void prepareDirectory(String directoryAbsolutePath) {
        File directory = new File(directoryAbsolutePath);
        if (directory.exists()) {
            clearDirectory(directory);
        } else {
            createDirectory(directory);
        }
    }

    private static void clearDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    clearDirectory(file);
                }
                boolean isDeleted = file.delete();
                if (!isDeleted) {
                    if (LOGGER.isLoggable(Level.INFO)) {
                        LOGGER.info("Cannot delete the file: " + file.getAbsolutePath());
                    }
                }
            }
        }
    }

    private static void createDirectory(File directory) {
        boolean isCreated = directory.mkdir();
        if (!isCreated) {
            if (LOGGER.isLoggable(Level.INFO)) {
                LOGGER.info("Cannot create the directory: " + directory.getAbsolutePath());
            }
        }
    }

}
