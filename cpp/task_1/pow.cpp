#include <stdio.h>
#include <tgmath.h>
#include <iostream>
#include <functional>
#include <time.h>  
 

int seconds = 1;
//hello 
//time complexity 0(n)
//This complexity comes from computing the algorithme, 
//which reduces my 1 every recursion until it hits 0 
//which gives us a time complexity of O(n)
double algo(double x, int n){
    if(n==0) return 1;
    else if(n>0) return x*algo(x,(n-1));
    return 0;
}

//time complexity is O(log(n))
//This time complexity comes from using the mastermethod
//T(n) = aT(n/b)+cn^k 
//where in this case a = 1 because the recursion is called once every round
//b = 2 because n is diveded by 2 every recursion
//the last part of the metode cn^k = 1 because k = 0, which comes from the fact that
//there are no loops for each recursion of the algorithme 
//this gives us T(n/2) which when computing the mastermethod 
// we get that b^k = a because 2^0 = 1 which means that teath == n^k log n or log(n)
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

    printf("\nanswer algo  for 3, 14 %f\n", algo(3,14));
    printf("answer algo2 for 3, 14 %f\n", algo2(3,14));
    printf("answer pow for 3, 14 %f\n", pow(3,14));


    printf("\nalgo time complexity O(n)\n");
    testTime(x,10, algo);
    testTime(x,100, algo);
    testTime(x,1000, algo); 
    testTime(x,100000, algo);

    printf("\nalgo2 time complexity O(log(n))\n");
    testTime(x,10, algo2);
    testTime(x,100, algo2);
    testTime(x,1000, algo2); 
    testTime(x,100000, algo2);

    printf("\ncpp math pow time complexity O(1)\n");
    testTime(x,10, pow);
    testTime(x,100, pow); 
    testTime(x,1000, pow);      
    testTime(x,100000, pow);
    
    //As we can see from running this program algo is alot slower then algo2 
    //when n grows lager and lager. the pow function beats both the algorithmes,
    //that is because it has a time complexity of O(1)
}


