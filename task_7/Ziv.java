public class Ziv {
    byte[] bytes;
    byte[] buffer = new byte[1024*8];
    int position = 0;

    public Ziv(byte[] bytes){
        this.bytes = bytes;
    }


    public void compress(){
        int last = 0;
        for(int i = 0; i<bytes.length; i++){
            int posBuff = checkInBuffer(bytes[i], i);
            if(posBuff == -1){
                buffer[position++] = bytes[i];
            }
            else{
                int max = buildWord(posBuff, i);
                int maxIndex = posBuff;
                while(posBuff != -1){
                    posBuff = checkInBuffer(bytes[i], posBuff-1);
                    if(posBuff == -1)break;
                    if(buildWord(posBuff, i) > max){
                        max = buildWord(posBuff, i);
                        maxIndex = posBuff;
                    }
                }
                if(max > 8){
                    String temp = "-"+(i-maxIndex)+" "+max;
                    
                    for(int j = last; j<position;j++){
                        System.out.print((char)buffer[j]);
                        
                    }
                    System.out.print(temp);
                    for(int j = 0; j<max; j++){
                        buffer[position++] = bytes[i++];
                    }
                    last = i;
                    i--;
                }
                else buffer[position++] = bytes[i];
            }
        }
        for(int i = last; i<position; i++){
            System.out.print((char)bytes[i]);
        }
    }

    private int checkInBuffer(byte b, int pos){
        for(int i = pos; i>=0; i--){
            if(buffer[i] == b) return i;
        }
        return -1;
    }

    private int buildWord(int posBuff, int posByte){
        byte byte1 = bytes[posByte];
        byte buff1 = buffer[posBuff];
        int count = 0;
        while(buff1 == byte1 && posByte != bytes.length-1){
            count++;
            byte1 = bytes[++posByte];
            buff1 = buffer[++posBuff];
            if(buff1 == (byte)'-'){
                int i = posBuff;    
                int nr = 0;
                while(buffer[++i] != (byte)' ') nr += (char)buffer[i];
                i = nr;
                posBuff -= i;
                buff1 = buffer[posBuff];
            }
        }
        return count;
    }
}
