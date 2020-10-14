 import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

class Compress {
    byte[] buffer = new byte[1024];
    byte[] bytes;
    public void compress(String path) {
        try {
            File file = new File(path);
            bytes = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        int matchPos = -1;
        int countMatches = 0;
        int bufferPos = 0;
        String output = "";
        String cache = "";
        for (int i = 0; i < bytes.length; i++) {
            if(bufferPos == 1022) bufferPos = 0;
            int match = checkInBuffer(bytes[i], bufferPos);
            if(match > -1){
                cache += (char) bytes[i];
                matchPos = (matchPos > -1)? matchPos : match; 
                if(matchPos != -1) countMatches++; else countMatches = 0;
                System.out.println("pos: "+i+ " matches: "+countMatches+ " byte: "+(char)bytes[i]+ "  "+bytes[i]+" matchpos: " +match);
            }else{
                output += cache + (char)bytes[i];
                countMatches = 0;
                matchPos = -1;
                cache = "";
                buffer[bufferPos] = bytes[i]; 
            }
            if(countMatches == 8){
                output += "-"+matchPos;
                cache = "";
                matchPos = -1;
                countMatches = 0;
            }
            bufferPos++;
            
        }
        System.out.println(output);

    }
    private int checkInBuffer(byte b, int pos){
        for(int i = pos; i>=0; i--){
            if(buffer[i] == b) return i;
        }
        return -1;
    }
    public static void main(String[] args) {
        Compress c = new Compress();
        c.compress("test");
    }

}