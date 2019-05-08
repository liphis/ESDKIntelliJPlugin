package module.builder;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import data.FopJson;
import module.ESDKSettingsDialog;
import utils.FileUtils;
import utils.Notifications;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A helper class to Read current ESDK Project
 */
public class ProjectReader {

    /**
     * The Development version.
     */
    public String developmentVersion, /**
     * The Esdk version.
     */
    esdkVersion, /**
     * The App id.
     */
    appID, /**
     * The App name.
     */
    appName, /**
     * The Vendor id.
     */
    vendorID, /**
     * The Package name.
     */
    packageName, /**
     * The Versions.
     */
    versions, /**
     * The Languages.
     */
    languages, /**
     * The Abas host.
     */
    edpHost,
    /**
     * The Nexus host.
     */
    nexusHost, /**
     * The Nexus version.
     */
    nexusVersion, /**
     * The Contentinfosystems.
     */
    abasHomeDir,
    /**
     * The Abas client dir.
     */
    abasClientDir,
    /**
     * The Edp user.
     */
    edpUser,
    /**
     * The Edp port.
     */
    edpPort,
    /**
     * The Edp password.
     */
    edpPassword,
    /**
     * The Nexus port.
     */
    nexusPort,
    /**
     * The Nexus user.
     */
    nexusUser,
    /**
     * The Nexus name.
     */
    nexusName,
    /**
     * The Nexus password.
     */
    nexusPassword,
    /**
     * The Ssh user.
     */
    sshUser,
    /**
     * The Ssh host.
     */
    sshHost,
    /**
     * The Ssh port.
     */
    sshPort,
    /**
     * The Ssh password.
     */
    sshPassword,
    /**
     * The Ssh key.
     */
    sshKey,
    /**
     * The Contentinfosystems.
     */
    contentinfosystems, /**
     * The Content tables.
     */
    contentTables, /**
     * The Content screens.
     */
    contentScreens, /**
     * The Content enums.
     */
    contentEnums, /**
     * The Content named types.
     */
    contentNamedTypes;


    /**
     * Read basic.
     *
     * @param baseDirectory      the base directory
     * @param esdkSettingsDialog the esdk settings dialog
     */
    public void readBasic(final String baseDirectory, final ESDKSettingsDialog esdkSettingsDialog) {
        final String buildGradlepath = baseDirectory + "/build.gradle";
        try {
            String readerFile = FileUtils.readFile(buildGradlepath);
            //App Name
            appName = getFirstMatcherGroup(
                    "esdk\\s*\\S*\\{\\R*\\s*\\S*app\\s*\\S*\\{[\\\\R*\\s*\\S*\\\"*\\w*\\=\\[*\\]*\\,*\\:*\\.*]*name\\s*\\S*=\\s*\\S*\\\"([\\w*\\d*]*)\\\"", readerFile);
            esdkSettingsDialog.getAppName().setText(appName);
            //Vendor ID
            vendorID = getFirstMatcherGroup(
                    "esdk\\s*\\S*\\{\\R*\\s*\\S*app\\s*\\S*\\{[\\\\R*\\s*\\S*\\\"*\\w*\\=\\[*\\]*\\,*\\:*\\.*]*vendorId\\s*\\S*=\\s*\\S*\\\"([\\w*\\d*]*)\\\"",
                    readerFile);
            esdkSettingsDialog.getVendorID().setText(vendorID);
            //App ID
            appID = getFirstMatcherGroup(
                    "esdk\\s*\\S*\\{\\R*\\s*\\S*app\\s*\\S*\\{[\\\\R*\\s*\\S*\\\"*\\w*\\=\\[*\\]*\\,*\\:*\\.*]*appId\\s*\\S*=\\s*\\S*\\\"([\\w*\\d*]*)\\\"",
                    readerFile);
            esdkSettingsDialog.getAppID().setText(appID);
            //ESDK Version#
            esdkVersion = getFirstMatcherGroup(
                    "plugins\\s*\\S*\\{\\R*\\s*\\S*id\\s*\\S*\\\"[\\w*\\.]*\\\"\\s*\\S*version\\s*\\S*\\\"([\\d*\\.*]*)\\\"",
                    readerFile);
            esdkSettingsDialog.getEsdkVersion().setText(esdkVersion);
            developmentVersion = getFirstMatcherGroup(
                    "erp:[\\s*\\S*\\\\R*]*image:[\\s*\\S*\\w*\\.*\\/]*:([\\d*\\w]*)",
                    FileUtils.readFile(baseDirectory + "/docker-compose.yml"));
            esdkSettingsDialog.getDevelopmentVersion().setText(developmentVersion);
            packageName = getFirstMatcherGroup(
                    "group\\s*\\S*\\=?\\s*\\S*[\\'\\\"]([\\w*\\.*]*)[\\'\\\"]", readerFile);
            esdkSettingsDialog.getPackageName().setText(packageName);

        } catch (IOException e) {
            Notifications.errorNotification("Couldn´t read your current Basic Configuration!");
        }
    }

