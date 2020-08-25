#include <stdio.h>
#include <tgmath.h>
#include <iostream>
#include <functional>
#include <time.h>  
 

int seconds = 1;

//complexity 0(n)
double algo(double x, int n){
    if(n==0) return 1;
    else if(n>0) return x*algo(x,(n-1));
    return 0;
}
//complexity is O(log2(n))
double algo2(double x, int n){
    if(n==0) return 1;
    else if(n%2 == 1) return x*algo2(x*x,(n-1)/2);
    else if(n%2 == 0) return algo2(x*x,n/2);
    return 0;
}

void testTime(double x, int n, std::function<double (double,int)> func){
    time_t start, end;
    double count = 0;
    start = time(NULL);
    while(difftime(time(NULL), start) < seconds) {
        func(x, n);
        count++;
    }
    double nano_sec = 1000000000*(seconds/count);
    double count_sec = count/seconds;
    printf("n = %d %f times per second and %f nanoseconds pr time\n",n, count_sec,nano_sec);
   
   
    
}
int main() {
    int x = 2;
    printf("answer algo  for 2, 10 %f\n", algo(2,10));
    printf("answer algo2 for 2, 10 %f\n", algo2(2,10));
    printf("answer pow for 2, 10 %f\n", pow(2,10));


    printf("\nalgo time complexity O(n)\n");
    testTime(x,10, algo);
    testTime(x,100, algo);
    testTime(x,1000, algo); 
    testTime(x,100000, algo);

    printf("\nalgo2 time complexity O(log2(n))\n");
    testTime(x,10, algo2);
    testTime(x,100, algo2);
    testTime(x,1000, algo2); 
    testTime(x,100000, algo2);

    printf("\ncpp math pow time complexity O(1)\n");
    testTime(x,10, pow);
    testTime(x,100, pow); 
    testTime(x,1000, pow);      
    testTime(x,100000, pow);

}


