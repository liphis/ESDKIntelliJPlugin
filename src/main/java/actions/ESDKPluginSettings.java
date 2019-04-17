package actions;

import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;


/**
 * ESDK Plugin Settings in IntelliJ Settings.
 */
class ESDKPluginSettings implements SearchableConfigurable {

    @NotNull
    @Override
    public String getId() {
        return "preference.ESDKPluginSettings";
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "ESDK-Plugin Settings";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        return new PluginSettings().getRootPanel();
    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public void apply() {

    }
}
