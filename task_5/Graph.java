import java.io.*;  
import java.util.*;  
//I used a array list of a linked list to easierly connect the path is the graph
//made it easier to visualize 
class Graph{ 
    private int SCCCount;
    private int N;   
    private ArrayList<LinkedList<Integer>> adj;  
    private int Time; 
    
    Graph(BufferedReader br) throws IOException {
        this. SCCCount = 0;
        StringTokenizer st = new StringTokenizer(br.readLine());
        this.N = Integer.parseInt(st.nextToken());  
        this.adj = new ArrayList<LinkedList<Integer>>(); 
        for(int i = 0; i < N; ++i)  
            adj.add(new LinkedList<Integer>());      
        this.Time = 0; 
        int K = Integer.parseInt(st.nextToken());
        for (int j = 0; j < K; j++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            addEdge(from, to);
        }
    }  
     
    void addEdge(int v,int w){  
        adj.get(v).add(w);  
    }
    
    void SCCUtil(int u, int low[], int disc[], boolean stackMember[],  Stack<Integer> st){ 
        //used to sotre discovvered times like last from the book
        disc[u] = Time;  
        //used to store the lowest found time for the nodes
        low[u] = Time;  
        Time += 1; 
        stackMember[u] = true; 
        st.push(u); 
        int n; 
        Iterator<Integer> i = adj.get(u).iterator();  
        //finds all member nodes of u and adds a time form them
        while (i.hasNext()){  
            n = i.next();  
            //checks if we have seen this node before
            //if not not we add the lowest discovered time to lowest
            if (disc[n] == -1){ 
                SCCUtil(n, low, disc, stackMember, st); 
                low[u] = Math.min(low[u], low[n]); 
            }else if (stackMember[n] == true){ 
                low[u] = Math.min(low[u], disc[n]); 
            } 
        }   
        //all of the above code makes sure that we find all nodes in the graph
        //and ads a found time and a lowest found time
        int w = -1;  
        //finds all the connected nodes in u's scc
        if (low[u] == disc[u]){
            if(N<100)System.out.print("#"+(this.SCCCount+1)+ " "); // pluss 1 because I     
            while (w != u){                                       //havent counted the comp yet
                w = (int)st.pop(); 
                if(N<100)System.out.print(w + " ");
                stackMember[w] = false; 
            } 
            this.SCCCount++;

            if(N<100)System.out.println();  
        } 
    }  
    void SCC(){  
        // makes the discovered list and lowest list and 
        //adds -1 to every index so that if we test for disc[i] = -1
        //and get false we know we have looked at that node
        int disc[] = new int[N];  
        int low[] = new int[N];  
        for(int i = 0;i < N; i++){ 
            disc[i] = -1; 
            low[i] = -1; 
        } 
        
        boolean stackMember[] = new boolean[N];  
        Stack<Integer> st = new Stack<Integer>();   
        for(int i = 0; i < N; i++){ 
            if (disc[i] == -1) SCCUtil(i, low, disc, stackMember, st); 
        } 
    }
    public static void main(String [] args){
        String name = "L7g6";
        Graph g =null;
        try {
            BufferedReader b = new BufferedReader(new FileReader(new File(name)));
           g = new Graph(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.SCC();
        System.out.println("filename: "+name);
        System.out.println("There are "+g.SCCCount +" Strongly connected components in this graph");

    }
} 