    /**
     * Read versions.
     *
     * @param baseDirectory      the base directory
     * @param esdkSettingsDialog the esdk settings dialog
     */
    public void readVersions(final String baseDirectory, final ESDKSettingsDialog esdkSettingsDialog) {
        final String buildGradlepath = baseDirectory + "/build.gradle";
        try {
            String readerFile = FileUtils.readFile(buildGradlepath);
            versions = getFirstMatcherGroup(
                    "esdk\\s*\\S*\\{\\R*\\s*\\S*app\\s*\\S*\\{[\\\\R*\\s*\\S*\\\"*\\w*\\=\\[*\\]*\\,*\\:*\\.*]*essentialsVersions\\s*\\S*\\=\\s*\\S*\\[(.*)\\]",
                    readerFile);
            final String versionString = versions.replaceAll("[\"\\s]", "").trim();
            if (!versionString.isEmpty()) {
                String[] versionsArray = versionString.split(",");

                for (String version : versionsArray) {
                    ((DefaultListModel) esdkSettingsDialog.getVersionList().getModel()).addElement(version);
                }
            }
        } catch (IOException e) {
            Notifications.errorNotification("Couldn´t read your current Versions Configuration!");
        }
    }

    /**
     * Readlanguages.
     *
     * @param baseDirectory      the base directory
     * @param esdkSettingsDialog the esdk settings dialog
     */
    public void readlanguages(final String baseDirectory, final ESDKSettingsDialog esdkSettingsDialog) {
        final String buildGradlepath = baseDirectory + "/build.gradle";
        try {
            languages = getFirstMatcherGroup(
                    "esdk\\s*\\S*\\{\\R*\\s*\\S*app\\s*\\S*\\{[\\\\R*\\s*\\S*\\\"*\\w*\\=\\[*\\]*\\,*\\:*\\.*]*languages\\s*\\S*=\\s*\\S*\\\"(\\w*)\\\"",
                    FileUtils.readFile(buildGradlepath));

            if (languages.contains("D")) esdkSettingsDialog.getGerman().setSelected(true);
            if (languages.contains("A")) esdkSettingsDialog.getAmericanEnglish().setSelected(true);
            if (languages.contains("E")) esdkSettingsDialog.getEnglish().setSelected(true);
            if (languages.contains("F")) esdkSettingsDialog.getFrench().setSelected(true);
            if (languages.contains("S")) esdkSettingsDialog.getSpanish().setSelected(true);
            if (languages.contains("I")) esdkSettingsDialog.getItalian().setSelected(true);
            if (languages.contains("P")) esdkSettingsDialog.getPolish().setSelected(true);
            if (languages.contains("R")) esdkSettingsDialog.getRussian().setSelected(true);
            if (languages.contains("U")) esdkSettingsDialog.getHungarian().setSelected(true);
            if (languages.contains("C")) esdkSettingsDialog.getCzech().setSelected(true);
            if (languages.contains("T")) esdkSettingsDialog.getTurkish().setSelected(true);
            if (languages.contains("O")) esdkSettingsDialog.getRomanian().setSelected(true);
            if (languages.contains("B")) esdkSettingsDialog.getIndonesian().setSelected(true);
            if (languages.contains("N")) esdkSettingsDialog.getDutch().setSelected(true);
            if (languages.contains("M")) esdkSettingsDialog.getMacedonian().setSelected(true);
            if (languages.contains("X")) esdkSettingsDialog.getBulgarian().setSelected(true);
            if (languages.contains("Y")) esdkSettingsDialog.getSerbian().setSelected(true);
            if (languages.contains("L")) esdkSettingsDialog.getSlovenian().setSelected(true);
            if (languages.contains("W")) esdkSettingsDialog.getSlovakian().setSelected(true);
            if (languages.contains("G")) esdkSettingsDialog.getPortugese().setSelected(true);
            if (languages.contains("Q")) esdkSettingsDialog.getChineseSimplified().setSelected(true);
            if (languages.contains("H")) esdkSettingsDialog.getChineseTraditional().setSelected(true);
            if (languages.contains("Z")) esdkSettingsDialog.getGreek().setSelected(true);
            if (languages.contains("K")) esdkSettingsDialog.getThai().setSelected(true);
            if (languages.contains("V")) esdkSettingsDialog.getArabic().setSelected(true);
            if (languages.contains("J")) esdkSettingsDialog.getPersian().setSelected(true);
            if (languages.contains("9")) esdkSettingsDialog.getVietnamese().setSelected(true);
            if (languages.contains("8")) esdkSettingsDialog.getUrdu().setSelected(true);
            if (languages.contains("7")) esdkSettingsDialog.getJapanese().setSelected(true);

        } catch (IOException e) {
            Notifications.errorNotification("Couldn´t read your current Languages Configuration!");
        }
    }

