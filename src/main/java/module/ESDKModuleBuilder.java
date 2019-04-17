package module;

import com.intellij.ProjectTopics;
import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.project.ModuleListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModifiableRootModel;
import module.builder.ProjectBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * This class builds the ESDK Module.
 */
public class ESDKModuleBuilder extends ModuleBuilder {


    private ESDKSettingsWizardStep esdkSettingsWizardStep = new ESDKSettingsWizardStep();

    /**
     * This Method calls the Project Builder to build the Project.
     * @param model
     */
    @Override
    final public void setupRootModel(final ModifiableRootModel model) {
        final module.builder.ProjectBuilder projectBuilder = new ProjectBuilder();
        final String basePath = model.getProject().getBasePath();
        projectBuilder.unpack(basePath);
        projectBuilder.enableGradle(basePath);
        model.getProject().getMessageBus().connect().subscribe(ProjectTopics.MODULES, new ModuleListener() {
            @Override
            public void moduleAdded(@NotNull final Project project, @NotNull final Module module) {
                try {
                    if (Objects.equals(module.getModuleTypeName(), ESDKModuleType.getID())) {
                        ModuleManager.getInstance(model.getProject()).disposeModule(model.getModule());
                        ModuleManager.getInstance(project).disposeModule(module);
                    }
                } catch (NullPointerException e) {
                }
            }
        });
        projectBuilder.setBasic(basePath, esdkSettingsWizardStep);
        projectBuilder.setConnectionSettings(basePath, esdkSettingsWizardStep);
        projectBuilder.setLanguages(basePath, esdkSettingsWizardStep);
        projectBuilder.setVersions(basePath, esdkSettingsWizardStep);
        projectBuilder.setFopJson(basePath, esdkSettingsWizardStep);
        projectBuilder.setContentInfosystems(basePath, esdkSettingsWizardStep);
        projectBuilder.setContentTables(basePath, esdkSettingsWizardStep);
        projectBuilder.setContentScreens(basePath, esdkSettingsWizardStep);
        projectBuilder.setContentEnums(basePath, esdkSettingsWizardStep);
        projectBuilder.setContentNamedTypes(basePath, esdkSettingsWizardStep);
    }

    /**
     * @return
     */
    @Override
    public ModuleType getModuleType() {
        return ESDKModuleType.getInstance();
    }

    /**
     * @param context
     * @param parentDisposable
     * @return
     */
    @Nullable
    @Override
    public ModuleWizardStep getCustomOptionsStep(final WizardContext context, final Disposable parentDisposable) {
        esdkSettingsWizardStep = new ESDKSettingsWizardStep();
        return esdkSettingsWizardStep;
    }

}
