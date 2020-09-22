import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.StringTokenizer;

class UnWeightedGraf {
    int N;
    LinkedList<Integer>[] adjListArray;
    public UnWeightedGraf() {
    }


    public void new_ugraf(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        this.N = Integer.parseInt(st.nextToken());
        for (int i = 0; i < N; i++)this.adjListArray[i] = new LinkedList<Integer>();
        int K = Integer.parseInt(st.nextToken());
        for (int j = 0; j < K; j++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            this.adjListArray[from].add(to);
            this.adjListArray[to].add(from);
        }
        
    }


    void DFSUtil(int v, boolean[] visited) { 
        visited[v] = true; 
        System.out.print(v+" "); 
        for (int x : adjListArray[v]) { 
            if(!visited[x]) DFSUtil(x,visited); 
        } 
  
    } 
    void connectedComponents() { 
        boolean[] visited = new boolean[N]; 
        for(int i = 0; i < N; ++i) { 
            if(!visited[i]) { 
                DFSUtil(i,visited); 
                System.out.println(); 
            } 
        } 
    } 

    public static void main(String[] args) {
        UnWeightedGraf g = new UnWeightedGraf();
        try {
            BufferedReader b = new BufferedReader(new FileReader(new File("L7g6")));
            g.new_ugraf(b);
        } catch (IOException e) {
            e.printStackTrace();
        }    
        g.connectedComponents();
    }

}

