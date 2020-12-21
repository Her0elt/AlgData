
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Huffman {
    static void compress(File file, String outputFile) throws IOException {
        int count[] = new int[256];
        DataInputStream f = new DataInputStream(new FileInputStream(file));
        int amount = f.available();
        for (int i = 0; i < amount; ++i) {
            int c = f.read();
            count[c]++;
        }
        f.close();
        PriorityQueue<Node> pq = new PriorityQueue<>(256, (a, b) -> a.count - b.count);
        pq.addAll(makeNodeList(count));
        Node tree = Node.makeHuffmanTree(pq);
        tree.printCode(tree, "");
        FileInputStream in = new FileInputStream(file);
        DataOutputStream out = new DataOutputStream(new FileOutputStream(outputFile));
        for (int t : count) {
            out.writeInt(t);
        }
        int input;
        long writeByte = 0L;
        int i = 0;
        int j = 0;
        ArrayList<Byte> bytes = new ArrayList<>();
        for (int k = 0; k < amount; ++k) {
            input =Math.abs(in.read());
            j = 0;
            String bitString = tree.bitstring[input];
            while (j < bitString.length()) {
                if (bitString.charAt(j) == '0')writeByte = (writeByte<<1);
                else writeByte = ((writeByte<<1)|1);
                ++j;
                ++i;
                 if (i == 8) {
                    bytes.add((byte)writeByte);
                    i = 0;
                    writeByte = 0L;
                }
            }
        }
        int lastByte = i;
        while (i < 8 && i != 0) {
            writeByte = (writeByte<<1);
            ++i;
        }
        bytes.add((byte)writeByte);
        out.writeInt(lastByte);
        for (Byte s : bytes) {
            out.write(s);
        }
        in.close();
        out.close();
    }

    private static ArrayList<Node> makeNodeList(int[] count) {
        ArrayList<Node> nodeList = new ArrayList<>();
        for (int i = 0; i < count.length; i++) {
            if(count[i] != 0){
                nodeList.add(new Node((char) i, count[i], null, null));
            }
        }
        return nodeList;
    }
    public static long convertByte(byte b, int length){
        long temp = 0;
        for(long i = 1<<length-1; i != 0; i >>= 1){
            if((b & i) == 0){
                temp = (temp << 1);
            }
            else temp = ((temp << 1) | 1);
        }
        return temp;
    }

    static void decompress(String file, File outputFile) throws IOException {
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int [] count = new int [256];
        for (int i = 0; i < count.length; i++) {
            int freq = in.readInt();
            count[i] = freq;
        }
     
        int lastByte = in.readInt();
        PriorityQueue<Node> pq = new PriorityQueue<>(256, (a, b) -> a.count - b.count);
        pq.addAll(makeNodeList(count));
        Node tree = Node.makeHuffmanTree(pq);
        DataOutputStream out = new DataOutputStream(new FileOutputStream(outputFile));
        byte ch;
        byte [] bytes = in.readAllBytes();
        in.close();
        int length = bytes.length;
        Bitstring h = new Bitstring(0, 0);
        if(lastByte>0) length--;
        for (int i = 0; i <length; i++) {
            ch = bytes[i];
            Bitstring b = new Bitstring(8, ch);
            h = Bitstring.concat(h,b);
            h = writeChar(tree, h, out);    
        }
        if(lastByte>0){
            Bitstring b = new Bitstring(lastByte, bytes[length]>>(8-lastByte));
            h = Bitstring.concat(h, b);
            writeChar(tree, h, out);
        }
        in.close();
        out.flush();
        out.close();
    }

    private static Bitstring writeChar(Node tree ,Bitstring h, DataOutputStream os) throws IOException {
            Node tempTree = tree;
            int c=0;
            for (long j = 1<< h.lengde-1; j!=0; j>>=1) {
                c++;
                if((h.biter & j) == 0)tempTree = tempTree.left;
                else tempTree = tempTree.right;
                if(tempTree.left == null){
                    long cha = tempTree.letter;
                    os.write((byte)cha);
                    long temp =(long) ~(0 << (h.lengde-c));
                    h.biter = (h.biter & temp);
                    h.lengde = h.lengde-c;
                    c = 0;
                    tempTree = tree;
                }
            }
            return h;
        }
    public static void main(String[] args) {
        // try {
        //     compress("diverse.txt", "file.hoe");
        //     decompress("file.hoe", "newfile");
        // } catch (IOException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }
    }
}
