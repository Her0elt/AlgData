import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Compress {
    static void compress(String file) throws IOException {
        int count[] = new int[256];
        FileInputStream f = new FileInputStream(file);
        int amount = f.available();
        for (int i = 0; i < amount; ++i) {
            int c = f.read();
            //System.out.println(c);
            count[c]++;
        }
        f.close();
        PriorityQueue<Node> pq = new PriorityQueue<>(256, (a, b) -> a.count - b.count);
        pq.addAll(makeNodeList(count));
        Node tree = Node.makeHuffmanTree(pq);
        tree.printCode(tree, "");
        FileInputStream in = new FileInputStream(file);
        FileOutputStream out = new FileOutputStream("file.hoe");
        for (int t : count) {
            out.write(t);
            //if(t > 0) System.out.println(t);
        }
        System.out.println(tree.left.letter);
        int input;
        long writeByte = 0b0;
        int i = 0;
        int j = 0;
        ArrayList<Byte> bytes = new ArrayList<>();
        for (int k = 0; k < amount; ++k) {
            input = in.read();
            j = 0;
            String bitString = tree.bitstring[input];
            while (j < bitString.length()) {
                // writeByte *= 2;
                // if (bitString.charAt(j) == '1')
                //     writeByte++;
                if (bitString.charAt(j) == '0')writeByte = (writeByte<<1);
                else writeByte = ((writeByte<<1)|1);
                ++j;
                ++i;
                //System.out.println((writeByte)+"   ");
                if (i == 8) {
                    //out.write((byte)writeByte);
                    bytes.add((byte)writeByte);
                    i = 0;
                    writeByte = 0b0;
                }
            }
        }
        int lastByte = i;
        while (i < 8 && i != 0) {
            writeByte = (writeByte<<1);
            ++i;
        }
        bytes.add((byte)writeByte);
        out.write(lastByte);
        for (Byte s : bytes) {
            out.write(s);
        }
        in.close();
        out.close();
    }

    private static ArrayList<Node> makeNodeList(int[] count) {
        ArrayList<Node> nodeList = new ArrayList<>();
        for (int i = 0; i < count.length; i++) {
            if(count[i] != 0)nodeList.add(new Node((char) i, count[i], null, null));
        }
        return nodeList;
    }

    static void decompress(String file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        int amount = in.available();
        //System.out.println();
        int [] count = new int [256];
        for (int i = 0; i < count.length; i++) {
            int freq = in.read();
            //System.out.println(freq);
            count[i] = freq;
        }
        // System.out.println("new file");
        // for (int t : count) {
        //        if(t > 0) System.out.println(t);
        // }
        int lastByte = in.read();
        System.out.println(lastByte +"last");
        PriorityQueue<Node> pq = new PriorityQueue<>(256, (a, b) -> a.count - b.count);
        pq.addAll(makeNodeList(count));
        Node tree = Node.makeHuffmanTree(pq);
        tree.printCode(tree, "");
        FileOutputStream out = new FileOutputStream(new File("newfile"));
        BufferedOutputStream bos = new BufferedOutputStream(out);
        DataOutputStream os = new DataOutputStream(bos);
        //System.out.println(tree.left.right.letter);
        Node tempTree = tree;
        int ch;
        byte [] bytes = in.readAllBytes();
        in.close();
        int rest = 0;
        long byteRest = 0;
        int c = 0;
        int length = bytes.length;
        bitstreng h = new bitstreng(0, 0);
        if(lastByte>0) length--;
        for (int i = 0; i <length; i++) {
            ch = bytes[i];
            bitstreng b = new bitstreng(8, ch);
            h = bitstreng.konkatenere(h,b);
            for (long j = 1<< h.lengde-1; j!=0; j>>=1) {
                c++;
                if((h.biter & j) == 0)tempTree = tempTree.left;
                else tempTree = tempTree.right;
                if(tempTree.left == null && tempTree.right == null){
                    os.write((byte)tempTree.letter);
                    long temp = ~(0 << (h.lengde-c));
                    h.biter = (h.biter & temp);
                    h.lengde = h.lengde-c;
                    c = 0;
                    tempTree = tree;
                }
            }
            
        }
                if(lastByte>0){
                    bitstreng b = new bitstreng(lastByte, bytes[length]>>lastByte);
                    h = bitstreng.konkatenere(h, b);
                for (long j = 1<< h.lengde-1; j!=0; j>>=1) {
                    c++;
                    if((h.biter & j) == 0)tempTree = tempTree.left;
                    else tempTree = tempTree.right;
                    if(tempTree.left == null && tempTree.right == null){
                        os.write((byte)tempTree.letter);
                        long temp = ~(0 << (h.lengde-c));
                        h.biter = (h.biter & temp);
                        h.lengde = h.lengde-c;
                        c = 0;
                        tempTree = tree;
                    }
                }
                }
               
        

                in.close();
                os.close();
    }
    public static void main(String[] args) {
        try {
            compress("diverse.txt");
            decompress("file.hoe");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
