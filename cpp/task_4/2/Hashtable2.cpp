#include <chrono>
#include <stdlib.h>
#include <stdio.h>
#include <time.h>

using namespace std;
using namespace chrono;


class HashTable2{
    public:
        int *arr;
        int collisions;
        int length;

    bool checkPrime(int k){
        bool isPrime = true;
        for (int i = 2; i<k/2;i++){
            if((k%i) == 0){
                isPrime = false;
            }
        }
        return isPrime;
    }
    int getLength(int length, double times){
        int num =(int)(length*times);
        bool isPrime = false;
        while(!isPrime){
            isPrime = checkPrime(num);
            num++;
        }
        return isPrime?num-1: -1;
    }

    explicit HashTable2(int length){
        this->length = getLength(length,1.2);
        this-> arr = new int[this->length];
        this->collisions = 0;
        for (int i = 0; i < this->length; i++){
            arr[i] = 0;
        }
        
    }
    
    int hash(int key){
        return key%this->length;
    }
    int hash2(int key){
        return (key%(length-1)) + 1;
    }

    void insert(int key){
        int h = hash(key);
        if(arr[h] == 0){
            arr[h] = key;
        }else{
            int h2 = hash2(key);
            if(arr[h2] == 0){
                arr[h2] = key;
                return;
            }
            int pos = (h2)%this->length;
            while(true){
                if(arr[pos] == 0){
                    arr[pos] = key;
                    break;
                }
                collisions++;
                pos = (pos + h2)%this->length;
            }
        }
        
    }
    int get(int key){
        int h = hash(key);
        if(arr[h] == key){
            return arr[h];
        }else{
            int h2 = hash2(key);
            if(arr[h2] == key){
                return arr[h2]; 
            }
            int pos = (h2)%this->length;
            while(true){
                if(arr[pos] == 0){
                    return arr[pos];
                }
                collisions++;
                pos = (pos + h2)%this->length;
            }
        }
    }
};

int main() {
    int length = 10000000;
    int find = 212121;
    HashTable2 ht(length);
    ht.insert(find);
    double time = 0;
    int *nrs = new int[length];
    int nr;
     timespec start, stop;
    for(int i = 0; i<(length/2)-1;i++ ){
            nr = (int)(rand()%length*10);
            nrs[i] = nr;
            nrs[(length/2)-1-i] = nr;
    }
    for(int i = 0; i<length-1;i++ ){
        clock_gettime(CLOCK_THREAD_CPUTIME_ID, &start);
        ht.insert(nrs[i]);
        clock_gettime(CLOCK_THREAD_CPUTIME_ID, &stop);
        time += stop.tv_nsec - start.tv_nsec ;
    }
        printf("size: %d \n", length);
        printf("time: %f ms \n", time/1000000);
        printf("a = %f \n",(double)length/ht.length);
        printf("collisions: %d \n", ht.collisions);
        printf("%d\n",ht.get(find));
}
