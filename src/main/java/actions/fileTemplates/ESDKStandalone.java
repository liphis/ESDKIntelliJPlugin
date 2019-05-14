package actions.fileTemplates;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

/**
 * "Create Standalone Class"-Action.
 */
public class ESDKStandalone extends AnAction {
    @Override
    public void actionPerformed(@NotNull final AnActionEvent e) {
        new StandaloneConfigDialog().main(e.getProject(), ESDKFileTemplateManager.findPSIDirectory(e));
    }
}
