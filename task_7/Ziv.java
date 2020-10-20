import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Ziv {
    static byte[] buffer;
    static int position = 0;
    static byte[] bytes;
    static int endPos = 0;

    public static void compress(String file, File outputFile)throws IOException {
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        DataOutputStream out = new DataOutputStream(new FileOutputStream(outputFile));
        bytes = in.readAllBytes();
        in.close();
        buffer = new byte[1024*32];
        position = 0;
        endPos = 0;
        int last = 0;
        for(int i = 0; i<bytes.length; i++){
            int posBuff = checkInBuffer(bytes[i], endPos);
            if(posBuff == -1){
                addToBuffer(bytes[i]);
                position++;
                
            }
            else{
                int max = buildWord(posBuff, i);
                int maxIndex = posBuff;
                while(posBuff != -1){
                    posBuff = checkInBuffer(bytes[i], posBuff-1);
                    if(posBuff == -1)break;
                    if(buildWord(posBuff, i) > max){
                        max = buildWord(posBuff, i);
                        maxIndex = posBuff;
                    }
                }
                if(max > 6){
                    out.writeShort((short)(position-last));
                    byte[] temp = new byte[position-last];
                    int count = 0;
                    for(int j = last; j<position;j++) temp[count++] = (byte)bytes[j];
                    out.write(temp);
                    out.writeShort((short)(-(i-maxIndex)));
                    out.writeShort((short)max);
                    
                    for(int j = 0; j<max; j++){
                        addToBuffer(bytes[i++]);
                        position++;
                        
                    }
                    last = i;
                    i--;
                }
                else { addToBuffer(bytes[i]);
                    position++;
                    }
            }
        }
        out.writeShort((short)(position-last));
        for(int i = last; i<position; i++) out.writeByte(bytes[i]);
        out.close();

    }
    public static void  addToBuffer(byte b){
        if(position>= buffer.length){
            endPos = 0;
        }
        buffer[endPos] = b;
        endPos = (position+1)%buffer.length;
    }

    public static byte get(int index){
        if(index >= buffer.length){
            int i = index%buffer.length;
            return buffer[i];
        }
        else if(index < 0){
            int i = buffer.length+(index%buffer.length);
            if(i == buffer.length) i = 0;
            return buffer[i];
        }
        return buffer[index];
    }
    public static void decompress(File path, String outputFile) throws IOException{
        DataOutputStream out = new DataOutputStream(new FileOutputStream(outputFile));
        DataInputStream in = new DataInputStream(new FileInputStream(path));
        position = 0;
        endPos = 0;
        buffer = new byte[1024*32];
        short start = in.readShort();
        bytes = new byte[start];
        in.readFully(bytes);
        out.write(bytes);
        for(int j = 0; j<start; j++){
            addToBuffer(bytes[j]);
            position++;
            
        }
        while(in.available() > 0){
            short back = in.readShort();
            short copyAmount = in.readShort();
            int end = endPos;
            bytes = new byte[copyAmount];
            int i = 0;
            for(int tempIndex = end+back; tempIndex<(end+back)+copyAmount; tempIndex++){
                byte index = get(tempIndex);
                bytes[i++] = index;
                addToBuffer(index);
                position++;
            }
            out.write(bytes);
            start = in.readShort();
            bytes = new byte[start];
            in.readFully(bytes);
            for(int j = 0; j<start; j++){
                addToBuffer(bytes[j]);
                position++;
            }
            out.write(bytes);
        }
        
        in.close();
        out.close();
    }
    private static int checkInBuffer(byte b, int pos){
        for(int i = pos; i>=0; i--){
            if(buffer[i] == b) return i;
        }
        return -1;
    }

    private static int buildWord(int posBuff, int posByte){
        byte byte1 = bytes[posByte];
        byte buff1 = get(posBuff);
        int count = 0;
        while(buff1 == byte1 && posByte != bytes.length-1){
            count++;
            byte1 = bytes[++posByte];
            buff1 = get(++posBuff);
        }
        return count;
    
    }
    public static void main(String[] args) {
        // try {
        //     Ziv.compress("enwik8", "man.hoe");
        //     Ziv.decompress("man.hoe","man");
        // } catch (IOException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }
    }
}
