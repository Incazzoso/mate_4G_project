import javax.swing.tree.DefaultMutableTreeNode;

public class QuestionTree {
    DefaultMutableTreeNode domanda1;
    DefaultMutableTreeNode domanda2A;
    DefaultMutableTreeNode domanda2B;
    DefaultMutableTreeNode domanda3A;
    DefaultMutableTreeNode domanda3B;

    DefaultMutableTreeNode risposta1A;
    DefaultMutableTreeNode risposta1B;
    DefaultMutableTreeNode risposta2A;
    DefaultMutableTreeNode risposta2B;
    DefaultMutableTreeNode risposta3A;
    DefaultMutableTreeNode risposta3B;

    QuestionTree(){

        //domande
        domanda1 = new DefaultMutableTreeNode("l'ordine ha importanza?");
        domanda2A = new DefaultMutableTreeNode("gli elementi sono nella stessa quantità?");
        domanda2B = new DefaultMutableTreeNode("gli elementi si ripetono?");
        domanda3A = new DefaultMutableTreeNode("gli elementi sono tutti diversi?");
        domanda3B = new DefaultMutableTreeNode("gli elementi si ripetono?");

        //rosposte
        risposta1A = new DefaultMutableTreeNode("combinazioni con ripetizione");
        risposta1B = new DefaultMutableTreeNode("combinazioni semplici");
        risposta2A = new DefaultMutableTreeNode("permutazioni semplici");
        risposta2B = new DefaultMutableTreeNode("permutazioni con ripetizioni");
        risposta3A = new DefaultMutableTreeNode("disposizioni con ripetizione");
        risposta3B = new DefaultMutableTreeNode("disposizioni semplici");

        domanda1.add(domanda2A);
            domanda2A.add(domanda3A);
                domanda3A.add(risposta2A);
                domanda3A.add(risposta2B);
            domanda2A.add(domanda3B);
                domanda3B.add(risposta3A);
                domanda3B.add(risposta3B);        
        domanda1.add(domanda2B);
            domanda2B.add(risposta1A);
            domanda2B.add(risposta1B);


        

    }

    public DefaultMutableTreeNode getRoot(){
        return domanda1;
    }
}