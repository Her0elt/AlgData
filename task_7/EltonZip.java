import java.io.File;
import java.io.IOException;

public class EltonZip {
    public static void main(String[] args) {
        // String inputFile = "diverse.txt";
        // String ZivOutputFile = "ELTON";
        // String HuffdecompressOutputfile ="weDidIT";
        // String decompressOutputfile = "WEDITIT";
        // String [] fileName = args[2].split("."); 
        // System.out.println(args[2]);
        try {
            if(args[0].equals("c")){
                File HuffcompressOutputFile = File.createTempFile(args[2],null);
                Ziv.compress(args[1], HuffcompressOutputFile);
                Huffman.compress(HuffcompressOutputFile, args[2]);
            }
            if(args[0].equals("d")){
                File HuffdecompressOutputfile = File.createTempFile(args[2],null);
            Huffman.decompress(args[1], HuffdecompressOutputfile);
            Ziv.decompress(HuffdecompressOutputfile, args[2]);
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
        

    }  
}
