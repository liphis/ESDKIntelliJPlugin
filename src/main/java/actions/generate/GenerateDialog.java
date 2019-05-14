package actions.generate;


import actions.fileTemplates.ESDKFileTemplateManager;
import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.ui.ComboBox;
import module.builder.ProjectReader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import utils.Notifications;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.Vector;

/**
 * "Generate ESDK Code" Config Dialog.
 */
public class GenerateDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField className;
    private JComboBox sort;
    private JComboBox event;
    private JTextField fieldName;
    private JLabel fieldNameLabel;
    private JLabel classNameLabel;
    private static AnActionEvent actionEvent;

    /**
     * Instantiates a new Generate dialog.
     */
    public GenerateDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(final WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        final ESDKFileTemplateManager esdkFileTemplateManager = new ESDKFileTemplateManager(actionEvent.getProject());
        FileTemplate fileTemplate = null;
        final Properties defaultProperties = esdkFileTemplateManager.getDefaultProperties();
        switch (sort.getSelectedIndex()) {
            default:
                return;
            case 0:
                fileTemplate = esdkFileTemplateManager
                        .loadFileTemplate("codetemplates/button/buttonEvHa.vm",
                                "ButtonEvHa");
                defaultProperties.setProperty("FIELDNAME", fieldName.getText());
                defaultProperties.setProperty("EVENT", event.getSelectedItem().toString());
                defaultProperties.setProperty("EVENTUP", event.getSelectedItem().toString().toUpperCase());
                defaultProperties.setProperty("MASKEDITOR", className.getText());
                break;
            case 1:
                fileTemplate = esdkFileTemplateManager
                        .loadFileTemplate("codetemplates/field/fieldEvHa.vm",
                                "FieldEvHa");
                defaultProperties.setProperty("FIELDNAME", fieldName.getText());
                defaultProperties.setProperty("EVENT", event.getSelectedItem().toString());
                defaultProperties.setProperty("EVENTUP", event.getSelectedItem().toString().toUpperCase());
                defaultProperties.setProperty("MASKEDITOR", className.getText());
                break;
            case 2:
                fileTemplate = esdkFileTemplateManager
                        .loadFileTemplate("codetemplates/screen/screenEvHa.vm",
                                "ScreenEvHa");
                defaultProperties.setProperty("EVENT", event.getSelectedItem().toString());
                defaultProperties.setProperty("EVENTUP", event.getSelectedItem().toString().toUpperCase());
                defaultProperties.setProperty("MASKEDITOR", className.getText());
                break;
            case 3:
                fileTemplate = esdkFileTemplateManager
                        .loadFileTemplate("codetemplates/row/rowEvHa.vm",
                                "RowEvHa");
                defaultProperties.setProperty("EVENT", event.getSelectedItem().toString().replaceAll("_", ""));
                defaultProperties.setProperty("CLASSNAME", className.getText());
                defaultProperties.setProperty("EVENTUP", event.getSelectedItem().toString().toUpperCase());
                break;
            case 4:
                defaultProperties.setProperty("CLASSTOSELECT", className.getText());
                switch (event.getSelectedIndex()) {
                    default:
                        return;
                    case 0:
                        fileTemplate = esdkFileTemplateManager
                                .loadFileTemplate("codetemplates/dbselection/headandtableSel.vm",
                                        "RowEvHa");
                        break;
                    case 1:
                        fileTemplate = esdkFileTemplateManager
                                .loadFileTemplate("codetemplates/dbselection/headSel.vm",
                                        "RowEvHa");
                        break;
                    case 2:
                        fileTemplate = esdkFileTemplateManager
                                .loadFileTemplate("codetemplates/dbselection/tableSel.vm",
                                        "RowEvHa");
                        break;
                }
                break;
            case 5:
                fileTemplate = esdkFileTemplateManager
                        .loadFileTemplate("codetemplates/standalone/standalone.vm",
                                "StandaloneCode");
                break;
            case 6:
                defaultProperties.setProperty("PRINTBUFFEREXISTS",  String.valueOf(getDocument().getText().contains("PrintBuffer") ? true : false));
                switch (event.getSelectedIndex()) {
                    default:
                        return;
                    case 0:

                        fileTemplate = esdkFileTemplateManager
                                .loadFileTemplate("codetemplates/tools/globalTextBuffer.vm",
                                        "GlobalTextBuffer");
                        break;
                    case 1:
                        fileTemplate = esdkFileTemplateManager
                                .loadFileTemplate("codetemplates/tools/visibilityAppend.vm",
                                        "VisibilityAppend");
                        break;
                    case 2:
                        fileTemplate = esdkFileTemplateManager
                                .loadFileTemplate("codetemplates/tools/visibilityDelete.vm",
                                        "VisibilityDelete");
                        break;
                    case 3:
                        fileTemplate = esdkFileTemplateManager
                                .loadFileTemplate("codetemplates/tools/visibilityReset.vm",
                                        "VisibilityReset");
                        break;
                }
                break;
        }
        String text = "";
        try {
            text = fileTemplate.getText(defaultProperties);
        }catch(@NotNull final NullPointerException e){
            text = fileTemplate.getText();
        }
        catch (@NotNull final IOException e) {
            Notifications.errorNotification("Could not generate Method!");
        }

        final String finalText = text;
        WriteCommandAction.runWriteCommandAction(actionEvent.getProject(), () -> getDocument().insertString(getCursorOffset(getEditor()), finalText));
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    /**
     * Main.
     *
     * @param e the e
     */
    public static void main(final AnActionEvent e) {
        actionEvent = e;
        final GenerateDialog dialog = new GenerateDialog();
        dialog.pack();
        dialog.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        try {
            className = new JTextField(ProjectReader
                    .getFirstMatcherGroup("@EventHandler\\s*\\(\\s*head\\s*\\=\\s*([\\w*\\d*]*).class",
                            getDocument().getText()));
        } catch (@NotNull final Exception e) {
            className = new JTextField();
        }

        sort = new ComboBox(new DefaultComboBoxModel<>(
                new Vector<String>(Arrays.asList("Button", "Field", "Screen", "Row", "Database Selection", "Standalone", "Tools"))));
        event = new ComboBox<String>(new DefaultComboBoxModel<>(new Vector<>(Arrays.asList("Before", "After"))));
        sort.addActionListener(e -> {
            switch (sort.getSelectedIndex()) {
                case 0:
                    event.setModel(new DefaultComboBoxModel<>(new Vector<>(Arrays.asList("Before", "After"))));
                    className.setVisible(true);
                    classNameLabel.setVisible(true);
                    fieldNameLabel.setVisible(true);
                    fieldName.setVisible(true);
                    break;
                case 1:
                    className.setVisible(true);
                    classNameLabel.setVisible(true);
                    fieldNameLabel.setVisible(true);
                    fieldName.setVisible(true);
                    event.setModel(new DefaultComboBoxModel<>(new Vector<>(Arrays.asList("Fill", "Validation", "Exit"))));
                    break;
                case 2:
                    className.setVisible(true);
                    classNameLabel.setVisible(true);
                    fieldNameLabel.setVisible(false);
                    fieldName.setVisible(false);
                    event.setModel(new DefaultComboBoxModel<>(new Vector<>(Arrays.asList("Enter", "Validation", "End", "Exit"))));
                    break;
                case 3:
                    className.setVisible(true);
                    classNameLabel.setVisible(true);
                    fieldNameLabel.setVisible(false);
                    fieldName.setVisible(false);
                    event.setModel(new DefaultComboBoxModel<>(new Vector<>(Arrays.asList("Changed", "Deletion_After", "Deletion_Before", "Insertion_After", "Insertion_Before", "Marked", "Moved_After", "Moved_Before"))));
                    break;
                case 4:
                    className.setVisible(true);
                    classNameLabel.setVisible(true);
                    fieldNameLabel.setVisible(false);
                    fieldName.setVisible(false);
                    event.setModel(new DefaultComboBoxModel<>(new Vector<>(Arrays.asList("Head Selection", "Table Selection", "Head and Table Selection"))));
                    break;

                case 5:
                    className.setVisible(false);
                    classNameLabel.setVisible(false);
                    fieldNameLabel.setVisible(false);
                    fieldName.setVisible(false);
                    event.setModel(new DefaultComboBoxModel<>(new Vector<>(Arrays.asList("RunFop"))));
                    break;
                case 6:
                    className.setVisible(false);
                    classNameLabel.setVisible(false);
                    fieldNameLabel.setVisible(false);
                    fieldName.setVisible(false);
                    event.setModel(new DefaultComboBoxModel<>(new Vector<>(Arrays.asList("GlobalTextBuffer", "Visibility Append", "Visibility Delete", "Visibility Reset"))));
                    break;
            }
        });
    }

    @Nullable
    private Document getDocument() {
        if (actionEvent == null) return null;
        final FileEditorManager manager = FileEditorManager.getInstance(actionEvent.getProject());
        final Editor editor = manager.getSelectedTextEditor();
        assert editor != null;
        return editor.getDocument();
    }

    @Nullable
    private Editor getEditor() {
        if (actionEvent == null) return null;
        final FileEditorManager manager = FileEditorManager.getInstance(actionEvent.getProject());
        final Editor editor = manager.getSelectedTextEditor();
        assert editor != null;
        return editor;
    }

    private int getCursorOffset(@NotNull final Editor editor) {
        return editor.getCaretModel().getOffset();
    }
}
