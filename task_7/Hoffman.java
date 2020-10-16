import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;;

//((x << 1) | 1) for right
//((x << 1)) for left
//Long.toBinaryString(x) if length isnt 8 do left until its 8
//
public class Hoffman {
    static ByteWriter p;

     Hoffman() throws FileNotFoundException {
        p = new ByteWriter(); 
    }
    static int place = 0;
    public void compress(String path) throws IOException{
        File file = new File(path);
        byte [] bytes = Files.readAllBytes(file.toPath());
        int [] count = new int[256];
        for (int i = 0; i< bytes.length; i++){
            int c = (128 + bytes[i] );
            count[c]++;
           
        }
        PriorityQueue<Node> pq = new PriorityQueue<>(256,(a,b)->a.count - b.count);
        pq.addAll(makeTreeList(count));
        Node tree = Node.makeHuffmanTree(pq);
        for(byte b : bytes){
            bitstreng bit = new bitstreng(0,0);
            setBits(tree, bit, b);
        }
    }
    private ArrayList<Node> makeTreeList(int[] count){
        ArrayList<Node> treeList = new ArrayList<>();
        for(int i = 0; i<count.length; i++){
           if(count[i] > 0)treeList.add(new Node(count[i], (char)i));
        }
        return treeList;
    }
    private void setBits(Node n, bitstreng t, byte b) throws IOException {
        if(n != null){
            if(n.letter == b)p.writeByte(t);
            t.addOne();
            setBits(n.right, t, b);
            t.remove();
            t.addZero();
            setBits(n.left, t, b);
            t.remove();
        }
    }
    public static void main(String[] args) {
        try {
            new Hoffman().compress("test");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }   
}
