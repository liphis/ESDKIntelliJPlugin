package actions.fileTemplates;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class ESDKMask extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        new MaskConfigDialog().main(e.getProject(), ESDKFileTemplateManager.findPSIDirectory(e));
    }
}
