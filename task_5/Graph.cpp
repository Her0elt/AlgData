#include <iostream>
#include <list>
#include <fstream>
#include <sstream>
#include <stack>
using namespace std;

class Graph {
    int nodeNum;
    list<int> *nodeArr;

public:

    explicit Graph(int size) {
        nodeNum = size;
        nodeArr = new list<int>[nodeNum];
    }

    void addEdge(int pos, int neighbour) {
        nodeArr[pos].push_back(neighbour);
    }

    void listAll() {
        for(int i = 0; i < nodeNum; i++) {
            cout << "Place " << i << ":";
            for(int j : nodeArr[i]) {
                cout << " --> " << j;
            }
            cout << endl;
        }
    }

    Graph getTranspose() {
        Graph transposedGraph(nodeNum);
        for(int i = 0; i < nodeNum; i++) {
            list<int>::iterator j;
            for(j = nodeArr[i].begin(); j != nodeArr[i].end(); j++) {
                transposedGraph.nodeArr[*j].push_back(i);
            }
        }
        return transposedGraph;
    }

    void DFS(int pos, bool visited[]) {
        visited[pos] = true;
        cout << pos << " ";

        list<int>::iterator i;
        for(i = nodeArr[pos].begin(); i != nodeArr[pos].end(); i++) {
            if(!visited[*i]) {
                DFS(*i, visited);
            }
        }
    }

    void stackFill(int pos, bool visited[], stack<int> &stack) {
        visited[pos] = true;

        list<int>::iterator i;
        for(i = nodeArr[pos].begin(); i != nodeArr[pos].end(); i++) {
            if(!visited[*i]) {
                stackFill(*i, visited, stack);
            }
        }
        stack.push(pos);
    }

    void findStronglyConnected() {
        stack<int> Stack;

        bool *visited = new bool[nodeNum];
        for(int i = 0; i < nodeNum; i++) {
            visited[i] = false;
        }

        for(int i = 0; i < nodeNum; i++) {
            if(!visited[i]) {
                stackFill(i, visited, Stack);
            }
        }

        Graph transposedGraph = getTranspose();

        for(int i = 0; i < nodeNum; i++) {
            visited[i] = false;
        }

        int count = 1;
        while(!Stack.empty()) {
            int pos = Stack.top();
            if(!visited[pos]) {
                cout << "SCC " << count << ": ";
                count++;
                transposedGraph.DFS(pos, visited);
                cout << endl;
            }
            Stack.pop();
        }

    }
};


int main() {

    int size;
    int edges;
    string str;
    ifstream file("L7g6");
        getline(file, str);
    istringstream iss(str);
    iss >> size;
    iss >> edges;
    Graph graph(size);

    int pos;
    int neighbour;
    for(int i = 0; i < edges; i++) {
        getline(file, str);
        istringstream iss2(str);
        iss2 >> pos;
        iss2 >> neighbour;
        graph.addEdge(pos, neighbour);
    }
    graph.findStronglyConnected();
}