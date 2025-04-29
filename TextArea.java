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
    private JTextField n;
    private JTextField k;
    private String testo = "";
    

    private java.util.List<JTextField> campiInput = new ArrayList<>();
    private JButton bottoneCalcola = null;

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
    }

    public void mostraInput(int quanti) {
        removeAll(); // Pulisce tutto
        setLayout(null); // Layout libero
    
        campiInput.clear(); // Puliamo anche la lista dei campi se esiste già
    
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
        int y = getHeight() / 2 - 50; // Posizione verticale
    
        // Etichette da usare
        String[] labels = {"N:", "k:"};
    
        for (int i = 0; i < quanti; i++) {
            // Crea la label
            JLabel label = new JLabel(labels[i]);
            label.setBounds(startX, y, larghezzaEtichetta, altezzaCampo);
            add(label);
    
            // Crea il campo
            JTextField campo = new JTextField();
            campo.setBounds(startX + larghezzaEtichetta + spazioTraCampoELabel, y, larghezzaCampo, altezzaCampo);
            campiInput.add(campo);
            add(campo);
    
            // Aggiorna startX per il prossimo elemento
            startX += larghezzaEtichetta + spazioTraCampoELabel + larghezzaCampo + spazioTraCampi;
        }
    
        // Crea il bottone Calcola
        bottoneCalcola = new JButton("Calcola");
        bottoneCalcola.setBounds((getWidth() - 120) / 2, y + altezzaCampo + 20, 120, 40);
        //bottoneCalcola.addActionListener(listener);
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
            setFont(new Font("Monospaced", Font.BOLD, 24));
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
