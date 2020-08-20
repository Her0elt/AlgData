#include <stdio.h>
#include  <tgmath.h>
#include <ctime>
#include <iostream>

double getUnixTime(void)
{
    struct timespec tv;

    if(clock_gettime(CLOCK_REALTIME, &tv) != 0) return 0;

    //return (tv.tv_sec + (tv.tv_nsec / 1000000000.0));
    return tv.tv_nsec;
}


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
    double start_time = getUnixTime();
    double stop_time, difference;
    printf("task 1 %f\n", algo(3,3));
    stop_time = getUnixTime();
    difference = stop_time - start_time;
    printf("%f nanoseconds used\n", difference);

    start_time = getUnixTime();
    printf("task 1 %f\n", algo(3,3));
    stop_time = getUnixTime();
    difference = stop_time - start_time;
    printf("%f nanoseconds used\n", difference);

    start_time = getUnixTime();
    printf("task 1 %f\n", algo(3,3));
    stop_time = getUnixTime();
    difference = stop_time - start_time;
    printf("%f nanoseconds used\n", difference);

}


