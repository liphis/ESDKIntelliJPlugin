package module.builder;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import data.FopJson;
import module.ESDKSettingsDialog;
import module.ESDKSettingsWizardStep;
import utils.FileUtils;
import utils.Notifications;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * A helper class to Build ESDK Projects
 */
public class ProjectBuilder {

    private static final String ARCHIVE_EXTENSION = ".zip";
    private static final String TEMPLATE_NAME = "/mctrigger";
    private static final String IDEA_FOLDER = "/.idea";

    /**
     * Unpack.
     *
     * @param baseDirectory the base directory
     */
    public void unpack(final String baseDirectory) {
        try {
            InputStream file = getClass().getResourceAsStream("/template" + TEMPLATE_NAME + ARCHIVE_EXTENSION);
            FileUtils.copyFileUsingStream(
                    file, new File(baseDirectory + TEMPLATE_NAME + ARCHIVE_EXTENSION));

            FileUtils.unzip(
                    baseDirectory + TEMPLATE_NAME + ARCHIVE_EXTENSION,
                    baseDirectory);
            FileUtils.deleteFile(baseDirectory + TEMPLATE_NAME + ARCHIVE_EXTENSION);
        } catch (IOException e) {
            Notifications.errorNotification("Couldn´t decompress Project Files!");
        }
    }

    /**
     * Enable gradle.
     *
     * @param baseDirectory the base directory
     */
    public void enableGradle(final String baseDirectory) {
        FileUtils.deleteFile(baseDirectory + IDEA_FOLDER + "/gradle.xml");
        try {
            FileUtils.copyFileUsingStream(this.getClass().getResourceAsStream("/ideaTemplates/gradle.xml"),
                    new File(baseDirectory + IDEA_FOLDER + "/gradle.xml"));
        } catch (IOException e) {
            Notifications.errorNotification("Couldn´t copy Project Files!");
        }
    }

    /**
     * Delete module.
     *
     * @param baseDirectory the base directory
     * @param moduleName    the module name
     */
    public void deleteModule(final String baseDirectory, final String moduleName) {

        try {
            FileUtils.deleteLine(baseDirectory + IDEA_FOLDER + "/modules.xml", moduleName);
        } catch (IOException e) {
            Notifications.errorNotification("Couldn´t remove default Module!");
        }
    }

    /**
     * Sets basic.
     *
     * @param baseDirectory          the base directory
     * @param esdkSettingsWizardStep the esdk settings wizard step
     */
    public void setBasic(final String baseDirectory, final ESDKSettingsWizardStep esdkSettingsWizardStep) {
        String buildGradlepath = baseDirectory + "/build.gradle";
        try {
            FileUtils.replaceStringInFile(buildGradlepath,
                    "appId = \"mctrg\"", "appId = \""
                            + esdkSettingsWizardStep.getAppID().getText() + "\"");
            FileUtils.replaceStringInFile(buildGradlepath,
                    "name = \"mctrigger\"", "name = \""
                            + esdkSettingsWizardStep.getAppName().getText() + "\"");
            FileUtils.replaceStringInFile(buildGradlepath,
                    "vendorId = \"ac\"", "vendorId = \""
                            + esdkSettingsWizardStep.getVendorID().getText() + "\"");
            FileUtils.replaceStringInFile(buildGradlepath,
                    "group = 'de.cons'", "group = '"
                            + esdkSettingsWizardStep.getPackageName().getText() + "'");


            FileUtils.replaceStringInFile(buildGradlepath, "id \"de.abas.esdk\" version \"0.11.8\"",
                    "id \"de.abas.esdk\" version \"" + esdkSettingsWizardStep.getEsdkVersion().getText() + "\"");
            FileUtils.replaceStringInFile(baseDirectory
                            + "/docker-compose.yml",
                    "image: sdp.registry.abas.sh/abas/test:2017r4n16",
                    "image: sdp.registry.abas.sh/abas/test:"
                            + esdkSettingsWizardStep.getDevelopmentVersion().getText());

            renamePackage(baseDirectory, esdkSettingsWizardStep.getPackageName().getText());
        } catch (IOException e) {
            Notifications.errorNotification("Couldn´t prepare ESDK Project Basic Settings!");
        }
    }

