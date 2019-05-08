package module;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBList;
import data.FopJson;
import utils.Notifications;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class is a WizardStep to customize the ESDK Project
 */
public class ESDKSettingsWizardStep extends ModuleWizardStep {
    //Basic
    private static final byte BASIC_APP_ID_LIMIT = 5, BASIC_APP_NAME_LIMIT = 15, BASIC_VENDOR_ID_LIMIT = 2;
    private static final byte BASIC_APP_NAME_MIN = 4, BASIC_PACKAGE_NAME_MIN = 6;
    //Versions
    private static final byte FROM_VERSION_MIN = 9, TO_VERSION_MIN = 9;
    //Connection Settings
    private static final byte CONNECTION_ABAS_HOST_MIN = 4, CONNECTION_NEXUS_HOST_MIN = 4;
    //Content Infosystems
    private static final byte INFOSYSTEMS_WORKING_DIRECTORY_MIN = 3, INFOSYSTEMS_SEARCHWORD_MIN = 3;
    //Content Enum
    private static final byte ENUMS_NAME_MIN = 3;
    //Content Name Types
    private static final byte NAMED_TYPES_NAME_MIN = 3;
    private static final ArrayList<FopJson> fopJsonList = new ArrayList<>();
    private JPanel rootPanel;
    private JTabbedPane tabbedPane1;
    private JTextField developmentVersion;
    private JTextField esdkVersion;
    private JTextField appID;
    private JTextField appName;
    private JTextField vendorID;
    private JTextField packageName;
    private JTextField edpHost;
    private JTextField nexusHost;
    private JTextField nexusVersion;
    private JCheckBox german;
    private JCheckBox americanEnglish;
    private JCheckBox english;
    private JCheckBox french;
    private JCheckBox spanish;
    private JCheckBox italian;
    private JCheckBox polish;
    private JCheckBox russian;
    private JCheckBox hungarian;
    private JCheckBox czech;
    private JCheckBox turkish;
    private JCheckBox romanian;
    private JCheckBox indonesian;
    private JCheckBox dutch;
    private JCheckBox macedonian;
    private JCheckBox bulgarian;
    private JCheckBox serbian;
    private JCheckBox slovenian;
    private JCheckBox slovakian;
    private JCheckBox portugese;
    private JCheckBox chineseSimplified;
    private JCheckBox chineseTraditional;
    private JCheckBox greek;
    private JCheckBox thai;
    private JCheckBox arabic;
    private JCheckBox persian;
    private JCheckBox vietnamese;
    private JCheckBox urdu;
    private JCheckBox japanese;
    private JList versionList;
    private JTextField fromVersion;
    private JTextField toVersion;
    private JButton addVersion;
    private JButton removeVersion;
    private JTabbedPane content;
    private JTextField infosystemWorkingDirectory;
    private JTextField infosystemSearchWord;
    private JList infosystemList;
    private JButton removeInfosystem;
    private JButton addInfosystem;
    private JTextField table;
    private JButton removeTable;
    private JButton addTable;
    private JList tableList;
    private JTextField screensTable;
    private JTextField screensGroup;
    private JList screensList;
    private JCheckBox screensA;
    private JCheckBox screensB;
    private JCheckBox screensC;
    private JCheckBox screensD;
    private JCheckBox screensE;
    private JCheckBox screensF;
    private JCheckBox screensG;
    private JCheckBox screensH;
    private JCheckBox screensI;
    private JCheckBox screensJ;
    private JButton removeScreen;
    private JButton addScreen;
    private JTextField enumName;
    private JList enumsList;
    private JButton addEnum;
    private JButton removeEnums;
    private JTextField namedType;
    private JButton removeNamedType;
    private JButton addNamedType;
    private JList namedTypesList;
    private JTextField eventTable;
    private JTextField eventGroup;
    private JComboBox<String> eventEditorMode;
    private JComboBox<String> event;
    private JTextField eventField;
    private JTextField eventHandler;
    private JRadioButton eventHeadRadio;
    private JRadioButton eventTableRadio;
    private JList eventList;
    private JButton eventRemove;
    private JButton eventAdd;
    private JTextField eventKey;
    private JTextField abasHomeDir;
    private JTextField abasClientDir;
    private JTextField edpUser;
    private JPasswordField edpPassword;
    private JTextField edpPort;
    private JTextField nexusPort;
    private JTextField nexusName;
    private JPasswordField nexusPassword;
    private JTextField sshUser;
    private JTextField sshHost;
    private JTextField sshPort;
    private JTextField sshPassword;
    private JTextField nexusUser;
    private JTextField sshKeyPath;
    private JButton browseSSHKey;

    /**
     * Gets fop json list.
     *
     * @return the fop json list
     */
    public static ArrayList<FopJson> getFopJsonList() {
        return fopJsonList;
    }

    @Override
    public JComponent getComponent() {
        return rootPanel;
    }

    @Override
    public void updateDataModel() {

    }

    /**
     * Gets development version.
     *
     * @return the development version
     */
    public JTextField getDevelopmentVersion() {
        return developmentVersion;
    }

    /**
     * Gets esdk version.
     *
     * @return the esdk version
     */
    public JTextField getEsdkVersion() {
        return esdkVersion;
    }

    /**
     * Gets app id.
     *
     * @return the app id
     */
    public JTextField getAppID() {
        return appID;
    }

