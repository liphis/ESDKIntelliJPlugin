package utils;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;


/**
 * This class opens the Standard Browser.
 */
public class BrowserUtils {


    private static boolean openWebpage(final URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
                return true;
            } catch (Exception e) {
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
    public static void openWebpage(final URL url) {
        try {
            openWebpage(url.toURI());
        } catch (URISyntaxException e) {
            Notifications.errorNotification("Couldn´t open Webpage!");
        }
    }
}
