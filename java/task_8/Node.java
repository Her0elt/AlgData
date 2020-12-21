class Node {
    Vertex vertex1;
    Last data;
    int index;
    double latitude;
    double longitude;
    int type;
    boolean done;
    boolean end;
    double cos_width;
    String name;
    Node(int i, double la, double lo){
        this.latitude = la;
        this.longitude = lo;
        this.index = i;
        this.name = "";
        this.done = false;
    }
    
    @Override
    public String toString() {
       return this.latitude*(180/Math.PI)+","+ this.longitude*(180/Math.PI);
    }


}
