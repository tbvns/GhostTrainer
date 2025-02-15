package xyz.tbvns.ghostTrainer.Config;

import javassist.NotFoundException;
import lombok.SneakyThrows;
import xyz.tbvns.ghostTrainer.Inputs.NativeMouseReader;
import xyz.tbvns.ghostTrainer.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.util.List;

public class ValorantConfigReader {
    public static final String folderCFG = new File(System.getenv("appdata")).getParent() + "\\Local\\VALORANT\\Saved\\Config";
    public static final String computerCFG = folderCFG + "\\Windows\\RiotLocalMachine.ini";
    @SneakyThrows
    public static String getLastKnownUser() {
        BufferedReader reader = new BufferedReader(new FileReader(computerCFG));

        List<String> lines = reader.lines().toList();
        for (String line : lines) {
            if (line.startsWith("LastKnownUser")) {
                return line.split("=")[1];
            }
        }

        throw new NotFoundException("LastKnownUser does not exits !");
    }

    @SneakyThrows
    public static String getUserCFG() {
        for (String folder : new File(folderCFG).list()) {
            if (folder.contains(getLastKnownUser())) {
                return folderCFG + "\\" + folder + "\\Windows\\RiotUserSettings.ini";
            }
        }

        throw new NotFoundException("LastUser config folder doesn't exist");
    }

    @SneakyThrows
    public static float getSensitivity() {
        File cfg = new File(getUserCFG());
        BufferedReader reader = new BufferedReader(new FileReader(cfg));
        List<String> lines = reader.lines().toList();
        for (String line : lines) {
            if (line.startsWith("EAresFloatSettingName::MouseSensitivity")) {
                reader.close();
                return Float.parseFloat(line.split("=")[1].strip());
            }
        }
        reader.close();
        return 1;
    }

    public static boolean running = false;
    public static Runnable checker = () -> {
        while (running) {
            NativeMouseReader.sensitivity = getSensitivity();
            Utils.sleep(1000);
        }
    };

    public static void start() {
        running = true;
        new Thread(checker, "ValorantCFGChecker"){{
            setDaemon(true);
            start();
        }};
    }

    public static void stop() {
        running = false;
    }
}
