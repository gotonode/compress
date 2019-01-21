package main.java.io.github.gotonode.compress.io;

import main.java.io.github.gotonode.compress.ui.UiController;

import java.io.File;

public class IO {

    private boolean checkFileExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    /**
     * Asks the path to a file, and if a file is found there, creates a Java's File-object, and returns that.
     * @return Java's own File-object, or null if the file wasn't found or an error occurred.
     */
    public File askForAndFindSourceFile(UiController uiController) {
        String path = uiController.askForSourceFilePath();

        boolean found = checkFileExists(path);
        if (!found) {
            uiController.printFileNotFound();
            return null;
        }

        File output;

        try {
            output = new File(path);
        } catch (Exception ex) {
            uiController.printFileError();
            return null;
        }

        return output;
    }

    public File askForAndDeleteAndUseTargetFile(UiController uiController) {
        String path = uiController.askForTargetFilePath();

        File output;

        try {
            output = new File(path);
            output.delete(); // Just for clarity.
        } catch (Exception ex) {
            uiController.printFileError();
            return null;
        }

        return output;
    }
}