    /**
     * Sets basic.
     *
     * @param baseDirectory      the base directory
     * @param esdkSettingsDialog the esdk settings dialog
     * @param projectReader      the project reader
     */
    public void setBasic(final String baseDirectory, final ESDKSettingsDialog esdkSettingsDialog, ProjectReader projectReader) {
        String buildGradlepath = baseDirectory + "/build.gradle";
        try {
            FileUtils.replaceStringInFile(buildGradlepath,
                    "appId = \"" + projectReader.appID + "\"",
                    "appId = \""
                            + esdkSettingsDialog.getAppID().getText() + "\"");
            FileUtils.replaceStringInFile(buildGradlepath,
                    "name = \"" + projectReader.appName + "\"",
                    "name = \""
                            + esdkSettingsDialog.getAppName().getText() + "\"");
            FileUtils.replaceStringInFile(buildGradlepath,
                    "vendorId = \"" + projectReader.vendorID + "\"", "vendorId = \""
                            + esdkSettingsDialog.getVendorID().getText() + "\"");
            FileUtils.replaceStringInFile(buildGradlepath,
                    "group = '" + projectReader.packageName + "'", "group = '"
                            + esdkSettingsDialog.getPackageName().getText() + "'");


            FileUtils.replaceStringInFile(buildGradlepath, "id\\s*\\S*\"de.abas.esdk\"\\s*\\S*version\\s*\\S*\"" + projectReader.esdkVersion + "\"",
                    "id \"de.abas.esdk\" version \"" + esdkSettingsDialog.getEsdkVersion().getText() + "\"");
            FileUtils.replaceStringInFile(baseDirectory + "/docker-compose.yml",
                    "image: sdp.registry.abas.sh/abas/test:" + projectReader.developmentVersion,
                    "image: sdp.registry.abas.sh/abas/test:"
                            + esdkSettingsDialog.getDevelopmentVersion().getText());

            renamePackage(baseDirectory, esdkSettingsDialog.getPackageName().getText());
        } catch (IOException e) {
            Notifications.errorNotification("Couldn´t prepare ESDK Project Basic Settings!");
        }
    }

    /**
     * Sets connection settings.
     *
     * @param baseDirecotry          the base direcotry
     * @param esdkSettingsWizardStep the esdk settings wizard step
     */
    public void setConnectionSettings(final String baseDirecotry, final ESDKSettingsWizardStep esdkSettingsWizardStep) {
        String propertiesTemplateGradlePath = baseDirecotry + "/gradle.properties";
        try {
            FileUtils.replaceStringInFile(propertiesTemplateGradlePath, "ABAS_HOMEDIR=/abas/s3",
                    "ABAS_HOMEDIR=" + esdkSettingsWizardStep.getAbasHomeDir().getText());
            FileUtils.replaceStringInFile(propertiesTemplateGradlePath, "ABAS_CLIENTDIR=/abas/erp",
                    "ABAS_CLIENTDIR=" + esdkSettingsWizardStep.getAbasClientDir().getText());
            final String[] newsplit = esdkSettingsWizardStep.getAbasClientDir().getText().split("/");
            final String newAbasClientID = newsplit[newsplit.length - 1];
            FileUtils.replaceStringInFile(propertiesTemplateGradlePath, "ABAS_CLIENTID=erp",
                    "ABAS_CLIENTID=" + newAbasClientID);

            FileUtils.replaceStringInFile(propertiesTemplateGradlePath, "EDP_HOST=undefined",
                    "EDP_HOST=" + esdkSettingsWizardStep.getEdpHost().getText());
            FileUtils.replaceStringInFile(propertiesTemplateGradlePath, "EDP_USER=",
                    "EDP_USER=" + esdkSettingsWizardStep.getEdpUser().getText());
            FileUtils.replaceStringInFile(propertiesTemplateGradlePath, "EDP_PASSWORD=sy",
                    "EDP_PASSWORD=" + new String(esdkSettingsWizardStep.getEdpPassword().getPassword()));
            FileUtils.replaceStringInFile(propertiesTemplateGradlePath, "EDP_PORT=6560",
                    "EDP_PORT=" + esdkSettingsWizardStep.getEdpPort().getText());


            FileUtils.replaceStringInFile(propertiesTemplateGradlePath, "NEXUS_HOST=undefined",
                    "NEXUS_HOST=" + esdkSettingsWizardStep.getNexusHost().getText());
            FileUtils.replaceStringInFile(propertiesTemplateGradlePath, "NEXUS_PORT=8081",
                    "NEXUS_PORT=" + esdkSettingsWizardStep.getNexusPort().getText());
            FileUtils.replaceStringInFile(propertiesTemplateGradlePath, "NEXUS_USER_NAME=admin",
                    "NEXUS_USER_NAME=" + esdkSettingsWizardStep.getNexusUser().getText());
            FileUtils.replaceStringInFile(propertiesTemplateGradlePath, "NEXUS_NAME=abas-essentials-libs",
                    "NEXUS_NAME=" + esdkSettingsWizardStep.getNexusName().getText());
            FileUtils.replaceStringInFile(propertiesTemplateGradlePath, "NEXUS_PASSWORD=admin123",
                    "NEXUS_PASSWORD=" + new String(esdkSettingsWizardStep.getNexusPassword().getPassword()));
            FileUtils.replaceStringInFile(propertiesTemplateGradlePath, "NEXUS_VERSION=2",
                    "NEXUS_VERSION=" + esdkSettingsWizardStep.getNexusVersion().getText());

            FileUtils.replaceStringInFile(propertiesTemplateGradlePath, "SSH_HOST=undefined",
                    "SSH_HOST=" + esdkSettingsWizardStep.getSshHost().getText());
            FileUtils.replaceStringInFile(propertiesTemplateGradlePath, "SSH_PORT=2205",
                    "SSH_PORT=" + esdkSettingsWizardStep.getSshPort().getText());
            FileUtils.replaceStringInFile(propertiesTemplateGradlePath, "SSH_USER=erp",
                    "SSH_USER=" + esdkSettingsWizardStep.getSshUser().getText());
            FileUtils.replaceStringInFile(propertiesTemplateGradlePath, "SSH_PASSWORD=none",
                    "SSH_PASSWORD=" + esdkSettingsWizardStep.getSshPassword().getText());
            FileUtils.replaceStringInFile(propertiesTemplateGradlePath, "SSH_KEY=",
                    "SSH_KEY=" + Paths.get(esdkSettingsWizardStep.getSshKeyPath().getText()).toString().replace("\\", "\\\\\\\\"));

        } catch (IOException e) {
            Notifications.errorNotification("Couldn´t prepare ESDK Project Connection Settings!");
        }
    }

