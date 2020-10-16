import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
        int input;
        int writeByte = 0;
        int i = 0;
        int j = 0;
        for (int k = 0; k < amount; ++k) {
            input = in.read();
            j = 0;
            String bitString = tree.bitstring[input];
            while (j < bitString.length()) {
                writeByte *= 2;
                if (bitString.charAt(j) == '1')
                    writeByte++;
                ++j;
                ++i;
                if (i == 8) {
                    out.write(writeByte);
                    i = 0;
                    writeByte = 0;
                }
            }
        }
        while (i < 8) {
            writeByte *= 2;
            ++i;
        }
        out.write(writeByte);
        in.close();
        out.close();
    }

    private static ArrayList<Node> makeNodeList(int[] count) {
        ArrayList<Node> nodeList = new ArrayList<>();
        for (int i = 0; i < count.length; i++) {
            nodeList.add(new Node(count[i], (char) i));
        }
        return nodeList;
    }

    static void decompress(String file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        int amount = in.available();
        int [] count = new int [256];
        for (int i = 0; i < count.length; i++) {
            int freq = in.read();
            count[i] = freq;
        }
        // System.out.println("new file");
        // for (int t : count) {
            //    if(t > 0) System.out.println(t);
            // }
        PriorityQueue<Node> pq = new PriorityQueue<>(256, (a, b) -> a.count - b.count);
        pq.addAll(makeNodeList(count));
        Node tree = Node.makeHuffmanTree(pq);
        tree.printCode(tree, "");
        FileOutputStream out = new FileOutputStream(new File("newfile"));
        BufferedOutputStream bos = new BufferedOutputStream(out);
        DataOutputStream os = new DataOutputStream(bos);
        Node tempTree = tree;
        int ch;
        for (int i = 0; i<amount-256; ++i) {
            ch = in.read();
        for (int pos = 128; pos > 0; pos/=2) {
          int siffer = ch / pos;
          ch %= pos;
          if (siffer == 0) tempTree = tempTree.left;
          else tempTree = tempTree.right;
          if (tempTree.left == null) {
            os.writeByte(tempTree.letter);
            tempTree = tree;
          }
        }
      }
      in.close();
      os.close();


    }
    public static void main(String[] args) {
        try {
            compress("test");
            decompress("file.hoe");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
