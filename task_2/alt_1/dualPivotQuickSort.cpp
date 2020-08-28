#include <stdlib.h>
#include <stdio.h>
#include <functional>
#include <chrono>

using namespace std;
using namespace chrono;

long count = 10000000;
  
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

        int lp, rp; 
        rp = partition(arr, low, high, &lp); 
        DualPivotQuickSort(arr, low, lp - 1); 
        DualPivotQuickSort(arr, lp + 1, rp - 1); 
        DualPivotQuickSort(arr, rp + 1, high); 
    } 
} 
  
int partition(int* arr, int low, int high, int* lp) 
{ 
    swap(&arr[low], &arr[(low + (high - low) / 3)]);
	swap(&arr[high], &arr[(high - (high - low) / 3)]);
    
    if (arr[low] > arr[high]) swap(&arr[low], &arr[high]); 
  
    int j = low + 1; 
    int g = high - 1, k = low + 1, p = arr[low], q = arr[high]; 
    while (k <= g) { 
  
        
        if (arr[k] < p) { 
            swap(&arr[k], &arr[j]); 
            j++; 
        } 
  
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
  
    swap(&arr[low], &arr[j]); 
    swap(&arr[high], &arr[g]); 
  
    *lp = j; 
  
    return g; 
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