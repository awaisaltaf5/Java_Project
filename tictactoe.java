// create class with name tictactoe

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;



public class tictactoe implements ActionListener {
    Random r1 = new Random();
    JFrame f = new JFrame();
    JPanel title = new JPanel();//title frame
    JPanel button = new JPanel();//game frame
    JPanel side=new JPanel();// side yellow panel


    JLabel t1=new JLabel();


    JLabel text = new JLabel();
    JButton[] buttons = new JButton[9];
    boolean turn;
    boolean gameOver = false;

    tictactoe() {
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setSize(800, 800);
        f.getContentPane().setBackground(new Color(0x123456));
        f.getContentPane().setLayout(new BorderLayout());
        f.setResizable(true);

        text.setBackground(new Color(10, 0, 0));
        text.setForeground(new Color(255, 255, 204));
        text.setFont(new Font("Calibre", Font.BOLD, 50));
        text.setHorizontalAlignment(JLabel.CENTER);
        text.setText("TIC-TAC-TOE");
        text.setOpaque(true);

        title.setLayout(new BorderLayout());
        title.setBounds(0, 0, 600, 300);
        side.setBackground(new Color(255,255,204));
        side.setBounds(800,0,250,100);

        button.setLayout(new GridLayout(3, 3)); //panel for grid layout panel
        button.setBackground(new Color(0x123456));
        button.setBounds(133,150,500,500);



        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            button.add(buttons[i]);
            buttons[i].setFont(new Font("Calibre", Font.BOLD, 50));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
            buttons[i].setBackground(new Color(0x123456));
        }


        side.add(t1);

        t1.setText("TIC-TAC-TOE");
        t1.setFont(new Font("Calibre",Font.BOLD,30));
        title.add(text);
        f.getContentPane().add(title, BorderLayout.NORTH);
        f.getContentPane().add(button);
        f.getContentPane().add(side);


        turn = r1.nextInt(2) == 0;
        text.setText(turn ? "X Turn!" : "O Turn!");

        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (gameOver) return;

        for (int i = 0; i < 9; i++) {
            if (e.getSource() == buttons[i] && buttons[i].getText().isEmpty()) {
                if (turn) {
                    buttons[i].setForeground(Color.YELLOW);
                    buttons[i].setText("X");
                    turn = false;
                    text.setText("O-Turn");
                    check();
                } else {
                    buttons[i].setForeground(Color.YELLOW);
                    buttons[i].setText("O");
                    turn = true;
                    text.setText("X-Turn");
                    check();
                }
                return;
            }
        }
    }

    public void check() {
        int[][] winCombos = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6},
                {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}
        };
        for (int[] combo : winCombos) {
            if (buttons[combo[0]].getText().equals("X") &&
                    buttons[combo[1]].getText().equals("X") &&
                    buttons[combo[2]].getText().equals("X")) {
                X(combo[0], combo[1], combo[2]);
                return;
            }
            if (buttons[combo[0]].getText().equals("O") &&
                    buttons[combo[1]].getText().equals("O") &&
                    buttons[combo[2]].getText().equals("O")) {
                O(combo[0], combo[1], combo[2]);
                return;
            }
        }
        boolean draw = true;
        for (JButton button : buttons) {
            if (button.getText().isEmpty()) {
                draw = false;
                break;
            }
        }
        if (draw) {
            text.setText("It's a Draw!");
            gameOver = true;
            for (JButton button : buttons) {
                button.setEnabled(false);
            }
            showRematchFrame();
        }
    }

    public void X(int a, int b, int c) {
        buttons[a].setBackground(Color.YELLOW);
        buttons[b].setBackground(Color.YELLOW);
        buttons[c].setBackground(Color.YELLOW);
        for (int i = 0; i < 9; i++) {
            buttons[i].setEnabled(false);
        }
        text.setText("X-Wins");
        gameOver = true;
        showRematchFrame();
    }

    public void O(int a, int b, int c) {
        buttons[a].setBackground(Color.YELLOW);
        buttons[b].setBackground(Color.YELLOW);
        buttons[c].setBackground(Color.YELLOW);
        for (int i = 0; i < 9; i++) {
            buttons[i].setEnabled(false);
        }
        text.setText("O-Wins");
        gameOver = true;
        showRematchFrame();
    }
    private void showRematchFrame() {
        Border border=BorderFactory.createLineBorder(new Color(0x00BCD4),3,true);
        JFrame rematchFrame = new JFrame("Tic-Tac-Toe");
        rematchFrame.setSize(400, 200);
        rematchFrame.getContentPane().setLayout(new BorderLayout());
        rematchFrame.getContentPane().setBackground(new Color(0x123456));
        rematchFrame.setResizable(false);




        JLabel rematchLabel = new JLabel("Play Again?", SwingConstants.CENTER);
        rematchLabel.setFont(new Font("Calibre", Font.BOLD, 25));
        rematchLabel.setForeground(Color.WHITE);
        rematchLabel.setBorder(border);


        rematchFrame.getContentPane().add(rematchLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(0x123456));

        JButton yesButton = new JButton("Yes");
        JButton noButton = new JButton("No");

        yesButton.setFont(new Font("Calibre", Font.BOLD, 20));
        yesButton.setBackground(new Color(0x00BCD4));
        yesButton.setFocusable(false);
        yesButton.addActionListener(e -> {
            rematchFrame.dispose();
            f.dispose();
            new tictactoe(); // restart the game
        });

        noButton.setFont(new Font("Calibre", Font.BOLD, 20));
        noButton.setBackground(new Color(0x00BCD4));
        noButton.setFocusable(false);
        noButton.addActionListener(e -> {
            rematchFrame.dispose();
            f.dispose();
            System.exit(0); // terminate the program
        });

        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        buttonPanel.setBorder(border);


        rematchFrame.getContentPane().add(buttonPanel, BorderLayout.CENTER);

        rematchFrame.setVisible(true);
    }
}