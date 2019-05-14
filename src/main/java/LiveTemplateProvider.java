import com.intellij.codeInsight.template.impl.DefaultLiveTemplatesProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This class provides all Live templates.
 */
class LiveTemplateProvider implements DefaultLiveTemplatesProvider {
    @NotNull
    @Override
    public String[] getDefaultLiveTemplateFiles() {
        return new String[]{"liveTemplates/AbasESDK"};
    }

    @Nullable
    @Override
    public String[] getHiddenLiveTemplateFiles() {
        return new String[0];
    }
}
