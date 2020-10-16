import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.PriorityQueue;

public class Huffman {

    // alphabet size of extended ASCII
    private static final int R = 256;
    static OutputStream oos;
    static BufferedOutputStream bos;
    static DataOutputStream os;

    // Do not instantiate.
    private Huffman() throws FileNotFoundException {

    }

    // Huffman trie node
    private static class Node implements Comparable<Node> {
        private final char ch;
        private final int freq;
        private final Node left, right;

        Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        // is the node a leaf node?
        private boolean isLeaf() {
            assert ((left == null) && (right == null)) || ((left != null) && (right != null));
            return (left == null) && (right == null);
        }

        // compare, based on frequency
        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }

    private static void init() throws FileNotFoundException {
        oos = new FileOutputStream(new File("file.hoe"));
        bos = new BufferedOutputStream(oos);
        os = new DataOutputStream(bos);
    }

    /**
     * Reads a sequence of 8-bit bytes from standard input; compresses them using
     * Huffman codes with an 8-bit alphabet; and writes the results to standard
     * output.
     * 
     * @throws IOException
     */
    public static void compress(String path) throws IOException {
        // read the input
        init();
        
        File file = new File(path);
       
        byte[] input = Files.readAllBytes(file.toPath());

        // tabulate frequency counts
        int[] freq = new int[R];
        for (int i = 0; i < input.length; i++)
            if(input[i]>0 && input[i]< 256)freq[input[i]]++;

        // build Huffman trie
        Node root = buildTrie(freq);

        // build code table
        String[] st = new String[R];
        buildCode(st, root, "");

        // print trie for decoder
        // writeTrie(root);

        // // print number of bytes in original uncompressed message
        // os.write(input.length);

        // use Huffman code to encode input
        for (int i = 0; i < input.length; i++) {
            if(input[i]>0 && input[i]< 256){
            String code = st[input[i]];
            for (int j = 0; j < code.length(); j++) {
                if (code.charAt(j) == '0') {
                    os.writeBoolean(false);
                }
                else if (code.charAt(j) == '1') {
                    os.writeBoolean(true);
                }
                else throw new IllegalStateException("Illegal state");
            }
            }else{
                os.write(input[i]);
            }
        }

        // close output stream
        os.close();
    }

    // build the Huffman trie given frequencies
    private static Node buildTrie(int[] freq) {

        // initialze priority queue with singleton trees
        PriorityQueue<Node> pq = new PriorityQueue<Node>();
        for (char c = 0; c < R; c++)
            if (freq[c] > 0)
                pq.add(new Node(c, freq[c], null, null));

        // merge two smallest trees
        while (pq.size() > 1) {
            Node left  = pq.poll();
            Node right = pq.poll();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            pq.add(parent);
        }
        return pq.poll();
    }


    // write bitstring-encoded trie to standard output
    private static void writeTrie(Node x) throws IOException {
        if (x.isLeaf()) {
            os.writeBoolean(true);
            write(x.ch, 8);
            return;
        }
        os.writeBoolean(false);
        writeTrie(x.left);
        writeTrie(x.right);
    }
    public static void write(int x, int r) throws IOException {
        if (r == 32) {
            os.write(x);
            return;
        }
        if (r < 1 || r > 32) throw new IllegalArgumentException("Illegal value for r = " + r);
        if (x >= (1 << r))   throw new IllegalArgumentException("Illegal " + r + "-bit char = " + x);
        for (int i = 0; i < r; i++) {
            boolean bit = ((x >>> (r - i - 1)) & 1) == 1;
            os.writeBoolean(bit);
        }
    }

    // make a lookup table from symbols and their encodings
    private static void buildCode(String[] st, Node x, String s) {
        if (!x.isLeaf()) {
            buildCode(st, x.left,  s + '0');
            buildCode(st, x.right, s + '1');
        }
        else {
            st[x.ch] = s;
        }
    }

    /**
     * Reads a sequence of bits that represents a Huffman-compressed message from
     * standard input; expands them; and writes the results to standard output.
     */
    // public static void expand() {

    //     // read in Huffman trie from input stream
    //     Node root = readTrie(); 

    //     // number of bytes to write
    //     int length = BinaryStdIn.readInt();

    //     // decode using the Huffman trie
    //     for (int i = 0; i < length; i++) {
    //         Node x = root;
    //         while (!x.isLeaf()) {
    //             boolean bit = .readBoolean();
    //             if (bit) x = x.right;
    //             else     x = x.left;
    //         }
    //         BinaryStdOut.write(x.ch, 8);
    //     }
    //     BinaryStdOut.close();
    // }


    // private static Node readTrie() {
    //     boolean isLeaf = BinaryStdIn.readBoolean();
    //     if (isLeaf) {
    //         return new Node(BinaryStdIn.readChar(), -1, null, null);
    //     }
    //     else {
    //         return new Node('\0', -1, readTrie(), readTrie());
    //     }
    // }

    /**
     * Sample client that calls {@code compress()} if the command-line
     * argument is "-" an {@code expand()} if it is "+".
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        // if      (args[0].equals("-")) compress();
        // else if (args[0].equals("+")) expand();
        // else throw new IllegalArgumentException("Illegal command line argument");
        try {
            compress("diverse.txt");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