    /**
     * Sets connection settings.
     *
     * @param baseDirecotry      the base direcotry
     * @param esdkSettingsDialog the esdk settings dialog
     * @param projectReader      the project reader
     */
    public void setConnectionSettings(final String baseDirecotry, final ESDKSettingsDialog esdkSettingsDialog,
                                      ProjectReader projectReader) {
        String propertiesTemplateGradlePath = baseDirecotry + "/gradle.properties";
        try {
            FileUtils.replaceStringInFile(propertiesTemplateGradlePath, "ABAS_HOMEDIR=" + projectReader.abasHomeDir,
                    "ABAS_HOMEDIR=" + esdkSettingsDialog.getAbasHomeDir().getText());
            FileUtils.replaceStringInFile(propertiesTemplateGradlePath, "ABAS_CLIENTDIR="+ projectReader.abasClientDir,
                    "ABAS_CLIENTDIR=" + esdkSettingsDialog.getAbasClientDir().getText());
            final String[] oldsplit = projectReader.abasClientDir.split("/");
            final String oldAbasClientID = oldsplit[oldsplit.length - 1];
            final String[] newsplit = esdkSettingsDialog.getAbasClientDir().getText().split("/");
            final String newAbasClientID = newsplit[newsplit.length - 1];
            FileUtils.replaceStringInFile(propertiesTemplateGradlePath, "ABAS_CLIENTID="+ oldAbasClientID,
                    "ABAS_CLIENTID=" + newAbasClientID);

            FileUtils.replaceStringInFile(propertiesTemplateGradlePath, "EDP_HOST=" + projectReader.edpHost,
                    "EDP_HOST=" + esdkSettingsDialog.getEdpHost().getText());
            FileUtils.replaceStringInFile(propertiesTemplateGradlePath, "EDP_USER=" + projectReader.edpUser,
                    "EDP_USER=" + esdkSettingsDialog.getEdpUser().getText());
            FileUtils.replaceStringInFile(propertiesTemplateGradlePath, "EDP_PASSWORD=" + projectReader.edpPassword,
                    "EDP_PASSWORD=" + new String(esdkSettingsDialog.getEdpPassword().getPassword()));
            FileUtils.replaceStringInFile(propertiesTemplateGradlePath, "EDP_PORT=" + projectReader.edpPort,
                    "EDP_PORT=" + esdkSettingsDialog.getEdpPort().getText());


            FileUtils.replaceStringInFile(propertiesTemplateGradlePath, "NEXUS_HOST=" + projectReader.nexusHost,
                    "NEXUS_HOST=" + esdkSettingsDialog.getNexusHost().getText());
            FileUtils.replaceStringInFile(propertiesTemplateGradlePath, "NEXUS_PORT=" + projectReader.nexusPort,
                    "NEXUS_PORT=" + esdkSettingsDialog.getNexusPort().getText());
            FileUtils.replaceStringInFile(propertiesTemplateGradlePath, "NEXUS_USER_NAME=" + projectReader.nexusUser,
                    "NEXUS_USER_NAME=" + esdkSettingsDialog.getNexusUser().getText());
            FileUtils.replaceStringInFile(propertiesTemplateGradlePath, "NEXUS_NAME=" + projectReader.nexusName,
                    "NEXUS_NAME=" + esdkSettingsDialog.getNexusName().getText());
            FileUtils.replaceStringInFile(propertiesTemplateGradlePath, "NEXUS_PASSWORD=" + projectReader.nexusPassword,
                    "NEXUS_PASSWORD=" + new String(esdkSettingsDialog.getNexusPassword().getPassword()));
            FileUtils.replaceStringInFile(propertiesTemplateGradlePath, "NEXUS_VERSION=" + projectReader.nexusVersion,
                    "NEXUS_VERSION=" + esdkSettingsDialog.getNexusVersion().getText());

            FileUtils.replaceStringInFile(propertiesTemplateGradlePath, "SSH_HOST=" + projectReader.sshHost,
                    "SSH_HOST=" + esdkSettingsDialog.getSshHost().getText());
            FileUtils.replaceStringInFile(propertiesTemplateGradlePath, "SSH_PORT=" + projectReader.sshPort,
                    "SSH_PORT=" + esdkSettingsDialog.getSshPort().getText());
            FileUtils.replaceStringInFile(propertiesTemplateGradlePath, "SSH_USER=" + projectReader.sshUser,
                    "SSH_USER=" + esdkSettingsDialog.getSshUser().getText());
            FileUtils.replaceStringInFile(propertiesTemplateGradlePath, "SSH_PASSWORD=" + projectReader.sshPassword,
                    "SSH_PASSWORD=" + esdkSettingsDialog.getSshPassword().getText());
            FileUtils.replaceStringInFile(propertiesTemplateGradlePath, "SSH_KEY=" + Paths.get(projectReader.sshKey)
                            .toString().replace("\\", "\\\\\\\\"),
                    "SSH_KEY=" + Paths.get(esdkSettingsDialog.getSshKeyPath().getText())
                            .toString().replace("\\", "\\\\\\\\"));
        } catch (IOException e) {
            Notifications.errorNotification("Couldn´t prepare ESDK Project Connection Settings!");
        }
    }

