class Last{
    int dist;
    int fullDist;
    int distToEnd;
    Node last;
    static int inf = 1000000000;
    public int getDist(){return dist;}
    public Node getLat(){return last;}
    Last(){
        this.dist = inf;
        this.fullDist = inf;
        this.distToEnd = -1;
    }
}