    /**
     * Gets app name.
     *
     * @return the app name
     */
    public JTextField getAppName() {
        return appName;
    }

    /**
     * Gets vendor id.
     *
     * @return the vendor id
     */
    public JTextField getVendorID() {
        return vendorID;
    }

    /**
     * Gets package name.
     *
     * @return the package name
     */
    public JTextField getPackageName() {
        return packageName;
    }

    /**
     * Gets abas host.
     *
     * @return the abas host
     */
    public JTextField getEdpHost() {
        return edpHost;
    }

    /**
     * Gets nexus host.
     *
     * @return the nexus host
     */
    public JTextField getNexusHost() {
        return nexusHost;
    }

    /**
     * Gets nexus version.
     *
     * @return the nexus version
     */
    public JTextField getNexusVersion() {
        return nexusVersion;
    }

    /**
     * Gets german.
     *
     * @return the german
     */
    public JCheckBox getGerman() {
        return german;
    }

    /**
     * Gets american english.
     *
     * @return the american english
     */
    public JCheckBox getAmericanEnglish() {
        return americanEnglish;
    }

    /**
     * Gets english.
     *
     * @return the english
     */
    public JCheckBox getEnglish() {
        return english;
    }

    /**
     * Gets french.
     *
     * @return the french
     */
    public JCheckBox getFrench() {
        return french;
    }

    /**
     * Gets spanish.
     *
     * @return the spanish
     */
    public JCheckBox getSpanish() {
        return spanish;
    }

    /**
     * Gets italian.
     *
     * @return the italian
     */
    public JCheckBox getItalian() {
        return italian;
    }

    /**
     * Gets polish.
     *
     * @return the polish
     */
    public JCheckBox getPolish() {
        return polish;
    }

    /**
     * Gets russian.
     *
     * @return the russian
     */
    public JCheckBox getRussian() {
        return russian;
    }

    /**
     * Gets hungarian.
     *
     * @return the hungarian
     */
    public JCheckBox getHungarian() {
        return hungarian;
    }

    /**
     * Gets czech.
     *
     * @return the czech
     */
    public JCheckBox getCzech() {
        return czech;
    }

    /**
     * Gets turkish.
     *
     * @return the turkish
     */
    public JCheckBox getTurkish() {
        return turkish;
    }

    /**
     * Gets romanian.
     *
     * @return the romanian
     */
    public JCheckBox getRomanian() {
        return romanian;
    }

    /**
     * Gets indonesian.
     *
     * @return the indonesian
     */
    public JCheckBox getIndonesian() {
        return indonesian;
    }

    /**
     * Gets dutch.
     *
     * @return the dutch
     */
    public JCheckBox getDutch() {
        return dutch;
    }

    /**
     * Gets macedonian.
     *
     * @return the macedonian
     */
    public JCheckBox getMacedonian() {
        return macedonian;
    }

    /**
     * Gets bulgarian.
     *
     * @return the bulgarian
     */
    public JCheckBox getBulgarian() {
        return bulgarian;
    }

    /**
     * Gets serbian.
     *
     * @return the serbian
     */
    public JCheckBox getSerbian() {
        return serbian;
    }

    /**
     * Gets slovenian.
     *
     * @return the slovenian
     */
    public JCheckBox getSlovenian() {
        return slovenian;
    }

    /**
     * Gets slovakian.
     *
     * @return the slovakian
     */
    public JCheckBox getSlovakian() {
        return slovakian;
    }

    /**
     * Gets portugese.
     *
     * @return the portugese
     */
    public JCheckBox getPortugese() {
        return portugese;
    }

    /**
     * Gets chinese simplified.
     *
     * @return the chinese simplified
     */
    public JCheckBox getChineseSimplified() {
        return chineseSimplified;
    }

    /**
     * Gets chinese traditional.
     *
     * @return the chinese traditional
     */
    public JCheckBox getChineseTraditional() {
        return chineseTraditional;
    }

    /**
     * Gets greek.
     *
     * @return the greek
     */
    public JCheckBox getGreek() {
        return greek;
    }

    /**
     * Gets thai.
     *
     * @return the thai
     */
    public JCheckBox getThai() {
        return thai;
    }

    /**
     * Gets arabic.
     *
     * @return the arabic
     */
    public JCheckBox getArabic() {
        return arabic;
    }

    /**
     * Gets persian.
     *
     * @return the persian
     */
    public JCheckBox getPersian() {
        return persian;
    }

    /**
     * Gets vietnamese.
     *
     * @return the vietnamese
     */
    public JCheckBox getVietnamese() {
        return vietnamese;
    }

    /**
     * Gets urdu.
     *
     * @return the urdu
     */
    public JCheckBox getUrdu() {
        return urdu;
    }

    /**
     * Gets japanese.
     *
     * @return the japanese
     */
    public JCheckBox getJapanese() {
        return japanese;
    }

    /**
     * Gets version list.
     *
     * @return the version list
     */
    public JList getVersionList() {
        return versionList;
    }

    /**
     * Gets infosystem list.
     *
     * @return the infosystem list
     */
    public JList getInfosystemList() {
        return infosystemList;
    }

    /**
     * Gets table list.
     *
     * @return the table list
     */
    public JList getTableList() {
        return tableList;
    }

    /**
     * Gets screens list.
     *
     * @return the screens list
     */
    public JList getScreensList() {
        return screensList;
    }

