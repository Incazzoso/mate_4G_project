public class CombinatoryCalc {

    // Raggruppamento
    public double raggrupamento(int n1, int n2){
        return (double) n1 * n2;
    }

    // Permutazioni semplici
    public double permutazione(int n){
        double ris = 1;
        for (int i = 2; i <= n; i++) {
            ris *= i;
        }
        return ris;
    }

    // Permutazioni con ripetizione
    public double permutazioniRip(int n, int[] ripetizioni) {
        double tmp = 1;
        for (int r : ripetizioni) {
            tmp *= permutazione(r); // Calcola r!
        }
        return permutazione(n) / tmp;
    }

    // Disposizioni semplici
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

    // Disposizioni con ripetizione
    public double disposizioneRip(int n, int k){
        return Math.pow(n, k);
    }

    // Combinazioni semplici
    public double combinazione(int n, int k){
        if(k > n) {
            return 0;
        }
        return disposizione(n, k) / permutazione(k);
    }

    // Combinazioni con ripetizione
    public double combinazioneRip(int n, int k){
        return permutazione(n + k - 1) / (permutazione(k) * permutazione(n - 1));
    }
}