    /**
     * Sets languages.
     *
     * @param baseDirectory          the base directory
     * @param esdkSettingsWizardStep the esdk settings wizard step
     */
    public void setLanguages(final String baseDirectory, final ESDKSettingsWizardStep esdkSettingsWizardStep) {
        String buildGradlepath = baseDirectory + "/build.gradle";
        try {
            String languageString = "";

            if (esdkSettingsWizardStep.getGerman().isSelected()) languageString += "D";
            if (esdkSettingsWizardStep.getAmericanEnglish().isSelected()) languageString += "A";
            if (esdkSettingsWizardStep.getEnglish().isSelected()) languageString += "E";
            if (esdkSettingsWizardStep.getFrench().isSelected()) languageString += "F";
            if (esdkSettingsWizardStep.getSpanish().isSelected()) languageString += "S";
            if (esdkSettingsWizardStep.getItalian().isSelected()) languageString += "I";
            if (esdkSettingsWizardStep.getPolish().isSelected()) languageString += "P";
            if (esdkSettingsWizardStep.getRussian().isSelected()) languageString += "R";
            if (esdkSettingsWizardStep.getHungarian().isSelected()) languageString += "U";
            if (esdkSettingsWizardStep.getCzech().isSelected()) languageString += "C";
            if (esdkSettingsWizardStep.getTurkish().isSelected()) languageString += "T";
            if (esdkSettingsWizardStep.getRomanian().isSelected()) languageString += "O";
            if (esdkSettingsWizardStep.getIndonesian().isSelected()) languageString += "B";
            if (esdkSettingsWizardStep.getDutch().isSelected()) languageString += "N";
            if (esdkSettingsWizardStep.getMacedonian().isSelected()) languageString += "M";
            if (esdkSettingsWizardStep.getBulgarian().isSelected()) languageString += "X";
            if (esdkSettingsWizardStep.getSerbian().isSelected()) languageString += "Y";
            if (esdkSettingsWizardStep.getSlovenian().isSelected()) languageString += "L";
            if (esdkSettingsWizardStep.getSlovakian().isSelected()) languageString += "W";
            if (esdkSettingsWizardStep.getPortugese().isSelected()) languageString += "G";
            if (esdkSettingsWizardStep.getChineseSimplified().isSelected()) languageString += "Q";
            if (esdkSettingsWizardStep.getChineseTraditional().isSelected()) languageString += "H";
            if (esdkSettingsWizardStep.getGreek().isSelected()) languageString += "Z";
            if (esdkSettingsWizardStep.getThai().isSelected()) languageString += "K";
            if (esdkSettingsWizardStep.getArabic().isSelected()) languageString += "V";
            if (esdkSettingsWizardStep.getPersian().isSelected()) languageString += "J";
            if (esdkSettingsWizardStep.getVietnamese().isSelected()) languageString += "9";
            if (esdkSettingsWizardStep.getUrdu().isSelected()) languageString += "8";
            if (esdkSettingsWizardStep.getJapanese().isSelected()) languageString += "7";

            FileUtils.replaceStringInFile(buildGradlepath, "languages = \"DA\"",
                    "languages = \"" + languageString + "\"");
        } catch (IOException e) {
            Notifications.errorNotification("Couldn´t prepare ESDK Project Language Settings!");
        }
    }