    /**
     * Gets enums list.
     *
     * @return the enums list
     */
    public JList getEnumsList() {
        return enumsList;
    }

    /**
     * Gets named types list.
     *
     * @return the named types list
     */
    public JList getNamedTypesList() {
        return namedTypesList;
    }

    /**
     * Gets root panel.
     *
     * @return the root panel
     */
    public JPanel getRootPanel() {
        return rootPanel;
    }

    /**
     * Gets tabbed pane 1.
     *
     * @return the tabbed pane 1
     */
    public JTabbedPane getTabbedPane1() {
        return tabbedPane1;
    }

    /**
     * Gets from version.
     *
     * @return the from version
     */
    public JTextField getFromVersion() {
        return fromVersion;
    }

    /**
     * Gets to version.
     *
     * @return the to version
     */
    public JTextField getToVersion() {
        return toVersion;
    }

    /**
     * Gets add version.
     *
     * @return the add version
     */
    public JButton getAddVersion() {
        return addVersion;
    }

    /**
     * Gets remove version.
     *
     * @return the remove version
     */
    public JButton getRemoveVersion() {
        return removeVersion;
    }

    /**
     * Gets content.
     *
     * @return the content
     */
    public JTabbedPane getContent() {
        return content;
    }

    /**
     * Gets infosystem working directory.
     *
     * @return the infosystem working directory
     */
    public JTextField getInfosystemWorkingDirectory() {
        return infosystemWorkingDirectory;
    }

    /**
     * Gets infosystem search word.
     *
     * @return the infosystem search word
     */
    public JTextField getInfosystemSearchWord() {
        return infosystemSearchWord;
    }

    /**
     * Gets remove infosystem.
     *
     * @return the remove infosystem
     */
    public JButton getRemoveInfosystem() {
        return removeInfosystem;
    }

    /**
     * Gets add infosystem.
     *
     * @return the add infosystem
     */
    public JButton getAddInfosystem() {
        return addInfosystem;
    }

    /**
     * Gets table.
     *
     * @return the table
     */
    public JTextField getTable() {
        return table;
    }

    /**
     * Gets remove table.
     *
     * @return the remove table
     */
    public JButton getRemoveTable() {
        return removeTable;
    }

    /**
     * Gets add table.
     *
     * @return the add table
     */
    public JButton getAddTable() {
        return addTable;
    }

    /**
     * Gets screens table.
     *
     * @return the screens table
     */
    public JTextField getScreensTable() {
        return screensTable;
    }

    /**
     * Gets screens group.
     *
     * @return the screens group
     */
    public JTextField getScreensGroup() {
        return screensGroup;
    }

    /**
     * Gets screens a.
     *
     * @return the screens a
     */
    public JCheckBox getScreensA() {
        return screensA;
    }

    /**
     * Gets screens b.
     *
     * @return the screens b
     */
    public JCheckBox getScreensB() {
        return screensB;
    }

    /**
     * Gets screens c.
     *
     * @return the screens c
     */
    public JCheckBox getScreensC() {
        return screensC;
    }

    /**
     * Gets screens d.
     *
     * @return the screens d
     */
    public JCheckBox getScreensD() {
        return screensD;
    }

    /**
     * Gets screens e.
     *
     * @return the screens e
     */
    public JCheckBox getScreensE() {
        return screensE;
    }

    /**
     * Gets screens f.
     *
     * @return the screens f
     */
    public JCheckBox getScreensF() {
        return screensF;
    }

    /**
     * Gets screens g.
     *
     * @return the screens g
     */
    public JCheckBox getScreensG() {
        return screensG;
    }

    /**
     * Gets screens h.
     *
     * @return the screens h
     */
    public JCheckBox getScreensH() {
        return screensH;
    }

    /**
     * Gets screens i.
     *
     * @return the screens i
     */
    public JCheckBox getScreensI() {
        return screensI;
    }

    /**
     * Gets screens j.
     *
     * @return the screens j
     */
    public JCheckBox getScreensJ() {
        return screensJ;
    }

    /**
     * Gets remove screen.
     *
     * @return the remove screen
     */
    public JButton getRemoveScreen() {
        return removeScreen;
    }

    /**
     * Gets add screen.
     *
     * @return the add screen
     */
    public JButton getAddScreen() {
        return addScreen;
    }

    /**
     * Gets enum name.
     *
     * @return the enum name
     */
    public JTextField getEnumName() {
        return enumName;
    }

    /**
     * Gets add enum.
     *
     * @return the add enum
     */
    public JButton getAddEnum() {
        return addEnum;
    }

    /**
     * Gets remove enums.
     *
     * @return the remove enums
     */
    public JButton getRemoveEnums() {
        return removeEnums;
    }

    /**
     * Gets named type.
     *
     * @return the named type
     */
    public JTextField getNamedType() {
        return namedType;
    }

    /**
     * Gets remove named type.
     *
     * @return the remove named type
     */
    public JButton getRemoveNamedType() {
        return removeNamedType;
    }

    /**
     * Gets add named type.
     *
     * @return the add named type
     */
    public JButton getAddNamedType() {
        return addNamedType;
    }

    /**
     * Gets event table.
     *
     * @return the event table
     */
    public JTextField getEventTable() {
        return eventTable;
    }

