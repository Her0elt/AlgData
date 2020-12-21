#include <stdlib.h>
#include <stdio.h>
#include <functional>
#include <time.h>  
#include <chrono>

int count = 10000000;

void shellSort(int n, int t[], double div){
    int s = n/2;
    while(s>0){
        for(int i = s; i<n; i++){
            int j = i, m = t[i];
            while(j>=s && m< t[j-s]){
                t[j]=t[j-s];
                j-=s;
            }
            t[j]=m;
        }
         if (s > 1 && s < div) s = 1; else s = (int)(s / div);
    }
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

void testTime(int t[],int n,double div, std::function<void (int, int [],int)> func){
    auto t1 = std::chrono::high_resolution_clock::now();
    func(n,t,div);
    auto t2 = std::chrono::high_resolution_clock::now();
    double nano_sec = std::chrono::duration_cast<std::chrono::milliseconds>(t2-t1).count();
    printf("\ntime for n = %f\n",div);
    printf("%f milliseconds\n",nano_sec);    
}

int main(){ 
    int ran[count]; 
    int dup[count];
    int sort[count];
    for(int i = 0; i<count; i++){
        ran[i] = rand()%100+1;
        sort[i] = i;
        if(i%2 == 0) dup[i] = rand()%100 +1;
        else dup[i] = count/2;
    }
    testTime(ran, count-1,2.8, shellSort);
    //after testing looks like 2 and 2.2 is the best u can get. 
    //for div under 2 time used is a lot higher then if its abov 2 and under 3
    return 0;
}

