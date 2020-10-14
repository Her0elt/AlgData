import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) {
        try {
            // File file = new File("test");
            // byte [] bytes = Files.readAllBytes(file.toPath());
            // ZivD z = new ZivD(bytes);
            // z.compress();
            // String file = args[0];
            // Ziv.compress(file);
            Hoffman.compress("test");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }  
}
