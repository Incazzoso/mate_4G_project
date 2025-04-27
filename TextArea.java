import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class TextArea extends JPanel {

    private BufferedImage sfondo;
    private PixelButton siButton;
    private PixelButton noButton;
    private String testo = "";

    public TextArea() {
        setLayout(null); // Layout libero

        try {
            sfondo = ImageIO.read(new File("TextArea.png")); // Percorso alla tua pergamena
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Pulsante Sì
        siButton = new PixelButton("SÌ");
        siButton.setBounds(100, 120, 80, 30);
        add(siButton);

        // Pulsante No
        noButton = new PixelButton("NO");
        noButton.setBounds(300, 120, 80, 30);
        add(noButton);
    }

    public JButton getSiButton() {
        return siButton;
    }

    public JButton getNoButton() {
        return noButton;
    }

    // Metodo per cambiare il testo
    public void setText(String nuovoTesto) {
        this.testo = nuovoTesto;
        repaint(); // Rinfresca il pannello per disegnare il nuovo testo
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Disegna la pergamena
        if (sfondo != null) {
            g.drawImage(sfondo, 0, 0, getWidth(), getHeight(), this);
        }

        // Disegna il testo sopra la pergamena
        if (testo != null && !testo.isEmpty()) {
            g.setColor(new Color(80, 40, 0)); // Marrone scuro
            g.setFont(new Font("Monospaced", Font.BOLD, 20));
            
            // Centrare il testo
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(testo);
            int x = (getWidth() - textWidth) / 2;
            int y = 50; // Posizione verticale del testo (puoi cambiare)
            
            g.drawString(testo, x, y);
        }
    }

    // Classe custom per bottoni stile pixel art con underline su hover
    class PixelButton extends JButton {
        private boolean hover = false;

        public PixelButton(String text) {
            super(text);
            setFocusPainted(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setForeground(new Color(255, 200, 0)); // Giallo pixel art
            setFont(new Font("Monospaced", Font.BOLD, 20));
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
                int y = (getHeight() + fm.getAscent()) / 2;
                g.setColor(getForeground());
                g.fillRect(x, y + 2, textWidth, 2); // Disegna la riga sotto il testo
            }
        }
    }
}