    /**
     * Sets languages.
     *
     * @param baseDirectory      the base directory
     * @param esdkSettingsDialog the esdk settings dialog
     * @param projectReader      the project reader
     */
    public void setLanguages(String baseDirectory, ESDKSettingsDialog esdkSettingsDialog, ProjectReader projectReader) {
        String buildGradlepath = baseDirectory + "/build.gradle";
        try {
            String languageString = "";

            if (esdkSettingsDialog.getGerman().isSelected()) languageString += "D";
            if (esdkSettingsDialog.getAmericanEnglish().isSelected()) languageString += "A";
            if (esdkSettingsDialog.getEnglish().isSelected()) languageString += "E";
            if (esdkSettingsDialog.getFrench().isSelected()) languageString += "F";
            if (esdkSettingsDialog.getSpanish().isSelected()) languageString += "S";
            if (esdkSettingsDialog.getItalian().isSelected()) languageString += "I";
            if (esdkSettingsDialog.getPolish().isSelected()) languageString += "P";
            if (esdkSettingsDialog.getRussian().isSelected()) languageString += "R";
            if (esdkSettingsDialog.getHungarian().isSelected()) languageString += "U";
            if (esdkSettingsDialog.getCzech().isSelected()) languageString += "C";
            if (esdkSettingsDialog.getTurkish().isSelected()) languageString += "T";
            if (esdkSettingsDialog.getRomanian().isSelected()) languageString += "O";
            if (esdkSettingsDialog.getIndonesian().isSelected()) languageString += "B";
            if (esdkSettingsDialog.getDutch().isSelected()) languageString += "N";
            if (esdkSettingsDialog.getMacedonian().isSelected()) languageString += "M";
            if (esdkSettingsDialog.getBulgarian().isSelected()) languageString += "X";
            if (esdkSettingsDialog.getSerbian().isSelected()) languageString += "Y";
            if (esdkSettingsDialog.getSlovenian().isSelected()) languageString += "L";
            if (esdkSettingsDialog.getSlovakian().isSelected()) languageString += "W";
            if (esdkSettingsDialog.getPortugese().isSelected()) languageString += "G";
            if (esdkSettingsDialog.getChineseSimplified().isSelected()) languageString += "Q";
            if (esdkSettingsDialog.getChineseTraditional().isSelected()) languageString += "H";
            if (esdkSettingsDialog.getGreek().isSelected()) languageString += "Z";
            if (esdkSettingsDialog.getThai().isSelected()) languageString += "K";
            if (esdkSettingsDialog.getArabic().isSelected()) languageString += "V";
            if (esdkSettingsDialog.getPersian().isSelected()) languageString += "J";
            if (esdkSettingsDialog.getVietnamese().isSelected()) languageString += "9";
            if (esdkSettingsDialog.getUrdu().isSelected()) languageString += "8";
            if (esdkSettingsDialog.getJapanese().isSelected()) languageString += "7";

            FileUtils.replaceStringInFile(buildGradlepath, "languages = \"" + projectReader.languages + "\"",
                    "languages = \"" + languageString + "\"");
        } catch (IOException e) {
            Notifications.errorNotification("Couldn´t prepare ESDK Project Language Settings!");
        }
    }

    /**
     * Sets versions.
     *
     * @param baseDirectory          the base directory
     * @param esdkSettingsWizardStep the esdk settings wizard step
     */
    public void setVersions(final String baseDirectory, final ESDKSettingsWizardStep esdkSettingsWizardStep) {
        String buildGradlepath = baseDirectory + "/build.gradle";
        try {
            String[] versions = new String[esdkSettingsWizardStep.getVersionList().getModel().getSize()];
            for (int i = 0; i < esdkSettingsWizardStep.getVersionList().getModel().getSize(); i++) {
                versions[i] = "\"" + esdkSettingsWizardStep.getVersionList().getModel().getElementAt(i) + "\"";
            }
            FileUtils.replaceStringInFile(buildGradlepath, "essentialsVersions = \\[\\]",
                    "essentialsVersions = " + Arrays.toString(versions));
        } catch (IOException e) {
            Notifications.errorNotification("Couldn´t prepare ESDK Project Versions Settings!");
        }
    }

    /**
     * Sets versions.
     *
     * @param baseDirectory      the base directory
     * @param esdkSettingsDialog the esdk settings dialog
     * @param projectReader      the project reader
     */
    public void setVersions(final String baseDirectory, final ESDKSettingsDialog esdkSettingsDialog, ProjectReader projectReader) {
        String buildGradlepath = baseDirectory + "/build.gradle";
        String replacement = "[]";
        if (esdkSettingsDialog.getVersionList().getModel().getSize() > 0) {
            String[] versions = new String[esdkSettingsDialog.getVersionList().getModel().getSize()];
            for (int i = 0; i < esdkSettingsDialog.getVersionList().getModel().getSize(); i++) {
                versions[i] = "\"" + esdkSettingsDialog.getVersionList().getModel().getElementAt(i) + "\"";
            }
            replacement = Arrays.toString(versions);
        }
        try {
            FileUtils.replaceStringInFile(buildGradlepath, "essentialsVersions = \\[" + projectReader.versions + "\\]",
                    "essentialsVersions = " + replacement);
        } catch (IOException e) {
            Notifications.errorNotification("Couldn´t prepare ESDK Project Versions Settings!");
        }
    }

