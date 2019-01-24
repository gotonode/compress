package io.github.gotonode.compress.main;

import io.github.gotonode.compress.app.App;
import io.github.gotonode.compress.io.IO;
import io.github.gotonode.compress.ui.UiController;

import java.util.Scanner;

public class Main {

    // This is the name. Not to be confused with the UNIX tool that shares the same name.
    public static final String APP_NAME = "Compress";

    // Newest version can be found here.
    public static final String APP_URL = "https://github.com/gotonode/compress/";

    // Per week numbering. So week 3 will have a version number of 3 and so forth.
    public static final int APP_VERSION = 2;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        UiController uiController = new UiController(scanner);

        IO io = new IO();

        // Create the App-object.
        App app = new App(uiController, io);

        // Launch the actual application.
        app.run();

        // Once this location has been reached, the program has terminated.
    }

    @Override
    public String toString() {
        return APP_NAME;
    }
}
