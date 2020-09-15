import java.util.Hashtable;

public class HashTable2 {
    private int arr[];
    private int collisions;
    double A = (Math.sqrt(5.0)-1.0)/2;

    public HashTable2(int length){
        this.arr = new int[(int)Math.pow(2,Math.ceil(Math.log(length)/Math.log(2)))];
        this.collisions = 0;
    }


    public int multiHash(int t){
        double nr = t*A;
        nr -= (int)nr;
        return (int)(Math.abs(nr)*arr.length);
    }
    public int h_2(int t){
        return ((2*Math.abs(t))+1)%(arr.length-1);
    }

    public void insert(int t){
        int h = multiHash(t);
        if(arr[h] == 0){
            arr[h] = t;
        }else{
            for(int i= 1; i<arr.length;i++){
                int h2 = (h_2(t)*i + h)%(arr.length-1);
                if(arr[h2]==0){
                    arr[h2] = t;
                    break;
                }else{
                    collisions++;
                    i++;
                }
            }
        }
    }
    public int get(int t){
        int h = multiHash(t);
        if(arr[h] == t){
            return arr[h];
        }else{
            for(int i = 1; i<arr.length;i++){
                int h2 = (h_2(t)*i + h)%(arr.length-1);
                if(arr[h2]==t){
                    return arr[h2];
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int length = 10000000;
        int find = 144555;
        HashTable2 ht = new HashTable2(length);
        ht.insert(find);
        long start, end;
        long totalT = 0;
        int nr;
        for(int i = 0; i<length-1;i++ ){
            nr = (int)(Math.random()*length*10);
            start = System.nanoTime();
            ht.insert(nr);
            end = System.nanoTime();
            totalT += end-start;
        }
        System.out.println("time: " + totalT/1000000 +"ms");
        System.out.println("a =" + (double)length/ht.arr.length);
        System.out.println("collisions: "+ ht.collisions);
        System.out.println(ht.get(find));
        totalT = 0;
        Hashtable<Integer,Integer> h = new Hashtable<>();
        for(int i = 0; i<length-1;i++ ){
            nr = (int)(Math.random()*length*10);
            start = System.nanoTime();
            h.put(i,nr);
            end = System.nanoTime();
            totalT += end-start;
        }
        System.out.println("Java Table time: " + totalT/1000000 +"ms");

    }

}