    /**
     * Gets event group.
     *
     * @return the event group
     */
    public JTextField getEventGroup() {
        return eventGroup;
    }

    /**
     * Gets event editor mode.
     *
     * @return the event editor mode
     */
    public JComboBox<String> getEventEditorMode() {
        return eventEditorMode;
    }

    /**
     * Gets event.
     *
     * @return the event
     */
    public JComboBox<String> getEvent() {
        return event;
    }

    /**
     * Gets event field.
     *
     * @return the event field
     */
    public JTextField getEventField() {
        return eventField;
    }

    /**
     * Gets event handler.
     *
     * @return the event handler
     */
    public JTextField getEventHandler() {
        return eventHandler;
    }

    /**
     * Gets event head radio.
     *
     * @return the event head radio
     */
    public JRadioButton getEventHeadRadio() {
        return eventHeadRadio;
    }

    /**
     * Gets event table radio.
     *
     * @return the event table radio
     */
    public JRadioButton getEventTableRadio() {
        return eventTableRadio;
    }

    /**
     * Gets event list.
     *
     * @return the event list
     */
    public JList getEventList() {
        return eventList;
    }

    /**
     * Gets event remove.
     *
     * @return the event remove
     */
    public JButton getEventRemove() {
        return eventRemove;
    }

    /**
     * Gets event add.
     *
     * @return the event add
     */
    public JButton getEventAdd() {
        return eventAdd;
    }

    /**
     * Gets event key.
     *
     * @return the event key
     */
    public JTextField getEventKey() {
        return eventKey;
    }

    /**
     * Gets abas home dir.
     *
     * @return the abas home dir
     */
    public JTextField getAbasHomeDir() {
        return abasHomeDir;
    }

    /**
     * Gets abas client dir.
     *
     * @return the abas client dir
     */
    public JTextField getAbasClientDir() {
        return abasClientDir;
    }

    /**
     * Gets edp user.
     *
     * @return the edp user
     */
    public JTextField getEdpUser() {
        return edpUser;
    }

    /**
     * Gets edp password.
     *
     * @return the edp password
     */
    public JPasswordField getEdpPassword() {
        return edpPassword;
    }

    /**
     * Gets edp port.
     *
     * @return the edp port
     */
    public JTextField getEdpPort() {
        return edpPort;
    }

    /**
     * Gets nexus port.
     *
     * @return the nexus port
     */
    public JTextField getNexusPort() {
        return nexusPort;
    }

    /**
     * Gets nexus name.
     *
     * @return the nexus name
     */
    public JTextField getNexusName() {
        return nexusName;
    }

    /**
     * Gets nexus password.
     *
     * @return the nexus password
     */
    public JPasswordField getNexusPassword() {
        return nexusPassword;
    }

    /**
     * Gets ssh user.
     *
     * @return the ssh user
     */
    public JTextField getSshUser() {
        return sshUser;
    }

    /**
     * Gets ssh host.
     *
     * @return the ssh host
     */
    public JTextField getSshHost() {
        return sshHost;
    }

    /**
     * Gets ssh port.
     *
     * @return the ssh port
     */
    public JTextField getSshPort() {
        return sshPort;
    }

    /**
     * Gets ssh password.
     *
     * @return the ssh password
     */
    public JTextField getSshPassword() {
        return sshPassword;
    }

    /**
     * Gets nexus user.
     *
     * @return the nexus user
     */
    public JTextField getNexusUser() {
        return nexusUser;
    }

    /**
     * Gets ssh key path.
     *
     * @return the ssh key path
     */
    public JTextField getSshKeyPath() {
        return sshKeyPath;
    }

    private void createUIComponents() {
        createBasicComponents();
        createLanguageComponents();
        createVersionComponents();
        createConnectionSettingsComponents();
        createContentComponents();

        setDefaults();

        setBasicLimits();
        setVersionLimits();
        setConnectionSettingsLimits();
        setContentLimits();
    }

    private void setBasicLimits() {
        limit(appID, BASIC_APP_ID_LIMIT);
        limit(appName, BASIC_APP_NAME_LIMIT);
        limit(vendorID, BASIC_VENDOR_ID_LIMIT);
        checkAlphaNumeric(appName);
        checkAlphaNumeric(appID);
        checkAlphaNumeric(vendorID);
        checkPackage(packageName);
        checkVersion(esdkVersion);
        checkAlphaNumeric(developmentVersion);
    }

    private void setVersionLimits() {
        checkAlphaNumeric(fromVersion);
        checkAlphaNumeric(toVersion);
    }

    private void setConnectionSettingsLimits() {
        checkNumeric(nexusVersion);
        checkNumeric(edpPort);
        checkNumeric(nexusPort);
        checkNumeric(sshPort);
    }

    private void setContentLimits() {
        setContentInfosystemsLimits();
        setContentTablesLimits();
        setContentScreenLimits();
        setContentEnumLimits();
        setContentNamedTypesLimits();
        setContentEventLimits();
    }

    private void setContentInfosystemsLimits() {
        checkAlphaNumeric(infosystemWorkingDirectory);
        checkAlphaNumeric(infosystemSearchWord);
    }

    private void setContentTablesLimits() {
        checkAlphaNumeric(table);
    }

    private void setContentScreenLimits() {
        checkAlphaNumeric(screensTable);
        checkAlphaNumeric(screensGroup);
    }

    private void setContentEnumLimits() {
        checkAlphaNumeric(enumName);
    }

