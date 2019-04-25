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

public class ESDKFileTemplateManager {


    private FileTemplateManager fileTemplateManager;

    public ESDKFileTemplateManager(Project project) {
        fileTemplateManager = FileTemplateManager.getInstance(project);
    }

    public FileTemplate loadFileTemplate(String fileName, String templateName) {
        return setTextIfNotSame(fileTemplateManager, templateName, readFileTemplate(fileName));
    }

    private String readFileTemplate(String filename) {
        StringBuilder sb = new StringBuilder();
        try {
            InputStreamReader isr = new InputStreamReader(this.getClass().getResourceAsStream("/fileTemplates/" + filename), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
        } catch (IOException e) {
            Notifications.errorNotification("Couldn´t create File Template!");
        }
        return sb.toString();
    }

    private FileTemplate setTextIfNotSame(FileTemplateManager fileTemplateManager, String templateName, String text) {
        try {
            if ((!text.isEmpty() && text != fileTemplateManager.getTemplate(templateName).getText())) {
                return setFileTemplateText(fileTemplateManager, templateName, text);
            }
        } catch (NullPointerException e) {
            return setFileTemplateText(fileTemplateManager, templateName, text);
        }
        return null;
    }

    public void openFileInEditor(Project project, PsiElement fromTemplate) {
        final PsiFile createdFile = fromTemplate.getContainingFile();

        if (createdFile != null) {
            final VirtualFile virtualFile = createdFile.getVirtualFile();

            if (virtualFile != null) {
                FileEditorManager.getInstance(project).openFile(virtualFile, true);
            }
        }
    }

    private FileTemplate setFileTemplateText(FileTemplateManager fileTemplateManager, String templateName, String text) {
        FileTemplate fileTemplate = fileTemplateManager.addTemplate(templateName, "java");
        fileTemplate.setText(text);
        return fileTemplate;
    }

    public Properties getDefaultProperties(){
        return fileTemplateManager.getDefaultProperties();
    }

    public static PsiDirectory findPSIDirectory(AnActionEvent e){
        VirtualFile virtualFile = e.getData(DataKeys.VIRTUAL_FILE);
        PsiDirectory psiDirectory = PsiManager.getInstance(e.getProject()).findDirectory(virtualFile);
        if(psiDirectory == null) psiDirectory = PsiManager.getInstance(e.getProject()).findDirectory(virtualFile.getParent());
        return psiDirectory;
    }

}



