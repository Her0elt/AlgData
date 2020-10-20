import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

class bitstreng {
  int lengde; 
  long biter; 

  bitstreng() {
  }

  bitstreng(int len, long bits) {
    lengde = len;
    biter = bits;
  }

  bitstreng(bitstreng s) {
    lengde = s.lengde;
    biter = s.biter;
  }
  bitstreng(int len, byte b){
    this.lengde = len;
    this.biter = convertByte(b, len);
  }
  public long convertByte(byte b, int length){
    long temp = 0;
    for(long i = 1<<length-1; i != 0; i >>= 1){
        if((b & i) == 0){
            temp = (temp << 1);
        }
        else temp = ((temp << 1) | 1);
    }
    return temp;
}


  static bitstreng konkatenere(bitstreng s1, bitstreng s2) {
    bitstreng ny = new bitstreng();
    ny.lengde = s1.lengde + s2.lengde;
    if (ny.lengde > 64) {
      System.out.println("For lang bitstreng, gÃ¥r ikke!");
      return null;
    }
    ny.biter = s2.biter | (s1.biter << s2.lengde);
    return ny;
  }
  bitstreng plukkut(int antall, int posisjon) {
    if (posisjon < 0 || antall < 0 || posisjon + antall > lengde) {
      System.out.println("Umulig kombinasjon av posisjon og antall");
      return null;
    }
    bitstreng ny = new bitstreng();
    ny.lengde = antall;
    long maske = (1L << (lengde - posisjon)) - 1;
    int forskyving = lengde - antall - posisjon;
    ny.biter = (biter & maske) >> forskyving;
    return ny;
  }

  public void addZero() {
    this.biter = (this.biter << 1);
    this.lengde++;
  }

  public void addOne() {
    this.biter = ((this.biter << 1) | 1);
    this.lengde++;
  }

  public void remove() {
    this.biter = (biter >> 1);
    this.lengde--;
  }
  
  String skrivut() {
    String s = "";
    for (long testbit = 1L << (lengde - 1); testbit != 0; testbit >>= 1) {
      s += ((biter & testbit) == 0) ? "0" : "1";
    }
    return s;
  }
  
}

class ByteWriter {
   boolean isLast = false;
   bitstreng last;
   DataOutputStream os;
   bitstreng bitstring;

   ByteWriter() throws FileNotFoundException {
    os = new DataOutputStream(new FileOutputStream(new File("file")));
  }

  public  void writeByte(bitstreng s) throws IOException {
      bitstreng b = new bitstreng(s);
      if(isLast){
        b = bitstreng.konkatenere(last, b);
      }
      if(s.lengde <8){
        isLast = true;
        last = b;
        return;
      }

      int amount = 8;
      int div = b.lengde/amount;
      int rest = b.lengde%amount;
      byte[] t = new byte[div];
      for (int i = 0; i < div; i++) {
        t[i] = (byte)(b.biter>> (rest+div-1-i)*amount);
        System.out.println(t[i]);
      }
      os.write(t);
      if(rest != 0){
        isLast = true;
        int y = (0b11111111 >> (8-rest));
        long c = (byte) b.biter & y;
        last = new bitstreng(rest, c);
      }else{
        isLast = false;
        last = null;
      }

    }
    // static void close() throws IOException {
    //   os.close();
    // }
  }