#include <string>
#include <iostream> 
#include <cmath>
#include <iomanip>
#include <vector>
#include <list>
#include <queue>
using namespace std;


typedef struct TreeNodeStruct{
    string element;
    struct TreeNodeStruct* left;
    struct TreeNodeStruct* right;
    struct TreeNodeStruct* parent; 
}TreeNode;

TreeNode* newTreeNode(string e, TreeNode* p, TreeNode* l, TreeNode* r){
    TreeNode* res =(TreeNode*)(malloc(sizeof(TreeNode)));
    res -> element = e;
    res -> left = l;
    res -> right = r;
    res -> parent = p;
    return res; 
}

typedef struct{
    TreeNode*root;
}Tree;

Tree* newTree(){
    Tree* res = (Tree*)(malloc(sizeof(Tree)));
    res -> root = NULL;
    return res;
}   

bool empty(Tree* t){
    return !(t->root);
}
void insert(Tree* t, string e){
    if(t->root == NULL){
        t->root = newTreeNode(e,NULL,NULL,NULL);
        return;
    }
    TreeNode *treeNode = t->root;
    string test;
    TreeNode *parent;
    while(treeNode){
        parent = treeNode;
        test = treeNode->element;
        if(e.compare(test)<=0)treeNode = treeNode->left;
        else treeNode = treeNode->right;
    }
    if(e.compare(test)<=0)parent->left = newTreeNode(e,parent,NULL,NULL);
    else parent->right = newTreeNode(e, parent, NULL,NULL);
}

typedef struct{
    TreeNode* node;
    int depth;
} listItem;
int findHeight(TreeNode *root) {
		return root == NULL ? 0 : 1 + max(findHeight(root->left), findHeight(root->right));
	}
string makeSpace(int length, int level){
        string space = "";
    for(int i = 0 ; i<length; i++){
        space.append("        ");
    }
    
    return space.substr(0,length/level);
}

void printTreeBranch(TreeNode* root){
    if(root== NULL) return;
    queue<TreeNode*> q;
    q.push(root);
    q.push(NULL);
    int length = pow(2,2+findHeight(root));
    int level = 1;
    string output = "";
    while(true){
        TreeNode* curr = q.front();
        q.pop();
        if(curr != NULL){
            string space = makeSpace(length, level);
            output += space.substr(0,space.length()/2-curr->element.length()) + curr->element + space.substr(0,space.length()/2-curr->element.length());
            if(curr->left !=NULL)q.push(curr->left);
            if(curr->right !=NULL)q.push(curr->right);
        }else{
            level++;
            output += "\n";
            if(q.empty()) break;
            q.push(NULL);
           
        }
    }
    cout <<output;
}
void printTree(Tree* t){
     printTreeBranch(t->root);
}

int main(int argc, char *argv[]){
    Tree* tree = newTree();
    for(int i = 1; i<argc;i++){
        insert(tree, argv[i]);
    }
    printTree(tree);
}
