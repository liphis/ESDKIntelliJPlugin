package utils;

import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import org.jetbrains.annotations.NotNull;

/**
 * This class shows IntelliJ Notifications.
 */
public class Notifications {

    private static final NotificationGroup notificationGroup =
            new NotificationGroup("ESDKProjectBuilder", NotificationDisplayType.BALLOON, false);

    /**
     * Show notification.
     *
     * @param text the text
     */
    public static void showNotification(@NotNull final String text) {
        showNotification(text, NotificationType.INFORMATION);
    }

    /**
     * Error notification.
     *
     * @param text the text
     */
    public static void errorNotification(@NotNull final String text) {
        showNotification(text, NotificationType.ERROR);
    }

    private static void showNotification(@NotNull final String text, @NotNull final NotificationType type) {
        notificationGroup.createNotification(text, type).notify(null);
    }
}
