package snake.game;

import javax.swing.*;

public class Snake extends JFrame {

    Snake() {
        super("Sname Game"); //set title should be first statement in constructor.
        add(new Board());
        pack(); //set frame size
        setLocationRelativeTo(null); //Frame center
//        setTitle("Snake Game"); //set title
        setResizable(false);
    }

    public static void main(String[] args) {
        new Snake().setVisible(true);
    }
}
