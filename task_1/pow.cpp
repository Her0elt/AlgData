#include <stdio.h>
#include  <tgmath.h>

//complexity 0(n)
double algo(double x, int n){
    if(n==0) return 1;
    else if(n>0) return x*algo(x,(n-1));
    return 0;
}
//complexity is log(n)
double algo2(double x, int n){
    if(n==0) return 1;
    else if(n%2 == 1) return x*algo2(x*x,(n-1)/2);
    else if(n%2 == 0) return algo2(x*x,n/2);
    return 0;
}
//complexity log(n)
double cppPow(double x, int n){
    return pow(x,n);
}
int main() {
    printf("task 1 %f\n", algo(3,3));
    printf("task 2 %f\n", algo2(3,3));
    printf("task 3 %f\n", cppPow(3,3));

}


