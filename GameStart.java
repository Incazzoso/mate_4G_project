import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GameStart extends JPanel {

    private BufferedImage[] sfondi;
    private int currentSfondo = 0;

    private int step = 0;
    private String formula = "";
    private CombinatoryCalc calc = new CombinatoryCalc();
    private TextArea textArea = new TextArea();
    private QuestionArea questionArea= new QuestionArea();

    // Domande
    String[] domande = {
        "Benvenuto, viandante... Il sentiero \ndelle combinazioni si dispiega innanzi \na te.. Pronto?",
        "Dimmi... n, k, h sono forse... indistinguibili ai tuoi occhi?",
        "Quando gli elementi si confondono, le regole cambiano...",
        "Conta forse l'ordine in cui compi le tue scelte?",
        "n è uguale a k?",
        "Gli elementi sono... tutti diversi tra loro?",

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

        // Area di testo
        questionArea.setBounds(50, 20, larghezza - 100, 100); // In alto
        textArea.setBounds(50, 590, larghezza - 100, 150);    // In basso

        add(questionArea);
        questionArea.setVisible(false);
        add(textArea);
        textArea.setVisible(false);
        
        textArea.addSiButtonListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gestisciRisposta(true);
                cambiaSfondo();
            }
        });
        
        textArea.addNoButtonListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gestisciRisposta(false);
                cambiaSfondo();
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                questionArea.setVisible(true);
                textArea.setVisible(true);
                questionArea.setText(domande[0]);
                currentSfondo = 2;
                repaint();
                removeMouseListener(this); // Dopo il primo click, rimuove il listener
            }
        });
        
        
    }

    private void gestisciRisposta(boolean risposta) {
        switch (step) {
            case 0:
                if (risposta) {
                    step++;
                    questionArea.setText("Dimmi... n, k sono forse... indistinguibili ai tuoi occhi?");
                } else {
                    currentSfondo = 1;
                    repaint();
                    questionArea.setText("Va bene... Sarà per la prossima volta allora.. :/");
                    //exit(0) dopo 5 secondi;
                }
                break;
            case 1:
                if (risposta) {
                    questionArea.setText("Allora parleremo di antichi raggruppamenti...  (n*k..)");
                    textArea.mostraInput(2);
                } else {
                    step++;
                    questionArea.setText("Quando gli elementi si confondono, le regole cambiano...");
                    //wait di 7 secondi;
                    questionArea.setText("Conta forse l'ordine in cui compi le tue scelte?");
                }
                break;
            case 2:
                if (risposta) {
                    step=4;
                    questionArea.setText("L'ordine... una trama sottile del destino..");
                    
                } else {
                    step++;
                    currentSfondo = 0;
                    repaint();
                    questionArea.setText("Allora le combinazioni ti attendono...");
                    //wait di 5 sec;
                    questionArea.setText("Gli elementi sono... tutti diversi tra loro?");
                }
                break;
            case 3:
                if (risposta) {
                    currentSfondo = 2;
                    repaint();
                    questionArea.setText("Trovato! Combinazioni semplici..");
                    formula = "Combinazionio semplici";
                    chiediTuttiDiversi();
                } else {
                    questionArea.setText("Trovato! Combinazioni con Ripetizione..");
                    formula = "Combinazionio con ripetizioni";
                    mostraDomanda();
                }
                break;
            case 4:
                if (risposta) {
                    questionArea.setText("Le permutazioni pure dominano questo regno.");
                    //wait di 5 sec;
                    questionArea.setText("Gli elementi sono... tutti diversi tra loro?");
                    step++;
                } else {
                    step=6;
                    questionArea.setText("Le disposizioni pure dominano questo regno.");
                    //wait di 5 sec;
                    questionArea.setText("Gli elementi sono... tutti diversi tra loro?");
                }
                break;

            case 5:
                if (risposta) {
                    questionArea.setText("Le permutazioni semplici.");
                    //wait di 5 sec;
                } else {
                    questionArea.setText("Le permutazioni con ripetizione.");
                }
                break;

            case 6:
                if (risposta) {
                    questionArea.setText("Le disposizioni semplici.");
                    //wait di 5 sec;
                } else {
                    questionArea.setText("Le disposizioni con ripetizione.");
                }
                break;
            case 7:
                questionArea.setText("Hai compiuto il tuo cammino. Ora... calcolo.. e che la magia delle combinazioni sia con te!");
                //wait di 4 sec;
                //sout del risultato
                break;
        }
    }

    private void mostraDomanda() {
        if (step < domande.length) {
            questionArea.setText(domande[step]); // Mostra domanda nella TextArea
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