    /**
     * Read connection settings.
     *
     * @param baseDirectory      the base directory
     * @param esdkSettingsDialog the esdk settings dialog
     */
    public void readConnectionSettings(final String baseDirectory, final ESDKSettingsDialog esdkSettingsDialog) {
        String propertiesTemplateGradlePath = baseDirectory + "/gradle.properties";
        try {
            String readerFile = FileUtils.readFile(propertiesTemplateGradlePath);
            abasHomeDir = getFirstMatcherGroup("ABAS_HOMEDIR=(.*)", readerFile);
            abasClientDir = getFirstMatcherGroup("ABAS_CLIENTDIR=(.*)", readerFile);

            edpHost = getFirstMatcherGroup("EDP_HOST=(.*)", readerFile);
            edpPort = getFirstMatcherGroup("EDP_PORT=(\\d*)", readerFile);
            edpUser = getFirstMatcherGroup("EDP_USER=(.*)", readerFile);
            edpPassword = getFirstMatcherGroup("EDP_PASSWORD=(.*)", readerFile);

            nexusHost = getFirstMatcherGroup("NEXUS_HOST=(.*)", readerFile);
            nexusPort = getFirstMatcherGroup("NEXUS_PORT=(\\d*)", readerFile);
            nexusUser = getFirstMatcherGroup("NEXUS_USER_NAME=(.*)", readerFile);
            nexusName = getFirstMatcherGroup("NEXUS_NAME=(.*)", readerFile);
            nexusPassword = getFirstMatcherGroup("NEXUS_PASSWORD=(.*)", readerFile);
            nexusVersion = getFirstMatcherGroup("NEXUS_VERSION=(\\d*)", readerFile);

            sshHost = getFirstMatcherGroup("SSH_HOST=(.*)", readerFile);
            sshUser = getFirstMatcherGroup("SSH_USER=(.*)", readerFile);
            sshPort = getFirstMatcherGroup("SSH_PORT=(\\d*)", readerFile);
            sshPassword = getFirstMatcherGroup("SSH_PASSWORD=(.*)", readerFile);
            sshKey = getFirstMatcherGroup("SSH_KEY=(.*)", readerFile);

            esdkSettingsDialog.getAbasHomeDir().setText(abasHomeDir);
            esdkSettingsDialog.getAbasClientDir().setText(abasClientDir);

            esdkSettingsDialog.getEdpHost().setText(edpHost);
            esdkSettingsDialog.getEdpPort().setText(edpPort);
            esdkSettingsDialog.getEdpUser().setText(edpUser);
            esdkSettingsDialog.getEdpPassword().setText(edpPassword);

            esdkSettingsDialog.getNexusHost().setText(nexusHost);
            esdkSettingsDialog.getNexusPort().setText(nexusPort);
            esdkSettingsDialog.getNexusUser().setText(nexusUser);
            esdkSettingsDialog.getNexusName().setText(nexusName);
            esdkSettingsDialog.getNexusPassword().setText(nexusPassword);
            esdkSettingsDialog.getNexusVersion().setText(nexusVersion);
            esdkSettingsDialog.getSshHost().setText(sshHost);
            esdkSettingsDialog.getSshUser().setText(sshUser);
            esdkSettingsDialog.getSshPort().setText(sshPort);
            esdkSettingsDialog.getSshPassword().setText(sshPassword);
            esdkSettingsDialog.getSshKeyPath().setText(Paths.get(sshKey).toString());

        } catch (IOException e) {
            Notifications.errorNotification("Couldn´t read your current Connection Settings Configuration!");
        }
    }

