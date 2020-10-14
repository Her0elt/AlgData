//Klasse med en bitstreng fra 1 til 64 bit
class bitstreng {
    int lengde;     //Hvor mange bits det er i denne strengen
    long biter;     //Opptil 64 bit
    bitstreng(){};
    bitstreng(int len, long bits) { lengde=len; biter=bits;}
  
  
  
    //SkjÃ¸ter sammen to bitstrenger, med s1 fÃ¸rst. returnerer resultatet
    static bitstreng konkatenere(bitstreng s1, bitstreng s2) {
      bitstreng ny = new bitstreng();
      ny.lengde = s1.lengde+s2.lengde;
      if (ny.lengde > 64) {
        System.out.println("For lang bitstreng, gÃ¥r ikke!");
        return null;
      }
      ny.biter = s2.biter | (s1.biter << s2.lengde);
      return ny;
    }
    /* Forklaring
       NÃ¥r vi skjÃ¸ter sammen to bitstrenger, blir den nye lengden lik
       summen av lengden pÃ¥ de to opprinnelige strengene. En long kan
       ikke ha mer enn 64 bit, i sÃ¥ fall gir vi opp hele greia.
  
       For at bitene ikke skal overlappe, venstreskifter vi den
       bitstrengen som skal stÃ¥ fÃ¸rst - med like mange posisjoner som
       lengden pÃ¥ den andre bitstrengen. Da overlapper de ikke.
       Deretter slÃ¥r vi sammen med en binÃ¦r eller-operasjon. "|"
    */
  
  
  
    //Plukker ut "antall" bits fra posisjon "pos" og returnerer det som en
    //kortere bitstreng. (Den fÃ¸rste biten har posisjon 0, neste 1 osv.)
    bitstreng plukkut(int antall, int posisjon) {
      //Sjekk om det er mulig fÃ¸rst:
      if (posisjon < 0 || antall < 0 || posisjon+antall > lengde) {
        System.out.println("Umulig kombinasjon av posisjon og antall");
        return null;
      }
      bitstreng ny = new bitstreng();
      ny.lengde = antall;
      long maske = (1L << (lengde-posisjon)) - 1;
      int forskyving = lengde - antall - posisjon;
      ny.biter = (biter & maske) >> forskyving;
      return ny;
    }
    /* Forklaring:
  Vi skal plukke ut "antall" bits fra en lengre bitstreng
  Lengden pÃ¥ resultatet blir altsÃ¥ "antall"
       
  OppnÃ¥r dette ved Ã¥ maskere (nulle ut) uinteressante biter
  som ligger fÃ¸r de vi vil plukke ut. Uinteressante biter
  som kommer etter, forsvinner nÃ¥r vi skyver de interessante
  bitene pÃ¥ plass med et hÃ¸yreskift.
  
  Eksempel:  
  Har 10110011100011 og Ã¸nsker Ã¥ plukke ut 10011 
  Opprinnelig streng har lengden 14, vi vil plukke ut
  5 bits fra posisjon 3 og utover. (FÃ¸rste bit er posisjon 0)  
  
  For Ã¥ maskere vekk bitene 0, 1 og 2, trenger vi en maske
  som har nuller i disse posisjonene og enere i resten.
  De tre kolonene skal vÃ¦re rett under hverandre,
  bruk "courier" eller lignende font med fast bredde
  
  Bitstrengen:                  : 10110011100011  (binÃ¦r, selvsagt)           
  Tallet 1L << (lengde-posisjon): 00100000000000  (binÃ¦r form)
  (1-L << (lengde-posisjon))-1  : 00011111111111  (binÃ¦r form)
  
  masken fÃ¥r vi ved Ã¥ venstreskifte tallet Ã©n med et passende antall
  posisjoner (lengde-posisjon). Deretter trekker vi fra Ã©n.
  
  Maskeringen fjerner de innledende 3 bitene vi ikke trenger:
  10110011100011 bitstreng
  00011111111111 maske
  00010011100011 bitstreng & maske  ("&" er binÃ¦r og-operasjon)
  
  Ved Ã¥ skyve bitmÃ¸nsteret 6 posisjoner til hÃ¸yre, blir vi kvitt 
  uinteressante biter:
  00010011100011 FÃ¸r hÃ¸yreskift
  00000000010011 etter hÃ¸yreskift
  
  Vi har innledende nuller her, (en long har alltid 64 bit),
  men "lengde" blir satt til 5, som forteller at det bare er de
  5 siste bitene som er i bruk. Og der har vi "10011" som Ã¸nsket.
  */
  
  
  
    //Skriver ut en bitstreng, bit for bit 
    String skrivut() {
      String s="";
      for (long testbit = 1L << (lengde-1); testbit != 0; testbit >>= 1) {
        s += ( (biter & testbit) == 0) ? "0" : "1";
      } 
      return s;
    }
    /* Forklaring:
       Start med den fÃ¸rste biten, som er: 1L << (lengde-1)
       (Ã‰n venstreskiftet "lengde-1" ganger. Samme som 2^(lengde-1))
  
       Sjekk om biten pÃ¥ plassen anvist av "testbit" er 1 eller 0, lag tekstsiffer
       
       Neste bit Ã¥ teste finnes ved Ã¥ hÃ¸yreskifte testbiten med Ã©n posisjon
       (Samme operasjon som testbit /= 2, men raskere)
   
       En long har ikke desimaler, sÃ¥ testbit blir 0 til slutt. (1 >> 1)==0
       Da avslutter lÃ¸kka.
    */
  }