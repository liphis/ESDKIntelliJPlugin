package utils;

import java.awt.*;

/**
 * This class sets the Dialog bounds.
 */
public class DialogUtils {

    /**
     * Set bounds half.
     *
     * @param dialog the dialog
     */
    public static void setBoundsHalf(final Dialog dialog) {
        DisplayMode displayMode = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
        //I'd also make this static and final and insert them at the class definition
        final int dialogWidth = displayMode.getWidth() / 2; //example; a quarter of the screen size
        final int dialogHeight = displayMode.getHeight() / 2; //example
        final int dialogX = displayMode.getWidth() / 2 - dialogWidth / 2; //position right in the middle of the screen
        final int dialogY = displayMode.getHeight() / 2 - dialogHeight / 2;

        dialog.setBounds(dialogX, dialogY, dialogWidth, dialogHeight);
    }

}