    /**
     * Read content infosystems.
     *
     * @param baseDirectory      the base directory
     * @param esdkSettingsDialog the esdk settings dialog
     */
    public void readContentInfosystems(final String baseDirectory, final ESDKSettingsDialog esdkSettingsDialog) {
        try {
            final String buildGradlepath = baseDirectory + "/build.gradle";
            contentinfosystems = getFirstMatcherGroup("esdk\\s*\\S*\\{\\R*\\s*\\S*app\\s*\\S*\\{[\\\\R*\\s*\\S*\\\"*\\w*\\=\\[*\\]*\\,*\\:*\\.*]*infosystems\\s*\\S*\\=\\s*\\S*\\[(.*)\\]",
                    FileUtils.readFile(buildGradlepath));
            final String infosystemsString = contentinfosystems
                    .replaceAll("[\"\\s]", "").trim();
            if (!infosystemsString.isEmpty()) {
                String[] infosystems = infosystemsString.split(",");
                for (String infosystem : infosystems) {
                    ((DefaultListModel) esdkSettingsDialog.getInfosystemList().getModel()).addElement(infosystem);
                }
            }
        } catch (IOException e) {
            Notifications.errorNotification("Couldn´t read your current Content Infosystems Configuration!");
        }
    }

    /**
     * Read content tables.
     *
     * @param baseDirectory      the base directory
     * @param esdkSettingsDialog the esdk settings dialog
     */
    public void readContentTables(final String baseDirectory, final ESDKSettingsDialog esdkSettingsDialog) {
        try {
            final String buildGradlepath = baseDirectory + "/build.gradle";
            contentTables = getFirstMatcherGroup("esdk\\s*\\S*\\{\\R*\\s*\\S*app\\s*\\S*\\{[\\\\R*\\s*\\S*\\\"*\\w*\\=\\[*\\]*\\,*\\:*\\.*]*tables\\s*\\S*\\=\\s*\\S*\\[(.*)\\]",
                    FileUtils.readFile(buildGradlepath));
            final String tablesString = contentTables
                    .replaceAll("[\"\\s]", "").trim();
            if (!tablesString.isEmpty()) {
                String[] tables = tablesString.split(",");
                for (String table : tables) {
                    ((DefaultListModel) esdkSettingsDialog.getTableList().getModel()).addElement(table);
                }
            }
        } catch (IOException e) {
            Notifications.errorNotification("Couldn´t read your current Content Tables Configuration!");
        }
    }

    /**
     * Read content screens.
     *
     * @param baseDirectory      the base directory
     * @param esdkSettingsDialog the esdk settings dialog
     */
    public void readContentScreens(final String baseDirectory, final ESDKSettingsDialog esdkSettingsDialog) {
        try {
            final String buildGradlepath = baseDirectory + "/build.gradle";
            final String screensString = getFirstMatcherGroup("esdk\\s*\\{\\R*\\s*app\\s*\\{[\\\\R*\\s*\\\"*\\w*\\=\\[*\\]*\\,*\\:*\\.]*screens\\s*\\=\\s*\\[(.*)\\]",
                    FileUtils.readFile(buildGradlepath)).trim();
            contentScreens = screensString;
            final Pattern pattern = Pattern.compile("\\w\"(,)\"");
            final Matcher matcher = pattern.matcher(screensString);
            final StringBuilder bufferString = new StringBuilder(screensString);
            while (matcher.find()) {
                bufferString.replace(matcher.toMatchResult().start() + 2, matcher.toMatchResult().end() - 1, "#");
            }
            if (!bufferString.toString().isEmpty()) {
                final String[] screens = bufferString.toString().split(",");
                for (String screen : screens) {
                    ((DefaultListModel) esdkSettingsDialog.getScreensList().getModel())
                            .addElement(screen.replaceAll("#", ","));
                }
            }
        } catch (IOException e) {
            Notifications.errorNotification("Couldn´t read your current Content Screens Configuration!");
        }
    }

