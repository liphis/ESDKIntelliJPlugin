package actions.fileTemplates;

import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import utils.Notifications;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * This class manages File Templates.
 */
public class ESDKFileTemplateManager {


    private FileTemplateManager fileTemplateManager;

    /**
     * Instantiates a new Esdk file template manager.
     *
     * @param project the project
     */
    public ESDKFileTemplateManager(final Project project) {
        fileTemplateManager = FileTemplateManager.getInstance(project);
    }

    /**
     * Load file template file template.
     *
     * @param fileName     the file name
     * @param templateName the template name
     * @return the file template
     */
    public FileTemplate loadFileTemplate(final String fileName, final String templateName) {
        return setTextIfNotSame(fileTemplateManager, templateName, readFileTemplate(fileName));
    }

    private String readFileTemplate(final String filename) {
        StringBuilder sb = new StringBuilder();
        try {
            final InputStreamReader isr = new InputStreamReader(
                    this.getClass().getResourceAsStream("/fileTemplates/" + filename), StandardCharsets.UTF_8);
            final BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
            br.close();
        } catch (IOException e) {
            Notifications.errorNotification("Couldn´t create File Template!");
        }
        return sb.toString();
    }

    private FileTemplate setTextIfNotSame(final FileTemplateManager fileTemplateManager, final String templateName,
                                          final String text) {
        try {
            if ((!text.isEmpty() && text.equals(fileTemplateManager.getTemplate(templateName).getText()))) {
                return setFileTemplateText(fileTemplateManager, templateName, text);
            }
        } catch (NullPointerException e) {
            return setFileTemplateText(fileTemplateManager, templateName, text);
        }
        return null;
    }

    /**
     * Open file in editor.
     *
     * @param project      the project
     * @param fromTemplate the from template
     */
    public void openFileInEditor(final Project project, final PsiElement fromTemplate) {
        final PsiFile createdFile = fromTemplate.getContainingFile();

        if (createdFile != null) {
            final VirtualFile virtualFile = createdFile.getVirtualFile();

            if (virtualFile != null) {
                FileEditorManager.getInstance(project).openFile(virtualFile, true);
            }
        }
    }

    private FileTemplate setFileTemplateText(final FileTemplateManager fileTemplateManager, final String templateName,
                                             final String text) {
        final FileTemplate fileTemplate = fileTemplateManager.addTemplate(templateName, "java");
        fileTemplate.setText(text);
        return fileTemplate;
    }

    /**
     * Gets default properties.
     *
     * @return the default properties
     */
    public Properties getDefaultProperties() {
        return fileTemplateManager.getDefaultProperties();
    }

    /**
     * Find psi directory psi directory.
     *
     * @param e the e
     * @return the psi directory
     */
    public static PsiDirectory findPSIDirectory(final AnActionEvent e) {
        final VirtualFile virtualFile = e.getData(DataKeys.VIRTUAL_FILE);
        PsiDirectory psiDirectory = PsiManager.getInstance(e.getProject()).findDirectory(virtualFile);
        if (psiDirectory == null)
            psiDirectory = PsiManager.getInstance(e.getProject()).findDirectory(virtualFile.getParent());
        return psiDirectory;
    }

}



