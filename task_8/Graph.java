import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Graph {
    int N;
    int K;
    Node[] node;
    PriorityQueue<Node> queue;
    HashMap<String, Integer> places; //only used to find places

    public Graph(BufferedReader nodes, BufferedReader vertexes, BufferedReader names) throws IOException {
        this.places = new HashMap<>();
        read_nodes(nodes);
        read_vertexes(vertexes);
        read_names_and_types(names);
    }

    private void read_nodes(BufferedReader nodes) throws IOException {
        StringTokenizer st = new StringTokenizer(nodes.readLine());
        this.N = Integer.parseInt(st.nextToken());
        this.node = new Node[N];
        for (int i = 0; i < N; ++i) {
            st = new StringTokenizer(nodes.readLine());
            int index = Integer.parseInt(st.nextToken());
            double lat = Double.parseDouble(st.nextToken())*(Math.PI/180);
            double lon = Double.parseDouble(st.nextToken())*(Math.PI/180);
            node[index] = new Node(index, lat, lon);
            node[index].data = new Last();
            node[index].cos_width = Math.cos(lat);
        }
    }

    private void read_vertexes(BufferedReader vertexes) throws IOException {
        StringTokenizer st = new StringTokenizer(vertexes.readLine());
        this.K = Integer.parseInt(st.nextToken());
        for (int i = 0; i < K; ++i) {
            st = new StringTokenizer(vertexes.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());
            int length = Integer.parseInt(st.nextToken());
            int speedlimit = Integer.parseInt(st.nextToken());
            Vertex v = new Vertex(node[to], node[from].vertex1, weight, speedlimit, length);
            node[from].vertex1 = v;
        }
    }

    private void read_names_and_types(BufferedReader names) throws IOException {
        StringTokenizer st = new StringTokenizer(names.readLine());
        int m = Integer.parseInt(st.nextToken());
        for (int i = 0; i < m; ++i) {
            st = new StringTokenizer(names.readLine());
            int n = Integer.parseInt(st.nextToken());
            int type = Integer.parseInt(st.nextToken());
            String name = st.nextToken();
            while (st.hasMoreTokens())
                name += " " + st.nextToken();
            this.node[n].type = type;
            this.node[n].name = name;
            this.places.put(name, n);
        }
    }

    private int distance(Node n1, Node n2) {
        double sin_width = Math.sin((n1.latitude - n2.latitude) / 2.0);
        double sin_length = Math.sin((n1.longitude - n2.longitude) / 2.0);
        //41701090.90909090909090909091
        //35285538.46153846153846153846 
        return (int) (35285538.46153846153846153846
                * Math.asin(Math.sqrt(sin_width * sin_width + n1.cos_width * n2.cos_width * sin_length * sin_length)));
    }

    
    
    private void make_prio() {
        this.queue = new PriorityQueue<>((a, b) -> (a.data.dist) - (b.data.dist));
    }
    private void make_prio_astar() {
        this.queue = new PriorityQueue<>((a, b) -> (a.data.fullDist) - (b.data.fullDist));
    }
    
    public Node[] dijkstra_find_type(Node s, int t) {
        make_prio();
        Node[] list = new Node[10];
        s.data.dist = 0;
        this.queue.add(s);
        int count = 0;
        while(!this.queue.isEmpty()) {
            Node n = this.queue.poll();
            if ((n.type == t || n.type == 6)) {
                list[count] = n;
                count++;
                n.done = true;
            }
            if (count == 10)break;
            for (Vertex k = n.vertex1; k != null; k = k.next) {
                shorten(n, k);
            }
        }
        return list;
    }
    
    public int dijkstra(Node s, Node e) {
        s.data.dist = 0;
        e.end = true;
        make_prio();
        this.queue.add(s);
        int count = 0;
        while (!this.queue.isEmpty()) {
            Node n = this.queue.poll();
            count++;
            if (n.end)return count;
            for (Vertex k = n.vertex1; k != null; k = k.next) {
                shorten(n, k);
            }
        }
        return -1;
    }
    
    public int Astar(Node s, Node e) {
        s.data.dist = 0;
        s.data.distToEnd = distance(s, e);
        s.data.fullDist = s.data.distToEnd;
        e.end = true;
        make_prio_astar();
        this.queue.add(s);
        int count = 0;
        while (!this.queue.isEmpty()) {
            Node n = this.queue.poll();
            count++;
            if (n.end)return count;
            for (Vertex k = n.vertex1; k != null; k = k.next) {
               shorten(n, k, e);
            }
        }
        return -1;
    }
    void shorten(Node n, Vertex v) {
        Last nd = n.data, md = v.to.data;
        if (md.dist > nd.dist + v.weight) {
            md.dist = nd.dist + v.weight;
            md.last = n;
            this.queue.add(v.to);
        }
    }
    void shorten(Node n, Vertex v, Node e) {
        Last nd = n.data, md = v.to.data;
        if (md.dist > nd.dist + v.weight) {
            if(md.distToEnd == -1)md.distToEnd = distance(v.to, e);
            md.dist = nd.dist + v.weight;
            md.last = n;
            md.fullDist = md.dist + md.distToEnd;
            this.queue.add(v.to);
        }
    }
    public void reset(){
        for (Node n : this.node) {
            n.data = new Last();
            n.done = false;
            n.end = false;
        }
    }
    public static void run_dijkstra_find_type(Graph g, int start, int type){
        Node [] m = g.dijkstra_find_type(g.node[start], type);
        for (Node node : m) {
            if(node !=null)System.out.println(node.name +" "+ node.type+ " " +node.index);
        }
    }

    public static void run_dijkstra(Graph g, int start, int end){
        long tstart = System.nanoTime();
        int m = (g.dijkstra(g.node[start], g.node[end]));
        long tend = System.nanoTime();
        System.out.println("Nr of nodes checked: "+m);
        long total = tend-tstart;
        System.out.println("Djikstra time: " + (double)total/1000000000 +"s");
        Node n = g.node[end];
        try {
            FileWriter os = new FileWriter("dijkstra.txt");
            int s = n.data.dist/100;
            System.out.print("Total time by driving: "+s/3600+" : ");
            System.out.print((s%3600)/60+" : ");
            System.out.print((s%3600)%60+"\n");
            while(n !=null){
            os.write(n.toString()+"\n");
            n = ((Last)n.data).last;
            }
            os.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void run_astar(Graph g, int start, int end){
        long tstart = System.nanoTime();
        int m = g.Astar(g.node[start], g.node[end]);
        long tend = System.nanoTime();
        System.out.println("Nr of nodes checked: "+m);
        long total = tend-tstart;
        System.out.println("Astar time: " + (double)total/1000000000 +"s");
        Node n = g.node[end];
        try {
            FileWriter os = new FileWriter("astar.txt");
            int s = n.data.dist/100;
            System.out.print("Total time by driving: "+s/3600+" : ");
            System.out.print((s%3600)/60+" : ");
            System.out.print((s%3600)%60+"\n");
            while(n !=null){
            os.write(n.toString()+"\n");
            n = ((Last)n.data).last;
            }
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String name1 = "noder.txt";
        String name2 = "kanter.txt";
        String name3 = "interessepkt.txt";
        Graph g = null;
        try {
            BufferedReader nodes = new BufferedReader(new FileReader(new File(name1)));
            BufferedReader vertexes = new BufferedReader(new FileReader(new File(name2)));
            BufferedReader names = new BufferedReader(new FileReader(new File(name3)));
            g = new Graph(nodes, vertexes, names);
        } catch (IOException e) {
            e.printStackTrace();
        }   
        //1117256 4
        //6198111 2
        //run_dijkstra_find_type(g, 6198111, 2);
        //g.reset();
        //1221382
        //6013683
        //6225195
        int start = g.places.get("\"Trondheim\"");
        int end = g.places.get("\"Helsinki\"");
        run_astar(g, start, 1221382);
        g.reset();
        run_dijkstra(g, start, 1221382);
        
        
    }
}