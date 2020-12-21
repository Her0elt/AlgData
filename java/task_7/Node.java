import java.util.PriorityQueue;

class Node{
    Node left;
    Node right;
    int count;
    char letter;
    String [] bitstring;
    public Node(int c, char l){
        this.count = c;
        this.letter = l;
        bitstring = new String[256];
    }
    public Node(int c){
        this.count = c;
        bitstring = new String[256];
    }
    public Node(char c, int i, Node l, Node r){
        this.letter = c;
        this.count = i;
        this.left = l;
        this.right = r;
        bitstring = new String[256];
    }
    public Node(){bitstring = new String[256];}

    public static Node makeHuffmanTree(PriorityQueue<Node> pq){
        Node tree = new Node();
        while(pq.size() > 1){
            Node t = pq.poll();
            Node n = pq.poll();
            Node h = new Node('\0',findSum(t, n), n, t);
            pq.add(h);
            tree = h;
        }
        return tree;

    }
    private static int findSum(Node t, Node n){
        return t.count+n.count;
    }

    public void printCode(Node root, String s) { 
        if (root.left != null && root.right != null) { 
            printCode(root.left, s+"0");
            printCode(root.right, s+"1"); 
            
        }else bitstring[root.letter] = s; 

    }
}