    private void setContentNamedTypesLimits() {
        checkAlphaNumeric(namedType);
    }

    private void setContentEventLimits() {
        checkAlphaNumeric(eventTable);
        checkAlphaNumeric(eventGroup);
    }

    private void createBasicComponents() {
        //Basic
        appID = new JTextField();
        appName = new JTextField();
        vendorID = new JTextField();
        packageName = new JTextField();
        developmentVersion = new JTextField();
        esdkVersion = new JTextField();

        //Connection Settings
        edpHost = new JTextField();
        nexusHost = new JTextField();
        nexusVersion = new JTextField();
    }

    private void createLanguageComponents() {
        //Languages
        german = new JCheckBox();
        americanEnglish = new JCheckBox();
        english = new JCheckBox();
        french = new JCheckBox();
        spanish = new JCheckBox();
        italian = new JCheckBox();
        polish = new JCheckBox();
        russian = new JCheckBox();
        hungarian = new JCheckBox();
        czech = new JCheckBox();
        turkish = new JCheckBox();
        romanian = new JCheckBox();
        indonesian = new JCheckBox();
        dutch = new JCheckBox();
        macedonian = new JCheckBox();
        bulgarian = new JCheckBox();
        serbian = new JCheckBox();
        slovenian = new JCheckBox();
        slovakian = new JCheckBox();
        portugese = new JCheckBox();
        chineseSimplified = new JCheckBox();
        chineseTraditional = new JCheckBox();
        greek = new JCheckBox();
        thai = new JCheckBox();
        arabic = new JCheckBox();
        persian = new JCheckBox();
        vietnamese = new JCheckBox();
        urdu = new JCheckBox();
        japanese = new JCheckBox();
    }

    private void createVersionComponents() {
        //Versions
        fromVersion = new JTextField();
        toVersion = new JTextField();
        final DefaultListModel<String> versionModel = new DefaultListModel<>();
        versionList = new JBList<>(versionModel);
        addVersion = new JButton();
        removeVersion = new JButton();

        addVersion.addActionListener(e -> {
            if (fromVersion.getText().length() < FROM_VERSION_MIN) {
                Notifications.errorNotification("Version Range 'from' must be a valid Essentials Version!");
                return;
            }
            if (toVersion.getText().length() < TO_VERSION_MIN) {
                Notifications.errorNotification("Version Range 'to' must be a valid Essentials Version!");
                return;
            }
            versionModel.addElement(fromVersion.getText() + "-" + toVersion.getText());
        });

        removeVersion.addActionListener(e -> {
            if (!versionList.isSelectionEmpty())
                versionModel.removeElementAt(versionList.getSelectedIndex());
        });
    }

    private void createConnectionSettingsComponents() {
        abasClientDir = new JTextField();
        abasHomeDir = new JTextField();
        edpHost = new JTextField();
        edpPassword = new JPasswordField();
        edpPort = new JTextField();
        edpUser = new JTextField();
        nexusHost = new JTextField();
        nexusVersion = new JTextField();
        nexusPort = new JTextField();
        nexusUser = new JTextField();
        nexusPassword = new JPasswordField();
        nexusName = new JTextField();
        nexusHost = new JTextField();
        sshPort = new JTextField();
        sshUser = new JTextField();
        browseSSHKey = new JButton();
        browseSSHKey.addActionListener(e ->{
            final JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.addChoosableFileFilter(
                    new FileNameExtensionFilter("SSH Keys", "ppk", "pub"));
            jFileChooser.setAcceptAllFileFilterUsed(true);
            jFileChooser.showOpenDialog(new JPanel());
            if(jFileChooser.getSelectedFile()!= null){
                sshKeyPath.setText(Paths.get(jFileChooser.getSelectedFile().getAbsolutePath()).toString());
            }

        });
    }

    private void createContentComponents() {
        createContentInfosystemComponents();
        createContentTableComponents();
        createContentScreenComponents();
        createContentEnumComponents();
        createContentNamedTypesComponents();
        createContentEventRegistrationComponents();
    }

    private void createContentInfosystemComponents() {
        //Infosystems

        infosystemWorkingDirectory = new JTextField();
        infosystemSearchWord = new JTextField();
        addInfosystem = new JButton();
        removeInfosystem = new JButton();
        final DefaultListModel<String> infosystemListModel = new DefaultListModel<>();
        infosystemList = new JBList<>(infosystemListModel);

        addInfosystem.addActionListener(e -> {
            if (infosystemWorkingDirectory.getText().length() < INFOSYSTEMS_WORKING_DIRECTORY_MIN) {
                Notifications.errorNotification("Working directory must be a valid workspace!");
                return;
            }
            if (infosystemSearchWord.getText().length() < INFOSYSTEMS_SEARCHWORD_MIN) {
                Notifications.errorNotification("Searchword must be a valid Searchword!");
                return;
            }
            infosystemListModel.addElement("IS." + infosystemWorkingDirectory.getText().toUpperCase() + "." + infosystemSearchWord.getText().toUpperCase());
        });

        removeInfosystem.addActionListener(e -> {
            if (!infosystemList.isSelectionEmpty())
                infosystemListModel.removeElementAt(infosystemList.getSelectedIndex());
        });
    }

