def desiAlgo(x,n):
    if(n == 0): return 1
    else: return x*desiAlgo(x,n-1)

def desiAlgo2(x,n):
    if(n==0): return 1
    elif(n%2 == 1): return x*desiAlgo2(x*x,(n-1)/2)
    elif(n%2 == 0): return desiAlgo2(x*x,(n)/2)
    return 0

def pythonPow(x,n):
    return pow(x,n)

print("task 1 ", desiAlgo(3,3))
print("task 2 ", desiAlgo(3,3))
print("task 3 ", pythonPow(3,3))
