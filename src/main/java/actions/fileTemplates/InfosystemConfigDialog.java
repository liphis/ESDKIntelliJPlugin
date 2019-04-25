package actions.fileTemplates;

import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateUtil;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import utils.Notifications;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Properties;

public class InfosystemConfigDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JCheckBox addStartButton;
    private JCheckBox addScreenEnter;
    private JTextField isClass;
    private JCheckBox addScreenValidation;
    private JCheckBox addScreenCancel;
    private JCheckBox addScreenEnd;
    private JCheckBox rowChanged;
    private JCheckBox rowDelAfter;
    private JCheckBox rowDelBef;
    private JCheckBox rowInsAfter;
    private JCheckBox rowInsBefore;
    private JCheckBox rowMarked;
    private JCheckBox rowMovAfter;
    private JCheckBox rowMovBefore;

    private static Project project;

    private Dialog dialog;
    private static PsiDirectory psiDirectory;

    public InfosystemConfigDialog() {
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
        if (isClass.getText().isEmpty()) {
            return;
        }

        final ESDKFileTemplateManager esdkFileTemplateManager = new ESDKFileTemplateManager(project);
        FileTemplate fileTemplate = esdkFileTemplateManager
                .loadFileTemplate("infosystem/infosystemEvHa.vm",
                        "InfosystemEvHa");

        Properties defaultProperties = esdkFileTemplateManager.getDefaultProperties();
        defaultProperties.setProperty("IS_CLASS", isClass.getText());
        defaultProperties.setProperty("START_BUTTON", String.valueOf(addStartButton.isSelected()));
        defaultProperties.setProperty("SCREEN_ENTER", String.valueOf(addScreenEnter.isSelected()));
        defaultProperties.setProperty("SCREEN_VALIDATION", String.valueOf(addScreenValidation.isSelected()));
        defaultProperties.setProperty("SCREEN_CANCEL", String.valueOf(addScreenCancel.isSelected()));
        defaultProperties.setProperty("SCREEN_END", String.valueOf(addScreenEnd.isSelected()));
        defaultProperties.setProperty("ROW_CHANGED", String.valueOf(rowChanged.isSelected()));
        defaultProperties.setProperty("ROW_DELETION_AFTER", String.valueOf(rowDelAfter.isSelected()));
        defaultProperties.setProperty("ROW_DELETION_BEFORE", String.valueOf(rowDelBef.isSelected()));
        defaultProperties.setProperty("ROW_INSERTION_AFTER", String.valueOf(rowInsAfter.isSelected()));
        defaultProperties.setProperty("ROW_INSERTION_BEFORE", String.valueOf(rowInsBefore.isSelected()));
        defaultProperties.setProperty("ROW_MARKED", String.valueOf(rowMarked.isSelected()));
        defaultProperties.setProperty("ROW_MOVED_AFTER", String.valueOf(rowMovAfter.isSelected()));
        defaultProperties.setProperty("ROW_MOVED_BEFORE", String.valueOf(rowMovBefore.isSelected()));

        try {
            esdkFileTemplateManager.openFileInEditor(project, FileTemplateUtil.createFromTemplate(fileTemplate,
                    isClass.getText() + "EventHandler", defaultProperties, psiDirectory));
        } catch (Exception e) {
            Notifications.errorNotification("Couldn´t create Infosystem Class!");
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
        InfosystemConfigDialog dialog = new InfosystemConfigDialog();
        dialog.pack();
        dialog.setVisible(true);
    }
}