    private void createContentTableComponents() {
        //Tables
        table = new JTextField();
        addTable = new JButton();
        removeTable = new JButton();
        final DefaultListModel<String> tableListModel = new DefaultListModel<>();
        tableList = new JBList<>(tableListModel);

        addTable.addActionListener(e -> tableListModel.addElement(table.getText()));

        removeTable.addActionListener(e -> {
            if (!tableList.isSelectionEmpty())
                tableListModel.removeElementAt(tableList.getSelectedIndex());
        });
    }

    private void createContentScreenComponents() {
        screensTable = new JTextField();
        screensGroup = new JTextField();
        addScreen = new JButton();
        removeScreen = new JButton();
        final DefaultListModel<String> screensListModel = new DefaultListModel<>();
        screensList = new JBList<>(screensListModel);

        addScreen.addActionListener(e -> {
            if (!(screensA.isSelected() || screensB.isSelected() || screensC.isSelected() || screensD.isSelected() ||
                    screensE.isSelected() || screensF.isSelected() || screensG.isSelected() || screensH.isSelected() ||
                    screensI.isSelected() || screensJ.isSelected())){
                Notifications.errorNotification("At least one of the Priorities must be selected!");
                return;
            }

                String priorities = "";
            if (screensA.isSelected()) priorities += "A";
            if (screensB.isSelected()) priorities += "B";
            if (screensC.isSelected()) priorities += "C";
            if (screensD.isSelected()) priorities += "D";
            if (screensE.isSelected()) priorities += "E";
            if (screensF.isSelected()) priorities += "F";
            if (screensG.isSelected()) priorities += "G";
            if (screensH.isSelected()) priorities += "H";
            if (screensI.isSelected()) priorities += "I";
            if (screensJ.isSelected()) priorities += "J";
            final char[] prioritiesCharArray = priorities.toCharArray();
            String[] prioritiesArray = new String[prioritiesCharArray.length];
            for (int i = 0; i < prioritiesCharArray.length; i++) {
                prioritiesArray[i] = "\"" + prioritiesCharArray[i] + "\"";
            }
            screensListModel.addElement("\"" + screensTable.getText() + ":" + screensGroup.getText() + "\":" + Arrays.toString(prioritiesArray));
        });

        removeScreen.addActionListener(e -> {
            if (!screensList.isSelectionEmpty())
                screensListModel.removeElementAt(screensList.getSelectedIndex());
        });
    }

    private void createContentEnumComponents() {
        enumName = new JTextField();
        addEnum = new JButton();
        removeEnums = new JButton();
        final DefaultListModel<String> enumsListModel = new DefaultListModel<>();
        enumsList = new JBList<>(enumsListModel);

        addEnum.addActionListener(e -> {
            if (enumName.getText().length() < ENUMS_NAME_MIN) {
                Notifications.errorNotification("Enum Name must be at least " + ENUMS_NAME_MIN + " characters long!");
                return;
            }
            enumsListModel.addElement(enumName.getText());
        });

        removeEnums.addActionListener(e -> {
            if (!enumsList.isSelectionEmpty())
                enumsListModel.removeElementAt(enumsList.getSelectedIndex());
        });
    }

    private void createContentNamedTypesComponents() {
        namedType = new JTextField();
        addNamedType = new JButton();
        removeNamedType = new JButton();
        final DefaultListModel<String> namedTypesModel = new DefaultListModel<>();
        namedTypesList = new JBList<>(namedTypesModel);

        addNamedType.addActionListener(e -> {
            if (namedType.getText().length() < NAMED_TYPES_NAME_MIN) {
                Notifications.errorNotification("Named Type Name must be at least " + NAMED_TYPES_NAME_MIN + " characters long!");
                return;
            }
            namedTypesModel.addElement(namedType.getText());
        });

        removeNamedType.addActionListener(e -> {
            if (!namedTypesList.isSelectionEmpty()) {
                namedTypesModel.removeElementAt(namedTypesList.getSelectedIndex());
            }
        });
    }

