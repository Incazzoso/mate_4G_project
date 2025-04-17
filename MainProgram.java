import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainProgram {

    //dichiarazioni
    JFrame frame;           //il frame
    JPanel northPanel;       //pannello nord
    JPanel centerPanel;     //pannello centrale
    JPanel southPanel;      //pannello sud
    JLabel questionLabel;   //dove avvengono le domande
    


    //costruttore (dove andrà tutto il codice)
    MainProgram(){
        //inizio dei settings per il Frame
        frame = new JFrame("calcolo combinatorio");            //titolo al frame
        frame.setLayout(new BorderLayout());                         //tipo di layout
        frame.setSize(500, 700);                        //dimensione


        //inizzializzazione centerPanel
        centerPanel = new JPanel(new GridLayout());


        //fine dei settings per il Frame
        frame.setVisible(true);                                    //window visibile
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);         //operazione di chiusura (esce quando lo si chiude)    
    }


    //run del main (usato per evitare la creazione di una classe Mai.java che faceva partire il programma, rendendo il tutto più leggero)
    public static void main(String[] args) {
        new MainProgram();      //inizzializzazionr istantanea (per evitare di istanziare un oggetto in più)
    }
}

