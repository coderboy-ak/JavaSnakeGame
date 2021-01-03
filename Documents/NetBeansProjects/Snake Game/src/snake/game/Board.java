package snake.game;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Board extends JPanel implements ActionListener {

    private Image apple;
    private Image dot;
    private Image head;
    private int dots;
    private final int DOT_SIZE = 10; //300*300 = 90000 framesize /100 = 900 dots can come 
    private final int ALL_DOTS = 900; //maximum dots is 900
    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];
    private final int RANDOM_POSITION = 29;
    private int apple_x;
    private int apple_y;
    private Timer timer;
    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true; //starting game 

    Board() {

        addKeyListener(new TAdapter()); //key event pressing function calling
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(300, 300)); //set size of frame
        setFocusable(true); //for working key event
        loadImage();
        initGame();

    }

    //load image
    public void loadImage() {
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("snake/game/icons/apple.png"));
        apple = i1.getImage();
        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("snake/game/icons/dot.png"));
        dot = i2.getImage();
        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("snake/game/icons/head.png"));
        head = i3.getImage();
    }

    //2 collision - snake himself and snake boundary
    public void checkCollision() {

        for (int z = dots; z > 0; z--) {
            //head cord == tail coord and any same coordinate
            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }
        if (y[0] >= 300) { //crossing y boundary
            inGame = false;
        }
        if (x[0] >= 300) {  //crossing x boundary
            inGame = false;
        }
        if (x[0] < 0) {
            inGame = false;
        }
        if (y[0] < 0) {
            inGame = false;
        }
        if (!inGame) {
            timer.stop();
        }
    }

    //function to add image one after another
    public void initGame() {

        dots = 3;
        for (int z = 0; z < dots; z++) {
            //x[0] y[0]  = head 
            x[z] = 50 - z * DOT_SIZE;  //1- 50th , 2 : 50 - dot_size(10)= 40 
            y[z] = 50; //dot add one after another on x- axis
        }
        locateApple(); //calling apple function 
        timer = new Timer(140, this); //adding delay between snake movement using Timer class
        timer.start();
    }

    //locating apple randomly  
    public void locateApple() {
        //30*1 =30 *10= (290,290) +10= 300,300(1- 290)
        int r = (int) (Math.random() * RANDOM_POSITION); //o and 1 random no. generate 
        apple_x = (r * DOT_SIZE);
        r = (int) (Math.random() * RANDOM_POSITION); //o and 1 random no. generate 
        apple_y = (r * DOT_SIZE);

    }

    //checking coordinate of both head and apple same or not
    public void checkApple() {
        if ((x[0] == apple_x) && (y[0] == apple_y)) {
            dots++;  //increasing snake size and create new apple
            locateApple(); //new 
//            checkCollision();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);

    }

    public void draw(Graphics g) {
        if (inGame) {
            g.drawImage(apple, apple_x, apple_y, this);
            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z], y[z], this);

                } else {
                    g.drawImage(dot, x[z], y[z], this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        } else {
            gameOver(g);
        }
    }

    public void gameOver(Graphics g) {
        String msg = "Game Over";
        Font font = new Font("SAN_SARIF", Font.BOLD, 14);
        FontMetrics metrices = getFontMetrics(font);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg, (300 - metrices.stringWidth(msg)) / 2, 300 / 2);
    }

    //making snake move means changing coordinate of dots
    public void move() {
        //moving tail dots
        for (int z = dots; z > 0; z--) {
            x[z] = x[z - 1]; //dots comes to first position 
            y[z] = y[z - 1];
        }

        //movind head dots 
        if (leftDirection) {
            x[0] = x[0] - DOT_SIZE;
        }
        //240-10 =230 = shifting dots to first dot coordinate
        if (rightDirection) {
            x[0] += DOT_SIZE;
        }
        if (upDirection) {
            y[0] -= DOT_SIZE;

        }
        if (downDirection) {
            y[0] += DOT_SIZE;
        }

    }

    public void actionPerformed(ActionEvent ae) {
        if (inGame) {//find apple - head touch to apple 
            checkApple();
            checkCollision();
            move();

        }
        repaint(); //called when look of component change
    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    //for using up , down ,left and right key - addKeyListener
    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;

            }
            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;

            }
            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                leftDirection = false;
                upDirection = true;
                rightDirection = false;

            }
            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                leftDirection = false;
                rightDirection = false;
                downDirection = true;

            }
        }
    }
}
