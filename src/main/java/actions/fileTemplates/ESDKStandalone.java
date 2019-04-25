package actions.fileTemplates;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class ESDKStandalone extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        new StandaloneConfigDialog().main(e.getProject(), ESDKFileTemplateManager.findPSIDirectory(e));
    }
}
