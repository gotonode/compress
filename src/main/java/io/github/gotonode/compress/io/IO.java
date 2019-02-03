package io.github.gotonode.compress.io;

import io.github.gotonode.compress.ui.UiController;

import java.io.File;

public class IO {

    private boolean checkFileExistsAndCanRead(String path) {
        File file = new File(path);

        return file.exists() && file.canRead();
    }

    /**
     * Asks the path to a file, and if a file is found there, creates a Java's File-object, and returns that.
     *
     * @return Java's own File-object, or null if the file wasn't found or an error occurred.
     */
    public File askForSourceFile(UiController uiController) {

        String path = uiController.askForSourceFilePath();

        boolean okay = checkFileExistsAndCanRead(path);

        if (!okay) {
            return null;
        }

        return new File(path);
    }

    public File askForTargetFile(UiController uiController) {

        String path = uiController.askForTargetFilePath();

        return new File(path);
    }
}
