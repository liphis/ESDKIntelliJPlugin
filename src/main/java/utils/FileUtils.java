package utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * This class contains methods for File manipulation
 */
public class FileUtils {

    private static final Gson GSON = new Gson();

    /**
     * Unzip.
     *
     * @param zipFilePath   the zip file path
     * @param unzipLocation the unzip location
     * @throws IOException the io exception
     */
    public static void unzip(@NotNull final String zipFilePath, @NotNull final String unzipLocation) throws IOException {

        if (!(Files.exists(Paths.get(unzipLocation)))) {
            Files.createDirectories(Paths.get(unzipLocation));
        }
        try (final ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry = zipInputStream.getNextEntry();
            while (entry != null) {
                final Path filePath = Paths.get(unzipLocation, entry.getName());
                if (!entry.isDirectory()) {
                    unzipFiles(zipInputStream, filePath);
                } else {
                    Files.createDirectories(filePath);
                }

                zipInputStream.closeEntry();
                entry = zipInputStream.getNextEntry();
            }
        }
    }

    private static void unzipFiles(@NotNull final ZipInputStream zipInputStream, @NotNull final Path unzipFilePath) throws IOException {
        try (final BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(unzipFilePath.toAbsolutePath().toString()))) {
            final byte[] bytesIn = new byte[1024];
            int read;
            while ((read = zipInputStream.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
        }
    }

    /**
     * Zip directory.
     *
     * @param sourceDirectoryPath the source directory path
     * @param zipPath             the zip path
     * @throws IOException the io exception
     */
    public static void zipDirectory(@NotNull final String sourceDirectoryPath, @NotNull final String zipPath) throws IOException {
        final Path zipFilePath = Files.createFile(Paths.get(zipPath));

        try (final ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(zipFilePath))) {
            final Path sourceDirPath = Paths.get(sourceDirectoryPath);

            Files.walk(sourceDirPath).filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        final ZipEntry zipEntry = new ZipEntry(sourceDirPath.relativize(path).toString());
                        try {
                            zipOutputStream.putNextEntry(zipEntry);
                            zipOutputStream.write(Files.readAllBytes(path));
                            zipOutputStream.closeEntry();
                        } catch (@NotNull final Exception e) {
                            Notifications.errorNotification(e.getMessage());
                        }
                    });
        }
    }

    /**
     * Delete directory stream.
     *
     * @param path the path
     * @throws IOException the io exception
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void deleteDirectoryStream(@NotNull final String path) throws IOException {
        Files.walk(Paths.get(path))
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

    /**
     * Copy file using stream.
     *
     * @param source the source
     * @param dest   the dest
     * @throws IOException the io exception
     */
    public static void copyFileUsingStream(final InputStream source, @NotNull final File dest) throws IOException {
        try (final InputStream is = source; final OutputStream os = new FileOutputStream(dest)) {
            final byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        }
    }

    /**
     * Write fop txt.
     *
     * @param rootPath  the root path
     * @param jsonArray the json array
     * @throws IOException the io exception
     */
    public static void writeFopTxt(final String rootPath, final JsonArray jsonArray) throws IOException {
        final FileOutputStream fileOutputStream = new FileOutputStream(rootPath + "/src/main/resources/fop.json");
        final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
        outputStreamWriter.write(GSON.toJson(jsonArray));
        outputStreamWriter.close();
    }

    /**
     * Check file exists boolean.
     *
     * @param filePath the file path
     * @return the boolean
     */
    public static boolean checkFileExists(@NotNull final String filePath) {
        return new File(filePath).exists();
    }

    /**
     * Delete file.
     *
     * @param filePath the file path
     */
    public static void deleteFile(@NotNull final String filePath) {
        new File(filePath).delete();
        new File(filePath.replaceAll("/", "\\\\")).delete();
    }

    /**
     * Replace string in file.
     *
     * @param pathToFile  the path to file
     * @param regex       the regex
     * @param replacement the replacement
     * @throws IOException the io exception
     */
    public static void replaceStringInFile(@NotNull final String pathToFile, @NotNull final String regex, @NotNull final String replacement)
            throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(pathToFile)), StandardCharsets.UTF_8);
        content = content.replaceFirst(regex, replacement);
        final FileOutputStream fileOutputStream = new FileOutputStream(pathToFile);
        final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
        outputStreamWriter.write(content);
        outputStreamWriter.close();
    }

    /**
     * Read file string.
     *
     * @param pathToFile the path to file
     * @return the string
     * @throws IOException the io exception
     */
    @NotNull
    public static String readFile(@NotNull final String pathToFile) throws IOException {
        return new String(Files.readAllBytes(Paths.get(pathToFile)), StandardCharsets.UTF_8);
    }

    /**
     * Delete line.
     *
     * @param filepath the filepath
     * @param match    the match
     * @throws IOException the io exception
     */
    public static void deleteLine(@NotNull final String filepath, @NotNull final String match) throws IOException {

        final FileInputStream fileInputStream = new FileInputStream(filepath);
        final InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
        final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line;
        final StringBuilder input = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            //System.out.println(line);
            if (line.contains(match)) {
                line = "";
            }
            input.append(line).append('\n');
        }

        final FileOutputStream fileOutputStream = new FileOutputStream(filepath);
        final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
        outputStreamWriter.write(input.toString());
        bufferedReader.close();
        outputStreamWriter.close();
    }
}
