# Eksamen Algdat 2018

### Oppgave 4
#### a) ) Sett tallene 2, 4, 1, 3, 7, 6 inn i en max-heap. Sett dem inn i den rekkefølgen de står, og tegn opp heapen en gang for hvert tall du setter inn.
    2
        4
    2

        4
    2       1

            4
        3       1
    2
            7
        4       1
    2      3 

            7
        4       6
    2      3  1

#### b) Sett de samme tallene inn i et binært søketre. Sett dem inn den rekkefølgen de står, og tegn opp søketreet en gang for hvert tall du setter inn.
    
    2
        2
            4
        
        2
    1       4

        2
    1       4
         3

        2         
    1       4
        3       7

        2
    1       4
         3      7
            6

###Oppgave 5
#### a) Demonstrer Burrows-Wheeler-transformasjonen på ordet «tralala»
tralala'
'tralala
a'tralal
la'trala
ala'tral
lala'tra
alala'tr
ralala't

sortert
alala'tr
ala'tral
a'tralal
lala'tra
la'trala
ralala't
tralala'
'tralala

rllaat'a

#### b) Hvorfor gjør Burrows-Wheeler-transformasjonen det lettere å komprimere data? forklar
gjerne ved hjelp av svaret på forrige deloppgave.
en burrows wheeler transformasjon i seg selv kompirmerer ikke en block med tekst eller noe den bare gjør om en block til repeterende sekvens noe som er lettere og komprimere senere og er også dekomprimerbar så det gjør ikke noe om vi utfører en burrows wheeler transformasjon på en  tekst, det hjelper oss bare når det kommer til komprimeringen

#### c) Beskriv et problem som har både en NP-komplett og en NP-hard variant.
NP-hard problemer er algoritmeproblemer som vi ikke klarer å regne tiden til i polynomisk tid, ett eksempel er ryggsekk problemet for å finne maksimal verdi vi kan ta med oss. Ryggesekproblemet går ut på at vi får en gitt bærekapistet og har udendelig varer med pris og vekt løs for om vi kan ta V. Som sakt når dette problemet spør og hva er den maksimale verdien vi kan ta med oss så blir dette en np-hard problem siden vi ikke klarer å finne noe reel løsning på problemet hvor vi har polynomsik tidkompleksitet. Men hvis vi sier at V ikke må være den maksimale verdien så blir problemet NPC som betyr at vi klarer å lage en løsning som ser ut til å være Polonomisk med vi er ikke sikre, eller det er noen elementer som har usikker komplekitet. Egentlig så er alle NPC/NP-komplett problemer np-hard problemer de npc er bare en subset av NP-hard. 

### Oppgave 6 Analyser de følgende programmene og finn kjøretiden. Bruk Θ om mulig, ellers O og Ω.
#### a)
    public void oppg_a(int n, int m, int [][] tab) {
        for (int i=0; i <= n-1; ++i) {
            for (int j=0; j<i; j++) {
                for (int k=1; k<m; ++k) tab[i][j] += tab[j][k];
            }   
        }
    }

O(n²*m) omega(1)

##### b)
    public void oppg_b(int n, int m, int [] tab) {
        if (n > m) return;
        for (int i=0; i <= n-1; ++i) {
            for (int j=0; j<m; j+=n) {
                tab[i+j*n] = 5;
            }
        }
    }

omega(1) O(n*m/n) = O(m)     

#### c) 
    public void oppg_c(int n, int[] tab, int x) {
        for (int j=0; j < n-1; ++j) tab[j+x]++;
        if (n>0) oppg_c(n/2, tab, x/3);
    }

T(n) = T(n/2) + cn¹

b^k ? a

2¹ > 1 

theta(n)

#### d)
    public void oppg_d(int n, int[] tab, int x) {
        tab[x] += n;
        if (x>0) oppg_d(n/2, tab, x/3);
    }

T(n) = T(n/2) + 1

b^k ? a

2⁰ = 1

theta(log n)

### Oppgave 7 
#### a) Finn og tegn et minimalt spenntre med vekt, eller forklar hvorfor det ikke er mulig.
                e,c,f,a,d,a 7

                e
            c       f
                 a      d
                    b

#### b) Finn korteste vei fra 𝑎 til de andre nodene. Tegn korteste-vei treet.

a:a,b1:a,b,d4:a,b,d,f5:a,b,d,f,e7:a,b,d,f,e,c8

#### c) Går det an å ha en graf som har flere ulike minimale spenntrær? Tegn i så fall et eksempel med to slike trær, eller forklar hvorfor det ikke er mulig.
Det er mulig å lage lere minimale spenntrær i denne grafen

#### d) Finn en topologisk sortering, eller forklar hvorfor det ikke er mulig.

Mange korrekte løsninger, f.eks. acebfgd. Alle korrekte løsninger må ha a før e, e før b, b
før f, c før f og g før d.
Første node blir a, c eller g, siste blir f eller d.

#### e) En rettet graf har K kanter og N noder. Gitt at den har en topologisk sortering, hvor mange sterkt sammenhengende komponenter har den? Gi en kort forklaring på svaret ditt.

Når en graf er topologisk sortert så er det ingen rundt turer noe som betyr at det ikke blir noen sterkt sammenhengede komponenter med mer enn en node, så i dette tilfele blir det N sterk sammenhengende kompontenter. 




