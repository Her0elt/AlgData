#include <math.h>
#include <string>
#include <iostream>
#include <fstream>
#include <vector>

using namespace std;

class HashNode{
    public:
        HashNode* next;
        string data;

        HashNode(string data, HashNode* next){
            this->data = data;
            this->next = next;
        }
        HashNode(){

        }
        
};
// typedef struct HashNodeStruct{
//     HashNodeStruct* next;
//     string data;
// }HashNode;

// HashNode* newHashNode(string s){
//     printf("node\n");
//     HashNode* node;
//     printf("data\n");
//     node-> data = s;
//     printf("string\n");
//     return node;
// }

class HashTabell{
    private:
        HashNode* nodes[86];
        int length;
        int collisions;
    
    public:
        HashTabell(int length){
            this->length = length/2;
            collisions = 0;
            for (int i = 0; i < this->length; i++){
                nodes[i] = NULL;
            }
            
        }

        int hash(string s){
            int nr = 0;
            int hash = 1; 
            for(int i = 0; i<s.length();i++){
                hash += s[i]*nr;
                nr++;
            } 
            return hash%length;
        }

        void insert(string s){
            string output;
            int h = hash(s);
            if(nodes[h] == NULL){
                nodes[h] = new HashNode(s,NULL);
            }else{
                output += nodes[h]->data + "-->";
                nodes[h] = new HashNode(s,nodes[h]);;
                output += nodes[h]->data;
                collisions++;
                
            }
            if(output != ""){
                printf("%s",output.c_str());
                printf("\n");
            }else{
                printf("%s",output.c_str());
            }
        }

        HashNode* find(string s){
            int h = hash(s);
            HashNode* temp = nodes[h];
            while(s.compare(temp -> data) !=0){
                temp = temp->next;
            }
            return temp;
        }

        void print(){
            HashNode* temp = new HashNode();
            int nr_people = 0;
            for(int i = 0; i<length; i++){
                if(nodes[i] != NULL){
                    temp =  nodes[i];
                    while(temp != NULL){
                        temp = temp->next;
                        nr_people++;

                    }
                }
            }
            printf("nr of collisions %d \n", collisions);
            printf("Load factor %f\n", (double)(nr_people/length));
            printf("avarage collisions pr person %f", ((double)collisions/(double)nr_people));

        }
};
int main(){
    string n[86]; //= (vector<string>*)malloc(sizeof(vector<string>));
    string text = "";
    int i =0;
    fstream readFile("name.txt");
    while (getline (readFile, text)) {
        n[i] = text;
        i++;
    }
    HashTabell ht(86);
    for (int j = 0; j<i;j++){
        ht.insert(n[j]);
    }
    readFile.close();
    ht.print();
}