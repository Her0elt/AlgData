#include <fstream>
#include <sstream>
#include <queue>
#include <stdio.h>

using namespace std;

class Node;

class Vertex{
    public:
    Vertex* next;
    Node* to;
    int weight;
    Vertex(Node* t, Vertex* next, int w){
        this->next = next;
        this->to = t;
        this->weight = w;
    }
    Vertex(){
        this->next = NULL;
        this->to = NULL;
    }
};

class Node{
    public:
        Vertex* firstV;
        int index;
        int dist;
        Node* from;

    Node(int i){
        this->index = i;
        this->dist = 1000000000;
        this->firstV = NULL;
    }
    Node(){
        this->firstV = NULL;
        this->dist = 1000000000;
    }

};
struct CompareDist { 
    bool operator()(Node* const& p1, Node* const& p2) { 

        return p1->dist - p2->dist; 
    } 
};

class Wgraph{
    public:
    Node *node;
    int N;
    int K;
    explicit Wgraph(ifstream file){
        string str;
        getline(file, str);
        istringstream iss(str);
        iss >> N;
        iss >> K;
        this->node = new Node[N];
        for (int i = 0; i < N; i++)node[i] = Node(i);
        int to;
        int from;
        int weight;
        for (int i = 0; i < K; i++){
            getline(file, str);
            istringstream iss2(str);
            iss2 >> to;
            iss2 >> from;
            iss2 >> weight;
            Vertex* v = new Vertex(&node[to], node[from].firstV, weight);
            node[from].firstV = v;
        }
    }
    void dijkstra(Node* s){
        priority_queue<Node*, vector<Node*>,CompareDist>pq;
        s->dist = 0;
        for(int i = 0; i<N; i++)pq.push(&node[i]);
        while(!pq.empty()){
            Node* n = pq.top();
            pq.pop();
            for(Vertex *k = n->firstV; k!=NULL; k = k->next){
                if(k->to->dist > n->dist + k->weight){
                    k->to->dist = n->dist + k->weight;
                    k->to->from = n;
                }
            }
        }
    }
}; 
int main(){
    string name = "vg1";
    Wgraph wg = Wgraph(ifstream(name));
    int start = 1;
    Node node = wg.node[start];
    wg.dijkstra(&node);
    printf("Node\tFrom\tDist\n");
    for(int i = 0; i<wg.N; i++)printf("%d nnn %d", wg.node[i].index, wg.node[i].dist);
    for(int i = 0; i<wg.N; i++){
        if(wg.node[i].dist != 1000000000){
            string from = (wg.node[i].index == start)? "start": to_string(wg.node[i].from->index);
            printf("%d\t\t%s\t\t%d\n",wg.node[i].index,from.c_str(),wg.node[i].dist);
        }else{
            printf("%d\t\t\t\tNot reached\n", wg.node[i].index);
        }
        
    }
    printf("%s with start in %d",name.c_str(),start);
}