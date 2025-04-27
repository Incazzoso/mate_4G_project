import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GameStart extends JPanel {

    private BufferedImage[] sfondi;
    private int currentSfondo = 0;

    private JButton yes, no;
    private int step = 0;
    private String formula = "";
    private CombinatoryCalc calc = new CombinatoryCalc();
    private TextArea textArea = new TextArea();

    // Domande
    String[] domande = {
        "n, k, h sono indistinguibili?",
        "L'ordine è importante?",
        "n = k?",
        "Gli elementi sono tutti diversi?"
    };

    int larghezza = 1147 / 2;
    int altezza = 768 / 2;

    public GameStart() {
        setPreferredSize(new Dimension(larghezza, altezza));
        setLayout(null);
        setBackground(Color.BLACK);

        try {
            sfondi = new BufferedImage[]{
                ImageIO.read(getClass().getResourceAsStream("/img/sfondo1.png")),
                ImageIO.read(getClass().getResourceAsStream("/img/sfondo2.png")),
                ImageIO.read(getClass().getResourceAsStream("/img/sfondo3.png"))
            };
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Pulsanti SI e NO
        yes = new JButton("SÌ");
        no = new JButton("NO");

        yes.setBounds(200, 400, 100, 50);
        no.setBounds(400, 400, 100, 50);

        yes.setFocusPainted(false);
        no.setFocusPainted(false);

        add(yes);
        add(no);

        // Area di testo
        textArea.setBounds(50, 500, 500, 100);
        add(textArea);

        yes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gestisciRisposta(true);
                cambiaSfondo();
            }
        });

        no.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gestisciRisposta(false);
                cambiaSfondo();
            }
        });
    }

    private void gestisciRisposta(boolean risposta) {
        switch (step) {
            case 0:
                if (risposta) {
                    formula = "raggruppamento";
                    chiediNKH();
                } else {
                    step++;
                    mostraDomanda();
                }
                break;
            case 1:
                if (risposta) {
                    step++;
                    mostraDomanda();
                } else {
                    step = 3;
                    mostraDomanda();
                }
                break;
            case 2:
                if (risposta) {
                    formula = "permutazione";
                    chiediTuttiDiversi();
                } else {
                    step = 3;
                    mostraDomanda();
                }
                break;
            case 3:
                if (risposta) {
                    formula = "disposizione";
                    chiediNK();
                } else {
                    formula = "disposizioneRip";
                    chiediNKRipetizioni();
                }
                break;
        }
    }

    private void mostraDomanda() {
        if (step < domande.length) {
            textArea.setText(domande[step]); // Mostra domanda nella TextArea
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
                chiediNK();
            } else {
                chiediNKRipetizioni();
            }
        } catch (Exception e) {
            mostraErrore();
        }
    }

    private void chiediNKRipetizioni() {
        try {
            int n = chiediIntero("Inserisci n:");
            int k = chiediIntero("Inserisci k:");
            double risultato = calc.disposizioneRip(n, k);
            mostraRisultato(risultato);
        } catch (Exception e) {
            mostraErrore();
        }
    }

    private void mostraRisultato(double risultato) {
        textArea.setText("Formula: " + formula + "\nRisultato: " + risultato);
        System.exit(0);
    }

    private void mostraErrore() {
        textArea.setText("Input non valido. Riprova.");
    }

    private int chiediIntero(String messaggio) {
        String input = JOptionPane.showInputDialog(this, messaggio);
        if (input == null) System.exit(0);
        return Integer.parseInt(input.trim());
    }

    private void cambiaSfondo() {
        currentSfondo = (currentSfondo + 1) % sfondi.length;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (sfondi != null && sfondi[currentSfondo] != null) {
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            int imgWidth = sfondi[currentSfondo].getWidth() / 2;
            int imgHeight = sfondi[currentSfondo].getHeight() / 2;
            int x = (panelWidth - imgWidth) / 2;
            int y = (panelHeight - imgHeight) / 2;
            g.drawImage(sfondi[currentSfondo], x, y, imgWidth, imgHeight, this);
        }
    }
}
