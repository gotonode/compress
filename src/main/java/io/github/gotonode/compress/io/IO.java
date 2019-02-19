package io.github.gotonode.compress.io;

import io.github.gotonode.compress.ui.UiController;

import java.io.File;

/**
 * Used to handle file input and output.
 */
public class IO {

    private boolean fileExistsAndIsReadable(String path) {
        File file = new File(path);

        return file.exists() && file.canRead();
    }

    /**
     * Asks the path to a file, and if a file is found there, creates a Java's File-object, and returns that.
     *
     * @param uiController The UI controller to use.
     * @return Java's own File-object, or null if the file wasn't found or an error occurred.
     */
    public File askForSourceFile(UiController uiController) {

        String path = uiController.askForSourceFilePath();

        boolean okay = fileExistsAndIsReadable(path);

        if (!okay) {
            return null;
        }

        return new File(path);
    }

    /**
     * Asks the path to a file, and if a file is found there, creates a Java's File-object, and returns that.
     *
     * @param uiController The UI controller to use.
     * @return Java's own File-object, or null if the file wasn't found or an error occurred.
     */
    public File askForTargetFile(UiController uiController) {

        String path = uiController.askForTargetFilePath();

        return new File(path);
    }
}
