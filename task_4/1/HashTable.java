import java.io.*;

class HashNode{
    public HashNode next;
    public String data;

    public HashNode(String data, HashNode next){
        this.data = data;
        this.next = next;
    }
    public HashNode(){}

}
class HashTabell{
    private HashNode nodes[];
    private int length;
    private int collisions;

    public HashTabell(int length){
        this.nodes = new HashNode[length/2];
        this.length = length/2;
        this.collisions = 0;
    }

    public int hash(String s){
        int nr = 1;
        int hash = 0;
        for(char n : s.toCharArray()){
            hash += n*nr;
            nr++;
        }
        return hash%length;
    }

    public void insert(String s){
        int h = hash(s);
        if(nodes[h] ==null){
            nodes[h] = new HashNode(s,null);
        }else{
            nodes[h] = new HashNode(s,nodes[h]);;
            collisions++;
        }
    }

    public HashNode find(String s){
        int h = hash(s);
        HashNode temp = nodes[h];
        while(!temp.data.equals(s)){
            temp = temp.next;
        }
        return temp;
    }

    public void print(){
        String output = "";
        HashNode temp;
        int people = 0;
        for(HashNode n : nodes){
            if(n != null){
                temp = n;
                output += temp.data;
                people++;
                while(temp.next != null) {
                    temp = temp.next;
                    output += "-->" + temp.data;
                    people++;
                }
            }
            output += "\n";
        }
        System.out.println("nr of collisions "+ collisions);
        System.out.print(output);
        System.out.println("Load factor "+(double)(people/length));
        System.out.println("avarage collisions pr person "+(double)(collisions/people));

    }

    public static void main(String[] args) {
        HashTabell ht = new HashTabell(86);
        try {
            BufferedReader b = new BufferedReader(new FileReader(new File("name.txt")));
            String text;
            while((text = b.readLine() )!= null){
                ht.insert(text);
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        ht.print();
        System.out.println(ht.find("Hermann Owren Elton").data);

    }
}