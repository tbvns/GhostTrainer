package xyz.tbvns.ghostTrainer.lwjgl3;

import xyz.tbvns.ghostTrainer.Main;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Resources {
    static File file = new File(System.getProperty("user.home") + "/GhostTrainer");

    public static void load() throws IOException {
        if (!file.exists()) file.mkdir();
        Files.copy(Launcher.class.getResourceAsStream("/JInput/jinput-dx8_64.dll"), Path.of(file.getAbsolutePath() + "/jinput-dx8_64.dll"), StandardCopyOption.REPLACE_EXISTING);
        Files.copy(Launcher.class.getResourceAsStream("/JInput/jinput-raw_64.dll"), Path.of(file.getAbsolutePath() + "/jinput-raw_64.dll"), StandardCopyOption.REPLACE_EXISTING);
        Files.copy(Launcher.class.getResourceAsStream("/JInput/jinput-wintab.dll"), Path.of(file.getAbsolutePath() + "/jinput-wintab.dll"), StandardCopyOption.REPLACE_EXISTING);
        Files.copy(Launcher.class.getResourceAsStream("/JInput/libjinput-linux64.so"), Path.of(file.getAbsolutePath() + "/libjinput-linux64.so"), StandardCopyOption.REPLACE_EXISTING);
        Files.copy(Launcher.class.getResourceAsStream("/JInput/libjinput-osx.jnilib"), Path.of(file.getAbsolutePath() + "/libjinput-osx.jnilib"), StandardCopyOption.REPLACE_EXISTING);
    }

    public static void start() throws IOException, URISyntaxException {
        System.out.println("java \"-Djava.library.path=" + Resources.file.getAbsolutePath() + "\" -jar " + Main.class.getProtectionDomain().getCodeSource().getLocation().getPath() + " true");
        File file = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        //make this use something that is not deprecated
        Process process = Runtime.getRuntime().exec("java \"-Djava.library.path=" + Resources.file.getAbsolutePath().replaceAll("\\.", "\\") + "\" -jar " + file.getPath()  + " true");
        while (process.isAlive()) {
            System.out.print(new String(process.getInputStream().readNBytes(process.getInputStream().available())));
            System.err.print(new String(process.getErrorStream().readNBytes(process.getErrorStream().available())));
        }
    }
}
