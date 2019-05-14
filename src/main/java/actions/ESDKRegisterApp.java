package actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;
import utils.BrowserUtils;
import utils.Notifications;

import java.net.MalformedURLException;
import java.net.URL;


/**
 * This action opens Browser to register the ESDK App
 */
class ESDKRegisterApp extends AnAction {
    @Override
    public void actionPerformed(@NotNull final AnActionEvent anActionEvent) {
        try {
            BrowserUtils.openWebpage(new URL("https://dev.abas-essentials-sdk.com/#/create-appid"));
        } catch (@NotNull final MalformedURLException e) {
            Notifications.errorNotification("Malformed URL!");
        }
    }

}
