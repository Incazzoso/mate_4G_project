import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.ArrayList;

public class TextArea extends JPanel {

    private BufferedImage sfondo;
    private PixelButton siButton;
    private PixelButton noButton;
    private String testo = "";
    

    private java.util.List<JTextField> campiInput = new ArrayList<>();
    private PixelButton bottoneCalcola;

    public TextArea() {
        setLayout(null); // Layout libero

        try {
            sfondo = ImageIO.read(getClass().getResourceAsStream("/IMG/TextArea.png")); // Percorso alla tua pergamena
        } catch (Exception e) {
            e.printStackTrace();
        }

        setOpaque(false);

        // Pulsante Sì
        siButton = new PixelButton("SÌ");
        siButton.setSize(120, 50); // Bottoni grandi

        // Pulsante No
        noButton = new PixelButton("NO");
        noButton.setSize(120, 50); // Bottoni grandi

        add(siButton);
        add(noButton);

        aggiungiCambioCursore(siButton);
        aggiungiCambioCursore(noButton);

    }

    public void mostraInput(int quanti) {
        removeAll();
        setLayout(null);
        campiInput.clear();
        
        int larghezzaCampo = 100;
        int altezzaCampo = 30;
        int larghezzaEtichetta = 20;
        int spazioTraCampoELabel = 5;
        int spazioTraCampi = 20;
    
        int totalWidth;
        if (quanti == 1) {
            totalWidth = larghezzaEtichetta + spazioTraCampoELabel + larghezzaCampo;
        } else {
            totalWidth = (larghezzaEtichetta + spazioTraCampoELabel + larghezzaCampo) * quanti + (quanti - 1) * spazioTraCampi;
        }
    
        int startX = (getWidth() - totalWidth) / 2;
        int y = getHeight() / 2 - 30;  // 🔥 Prima era -50, ora lo abbassiamo un po'
    
        String[] labels = {"N:", "k:"};
    
        for (int i = 0; i < quanti; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setBounds(startX, y, larghezzaEtichetta, altezzaCampo);
            add(label);
    
            JTextField campo = new JTextField();
            campo.setBounds(startX + larghezzaEtichetta + spazioTraCampoELabel, y, larghezzaCampo, altezzaCampo);
            campiInput.add(campo);
            add(campo);
    
            startX += larghezzaEtichetta + spazioTraCampoELabel + larghezzaCampo + spazioTraCampi;
        }
    
        bottoneCalcola = new PixelButton("Calcola");
        bottoneCalcola.setSize(200, 40);  // 🔥 Più piccolo
        bottoneCalcola.setBounds((getWidth() - bottoneCalcola.getWidth()) / 2, y + altezzaCampo + 7, bottoneCalcola.getWidth(), bottoneCalcola.getHeight());
        add(bottoneCalcola);
    
        repaint();
        revalidate();
    }
    

    @Override
    public void doLayout() {
        super.doLayout();
        
        // Centrare i bottoni in mezzo
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int buttonWidth = siButton.getWidth();
        int buttonHeight = siButton.getHeight();
        
        int spacing = 40; // Spazio tra i due bottoni

        int totalWidth = buttonWidth * 2 + spacing;
        int startX = (panelWidth - totalWidth) / 2;
        int centerY = (panelHeight - buttonHeight) / 2;

        siButton.setBounds(startX, centerY, buttonWidth, buttonHeight);
        noButton.setBounds(startX + buttonWidth + spacing, centerY, buttonWidth, buttonHeight);
    }

    public void addSiButtonListener(ActionListener listener) {
        siButton.addActionListener(listener);
    }
    
    public void addNoButtonListener(ActionListener listener) {
        noButton.addActionListener(listener);
    }

    public void setText(String nuovoTesto) {
        this.testo = nuovoTesto;
        repaint();
    }

    public int[] getValoriInput() throws NumberFormatException {
        int[] valori = new int[campiInput.size()];
        for (int i = 0; i < campiInput.size(); i++) {
            String testo = campiInput.get(i).getText().trim();
            if (!testo.matches("\\d+")) {
                throw new NumberFormatException("Inserisci solo numeri interi positivi.");
            }
            valori[i] = Integer.parseInt(testo);
        }
        return valori;
    }

