import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class MainProgram {
    JFrame frame;
    JPanel centerPanel, southPanel;
    JLabel questionLabel;
    JButton yesButton, noButton;
    DefaultMutableTreeNode currentPos;
    QuestionTree questionTree;

    public MainProgram() {
        frame = new JFrame("Calcolo Combinatorio");
        frame.setLayout(new BorderLayout());
        frame.setSize(500, 700);

        centerPanel = new JPanel(new GridLayout(1, 1));
        questionLabel = new JLabel("Inizio");
        centerPanel.add(questionLabel);

        southPanel = new JPanel(new GridLayout(1, 2));
        yesButton = new JButton("SÌ");
        noButton = new JButton("NO");
        southPanel.add(yesButton);
        southPanel.add(noButton);

        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(southPanel, BorderLayout.SOUTH);

        // Creazione dell'albero delle domande
        questionTree = new QuestionTree();
        currentPos = questionTree.getRoot();

        // Aggiornamento iniziale del testo
        questionLabel.setText(currentPos.toString());

        // Azione sui bottoni
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigateTree(0); // Sposta il nodo alla prima opzione (SI)
                //TODO: a seconda delle opzioni deve far apparire una nuova scheda che ti calcola le cose
            }
        });
        
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigateTree(1); // Sposta il nodo alla seconda opzione (NO)
                
                //TODO: a seconda delle opzioni deve far apparire una nuova scheda che ti calcola le cose
            }
        });

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Metodo per navigare nell'albero
    private void navigateTree(int childIndex) {
        if (currentPos.getChildCount() > childIndex) {
            currentPos = (DefaultMutableTreeNode) currentPos.getChildAt(childIndex);
            questionLabel.setText(currentPos.toString());
        }
    }

    public static void main(String[] args) {
        new MainProgram();
    }
}