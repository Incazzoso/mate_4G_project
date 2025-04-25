<<<<<<< HEAD
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
=======
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
>>>>>>> e1e3f305d3fae6540ec76e7f6caa1a1af73577d3

public class MainProgram extends JFrame {
    // Domande per determinare il tipo di calcolo combinatorio
    String[] domande = {"n, k, h sono indistinguibili?", "L'ordine è importante?", "n = k?", "Gli elementi sono tutti diversi?"};

<<<<<<< HEAD
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

    public static void main(String[] args) {
        new MainProgram();
    }
}
