import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
//((x << 1) | 1) for left
//((x << 1)) for right
//Long.toBinaryString(x) if length isnt 8 do left until its 8
//
public class Hoffman {
    public static void compress(String path) throws IOException{
        File file = new File(path);
        byte [] bytes = Files.readAllBytes(file.toPath());
        byte [] count = new byte[256];
        for (int i = 0; i< bytes.length; i++)count[bytes[i]]++;
        Tree tree = new Tree();
        PriorityQueue<Tree> pq = new PriorityQueue<Tree>((a,b)->a.root.count - b.root.count);
        pq.addAll(makeTreeList(count));
        while(!pq.isEmpty()){
              Tree t = pq.poll();
              Tree n = pq.poll();
              tree = t.insert(n);
              pq.add(tree);
            }
        System.out.println(tree.root.letter);
        
    } 
    public static ArrayList<Tree> makeTreeList(byte[] count){
        ArrayList<Tree> treeList = new ArrayList<>();
        for(int i = 0; i<count.length; i++){
           treeList.add(new Tree(new Node(count[i], (char)i)));
        }
        return treeList;
    }
}
