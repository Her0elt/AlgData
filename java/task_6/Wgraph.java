import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Node {
    Vertex vertex1;
    Object data;
    int index;
    int currentIndex;
    Node(int i, int c){
        this.index = i;
        this.currentIndex = i;
    }    

}

class Vertex{
    Vertex next;
    Node to;
    int weight;
    public Vertex(Node n, Vertex v, int w){
        this.to = n;
        this.next = v;
        this.weight = w;

    }
}

class Last{
    int dist;
    Node last;
    static int inf = 1000000000;
    public int getDist(){return dist;}
    public Node getLat(){return last;}
    Last(){
        this.dist = inf;
    }
}

class Wgraph{
    int N;
    int K; 
    Node [] node;
    PriorityQueue<Node> queue;
    

    Wgraph(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        this.N = Integer.parseInt(st.nextToken());  
        this.node = new Node[N];
        for(int i = 0; i < N; ++i)node[i] = new Node(i,i); 
        int K = Integer.parseInt(st.nextToken());
        for (int j = 0; j < K; j++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());
            Vertex v = new Vertex(node[to], node[from].vertex1, weight);
            node[from].vertex1 = v;
        }
    }

    void shorten(Node n, Vertex v){
        Last nd = (Last)n.data,md = (Last)v.to.data;
        if(md.dist > nd.dist + v.weight){
            md.dist = nd.dist + v.weight;
            md.last = n;
            this.queue.remove(v.to);
            this.queue.add(v.to);
        }
    }
    void initLast(Node s){
        for (int i = 0; i < node.length; i++) {
            node[i].data = new Last();
        }
        ((Last)s.data).dist =0;
    }
    void make_prio(){
       this.queue = new PriorityQueue<>(this.N,(a,b) ->((Last)a.data).dist - ((Last)b.data).dist);
       for (int i = 0; i < N; i++) {

            this.queue.add(node[i]);
        }   
    }
    
 
    void dijkstra(Node s){
        initLast(s);
        make_prio();
        for(int i = this.N; i> 1 ;--i){
            Node n = this.queue.poll();
            for(Vertex k = n.vertex1; k!=null; k = k.next){
                shorten(n, k);
            }
        }
    }

    public static void main(String [] args) {
        String name = "vg1";
        Wgraph g =null;
        try {
            BufferedReader b = new BufferedReader(new FileReader(new File(name)));
           g = new Wgraph(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int nodeIndex = 1;
        Node node = g.node[nodeIndex];
        g.dijkstra(node);
        System.out.format("%-7s%-7s%-7s%n", "Node","From", "Dist");
        for (int i = 0; i < g.N; i++) {
            if(((Last)g.node[i].data).dist != Last.inf){
                String from = (g.node[i].index == nodeIndex)? "Start": String.valueOf(((Last)g.node[i].data).last.index);
                System.out.format("%-7s%-7s%-7s%n",g.node[i].index,from,((Last)g.node[i].data).dist);
            }else{
                System.out.format("%-7s%-7s%-7s%n",g.node[i].index,"","Not reached");
            }
            
        }
        System.out.println(name +" with start in "+nodeIndex);
    }
}