    private void createContentEventRegistrationComponents() {
        //Event Registration
        eventTable = new JTextField();
        eventGroup = new JTextField();
        eventAdd = new JButton();
        eventRemove = new JButton();

        eventEditorMode = new ComboBox<>();
        eventEditorMode.addItem("*");
        eventEditorMode.addItem("Fertig");
        eventEditorMode.addItem("Hole");
        eventEditorMode.addItem("Freigeben");
        eventEditorMode.addItem("Lieferung");
        eventEditorMode.addItem("Rechnung");
        eventEditorMode.addItem("Zahlung");
        eventEditorMode.addItem("Storno");
        eventEditorMode.addItem("Kalkulieren");
        eventEditorMode.addItem("Uebertragen");
        eventEditorMode.addItem("Loeschen");
        eventEditorMode.addItem("Verweis");
        eventEditorMode.addItem("Zeigen");
        eventEditorMode.addItem("Aendern");
        eventEditorMode.addItem("Neu");
        eventEditorMode.addItem("Waehlen");
        eventEditorMode.addItem("Abbruch");
        eventEditorMode.addItem("Kopieren");
        eventEditorMode.addItem("Leer");

        event = new ComboBox<>();
        event.addItem("*");
        event.addItem("ScreenEnter");
        event.addItem("ScreenValidation");
        event.addItem("ScreenEnd");
        event.addItem("ScreenExit");
        event.addItem("ButtonBefore");
        event.addItem("ButtonAfter");
        event.addItem("FieldEnter");
        event.addItem("FieldValidation");
        event.addItem("FieldExit");
        event.addItem("RowInsertBefore");
        event.addItem("RowInsertAfter");
        event.addItem("RowMoveBefore");
        event.addItem("RowMoveAfter");
        event.addItem("RowDeleteBefore");
        event.addItem("RowDeleteAfter");

        final DefaultListModel<String> eventListModel = new DefaultListModel<>();
        eventList = new JBList<>(eventListModel);

        eventAdd.addActionListener(e -> {

            if (!eventHandler.getText().contains("@") || eventHandler.getText().length() < 8 || !eventHandler.getText().startsWith("java:")) {
                Notifications.errorNotification("Eventhandler must contain '@' and 'java:' and must be at least 8 characters long!");
                return;
            }

            if (!eventTable.getText().isEmpty() && !eventGroup.getText().isEmpty() && !eventField.getText().isEmpty()
                    && !eventHandler.getText().isEmpty()) {


                String editorModeString = eventEditorMode.getSelectedItem().toString().toLowerCase();

                String eventString;
                switch (event.getSelectedItem().toString()) {
                    default:
                        eventString = "*";
                        break;
                    case "ScreenEnter":
                        eventString = "maskein";
                        break;
                    case "ScreenValidation":
                        eventString = "maskpruef";
                        break;
                    case "ScreenEnd":
                        eventString = "maskende";
                        break;
                    case "ScreenExit":
                        eventString = "maskabbr";
                        break;

                    case "ButtonBefore":
                        eventString = "buttonvor";
                        break;
                    case "ButtonAfter":
                        eventString = "buttonnach";
                        break;

                    case "FieldEnter":
                        eventString = "feldein";
                        break;
                    case "FieldValidation":
                        eventString = "feldpruef";
                        break;
                    case "FieldExit":
                        eventString = "feldaus";
                        break;
                    case "RowInsertBefore":
                        eventString = "zreinvo";
                        break;
                    case "RowInsertAfter":
                        eventString = "zreinna";
                        break;
                    case "RowMoveBefore":
                        eventString = "zbewvo";
                        break;
                    case "RowMoveAfter":
                        eventString = "zbewna";
                        break;
                    case "RowDeleteBefore":
                        eventString = "zrausvo";
                        break;
                    case "RowDeleteAfter":
                        eventString = "zrausna";
                        break;
                }

                fopJsonList.add(new FopJson(
                        eventTable.getText(),
                        eventGroup.getText(),
                        editorModeString, eventString, eventKey.getText(),
                        eventField.getText(), eventHeadRadio.isSelected() ? "K" : "T",
                        "[C]",
                        eventHandler.getText()));
                eventListModel.addElement(eventField.getText() + " | " + eventHandler.getText());
            }
        });

        eventRemove.addActionListener(e -> {
            if (!eventList.isSelectionEmpty()) {
                fopJsonList.remove(eventList.getSelectedIndex());
                eventListModel.removeElementAt(eventList.getSelectedIndex());
            }
        });
    }

    private void setDefaults() {
        final JsonObject defaults = loadDefaults();
        if (defaults != null) {
            setBasicDefaults(defaults);
            setLanguageDefaults(defaults);
            setConnectionSettingsDefaults(defaults);
            setVersionDefaults(defaults);
        }
    }

    private void setBasicDefaults(JsonObject defaults) {
        developmentVersion.setText(defaults.get("developmentVersion").getAsString());
        esdkVersion.setText(defaults.get("esdkVersion").getAsString());
    }

    private void setConnectionSettingsDefaults(JsonObject defaults) {
        abasHomeDir.setText(defaults.get("abasHomeDir").getAsString());
        abasClientDir.setText(defaults.get("abasClientDir").getAsString());
        edpHost.setText(defaults.get("edpHost").getAsString());
        edpPort.setText(defaults.get("edpPort").getAsString());
        edpUser.setText(defaults.get("edpPassword").getAsString());
        nexusHost.setText(defaults.get("nexusHost").getAsString());
        nexusVersion.setText(defaults.get("nexusVersion").getAsString());
        nexusPort.setText(defaults.get("nexusPort").getAsString());
        nexusUser.setText(defaults.get("nexusUser").getAsString());
        nexusPassword.setText(defaults.get("nexusPassword").getAsString());
        nexusName.setText(defaults.get("nexusName").getAsString());
        sshPort.setText(defaults.get("sshPort").getAsString());
        sshUser.setText(defaults.get("sshUser").getAsString());
    }

