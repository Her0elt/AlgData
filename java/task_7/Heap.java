public class Heap {
    int len;
    Tre tre[];

    public Heap(Tre tre[]){
        this.tre = tre;
        len = tre.length;
        lagHeap();
    }


    private int venstre(int i){return (i << 1)+1;}
    private int over(int i){return (i-1)>>1;}
    private void bytt(int i,int m){
        Tre temp = tre[i];
        tre[i] = tre[m];
        tre[m] = temp;
    }

    public void fiksHeap(int i){
        int m = venstre(i);
        if(m < len){
            int h = m+1;
            if(h < len && tre[h].rot.count<tre[m].rot.count)m=h;
            if(tre[m].rot.count < tre[i].rot.count){
                bytt(i,m);
                fiksHeap(m);
            }
        }
    }

    public Tre hentMin(){
        Tre min = tre[0];
        tre[0] = tre[--len];
        fiksHeap(0);
        return min;
    }

    private void prioOpp(int i){
        int f;
        while(i>0 && tre[i].rot.count<tre[f=over(i)].rot.count){
            bytt(i,f);
            i = f;
        }
    }

    public void settInn(Tre t){
        int i = len++;
        tre[i] = t;
        prioOpp(i);
    }

    public void lagHeap(){
        int i = len/2;
        while(i-- > 0)fiksHeap(i);
    }
}