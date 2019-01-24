package io.github.gotonode.compress.main;

import io.github.gotonode.compress.app.App;
import io.github.gotonode.compress.io.IO;
import io.github.gotonode.compress.ui.UiController;

import java.util.Scanner;

public class Main {

    public static final String APP_NAME = "Compress";
    public static final String APP_URL = "https://github.com/gotonode/compress/";
    public static final int APP_VERSION = 2; // Per week numbering.

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
