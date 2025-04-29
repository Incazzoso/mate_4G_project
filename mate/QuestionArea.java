import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class QuestionArea extends JPanel {

    private BufferedImage sfondo;
    private String testo = "";

    public QuestionArea(){
        setLayout(null); // Layout libero

        try {
            sfondo = ImageIO.read(getClass().getResourceAsStream("/IMG/TextArea.png")); // Percorso alla tua pergamena
        } catch (Exception e) {
            e.printStackTrace();
        }
        setOpaque(false);

    }

    // Metodo per cambiare il testo
    public void setText(String nuovoTesto) {
        this.testo = nuovoTesto;
        repaint(); // Rinfresca il pannello per disegnare il nuovo testo
    }

    @Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();

    // Disegna la pergamena
    if (sfondo != null) {
        g2d.drawImage(sfondo, 0, 0, getWidth(), getHeight(), this);
    }

    // Disegna il testo
    if (testo != null && !testo.isEmpty()) {
        g2d.setColor(new Color(80, 40, 0)); // Marrone scuro
        g2d.setFont(new Font("Monospaced", Font.BOLD, 17));

        FontMetrics fm = g2d.getFontMetrics();
        int maxTextWidth = getWidth() - 100; // lascia 50px di margine a sinistra e a destra

        java.util.List<String> righe = new java.util.ArrayList<>();
        String[] parole = testo.split(" ");
        StringBuilder rigaCorrente = new StringBuilder();

        for (String parola : parole) {
            String tempRiga = rigaCorrente + parola + " ";
            int tempWidth = fm.stringWidth(tempRiga);
            if (tempWidth > maxTextWidth) {
                righe.add(rigaCorrente.toString());
                rigaCorrente = new StringBuilder(parola + " ");
            } else {
                rigaCorrente.append(parola).append(" ");
            }
        }
        righe.add(rigaCorrente.toString());

        int startY = getHeight() / 2 - (righe.size() * fm.getHeight()) / 2+13;

        for (String riga : righe) {
            int textWidth = fm.stringWidth(riga);
            int x = (getWidth() - textWidth) / 2;
            g2d.drawString(riga, x, startY);
            startY += fm.getHeight() + 5; // Spazio tra le righe
        }
    }

    g2d.dispose();
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
