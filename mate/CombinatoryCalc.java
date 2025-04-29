public class CombinatoryCalc {
    public double raggrupamento(int n1, int n2){
        return n1 * n2 ;
    }
    //permutazioni semplici
    public double permutazione(int n){
        int ris = 1;
        for(; n >= 1; n--)
            ris = ris * n;
        return ris;
    }
    //permutazioni con ripetizione
    public double permutazioniRip(int n, int[] ripetizioni) {
        double tmp = 1;
        for (int r : ripetizioni) {
            tmp *= permutazione(r);  // permutazione calcola n!
        }
        return permutazione(n) / tmp;
    }
    //disposizioni semplici
    public double disposizione(int n, int k) {
        if (k > n) {
            return 0;
        }
        double risultato = 1;
        for (int i = 0; i < k; i++) {
            risultato *= (n - i);
        }
        return risultato;
    }
    

    //disposizioni con ripetizioni
    public double disposizioneRip(int n, int k){
        if(k > n)
            return 0;
        return (int)Math.pow(n, k);
    }

    //combinazioni semplici
    public double combinazione(int n, int k){
        if(k > n)
            return 0;
            return disposizione(n,k) / permutazione(k);     //deve essere float or double, visto che non è detto che dia un integer (come tutto il resto)
    }

    //combinazioni con ripetizione
    public double combinazioneRip(int n, int k){
        if(k > n)
            return 0;
        return permutazione(n + k -1) / (permutazione(k) * permutazione(n - 1));
    }

    /*//data una parola ti conta quante volte una lettera appare per ogni lettera nella parola
    public String letterCounterAll(String word){
        if(word == null)
            return null;
        word = word.toUpperCase();
        String letters = "";
        for(int i = 0; i < word.length(); i++){
            if(letters.indexOf(word.charAt(i)) == -1)
                letters += "["+word.charAt(i)+": "+ letterCounter(word, word.charAt(i))+"] ";
        }
        return letters;
    }

    //conta quante volte 1 lettera appare in una parola (serve a letterCounterAll)
    private int letterCounter(String word, char ch){
        if(word == null)
            return 0;
        int counter = 0;
        for(int i = 0; i < word.length(); i++){
            if(word.charAt(i) == ch)
                counter++;
        }
        return counter;
    }*/
}