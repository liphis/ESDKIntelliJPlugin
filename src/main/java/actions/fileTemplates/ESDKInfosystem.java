package actions.fileTemplates;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class ESDKInfosystem extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        new InfosystemConfigDialog().main(e.getProject(), ESDKFileTemplateManager.findPSIDirectory(e));
    }
}
