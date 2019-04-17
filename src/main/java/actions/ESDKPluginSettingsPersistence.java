package actions;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Saving ESDK Plugin Settings persistent.
 */
@State(
        name = "ESDKPluginSettingsPersistence",
        storages = {
                @Storage("ESDKPluginSettingsPersistence.xml")}
)
public class ESDKPluginSettingsPersistence implements PersistentStateComponent<ESDKPluginSettingsPersistence> {

    private boolean esdkEventhandlerLiveTemplates;
    private boolean infinityColorScheme;

    /**
     * Instantiates a new Esdk plugin settings persistence.
     */
    public ESDKPluginSettingsPersistence() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    @Nullable
    public static ESDKPluginSettingsPersistence getInstance() {
        return ServiceManager.getService(ESDKPluginSettingsPersistence.class);
    }

    /**
     * Is esdk eventhandler live templates boolean.
     *
     * @return the boolean
     */
    boolean isEsdkEventhandlerLiveTemplates() {
        return esdkEventhandlerLiveTemplates;
    }

    /**
     * Sets esdk eventhandler live templates.
     *
     * @param esdkEventhandlerLiveTemplates the esdk eventhandler live templates
     */
    void setEsdkEventhandlerLiveTemplates(final boolean esdkEventhandlerLiveTemplates) {
        this.esdkEventhandlerLiveTemplates = esdkEventhandlerLiveTemplates;
    }

    /**
     * Is infinity color scheme boolean.
     *
     * @return the boolean
     */
    boolean isInfinityColorScheme() {
        return infinityColorScheme;
    }

    /**
     * Sets infinity color scheme.
     *
     * @param infinityColorScheme the infinity color scheme
     */
    void setInfinityColorScheme(final boolean infinityColorScheme) {
        this.infinityColorScheme = infinityColorScheme;
    }

    @Nullable
    @Override
    public ESDKPluginSettingsPersistence getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull final ESDKPluginSettingsPersistence esdkPluginSettingsPersistence) {
        XmlSerializerUtil.copyBean(esdkPluginSettingsPersistence, this);
    }
}
