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
    private CombinatoryCalc calc = new CombinatoryCalc();
    private TextArea textArea = new TextArea();
    private QuestionArea questionArea= new QuestionArea();

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
                questionArea.setText("Benvenuto, viandante... Il sentiero delle combinazioni si dispiega innanzi a te.. Pronto?");
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
                    textArea.removeAll();
                    ritardaEsegui(3000, () -> startFadeOutAndExit());

                }
                break;
            case 1:
                if (risposta) {
                    questionArea.setText("Allora parleremo di antichi raggruppamenti...  (n*k..)");
                    textArea.mostraInput(2);
                    textArea.getCalcolaButton().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            calcola();
                        }
                    });


                } else {
                    step++;
                    questionArea.setText("Quando gli elementi si confondono, le regole cambiano...");
                    ritardaEsegui(3500, () -> questionArea.setText("Conta forse l'ordine in cui compi le tue scelte?"));

                }
                break;
            case 2:
                if (risposta) {
                    step=4;
                    questionArea.setText("L'ordine... una trama sottile del destino..");
                    //wait di 5 sec;
                    ritardaEsegui(3500, () -> questionArea.setText("Per caso N e K sono uguali?"));
                    
                } else {
                    step++;
                    currentSfondo = 0;
                    repaint();
                    questionArea.setText("Allora le combinazioni ti attendono...");
                    //wait di 5 sec;
                    ritardaEsegui(3500, () ->questionArea.setText("Gli elementi sono... tutti diversi tra loro?"));
                }
                break;
            case 3:
                if (risposta) {
                    currentSfondo = 2;
                    repaint();
                    questionArea.setText("Trovato! Combinazioni semplici..");
                    textArea.mostraInput(2);
                    textArea.getCalcolaButton().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            calcola();
                        }
                    });


                } else {
                    currentSfondo = 2;
                    repaint();
                    questionArea.setText("Trovato! Combinazioni con Ripetizione..");
                    textArea.mostraInput(2);
                    textArea.getCalcolaButton().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            calcola();
                        }
                    });

                }
                break;
            case 4:
                if (risposta) {
                    questionArea.setText("Le permutazioni pure dominano questo regno.");
                    //wait di 5 sec;
                    ritardaEsegui(3500, () ->questionArea.setText("Gli elementi sono... tutti diversi tra loro?"));
                    step++;
                } else {
                    step=6;
                    questionArea.setText("Le disposizioni pure dominano questo regno.");
                    //wait di 5 sec;
                    ritardaEsegui(3500, () ->questionArea.setText("Gli elementi sono... tutti diversi tra loro?"));
                }
                break;

            case 5:
                if (risposta) {
                    questionArea.setText("Le permutazioni semplici.");
                    textArea.mostraInput(2);
                    textArea.getCalcolaButton().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            calcola();
                        }
                    });

                } else {
                    questionArea.setText("Le permutazioni con ripetizione.");
                    textArea.mostraInput(2);
                    textArea.getCalcolaButton().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            calcola();
                        }
                    });

                }
                break;

            case 6:
                if (risposta) {
                    questionArea.setText("Le disposizioni semplici.");
                    textArea.mostraInput(2);
                    textArea.getCalcolaButton().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            calcola();
                        }
                    });

                } else {
                    questionArea.setText("Le disposizioni con ripetizione.");
                    textArea.mostraInput(2);
                    textArea.getCalcolaButton().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            calcola();
                        }
                    });

                }
                break;
        }
    }

    private void calcola() {
        try {
            int[] valori = textArea.getValoriInput(); // Prendi input da textArea
            double risultato = 0;
    
            switch (step) {
                case 1: // Raggruppamento → usa raggrupamento(n, k)
                    risultato = calc.raggrupamento(valori[0], valori[1]); // se servono solo n, k
                    break;
    
                case 3: // Combinazione semplice
                    risultato = calc.combinazione(valori[0], valori[1]);
                    break;
    
                case 4: // Combinazione con ripetizione
                    risultato = calc.combinazioneRip(valori[0], valori[1]);
                    break;
    
                case 5: // Permutazione semplice
                    risultato = calc.permutazione(valori[0]);
                    break;
    
                case 6: // Permutazione con ripetizione
                    // qui dovresti passare un array delle ripetizioni, usa tutti i campi tranne il primo
                    int n = valori[0];
                    int[] ripetizioni = java.util.Arrays.copyOfRange(valori, 1, valori.length);
                    risultato = calc.permutazioniRip(n, ripetizioni);
                    break;
    
                case 7: // Disposizione semplice
                    risultato = calc.disposizione(valori[0], valori[1]);
                    break;
    
                case 8: // Disposizione con ripetizione
                    risultato = calc.disposizioneRip(valori[0], valori[1]);
                    break;
    
                default:
                    textArea.setText("Errore: calcolo non previsto per questo step.");
                    return;
            }

            textArea.removeAll();
            questionArea.setText("Ecco a te..");
            textArea.Risultato(risultato, () -> startFadeOutAndExitClose(),() -> ricominciaGioco());
        } catch (Exception e) {
            textArea.setText("Errore durante il calcolo. Verifica gli input.");
        }
    }

    private void startFadeOutAndExit() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);  // Trova il frame padre
        if (frame != null) {
            Timer fadeTimer = new Timer(30, null);
            final float[] alpha = {0f};
    
            fadeTimer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    alpha[0] += 0.05f;
                    if (alpha[0] >= 1f) {
                        alpha[0] = 1f;
                        fadeTimer.stop();
                        // Dopo che è completamente nero, aspetta 2 secondi
                        new Timer(2000, ev -> System.exit(0)).start();
                    }
                    frame.repaint();
                }
            });
            fadeTimer.start();
    
            // Devi sovrascrivere il paint del frame per disegnare il nero sopra
            frame.setGlassPane(new JPanel() {
                {
                    setOpaque(false);
                }
    
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    if (alpha[0] > 0f) {
                        Graphics2D g2d = (Graphics2D) g.create();
                        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha[0]));
                        g2d.setColor(Color.BLACK);
                        g2d.fillRect(0, 0, getWidth(), getHeight());
                        g2d.dispose();
                    }
                }
            });
            frame.getGlassPane().setVisible(true);
        }
    }

    private void startFadeOutAndExitClose() {
        questionArea.setText("Va bene.. E' stato bello finchè è durato!");
        cambiaSfondo();
        ritardaEsegui(2500, () ->questionArea.setText("Arrivederci! :D"));
        ritardaEsegui(2000, () ->startFadeOutAndExit());
    }
    

    private void cambiaSfondo() {
        currentSfondo = (currentSfondo + 1) % sfondi.length;
        repaint();
    }

    private void ritardaEsegui(int millisecondi, Runnable azione) {
        new javax.swing.Timer(millisecondi, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((Timer) e.getSource()).stop();
                azione.run();
            }
        }).start();
    }

    public void ricominciaGioco() {
        step = 0;  
        currentSfondo = 2;  
        textArea.removeAll();  // Pulisci eventuali input
        textArea.yesnoback();
        textArea.setVisible(true);
        questionArea.setVisible(true);
        questionArea.setText("Rieccoci.. Pronto a Ricomincire?");

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
        
        repaint();
        revalidate();
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
