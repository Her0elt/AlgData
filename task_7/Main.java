import java.io.IOException;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) {
        String inputFile = "diverse.txt";
        String compressOutputFile = "EltonZipped";
        String decompressOutputfile ="weDidIT";
        try {
            Ziv.compress(inputFile, compressOutputFile);
            Huffman.compress(compressOutputFile, compressOutputFile);
            Huffman.decompress(compressOutputFile, decompressOutputfile);
            Ziv.decompress(decompressOutputfile, decompressOutputfile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

    }  
}
