import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class MainProgram extends JFrame {
    // Domande per determinare il tipo di calcolo combinatorio
    String[] domande = {"n, k, h sono indistinguibili?", "L'ordine è importante?", "n = k?", "Gli elementi sono tutti diversi?"};

    int step = 0;
    String formula = "";

    CombinatoryCalc calc = new CombinatoryCalc();

    JLabel domandaLabel = new JLabel("", SwingConstants.CENTER);
    JButton siButton = new JButton("Sì");
    JButton noButton = new JButton("No");

    public MainProgram() {

        super("Calcolo Combinatorio");
        setSize(400, 200);
        setLayout(new BorderLayout());

        domandaLabel.setText(domande[step]);
        add(domandaLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(siButton);
        buttonPanel.add(noButton);
        add(buttonPanel, BorderLayout.SOUTH);

        //due pulsanti si e no
        siButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gestisciRisposta(true);
            }
        });
        noButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gestisciRisposta(false);
            }
        });


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void gestisciRisposta(boolean risposta) {
        switch (step) {
            case 0:
                if (risposta) {
                    formula = "raggruppamento";
                    chiediNKH();
                } else {
                    step++;
                    domandaLabel.setText(domande[step]);
                }
                break;
            case 1:
                if (risposta) {
                    step++;
                    domandaLabel.setText(domande[step]);
                } else {
                    step = 3;
                    domandaLabel.setText(domande[step]);
                }
                break;
            case 2: //n == k?
                if (risposta) {
                    formula = "permutazione";
                    chiediTuttiDiversi();
                } else {
                    step = 3;
                    domandaLabel.setText(domande[step]);
                }
                break;
            case 3: //elementi tutti diversi?
                if (risposta) {
                    formula = "disposizione";
                    chiediNK();
                } else {
                    formula = "disposizioneRip";
                    chiediNKRipetizioni(); //Disposizione con ripetizioni
                }
                break;
        }
    }

    private void chiediNKH() {
        try {
            int n = chiediIntero("Inserisci n:");
            int k = chiediIntero("Inserisci k:");
            int h = chiediIntero("Inserisci h:");
            double risultato = calc.raggrupamento(n, k, h);
            mostraRisultato(risultato);
        } catch (Exception e) {
            mostraErrore();
        }
    }

    private void chiediNK() {
        try {
            int n = chiediIntero("Inserisci n:");
            int k = chiediIntero("Inserisci k:");
            double risultato = formula.equals("combinazione") ? calc.combinazione(n, k) : calc.disposizione(n, k);
            mostraRisultato(risultato);
        } catch (Exception e) {
            mostraErrore();
        }
    }

    private void chiediTuttiDiversi() {
        try {
            int risposta = chiediIntero("Gli elementi sono tutti diversi? (1 = Sì, 0 = No)");
            if (risposta == 1) {
                chiediNK(); //Disposizione senza ripetizioni
            } else {
                chiediNKRipetizioni(); //Disposizione con ripetizioni
            }
        } catch (Exception e) {
            mostraErrore();
        }
    }

    private void chiediNKRipetizioni() {
        try {
            int n = chiediIntero("Inserisci n:"); //Numero di elementi
            int k = chiediIntero("Inserisci k:"); //Numero di posti da occupare
            double risultato = calc.disposizioneRip(n, k);
            mostraRisultato(risultato);
        } catch (Exception e) {
            mostraErrore();
        }
    }
    

    private void mostraRisultato(double risultato) {
        JOptionPane.showMessageDialog(this, "Formula: " + formula + "\nRisultato: " + risultato);
        System.exit(0);
    }

    private void mostraErrore() {
        JOptionPane.showMessageDialog(this, "Input non valido. Riprova.");
    }

    private int chiediIntero(String messaggio) {
        String input = JOptionPane.showInputDialog(this, messaggio);
        if (input == null) System.exit(0);
        return Integer.parseInt(input.trim());
    }

public class MainProgram {
    JFrame frame;
    JPanel centerPanel, southPanel;
    JLabel questionLabel;
    JButton yesButton, noButton;
    DefaultMutableTreeNode currentPos;
    QuestionTree questionTree;

    public MainProgram() {
        frame = new JFrame("Calcolo Combinatorio");
        frame.setLayout(new BorderLayout());
        frame.setSize(500, 700);

        centerPanel = new JPanel(new GridLayout(1, 1));
        questionLabel = new JLabel("Inizio");
        centerPanel.add(questionLabel);

        southPanel = new JPanel(new GridLayout(1, 2));
        yesButton = new JButton("SÌ");
        noButton = new JButton("NO");
        southPanel.add(yesButton);
        southPanel.add(noButton);

        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(southPanel, BorderLayout.SOUTH);

        // Creazione dell'albero delle domande
        questionTree = new QuestionTree();
        currentPos = questionTree.getRoot();

        // Aggiornamento iniziale del testo
        questionLabel.setText(currentPos.toString());

        // Azione sui bottoni
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigateTree(0); // Sposta il nodo alla prima opzione (SI)
                //TODO: a seconda delle opzioni deve far apparire una nuova scheda che ti calcola le cose
            }
        });
        
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigateTree(1); // Sposta il nodo alla seconda opzione (NO)
                
                //TODO: a seconda delle opzioni deve far apparire una nuova scheda che ti calcola le cose
            }
        });

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Metodo per navigare nell'albero
    private void navigateTree(int childIndex) {
        if (currentPos.getChildCount() > childIndex) {
            currentPos = (DefaultMutableTreeNode) currentPos.getChildAt(childIndex);
            questionLabel.setText(currentPos.toString());
        }
    }

    public static void main(String[] args) {
        new MainProgram();
        new MainProgram();
    }
}
