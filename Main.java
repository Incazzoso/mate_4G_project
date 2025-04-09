public class Main {
    public static void main(String[] args) {
        int n = 6;
        int k = 3;
        String word = "torta";
        CombinatoryCalc calc = new CombinatoryCalc();
        System.out.println("\npermutazioni: "+calc.permutazione(n));
        System.out.println("disposizioni: "+calc.disposizione(n, k));
        System.out.println("combinazioni: "+ calc.combinazione(n, k));
        System.out.println("disposizioni con rip: "+calc.disposizioneRip(n, k));
        System.out.println("combinazioni con rip: "+ calc.combinazioneRip(n, k));
        System.out.println();
        System.out.println(calc.letterCounterAll(word));
    }
}