class Vertex{
    Vertex next;
    Node to;
    int weight;
    int speedlimit;
    int length;
    public Vertex(Node n, Vertex v, int w, int s, int l){
        this.to = n;    
        this.next = v;
        this.weight = w;
        this.speedlimit = s;
        this.length = l;

    }
}
