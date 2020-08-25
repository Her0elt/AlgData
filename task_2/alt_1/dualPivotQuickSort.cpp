// C++ program to implement dual pivot QuickSort 
#include <stdlib.h>
#include <stdio.h>
#include <functional>
#include <time.h>  
#include <chrono>
#include <iostream>

using namespace std; 

int seconds = 1;
int count = 10000;
  
int partition(int* arr, int low, int high, int* lp); 
  
void swap(int* a, int* b) 
{ 
    int temp = *a; 
    *a = *b; 
    *b = temp; 
} 
  
void DualPivotQuickSort(int* arr, int low, int high) 
{ 
    if (low < high) { 
        // lp means left pivot, and rp means right pivot. 
        int lp, rp; 
        rp = partition(arr, low, high, &lp); 
        DualPivotQuickSort(arr, low, lp - 1); 
        DualPivotQuickSort(arr, lp + 1, rp - 1); 
        DualPivotQuickSort(arr, rp + 1, high); 
    } 
} 
  
int partition(int* arr, int low, int high, int* lp) 
{ 
    // low = low+(high-low)/3;
    // high = high-(high+low)/3;
    if (arr[low] > arr[high]) 
        swap(&arr[low], &arr[high]); 
  
    // p is the left pivot, and q is the right pivot. 
    int j = low + 1; 
    int g = high - 1, k = low + 1, p = arr[low], q = arr[high]; 
    while (k <= g) { 
  
        // if elements are less than the left pivot 
        if (arr[k] < p) { 
            swap(&arr[k], &arr[j]); 
            j++; 
        } 
  
        // if elements are greater than or equal 
        // to the right pivot 
        else if (arr[k] >= q) { 
            while (arr[g] > q && k < g) 
                g--; 
            swap(&arr[k], &arr[g]); 
            g--; 
            if (arr[k] < p) { 
                swap(&arr[k], &arr[j]); 
                j++; 
            } 
        } 
        k++; 
    } 
    j--; 
    g++; 
  
    // bring pivots to their appropriate positions. 
    swap(&arr[low], &arr[j]); 
    swap(&arr[high], &arr[g]); 
  
    // returning the indices of the pivots. 
    *lp = j; // because we cannot return two elements 
    // from a function. 
  
    return g; 
} 

void testTime(int t[],int v, int h, std::function<void (int [],int,int)> func){
    auto t1 = std::chrono::high_resolution_clock::now();
    func(t,v,h);
    auto t2 = std::chrono::high_resolution_clock::now();
    double nano_sec = std::chrono::duration_cast<std::chrono::nanoseconds>(t2-t1).count();
    printf("%f nanoseconds\n",nano_sec);    
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
// Driver code 
int main() 
{ 
    int ran[count]; 
    int dup[count];
    int sort[count];
    for(int i = 0; i<count; i++){
        ran[i] = rand()%100+1;
        sort[i] = i;
        if(i%2 == 0) dup[i] = rand()%100 +1;
        else dup[i] = count/2;
    }
    printf("random numbers array\n");
    printf("sum before sort %ld \n", checkSum(ran));
    testTime(ran,0,count-1, DualPivotQuickSort);
    printf("sum after sort %ld \n", checkSum(ran));
    printf(checkSorted(ran)? "is sorted\n":"is not sorted\n");
    
    printf("\nduplicate numbers array\n");
    printf("sum before sort %ld\n", checkSum(dup));
    testTime(dup,0,count-1, DualPivotQuickSort);
    printf("sum after sort %ld\n", checkSum(dup));
    printf(checkSorted(dup)? "is sorted\n":"is not sorted\n");

    printf("\nsorted numbers numbers array\n");
    printf("sum before sort %ld\n", checkSum(sort));
    testTime(sort,0,(count-1), DualPivotQuickSort);
    printf("sum after sort %ld\n", checkSum(sort));
    printf(checkSorted(sort)? "is sorted\n":"is not sorted\n");
} 