    /**
     * Read content enums.
     *
     * @param baseDirectory      the base directory
     * @param esdkSettingsDialog the esdk settings dialog
     */
    public void readContentEnums(final String baseDirectory, final ESDKSettingsDialog esdkSettingsDialog) {
        try {
            final String buildGradlepath = baseDirectory + "/build.gradle";
            contentEnums = getFirstMatcherGroup("esdk\\s*\\S*\\{\\R*\\s*\\S*app\\s*\\S*\\{[\\\\R*\\s*\\S*\\\"*\\w*\\=\\[*\\]*\\,*\\:*\\.*]*enums\\s*\\S*\\=\\s*\\S*\\[(.*)\\]",
                    FileUtils.readFile(buildGradlepath));
            final String enumsString = contentEnums.replaceAll("[\"\\s]", "").trim();
            if (!enumsString.isEmpty()) {
                final String[] enums = enumsString.split(",");
                for (String enumerator : enums) {
                    ((DefaultListModel) esdkSettingsDialog.getEnumsList().getModel()).addElement(enumerator);
                }
            }
        } catch (
                IOException e) {
            Notifications.errorNotification("Couldn´t read your current Content Enums Configuration!");
        }

    }

    /**
     * -
     * Read content named types.
     *
     * @param baseDirectory      the base directory
     * @param esdkSettingsDialog the esdk settings dialog
     */
    public void readContentNamedTypes(final String baseDirectory, final ESDKSettingsDialog esdkSettingsDialog) {
        try {
            final String buildGradlepath = baseDirectory + "/build.gradle";
            contentNamedTypes = getFirstMatcherGroup("esdk\\s*\\S*\\{\\R*\\s*\\S*app\\s*\\S*\\{[\\\\R*\\s*\\S*\\\"*\\w*\\=\\[*\\]*\\,*\\:*\\.*]*namedTypes\\s*\\S*\\=\\s*\\S*\\[(.*)\\]",
                    FileUtils.readFile(buildGradlepath));
            final String namedTypesString = contentNamedTypes
                    .replaceAll("[\"\\s]", "").trim();
            if (!namedTypesString.isEmpty()) {
                String[] namedTypes = namedTypesString.split(",");
                for (String namedType : namedTypes) {
                    ((DefaultListModel) esdkSettingsDialog.getNamedTypesList().getModel()).addElement(namedType);
                }
            }
        } catch (
                IOException e) {
            Notifications.errorNotification("Couldn´t read your current Content Named Types Configuration!");
        }

    }

    /**
     * Read content events.
     *
     * @param baseDirectory      the base directory
     * @param esdkSettingsDialog the esdk settings dialog
     */
    public void readContentEvents(final String baseDirectory, final ESDKSettingsDialog esdkSettingsDialog) {
        final String fopJson = baseDirectory + "/src/main/resources/fop.json";
        try {
            final FileInputStream fileInputStream = new FileInputStream(fopJson);
            final InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            final JsonArray jsonArray = new Gson().fromJson(inputStreamReader, JsonArray.class);
            inputStreamReader.close();
            fileInputStream.close();

            for (int i = 0; i < jsonArray.size(); i++) {
                final JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                ESDKSettingsDialog.getFopJsonList().add(new FopJson(
                        jsonObject.get("databaseName").getAsString(),
                        jsonObject.get("groupName").getAsString(),
                        jsonObject.get("editorMode").getAsString(),
                        jsonObject.get("event").getAsString(),
                        jsonObject.get("key").getAsString(),
                        jsonObject.get("field").getAsString(),
                        jsonObject.get("headOrTable").getAsString(),
                        jsonObject.get("isContinue").getAsString(),
                        jsonObject.get("handler").getAsString())
                );
                ((DefaultListModel) esdkSettingsDialog.getEventList().getModel())
                        .addElement(jsonObject.get("field") + " | " + jsonObject.get("handler"));
            }
        } catch (NullPointerException | IOException e) {
            Notifications.errorNotification("Couldn´t read your current Content Events Configuration!");
        }
    }

    /**
     * Gets first matcher group.
     *
     * @param regex  the regex
     * @param string the string
     * @return the first matcher group
     */
    public static String getFirstMatcherGroup(final String regex, final String string) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        if (matcher.find())
            return matcher.group(1);
        else return "";
    }

}
