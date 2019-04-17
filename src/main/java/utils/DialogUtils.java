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
        final Dimension SCREEN_DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();
        //I'd also make this static and final and insert them at the class definition
        final int dialogWidth = SCREEN_DIMENSION.width / 2; //example; a quarter of the screen size
        final int dialogHeight = SCREEN_DIMENSION.height / 2; //example
        final int dialogX = SCREEN_DIMENSION.width / 2 - dialogWidth / 2; //position right in the middle of the screen
        final int dialogY = SCREEN_DIMENSION.height / 2 - dialogHeight / 2;

        dialog.setBounds(dialogX, dialogY, dialogWidth, dialogHeight);
    }

}
