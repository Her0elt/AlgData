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

double testTime(double x, int n, std::function<double (double,int)> func){
    time_t start, end;
    int count = 0;
    start = time(NULL);
    while(difftime(time(NULL), start) < seconds) {
        func(x, n);
        count++;
    }
    return count/seconds;
    
}
int main() {

    printf("answer algo  for 2, 10 %f\n", algo(2,10));
    printf("answer algo2 for 2, 10 %f\n", algo2(2,10));
    printf("answer pow for 2, 10 %f\n", pow(2,10));

    printf("\ntest 1 algo %f times per second\n", testTime(3,3, algo));
    printf("test 2 algo %f times per second\n", testTime(2,10, algo));
    printf("test 3 algo %f times per second\n", testTime(3,14, algo));

     printf("\ntest 1 algo2 %f times per second\n", testTime(3,3, algo2));
    printf("test 2 algo2 %f times per second\n", testTime(2,10, algo2));
    printf("test 3 algo2 %f times per second\n", testTime(3,14, algo2));

     printf("\ntest 1 pow %f times per second\n", testTime(3,3, pow));
    printf("test 2 pow %f times per second\n", testTime(2,10, pow));
    printf("test 3 pow %f times per second\n", testTime(3,14, pow));

}


