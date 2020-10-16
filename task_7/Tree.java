public class Tree {
    Node root;

    public Tree(Node r){
        this.root = r;
    }
    public Tree(){}

    public Tree insert(Tree n){
        int sum = this.root.count + n.root.count;
        Tree t = new Tree(new Node(sum));
        if(this.root.count > n.root.count){
            t.root.left = this.root;
            t.root.right = n.root;
        }else{
            t.root.right = this.root;
            t.root.left = n.root;
        }
        return t;
    }
    public void insert(Node n){
        if(this.root == null) this.root = n;
        Node treeNode = this.root;
        int test = 0;
        Node parent = null;
        while(treeNode != null){
            parent = treeNode;
            test = treeNode.count;
            if(test > n.count) treeNode = treeNode.left;
            else treeNode = treeNode.right;
        }
        if(test > n.count)parent.left = n;
        else parent.right = n;

    }
    
}
