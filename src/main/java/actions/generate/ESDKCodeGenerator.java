package actions.generate;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

/**
 *  "Generate ESDK Code"-Action.
 */
public class ESDKCodeGenerator extends AnAction {
    @Override
    public void actionPerformed(@NotNull final AnActionEvent e) {
        new GenerateDialog().main(e);
    }
}
