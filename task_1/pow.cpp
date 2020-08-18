#include <stdio.h>
#include  <tgmath.h>

//complexity 0(n)
double desiAlgo(double x, int n){
    if(n==0) return 1;
    else if(n>0) return x*desiAlgo(x,(n-1));
    return 0;
}
//complexity is log2(n)
double desiAlgo2(double x, int n){
    if(n==0) return 1;
    else if(n%2 == 1) return x*desiAlgo2(x*x,(n-1)/2);
    else if(n%2 == 0) return desiAlgo2(x*x,n/2);
    return 0;
}
//complexity log2(n)
double cppPow(double x, int n){
    return pow(x,n);
}
int main() {
    printf("task 1 %f\n", desiAlgo(3,3));
    printf("task 2 %f\n", desiAlgo2(3,3));
    printf("task 3 %f\n", cppPow(3,3));

}


