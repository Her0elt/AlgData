import java.io.*;

class HashTabell{
    class HashNode{
        public HashNode next;
        public String data;
    
        public HashNode(String data, HashNode next){
            this.data = data;
            this.next = next;
        }
        public HashNode(){}
    
    }
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
            hash += n*7*nr;
            nr++;
        }
        return hash%length;
    }

    public void insert(String s){
        String output = "";
        int h = hash(s);
        if(nodes[h] ==null){
            nodes[h] = new HashNode(s,null);
        }else{
            output += nodes[h].data + "-->";
            nodes[h] = new HashNode(s,nodes[h]);;
            output += nodes[h].data;
            collisions++;
        }
        if(!output.equals("")){
            System.out.println(output);
        }else{
            System.out.print(output);
        }
    }

    public HashNode find(String s){
        String output = "";
        int h = hash(s);
        HashNode temp = nodes[h];
        output += temp.data;
        while(!temp.data.equals(s)){
            temp = temp.next;
            output += "-->"+temp.data;
        }
        System.out.println(output);
        return temp;
    }

    public void print(){    
        HashNode temp;
        int people = 0;
        for(HashNode n : nodes){
            if(n != null){
                temp = n;
                people++;
                while(temp.next != null) {
                    temp = temp.next;
                    people++;
                }
                  
            }
        }
        System.out.println("nr of collisions "+ collisions);
        System.out.println("Load factor "+(double)(people/length));
        System.out.println("avarage collisions pr person "+((double)collisions/(double)people));

    }

    public static void main(String[] args) {
        HashTabell ht = new HashTabell(86);
        try {
            BufferedReader b = new BufferedReader(new FileReader(new File("/home/hermanoe/projects/Algo and datastruck/task_4/1/name.txt")));
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