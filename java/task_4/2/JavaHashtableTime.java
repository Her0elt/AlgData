import java.util.HashMap;

public class JavaHashtableTime {
    public static void main(String[] args) {
        int length = 10000000;
        long start, end;
        long totalT = 0;
        int nrs[] = new int[length];
        int nr;
        for(int i = 0; i<(length/2)-1;i++ ){
            nr = (int)(Math.random()*length*10);
            nrs[i] = nr;
            nrs[(length/2)-1-i] = nr;
        }
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
