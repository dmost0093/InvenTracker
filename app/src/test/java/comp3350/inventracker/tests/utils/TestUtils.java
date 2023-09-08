package comp3350.inventracker.tests.utils;

import static comp3350.inventracker.app.AppPersistence.*;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;

public class TestUtils {
    private static final File DB_SRC = new File("src/main/assets/db/SC.script");

    public static File copyDB() throws IOException {
        final File target = File.createTempFile("temp-db", ".script");
        Files.copy(DB_SRC, target);
        setDBPathName(target.getAbsolutePath().replace(".script", ""));
        return target;
    }
}
