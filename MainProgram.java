import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainProgram {

    //dichiarazioni
    JFrame frame;           //il frame
    JPanel northPanel;      //pannello nord
    JPanel centerPanel;     //pannello centrale
    JPanel southPanel;      //pannello sud
    JLabel questionLabel;   //dove avvengono le domande
    JButton yesButton;      //se alla domanda la risposta è si
    JButton noButton;       //se alla domanda la risposta è no
    JButton exitButton;     //se si vuole uscire dal programma
    


    //costruttore (dove andrà tutto il codice)
    MainProgram(){

        //inizio dei settings per il Frame
        frame = new JFrame("calcolo combinatorio");            //titolo al frame
        frame.setLayout(new BorderLayout());                         //tipo di layout
        frame.setSize(500, 700);                        //dimensione


        //inizzializzazione centerPanel ed i suoi componenti
        centerPanel = new JPanel(new GridLayout(1,1));     //layout a griglia
        questionLabel = new JLabel("");                         //label vuota

        //aggiunta elementi al centerPanel
        centerPanel.add(questionLabel);                              //zona dove verrando mandate le risposte

        //inizzializzazione southPanel ed i suoi componenti
        centerPanel = new JPanel(new GridLayout(1,2));     //layout a griglia
        yesButton = new JButton("SI");
        noButton = new JButton("NO");

        //aggiunta elementi al centerPanel
        southPanel.add(yesButton);                                   //bottone si
        southPanel.add(noButton);                                    //bottone no


        //fine dei settings per il Frame
        frame.setVisible(true);                                    //window visibile
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);         //operazione di chiusura (esce quando lo si chiude)    
    }


    //run del main (usato per evitare la creazione di una classe Mai.java che faceva partire il programma, rendendo il tutto più leggero)
    public static void main(String[] args) {
        new MainProgram();      //inizzializzazionr istantanea (per evitare di istanziare un oggetto in più)
    }
}

