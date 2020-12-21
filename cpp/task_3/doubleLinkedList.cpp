#include <cmath>
#include <string>
#include <iostream>
#include <stdio.h>
#include <iomanip>

using namespace std;

typedef struct NodeStruct{
  NodeStruct* next;
  NodeStruct* last;
  int element;
}Node;

Node* newNode(int e, Node* n, Node* l){
  Node* res = (Node*)(malloc(sizeof(Node)));
  res -> element = e;
  res -> next = n;
  res -> last = l;
  return res;
}
typedef struct{
  Node* head;
  Node* tail;
  int amount;
}DoubleLinked;

void insertFirst(DoubleLinked* l, int e){
  Node* newN = newNode(e,l->head,NULL);
  l -> head = newN;
  if(!l->tail)l->tail= newN;
  else newN-> next->last = newN;
  l->amount++;
}

void insertLast(DoubleLinked* l, int e){
  Node * newN = newNode(e, NULL, l->tail);
  if(l->tail)l->tail -> next = newN;
  else l->head = newN;
  l->tail = newN;
  l->amount++;
}

Node* remove(DoubleLinked* l, Node* n){
  if(n->last) n->last->next = n-> next;
  else l->head = n->next;
  if(n->next) n -> next -> last = n -> last;
  else l-> tail = n -> last;
  n ->next = NULL;
  n -> last = NULL;
  l -> amount--;
  return n;
} 

 string makeString(DoubleLinked* l){
  string s = "";
  Node *node = l->head;
  for(int i = 0; i!=l->amount;i++){
    s += to_string(node->element);
    node = node -> next;
  }
  return s;
}
void calculate(DoubleLinked* l1, DoubleLinked* l2, string op){
  DoubleLinked *sum = (DoubleLinked*)malloc(sizeof(DoubleLinked));
  Node *node1 = l1->tail;
  Node *node2 = l2->tail;
  int extra = 0;
  for(int i = 0; i != l1->amount; i++){
    int first = (node1)? node1 ->element: 0;
    int last = (node2)? node2 ->element: 0; 
    if (op == "+") {
			int value = first + last + extra;
			if (value < 10) {
				insertFirst(sum, value);
				extra = 0;
			} else {
				insertFirst(sum, value - 10);
				extra = 1;
			}
		} else {
			int value = first + extra - last;
			if (value < 0) {
				insertFirst(sum, 10 + value);
				extra = -1;
			} else {
				insertFirst(sum, value);
				extra = 0;
			}
		}
    node1 = node1 -> last;
    node2 = (node2)? node2 ->last: nullptr;
	}
  string n1 = makeString(l1).c_str();
  string n2 = makeString(l2).c_str();
  string n3 =  makeString(sum).c_str();
  cout<<" "<<n1<<"\n";
  cout<<op;
  cout<<setw(n1.length())<<right<<n2<<"\n";
  cout<<"="<< n3 <<"\n";
}




int main(int argc, char *argv[]){
  
  DoubleLinked *l1 = (DoubleLinked*)malloc(sizeof(DoubleLinked));
  DoubleLinked *l2 = (DoubleLinked*)malloc(sizeof(DoubleLinked));
  string op = argv[2];
  string input1 = argv[1];
  string input2 = argv[3];

  for(int i = 0; i<input1.length();i++) {
      int num = input1[i]-'0';
      insertLast(l1, num);
  }
  for(int y = 0; y<input2.length();y++){
    int num = input2[y]-'0';
    insertLast(l2, num);
  }

  calculate(l1,l2, op);

}
