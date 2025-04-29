import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BackgroundStart extends JPanel {
    private BufferedImage backgroundImage;
    private float alpha = 0f;
    private Timer fadeTimer;
    private MainWindow parent;

    private int imgWidth=768/2+200;
    private int imgHeight=1147/2+200;

    public BackgroundStart(MainWindow parent) throws IOException {
        this.parent = parent;
        backgroundImage = ImageIO.read(getClass().getResourceAsStream("/img/BackgroundStart.png"));

        // Imposta la dimensione del pannello
        
        setPreferredSize(new Dimension(imgWidth, imgHeight));


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startFadeOut();
            }
        });
    }

    private void startFadeOut() {
        fadeTimer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alpha += 0.05f;
                if (alpha >= 1f) {
                    alpha = 1f;
                    fadeTimer.stop();
                    parent.switchToGame();
                }
                repaint();
            }
        });
        fadeTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 0;
        int y = -25;

        g.drawImage(backgroundImage, x, y, imgWidth, imgHeight, this);
        if (alpha > 0f) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2d.setColor(Color.BLACK);
            g2d.fillRect(x, y, imgWidth, imgHeight);
        }
    }
}
