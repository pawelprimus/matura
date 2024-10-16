package pl.prim;

import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReader {


    final static String FILE_PATH = System.getProperty("user.dir") + "\\src\\main\\java\\pl\\prim\\y2023\\Exercise_";

    public static String readFileAsString(String number, String fileName) throws Exception {
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(FILE_PATH + number + "\\" + fileName)));
        return data;
    }
}

