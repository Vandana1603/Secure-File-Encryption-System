package file;

import java.io.File;
import java.nio.file.Files;

public class FileManager {

    public static byte[] readFile(File file) throws Exception {
        return Files.readAllBytes(file.toPath());
    }

    public static void writeFile(File file, byte[] data) throws Exception {
        Files.write(file.toPath(), data);
    }
}