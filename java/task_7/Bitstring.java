class Bitstring {
  int lengde; 
  long biter; 

  Bitstring() {
  }

  Bitstring(int len, long bits) {
    lengde = len;
    biter = bits;
  }

  Bitstring(Bitstring s) {
    lengde = s.lengde;
    biter = s.biter;
  }
  Bitstring(int len, byte b){
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


  static Bitstring concat(Bitstring s1, Bitstring s2) {
    Bitstring ny = new Bitstring();
    ny.lengde = s1.lengde + s2.lengde;
    if (ny.lengde > 64) {
      System.out.println("For lang bitstreng, gÃ¥r ikke!");
      return null;
    }
    ny.biter = s2.biter | (s1.biter << s2.lengde);
    return ny;
  }
  Bitstring plukkut(int antall, int posisjon) {
    if (posisjon < 0 || antall < 0 || posisjon + antall > lengde) {
      System.out.println("Umulig kombinasjon av posisjon og antall");
      return null;
    }
    Bitstring ny = new Bitstring();
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