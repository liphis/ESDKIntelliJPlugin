package actions.fileTemplates;

import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateUtil;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import utils.Notifications;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Properties;

public class StandaloneConfigDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JCheckBox addScreenEnter;
    private JTextField className;
    private JCheckBox addScreenValidation;
    private JCheckBox addScreenCancel;
    private JCheckBox addScreenEnd;

    private static Project project;

    private Dialog dialog;
    private static PsiDirectory psiDirectory;

    public StandaloneConfigDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        if (className.getText().isEmpty()) {
            return;
        }

        final ESDKFileTemplateManager esdkFileTemplateManager = new ESDKFileTemplateManager(project);
        FileTemplate fileTemplate = esdkFileTemplateManager
                .loadFileTemplate("standalone/standalone.vm",
                        "Standalone");

        Properties defaultProperties = esdkFileTemplateManager.getDefaultProperties();
        defaultProperties.setProperty("CLASSNAME", className.getText());

        try {
            esdkFileTemplateManager.openFileInEditor(project, FileTemplateUtil.createFromTemplate(fileTemplate,
                    className.getText(), defaultProperties, psiDirectory));
        } catch (Exception e) {
            Notifications.errorNotification("Couldn´t create Standalone Class!");
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(Project pProject, PsiDirectory pPsiDirectory) {
        project = pProject;
        psiDirectory = pPsiDirectory;
        StandaloneConfigDialog dialog = new StandaloneConfigDialog();
        dialog.pack();
        dialog.setVisible(true);
    }
}
