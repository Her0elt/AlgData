import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.StringTokenizer;

class last{
    Node last;
    static int inf = 1000000000;
    int found_time, done_time;
    static int time = 0;
    static void nullTime(){time = 0;}
    static int readTime(){return time++;}
    public Node findForgj(){return last;}
    public last(){}
}

class Node {
    Edge edge1;
    Object data;
    int index;
    public Node(int i){this.index = i;}
}

class Edge {
    Edge next;
    Node to;

    public Edge(Node n, Edge nst) {
        this.to = n;
        this.next = nst;
    }

}

class UnWeightedGraf {
    int N, K;
    Node[] nodes;
    int [][] comp;
    public UnWeightedGraf() {
    }

    public UnWeightedGraf(int n, int k) {
        this.N = n;
        this.K = k;
    }

    public void new_ugraf(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        this.N = Integer.parseInt(st.nextToken());
        this.nodes = new Node[N];
        for (int i = 0; i < N; i++)this.nodes[i] = new Node(i);
        this.K = Integer.parseInt(st.nextToken());
        for (int j = 0; j < K; j++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            Edge e = new Edge(this.nodes[to], this.nodes[from].edge1);
            this.nodes[from].edge1 = e;
        }
        
    }
    public void new_ugrafFlip(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        this.N = Integer.parseInt(st.nextToken());
        this.nodes = new Node[N];
        for (int i = 0; i < N; i++)this.nodes[i] = new Node(i);
        this.K = Integer.parseInt(st.nextToken());
        for (int j = 0; j < K; j++) {
            st = new StringTokenizer(br.readLine());
            int to = Integer.parseInt(st.nextToken());
            int from = Integer.parseInt(st.nextToken());
            Edge e = new Edge(this.nodes[to], this.nodes[from].edge1);
            this.nodes[from].edge1 = e;
        }
        
    }

    public void dfs_init(){
        this.comp = new int[N][N];
        for (int i = 0; i <N;i++) {
            this.nodes[i].data = new last();
        }
        last.nullTime();
    }

    public void df_search(Node n, boolean print, boolean [] visited, int i) {
        last nd = (last) n.data;
        nd.found_time = last.readTime();
        for (Edge e = n.edge1; e != null; e = e.next) {
            last md = (last) e.to.data;
            if(print && !visited[e.to.index]){
                this.comp[i][e.to.index] = e.to.index;
                visited[e.to.index] = true;
            }
            if (md.found_time == 0) {
                md.last = n;
                df_search(e.to, print, visited, i);
            }

        }
        nd.done_time = last.readTime();
    }
    public void dfs(Node n, boolean print,boolean [] visited, int i){
        this.dfs_init();
        df_search(n, print, visited, i);
    }


    public static void main(String[] args) {
        UnWeightedGraf g = new UnWeightedGraf();
        try {
            BufferedReader b = new BufferedReader(new FileReader(new File("L7g6")));
            g.new_ugraf(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < g.nodes.length; i++) {
            g.dfs(g.nodes[i], false, null, i);
        }
        Node [] ns = new Node[g.N];
        for (int i =0;i<g.nodes.length; i++) {
            ns[i] = g.nodes[i];
        }
        UnWeightedGraf gf = new UnWeightedGraf();
        try {
            BufferedReader b = new BufferedReader(new FileReader(new File("L7g6")));
            gf.new_ugrafFlip(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean [] visited = new boolean[gf.N];
        Arrays.sort(ns, (a,b) ->((last)b.data).done_time - ((last)a.data).done_time );
        int count = 1;
        for (int i = 0; i < ns.length; i++) {
            gf.dfs(gf.nodes[ns[i].index], true, visited,ns[i].index);
        }
        Arrays.sort(gf.comp, (a, b)->Integer.compare(a[0], b[0]));
        for (int i = 0; i < gf.comp.length; i++) {
            for (int j = 0; j < gf.comp[0].length; j++) {
                if(!visited[i]){
                    System.out.print(gf.comp[i][j]);
                    count++;
                    visited[i] = true;
               }
            }
        }
        
    }

}