    /**
     * Sets fop json.
     *
     * @param baseDirectory          the base directory
     * @param esdkSettingsWizardStep the esdk settings wizard step
     */
    public void setFopJson(final String baseDirectory, final ESDKSettingsWizardStep esdkSettingsWizardStep) {
        if (ESDKSettingsWizardStep.getFopJsonList().size() > 0) {
            JsonArray jsonArray = new JsonArray();
            for (FopJson fopJson : ESDKSettingsWizardStep.getFopJsonList()) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("databaseName", fopJson.databaseName);
                jsonObject.addProperty("groupName", fopJson.groupName);
                jsonObject.addProperty("editorMode", fopJson.editorMode);
                jsonObject.addProperty("event", fopJson.event);
                jsonObject.addProperty("key", fopJson.key);
                jsonObject.addProperty("field", fopJson.field);
                jsonObject.addProperty("headOrTable", fopJson.headOrTable);
                jsonObject.addProperty("isContinue", fopJson.isContinue);
                jsonObject.addProperty("handler", fopJson.handler);
                jsonArray.add(jsonObject);
            }

            try {
                FileOutputStream fileStream = new FileOutputStream(baseDirectory + "/src/main/resources/fop.json");
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileStream, StandardCharsets.UTF_8);
                outputStreamWriter.write(new Gson().toJson(jsonArray));
                outputStreamWriter.close();
                fileStream.close();
            } catch (IOException e) {
                Notifications.errorNotification("Couldn´t prepare ESDK fop.json!");
            }
        }
    }

    /**
     * Sets fop json.
     *
     * @param baseDirectory      the base directory
     * @param esdkSettingsDialog the esdk settings dialog
     */
    public void setFopJson(final String baseDirectory, final ESDKSettingsDialog esdkSettingsDialog) {
        if (ESDKSettingsDialog.getFopJsonList().size() > 0) {
            JsonArray jsonArray = new JsonArray();
            for (FopJson fopJson : ESDKSettingsDialog.getFopJsonList()) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("databaseName", fopJson.databaseName);
                jsonObject.addProperty("groupName", fopJson.groupName);
                jsonObject.addProperty("editorMode", fopJson.editorMode);
                jsonObject.addProperty("event", fopJson.event);
                jsonObject.addProperty("key", fopJson.key);
                jsonObject.addProperty("field", fopJson.field);
                jsonObject.addProperty("headOrTable", fopJson.headOrTable);
                jsonObject.addProperty("isContinue", fopJson.isContinue);
                jsonObject.addProperty("handler", fopJson.handler);
                jsonArray.add(jsonObject);
            }

            try {
                FileOutputStream fileStream = new FileOutputStream(baseDirectory + "/src/main/resources/fop.json");
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileStream, StandardCharsets.UTF_8);
                outputStreamWriter.write(new Gson().toJson(jsonArray));
                outputStreamWriter.close();
                fileStream.close();
            } catch (IOException e) {
                Notifications.errorNotification("Couldn´t prepare ESDK fop.json!");
            }
        }
    }

    /**
     * Sets content infosystems.
     *
     * @param baseDirectory          the base directory
     * @param esdkSettingsWizardStep the esdk settings wizard step
     */
    public void setContentInfosystems(final String baseDirectory, final ESDKSettingsWizardStep esdkSettingsWizardStep) {
        String buildGradlepath = baseDirectory + "/build.gradle";
        if (esdkSettingsWizardStep.getInfosystemList().getModel().getSize() > 0) {
            String[] infosystems = new String[esdkSettingsWizardStep.getInfosystemList().getModel().getSize()];
            for (int i = 0; i < esdkSettingsWizardStep.getInfosystemList().getModel().getSize(); i++) {
                infosystems[i] = "\"" + esdkSettingsWizardStep.getInfosystemList().getModel().getElementAt(i) + "\"";
            }

            try {
                FileUtils.replaceStringInFile(buildGradlepath, "infosystems = \\[\\]",
                        "infosystems = " + Arrays.toString(infosystems));
            } catch (IOException e) {
                Notifications.errorNotification("Couldn´t prepare ESDK Infosystems!");
            }
        }
    }

    /**
     * Sets content infosystems.
     *
     * @param baseDirectory      the base directory
     * @param esdkSettingsDialog the esdk settings dialog
     * @param projectReader      the project reader
     */
    public void setContentInfosystems(final String baseDirectory, final ESDKSettingsDialog esdkSettingsDialog,
                                      ProjectReader projectReader) {
        String buildGradlepath = baseDirectory + "/build.gradle";
        String replacement = "[]";
        if (esdkSettingsDialog.getInfosystemList().getModel().getSize() > 0) {
            String[] infosystems = new String[esdkSettingsDialog.getInfosystemList().getModel().getSize()];
            for (int i = 0; i < esdkSettingsDialog.getInfosystemList().getModel().getSize(); i++) {
                infosystems[i] = "\"" + esdkSettingsDialog.getInfosystemList().getModel().getElementAt(i) + "\"";
            }
            replacement = Arrays.toString(infosystems);
        }
        try {
            FileUtils.replaceStringInFile(buildGradlepath, "infosystems = \\[" + projectReader.contentinfosystems + "\\]",
                    "infosystems = " + replacement);
        } catch (IOException e) {
            Notifications.errorNotification("Couldn´t prepare ESDK Infosystems!");
        }
    }

    /**
     * Sets content tables.
     *
     * @param baseDirectory          the base directory
     * @param esdkSettingsWizardStep the esdk settings wizard step
     */
    public void setContentTables(final String baseDirectory, final ESDKSettingsWizardStep esdkSettingsWizardStep) {
        String buildGradlepath = baseDirectory + "/build.gradle";
        if (esdkSettingsWizardStep.getTableList().getModel().getSize() > 0) {
            String[] tables = new String[esdkSettingsWizardStep.getTableList().getModel().getSize()];
            for (int i = 0; i < esdkSettingsWizardStep.getTableList().getModel().getSize(); i++) {
                tables[i] = "\"" + esdkSettingsWizardStep.getTableList().getModel().getElementAt(i) + "\"";
            }

            try {
                FileUtils.replaceStringInFile(buildGradlepath, "tables = \\[\\]",
                        "tables = " + Arrays.toString(tables));
            } catch (IOException e) {
                Notifications.errorNotification("Couldn´t prepare ESDK Tables!");
            }
        }
    }

    /**
     * Sets content tables.
     *
     * @param baseDirectory      the base directory
     * @param esdkSettingsDialog the esdk settings dialog
     * @param projectReader      the project reader
     */
    public void setContentTables(final String baseDirectory, final ESDKSettingsDialog esdkSettingsDialog,
                                 ProjectReader projectReader) {
        String buildGradlepath = baseDirectory + "/build.gradle";
        String replacement = "[]";
        if (esdkSettingsDialog.getTableList().getModel().getSize() > 0) {
            String[] tables = new String[esdkSettingsDialog.getTableList().getModel().getSize()];
            for (int i = 0; i < esdkSettingsDialog.getTableList().getModel().getSize(); i++) {
                tables[i] = "\"" + esdkSettingsDialog.getTableList().getModel().getElementAt(i) + "\"";
            }
            replacement = Arrays.toString(tables);
        }
        try {
            FileUtils.replaceStringInFile(buildGradlepath, "tables = \\[" + projectReader.contentTables + "\\]",
                    "tables = " + replacement);
        } catch (IOException e) {
            Notifications.errorNotification("Couldn´t prepare ESDK Tables!");
        }
    }

    /**
     * Sets content screens.
     *
     * @param baseDirectory          the base directory
     * @param esdkSettingsWizardStep the esdk settings wizard step
     */
    public void setContentScreens(final String baseDirectory, final ESDKSettingsWizardStep esdkSettingsWizardStep) {
        String buildGradlepath = baseDirectory + "/build.gradle";
        if (esdkSettingsWizardStep.getScreensList().getModel().getSize() > 0) {
            String[] screens = new String[esdkSettingsWizardStep.getScreensList().getModel().getSize()];
            for (int i = 0; i < esdkSettingsWizardStep.getScreensList().getModel().getSize(); i++) {
                screens[i] = esdkSettingsWizardStep.getScreensList().getModel().getElementAt(i).toString();
            }

            try {
                FileUtils.replaceStringInFile(buildGradlepath, "screens = \\[\\:\\]",
                        "screens = " + Arrays.toString(screens).trim().replaceAll("\\s*", ""));
            } catch (IOException e) {
                Notifications.errorNotification("Couldn´t prepare ESDK Screens!");
            }
        }
    }

    /**
     * Sets content screens.
     *
     * @param baseDirectory      the base directory
     * @param esdkSettingsDialog the esdk settings dialog
     * @param projectReader      the project reader
     */
    public void setContentScreens(final String baseDirectory, final ESDKSettingsDialog esdkSettingsDialog,
                                  ProjectReader projectReader) {
        String buildGradlepath = baseDirectory + "/build.gradle";
        String replacement = "[:]";
        if (esdkSettingsDialog.getScreensList().getModel().getSize() > 0) {
            String[] screens = new String[esdkSettingsDialog.getScreensList().getModel().getSize()];
            for (int i = 0; i < esdkSettingsDialog.getScreensList().getModel().getSize(); i++) {
                screens[i] = esdkSettingsDialog.getScreensList().getModel().getElementAt(i).toString();
            }
            replacement = Arrays.toString(screens).trim().replaceAll("\\s*", "");
        }
        try {
            FileUtils.replaceStringInFile(buildGradlepath, "screens = \\[" + projectReader.contentScreens.
                    replaceAll("\\[", "\\\\[").
                    replaceAll("\\]", "\\\\]") + "\\]", "screens = " + replacement
            );
        } catch (IOException e) {
            Notifications.errorNotification("Couldn´t prepare ESDK Screens!");
        }
    }

    /**
     * Sets content enums.
     *
     * @param baseDirectory          the base directory
     * @param esdkSettingsWizardStep the esdk settings wizard step
     */
    public void setContentEnums(final String baseDirectory, ESDKSettingsWizardStep esdkSettingsWizardStep) {
        String buildGradlepath = baseDirectory + "/build.gradle";
        if (esdkSettingsWizardStep.getEnumsList().getModel().getSize() > 0) {
            String[] enums = new String[esdkSettingsWizardStep.getEnumsList().getModel().getSize()];
            for (int i = 0; i < esdkSettingsWizardStep.getEnumsList().getModel().getSize(); i++) {
                enums[i] = "\"" + esdkSettingsWizardStep.getEnumsList().getModel().getElementAt(i) + "\"";
            }

            try {
                FileUtils.replaceStringInFile(buildGradlepath, "enums = \\[\\]",
                        "enums = " + Arrays.toString(enums));
            } catch (IOException e) {
                Notifications.errorNotification("Couldn´t prepare ESDK Enums!");
            }
        }
    }

    /**
     * Sets content enums.
     *
     * @param baseDirectory      the base directory
     * @param esdkSettingsDialog the esdk settings dialog
     * @param projectReader      the project reader
     */
    public void setContentEnums(final String baseDirectory, final ESDKSettingsDialog esdkSettingsDialog,
                                ProjectReader projectReader) {
        String buildGradlepath = baseDirectory + "/build.gradle";
        String replacement = "[]";
        if (esdkSettingsDialog.getEnumsList().getModel().getSize() > 0) {
            String[] enums = new String[esdkSettingsDialog.getEnumsList().getModel().getSize()];
            for (int i = 0; i < esdkSettingsDialog.getEnumsList().getModel().getSize(); i++) {
                enums[i] = "\"" + esdkSettingsDialog.getEnumsList().getModel().getElementAt(i) + "\"";
            }
            replacement = Arrays.toString(enums);
        }
        try {
            FileUtils.replaceStringInFile(buildGradlepath, "enums = \\[" + projectReader.contentEnums + "\\]",
                    "enums = " + replacement);
        } catch (IOException e) {
            Notifications.errorNotification("Couldn´t prepare ESDK Enums!");
        }
    }

    /**
     * Sets content named types.
     *
     * @param baseDirectory          the base directory
     * @param esdkSettingsWizardStep the esdk settings wizard step
     */
    public void setContentNamedTypes(final String baseDirectory, final ESDKSettingsWizardStep esdkSettingsWizardStep) {
        String buildGradlepath = baseDirectory + "/build.gradle";
        if (esdkSettingsWizardStep.getNamedTypesList().getModel().getSize() > 0) {
            String[] namedTypes = new String[esdkSettingsWizardStep.getNamedTypesList().getModel().getSize()];
            for (int i = 0; i < esdkSettingsWizardStep.getNamedTypesList().getModel().getSize(); i++) {
                namedTypes[i] = "\"" + esdkSettingsWizardStep.getNamedTypesList().getModel().getElementAt(i) + "\"";
            }

            try {
                FileUtils.replaceStringInFile(buildGradlepath, "namedTypes = \\[\\]",
                        "namedTypes = " + Arrays.toString(namedTypes));
            } catch (IOException e) {
                Notifications.errorNotification("Couldn´t prepare ESDK Named Types!");
            }
        }
    }

    /**
     * Sets content named types.
     *
     * @param baseDirectory      the base directory
     * @param esdkSettingsDialog the esdk settings dialog
     * @param projectReader      the project reader
     */
    public void setContentNamedTypes(final String baseDirectory, final ESDKSettingsDialog esdkSettingsDialog, ProjectReader projectReader) {
        String buildGradlepath = baseDirectory + "/build.gradle";
        String replacement = "[]";
        if (esdkSettingsDialog.getNamedTypesList().getModel().getSize() > 0) {
            String[] namedTypes = new String[esdkSettingsDialog.getNamedTypesList().getModel().getSize()];
            for (int i = 0; i < esdkSettingsDialog.getNamedTypesList().getModel().getSize(); i++) {
                namedTypes[i] = "\"" + esdkSettingsDialog.getNamedTypesList().getModel().getElementAt(i) + "\"";
            }
            replacement = Arrays.toString(namedTypes);
        }
        try {
            FileUtils.replaceStringInFile(buildGradlepath, "namedTypes = \\[" + projectReader.contentNamedTypes + "\\]",
                    "namedTypes = " + replacement);
        } catch (IOException e) {
            Notifications.errorNotification("Couldn´t prepare ESDK Named Types!");
        }

    }

    private void renamePackage(final String baseDirectory, final String packageName) {
        new File(baseDirectory + "/src/main/java/" + (packageName.replaceAll("\\.", "/"))).mkdirs();
    }

}
