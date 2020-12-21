import java.util.HashMap;

public class HashTable2 {
    private int arr[];
    private int collisions;
    final int PRIME = 7 ;
    double A = (Math.sqrt(5.0)-1.0)/2;

    boolean checkPrime(int k){
        boolean isPrime = true;
        for (int i = 2; i<k/2;i++){
            if((k%i) == 0){
                isPrime = false;
            }
        }
        return isPrime;
    }
    int getLength(int length, double times){
        int num =(int)(length*times);
        boolean isPrime =false;
        while(!isPrime){
            isPrime = checkPrime(num);
            num++;
        }
        return isPrime?num-1: -1;
    }
    public HashTable2(int length){
        //this.arr = new int[(int)Math.pow(2,Math.ceil(Math.log(length)/Math.log(2)))];
        this.arr = new int[getLength(length,1.2)];
        this.collisions = 0;
    }

    public int multiHash(int t){
        double nr = t*A;
        nr -= (int)nr;
        return (int)(Math.abs(nr)*arr.length);
    }
    public int divHash(int t){
        return t%(arr.length);
    }
    public int h_2(int t){
        return ((2*Math.abs(t))+1)%(arr.length);
    }
    public  int h_2_p(int t){
        return PRIME-(t%PRIME);//(t%(PRIME-1))+1;//PRIME-(t%PRIME);
    }
    public int probe(int index, int index2, int i){
       return (index + index2*i)%(arr.length);
    }

    public void insert(int t){
        int h = divHash(t);
        if(arr[h] == 0){
            arr[h] = t;
        }else{
            int h2 = h_2_p(t);
            int n = probe(h,h2,1);
            while(true){
                n = (n+h2)%arr.length;
                if(arr[n]==0){
                    arr[n] = t;
                    break;
                }
                collisions++;              
            }
        }
    }
    public int get(int t){
        int h = divHash(t);
        if(arr[h] == t){
            return arr[h];
        }else{
            int h2 = h_2_p(t);
            int n = probe(h,h2,1);
            while(true){
                n = (n+h2)%arr.length;
                if(arr[n]==t){
                    return arr[n];
                }
            }
        }
    }

    public static void main(String[] args) {
        int length = 10000000;
        int find = 212121;
        HashTable2 ht = new HashTable2(length);
        ht.insert(find);
        long start, end;
        long totalT = 0;
        int nrs[] = new int[length];
        int nr;
        for(int i = 0; i<(length/2)-1;i++ ){
            nr = (int)(Math.random()*length*10);
            nrs[i] = nr;
            nrs[(length/2)-1-i] = nr;
        }
        for(int i = 0; i<length-1;i++ ){
            start = System.nanoTime();
            ht.insert(nrs[i]);
            end = System.nanoTime();
            totalT += end-start;
        }
        System.out.println("size: " + length);
        System.out.println("time: " + totalT/1000000 +"ms");
        System.out.println("a =" + (double)length/ht.arr.length);
        System.out.println("collisions: "+ ht.collisions);
        System.out.println(ht.get(find));
        totalT = 0;
        HashMap<Integer,Integer> h = new HashMap<>();
        for(int i = 0; i<length-1;i++ ){
            start = System.nanoTime();
            h.put(nrs[i],nrs[i]);
            end = System.nanoTime();
            totalT += end-start;
        }
        System.out.println("Java Map time: " + totalT/1000000 +"ms");

    }

}

