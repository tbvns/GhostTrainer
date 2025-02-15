package xyz.tbvns.ghostTrainer.Ai;

import lombok.SneakyThrows;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.util.Zip4jUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;

public class AIManager {
    @SneakyThrows
    public static String extract() {
        File file = File.createTempFile("GhostTrainerAI", "zip");
        file.deleteOnExit();
        file.mkdirs();

        FileUtils.copyToFile(AIManager.class.getResourceAsStream("/ScreenGuesser.zip"), file);

        ZipFile zipFile = new ZipFile(file);
        String extractedPath = file.getParent() + "/" + file.getName() + "-extracted";
        zipFile.extractAll(extractedPath);
        new File(extractedPath).deleteOnExit();
        return extractedPath + "/model.savedmodel";
    }
}
