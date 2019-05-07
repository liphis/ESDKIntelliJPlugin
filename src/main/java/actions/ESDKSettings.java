package actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import module.ESDKSettingsDialog;
import module.builder.ProjectReader;
import org.jetbrains.annotations.NotNull;
import utils.DialogUtils;
import utils.Notifications;

import java.util.Objects;

/**
 * This Action opens a Dialog with all current ESDK Project Settings.
 */
class ESDKSettings extends AnAction {

    @Override
    public void actionPerformed(@NotNull final AnActionEvent anActionEvent) {
        ESDKSettingsDialog dialog;
        ProjectReader projectReader = new ProjectReader();
        try {
            dialog = ESDKSettingsDialog.main(anActionEvent.getProject(), projectReader);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return;
        }

        DialogUtils.setBoundsHalf(dialog);
        String basePath = Objects.requireNonNull(anActionEvent.getProject()).getBasePath();
        projectReader.readBasic(basePath, dialog);
        projectReader.readVersions(basePath, dialog);
        projectReader.readlanguages(basePath, dialog);
        projectReader.readConnectionSettings(basePath, dialog);
        projectReader.readContentInfosystems(basePath, dialog);
        projectReader.readContentTables(basePath, dialog);
        projectReader.readContentScreens(basePath, dialog);
        projectReader.readContentEnums(basePath, dialog);
        projectReader.readContentNamedTypes(basePath, dialog);
        projectReader.readContentEvents(basePath, dialog);

        dialog.setVisible(true);
    }
}
