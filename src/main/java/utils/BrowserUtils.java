package utils;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;


/**
 * This class opens the Standard Browser.
 */
public class BrowserUtils {


    private static boolean openWebpage(@NotNull final URI uri, final URL url) {
        final Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
                return true;
            } catch (@NotNull final Exception e) {
                Notifications.errorNotification("Couldn´t open Webpage!");
            }
        }else{
            final Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("xdg-open " + url);
            } catch (@NotNull final IOException e) {
                Notifications.errorNotification("Couldn´t open Webpage!");
            }
        }
        return false;
    }

    /**
     * Open webpage.
     *
     * @param url the url
     */
    public static void openWebpage(@NotNull final URL url) {
        try {
            openWebpage(url.toURI(), url);
        } catch (@NotNull final URISyntaxException e) {
            Notifications.errorNotification("Couldn´t open Webpage!");
        }
    }
}
