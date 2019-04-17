package actions;

import javax.swing.*;
import java.util.Objects;

/**
 * This creates the ESDK Plugin Settings in IntelliJ Settings.
 */
class PluginSettings {
    private JPanel rootPanel;
    private JLabel features;
    private JCheckBox evhaLiveTemplates;
    private JCheckBox infinityColorScheme;

    /**
     * Gets root panel.
     *
     * @return the root panel
     */
    public JPanel getRootPanel() {
        return rootPanel;
    }

    private void createUIComponents() {
        evhaLiveTemplates = new JCheckBox();
        infinityColorScheme = new JCheckBox();
        evhaLiveTemplates.addActionListener(e -> {
            System.out.println("adsadas");
            Objects.requireNonNull(ESDKPluginSettingsPersistence.getInstance()).setEsdkEventhandlerLiveTemplates(evhaLiveTemplates.isSelected());
        });

        infinityColorScheme.addActionListener(e -> Objects.requireNonNull(ESDKPluginSettingsPersistence.getInstance()).setInfinityColorScheme(infinityColorScheme.isSelected()));
        try {
            evhaLiveTemplates.setSelected(Objects.requireNonNull(ESDKPluginSettingsPersistence.getInstance()).isEsdkEventhandlerLiveTemplates());
            infinityColorScheme.setSelected(ESDKPluginSettingsPersistence.getInstance().isInfinityColorScheme());
        } catch (NullPointerException e) {
        }

    }
}
