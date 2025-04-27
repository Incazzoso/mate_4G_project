import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.io.IOException;

public class MainWindow extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private BackgroundStart backgroundStart;
    private GameStart gameStart;

    public MainWindow() throws IOException {
        setTitle("Calcolo Combinatorio - Start");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // Dimensione FISSA
        int altezza=768/2+200;
        int larghezza=1147/2+200;
        setSize(altezza, larghezza);
        setResizable(false); // <-- NON RIDIMENSIONABILE
        setLocationRelativeTo(null); // Centra la finestra

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setPreferredSize(new java.awt.Dimension(600, 700)); // Preferita 600x700

        backgroundStart = new BackgroundStart(this);
        gameStart = new GameStart();

        mainPanel.add(backgroundStart, "background");
        mainPanel.add(gameStart, "game");

        add(mainPanel);
        setVisible(true);
    }

    public void switchToGame() {
        cardLayout.show(mainPanel, "game");
    }

    public static void main(String[] args) throws IOException {
        new MainWindow();
    }
}
