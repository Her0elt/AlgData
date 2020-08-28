#include <stdlib.h>
#include <stdio.h>
#include <functional>
#include <chrono>

using namespace std;
using namespace chrono;

void swap(int *i, int *j);
int median3Sort(int t[], int v, int h);

int count = 10000000;

int split(int t[], int v, int h){
    int iv, ih;
    int m = median3Sort(t,v,h);
    int dv = t[m];
    swap(&t[m], &t[h-1]);
    for(iv = v, ih = h-1;;){
        while(t[++iv]<dv);
        while(t[--ih]>dv);
        if(iv>=ih)break;
        swap(&t[iv], &t[ih]);
    }
    swap(&t[iv], &t[h-1]);
    return iv;
}
int median3Sort(int t[], int v, int h){
    int m = (v+h)/2;
    if(t[v]>t[m])swap(&t[v], &t[m]);
    if(t[m]>t[h]){
        swap(&t[m], &t[h]);
    }
    if(t[v]>t[m]) swap(&t[v], &t[m]);
    return m;
}

void swap(int *i, int *j){
    int k = *j;
    *j = *i;
    *i = k;
}
void quicksort(int t[], int v, int h){
    if(h-v>2){
        int delpos = split(t,v,h);
        quicksort(t,v,delpos-1);
        quicksort(t,delpos+1,h);
    }else{
        median3Sort(t,v,h);
    }
}
void testTime(int t[],int v, int h, std::function<void (int [],int,int)> func){
    auto t1 = high_resolution_clock::now();
    func(t,v,h);
    auto t2 = high_resolution_clock::now();
    double milli_sec = duration_cast<milliseconds>(t2-t1).count();
    printf("%f milliseconds\n",milli_sec);    
}

long checkSum(int t[]){
    long int sum = 0;
    for(int i = 0; i<count;i++){
        sum += t[i];
    }
    return sum;
}
bool checkSorted(int t[]){
    for(int i = 0; i<count-1;i++){
        if(!(t[i+1]>=t[i])) return false;
    }
    return true;
}

int main(){
    int ran[count]; 
    int dup[count];
    int sort[count];
    for(int i = 0; i<count-1; i++){
        ran[i] = rand()%100+1;
        sort[i] = i;
        if(i%2 == 0) dup[i] = rand()%100 +1;
        else dup[i] = count/2;
    }
    printf("random numbers array\n");
    printf("sum before sort %ld \n", checkSum(ran));
    testTime(ran,0,count-1, quicksort);
    printf("sum after sort %ld \n", checkSum(ran));
    printf(checkSorted(ran)? "is sorted\n":"is not sorted\n");
    
    printf("\nduplicate numbers array\n");
    printf("sum before sort %ld\n", checkSum(dup));
    testTime(dup,0,count-1, quicksort);
    printf("sum after sort %ld\n", checkSum(dup));
    printf(checkSorted(dup)? "is sorted\n":"is not sorted\n");

    printf("\nsorted numbers numbers array\n");
    printf("sum before sort %ld\n", checkSum(sort));
    testTime(sort,0,(count-1), quicksort);
    printf("sum after sort %ld\n", checkSum(sort));
    printf(checkSorted(sort)? "is sorted\n":"is not sorted\n");

    
}
   
