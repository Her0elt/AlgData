import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String inputFile = "diverse.txt";
        String HuffcompressOutputFile = "EltonZipped";
        String ZivOutputFile = "ELTON";
        String HuffdecompressOutputfile ="weDidIT";
        String decompressOutputfile = "WEDITIT";
        try {
            Ziv.compress(inputFile, HuffcompressOutputFile);
            Huffman.compress(HuffcompressOutputFile, ZivOutputFile);
            Huffman.decompress(ZivOutputFile, HuffdecompressOutputfile);
            Ziv.decompress(HuffdecompressOutputfile, decompressOutputfile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

    }  
}