    public void Risultato(double risultato, Runnable azioneEsci,Runnable azioneRicomincia) {
        removeAll();
        setLayout(null);
    
        JLabel label = new JLabel("Risultato: " + risultato, SwingConstants.CENTER);
        label.setFont(new Font("Monospaced", Font.BOLD, 20));
        label.setForeground(new Color(80, 40, 0));
        label.setBounds(0, 20, getWidth(), 85);
        add(label);
    
        PixelButton ricomincia = new PixelButton("Ricomincia");
        PixelButton esci = new PixelButton("Esci");
    
        ricomincia.setSize(200, 50);
        esci.setSize(140, 50);
    
        int spacing = 30;
        int totalWidth = ricomincia.getWidth() + esci.getWidth() + spacing;
        int startX = (getWidth() - totalWidth) / 2;
        int y = 80;
    
        ricomincia.setBounds(startX, y, ricomincia.getWidth(), ricomincia.getHeight());
        esci.setBounds(startX + ricomincia.getWidth() + spacing, y, esci.getWidth(), esci.getHeight());

    
        // Qui invece collego al fade out!
        ricomincia.addActionListener(e -> azioneRicomincia.run());
        esci.addActionListener(e -> azioneEsci.run());
    
        add(ricomincia);
        add(esci);
    
        repaint();
        revalidate();
    }
    
    
    public PixelButton getCalcolaButton() {
        return bottoneCalcola;
    }
    
    private void aggiungiCambioCursore(Component comp) {
        comp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Image image = new ImageIcon(getClass().getResource("/IMG/mano.png")).getImage();
                Image scaledImage = image.getScaledInstance(29, 32, Image.SCALE_SMOOTH);
                Cursor customCursor = toolkit.createCustomCursor(scaledImage, new Point(0, 0), "CustomCursor");
                setCursor(customCursor);
            }
    
            @Override
            public void mouseExited(MouseEvent e) {
                // Ricrea manualmente il cursore personalizzato
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Image image = new ImageIcon(getClass().getResource("/IMG/freccia.png")).getImage();
                Image scaledImage = image.getScaledInstance(29, 32, Image.SCALE_SMOOTH);
                Cursor customCursor = toolkit.createCustomCursor(scaledImage, new Point(0, 0), "CustomCursor");
                setCursor(customCursor);
            }
        });
    }

    public void yesnoback(){
        // Pulsante Sì
        siButton = new PixelButton("SÌ");
        siButton.setSize(120, 50); // Bottoni grandi

        // Pulsante No
        noButton = new PixelButton("NO");
        noButton.setSize(120, 50); // Bottoni grandi

        add(siButton);
        add(noButton);

        aggiungiCambioCursore(siButton);
        aggiungiCambioCursore(noButton);
    }
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        
        // Disegna la pergamena
        if (sfondo != null) {
            g2d.drawImage(sfondo, 0, 0, getWidth(), getHeight(), this);
        }
        
        // Disegna il testo sopra la pergamena
        if (testo != null && !testo.isEmpty()) {
            g2d.setColor(new Color(80, 40, 0)); // Marrone scuro
            g2d.setFont(new Font("Monospaced", Font.BOLD, 24)); // Font un po' più grande
            
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(testo);
            int x = (getWidth() - textWidth) / 2;
            int y = 50; // Più in alto il testo
            
            g2d.drawString(testo, x, y);
        }
        
        g2d.dispose();
    }

    class PixelButton extends JButton {
        private boolean hover = false;

        public PixelButton(String text) {
            super(text);
            setFocusPainted(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setForeground(new Color(80, 40, 0)); 
            setFont(new Font("Monospaced", Font.BOLD, 22));
            setOpaque(false);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    hover = true;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    hover = false;
                    repaint();
                }
            });
        }

    

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (hover) {
                FontMetrics fm = g.getFontMetrics();
                int textWidth = fm.stringWidth(getText());
                int x = (getWidth() - textWidth) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - 6;
                g.setColor(getForeground());
                g.fillRect(x, y + 2, textWidth, 2);
            }
        }
    }
}
