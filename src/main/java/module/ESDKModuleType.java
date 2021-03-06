package module;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.ModuleTypeManager;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import org.jetbrains.annotations.NotNull;
import icons.Icons;

import javax.swing.*;

/**
 * This class adds the ESDK Module to Projectbuilder-Modules
 */
public class ESDKModuleType extends ModuleType<ESDKModuleBuilder> implements Icons{
    private static final String ID = "ESDK_MODULE_TYPE";

    /**
     * Instantiates a new Esdk module type.
     */
    public ESDKModuleType() {
        super(ID);
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    @NotNull
    public static ESDKModuleType getInstance() {
        return (ESDKModuleType) ModuleTypeManager.getInstance().findByID(ID);
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    @NotNull
    static String getID() {
        return ID;
    }

    @NotNull
    @Override
    public ESDKModuleBuilder createModuleBuilder() {
        return new ESDKModuleBuilder();
    }

    @NotNull
    @Override
    public String getName() {
        return "ESDK Project";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Abas ESDK Project";
    }

    @NotNull
    @Override
    public Icon getNodeIcon(@Deprecated final boolean b) {
        return ESDK_BOAT;
    }

    @NotNull
    @Override
    public ModuleWizardStep[] createWizardSteps(@NotNull final WizardContext wizardContext,
                                                @NotNull final ESDKModuleBuilder moduleBuilder,
                                                @NotNull final ModulesProvider modulesProvider) {
        return super.createWizardSteps(wizardContext, moduleBuilder, modulesProvider);
    }
}