    private void setLanguageDefaults(JsonObject defaults) {
        final JsonObject languages = defaults.get("languages").getAsJsonObject();
        german.setSelected(languages.get("German").getAsBoolean());
        americanEnglish.setSelected(languages.get("AmericanEnglish").getAsBoolean());
        english.setSelected(languages.get("English").getAsBoolean());
        french.setSelected(languages.get("French").getAsBoolean());
        spanish.setSelected(languages.get("Spanish").getAsBoolean());
        italian.setSelected(languages.get("Italian").getAsBoolean());
        polish.setSelected(languages.get("Polish").getAsBoolean());
        russian.setSelected(languages.get("Russian").getAsBoolean());
        hungarian.setSelected(languages.get("Hungarian").getAsBoolean());
        czech.setSelected(languages.get("Czech").getAsBoolean());
        turkish.setSelected(languages.get("Turkish").getAsBoolean());
        romanian.setSelected(languages.get("Romanian").getAsBoolean());
        indonesian.setSelected(languages.get("Indonesian").getAsBoolean());
        dutch.setSelected(languages.get("Dutch").getAsBoolean());
        macedonian.setSelected(languages.get("Macedonian").getAsBoolean());
        bulgarian.setSelected(languages.get("Bulgarian").getAsBoolean());
        serbian.setSelected(languages.get("Serbian").getAsBoolean());
        slovenian.setSelected(languages.get("Slovenian").getAsBoolean());
        slovakian.setSelected(languages.get("Slovakian").getAsBoolean());
        portugese.setSelected(languages.get("Portuguese").getAsBoolean());
        chineseSimplified.setSelected(languages.get("ChineseSimplified").getAsBoolean());
        chineseTraditional.setSelected(languages.get("ChineseTraditional").getAsBoolean());
        greek.setSelected(languages.get("Greek").getAsBoolean());
        thai.setSelected(languages.get("Thai").getAsBoolean());
        arabic.setSelected(languages.get("Arabic").getAsBoolean());
        persian.setSelected(languages.get("Persian").getAsBoolean());
        vietnamese.setSelected(languages.get("Vietnamese").getAsBoolean());
        urdu.setSelected(languages.get("Urdu").getAsBoolean());
        japanese.setSelected(languages.get("Japanese").getAsBoolean());
    }

    private void setVersionDefaults(JsonObject defaults) {
        fromVersion.setText(defaults.get("essentialsVersionRangeFrom").getAsString());
        toVersion.setText(defaults.get("essentialsVersionRangeTo").getAsString());
    }

    private void limit(JTextField jTextField, int limit) {
        jTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (jTextField.getText().length() >= limit) {
                    e.setKeyChar(Character.MIN_VALUE);
                } else {
                    super.keyTyped(e);
                }
            }
        });
    }

    private void checkAlphaNumeric(JTextField jTextField) {
        jTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!(Character.isLetterOrDigit(e.getKeyChar()))) {
                    e.setKeyChar(Character.MIN_VALUE);
                } else {
                    super.keyTyped(e);
                }
            }
        });
    }

    private void checkNumeric(JTextField jTextField) {
        jTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!(Character.isDigit(e.getKeyChar()))) {
                    e.setKeyChar(Character.MIN_VALUE);
                } else {
                    super.keyTyped(e);
                }
            }
        });
    }

    private void checkVersion(JTextField jTextField) {
        jTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!(Character.isDigit(e.getKeyChar()) || Character.valueOf('.').equals(e.getKeyChar()))) {
                    e.setKeyChar(Character.MIN_VALUE);
                } else {
                    super.keyTyped(e);
                }
            }
        });
    }

    private void checkPackage(JTextField jTextField) {
        jTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!(Character.isLetter(e.getKeyChar()) || Character.valueOf('.').equals(e.getKeyChar()))) {
                    e.setKeyChar(Character.MIN_VALUE);
                } else {
                    super.keyTyped(e);
                }
            }
        });
    }



    @Override
    public boolean validate() throws ConfigurationException {
        validateBasics();
        validateConnectionSettings();
        return super.validate();
    }

    private void validateBasics() throws ConfigurationException {
        if (appID.getText().length() != BASIC_APP_ID_LIMIT)
            throw new ConfigurationException("Please fill App ID with exact " + BASIC_APP_ID_LIMIT + " characters!");
        if (vendorID.getText().length() != BASIC_VENDOR_ID_LIMIT)
            throw new ConfigurationException("Please fill Vendor ID with exact " + BASIC_VENDOR_ID_LIMIT + " characters!");
        if (appName.getText().length() < BASIC_APP_NAME_MIN)
            throw new ConfigurationException("Please fill App Name with at least " + BASIC_APP_NAME_MIN + " characters!");
        if (packageName.getText().length() < BASIC_PACKAGE_NAME_MIN)
            throw new ConfigurationException("Please fill Package Name with at least " + BASIC_PACKAGE_NAME_MIN + " characters!");
    }

    private void validateConnectionSettings() throws ConfigurationException {
        if (edpHost.getText().length() > 0 && edpHost.getText().length() < CONNECTION_ABAS_HOST_MIN)
            throw new ConfigurationException("Please fill Abas Host with at least " + CONNECTION_ABAS_HOST_MIN + " characters!");
        if (nexusHost.getText().length() > 0 && nexusHost.getText().length() < CONNECTION_NEXUS_HOST_MIN)
            throw new ConfigurationException("Please fill Nexus Host with at least " + CONNECTION_NEXUS_HOST_MIN + " characters!");
    }

    private JsonObject loadDefaults() {
        Gson gson = new Gson();
        try {

            InputStream is = getClass().getResourceAsStream("/defaults/defaultValues.json");
            InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            isr.close();
            is.close();
            return gson.fromJson(sb.toString(), JsonObject.class);

/*
Can´t be used because Plugin is packed as JAR and the File has to be read as Stream
return gson.fromJson(new FileReader(new File(getClass().getResource("/defaults/defaultValues.json").getFile())), JsonObject.class);
*/
        } catch (IOException e) {
            Notifications.errorNotification("Can´t find default Values!");
        }
        return null;
    }
}
