// class name BeautifulProgressBarFrame
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class BeautifulProgressBarFrame extends JFrame {
    private final Runnable onComplete;

    public BeautifulProgressBarFrame(Runnable onComplete) {
        this.onComplete = onComplete;
        initialize();
    }

    private void initialize() {
        setTitle("Tic-Tac-Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the window
        setResizable(true);

        BeautifulProgressBar progressBar = new BeautifulProgressBar(onComplete);
        add(progressBar, BorderLayout.CENTER);

        setVisible(true);
        progressBar.startAnimation();
    }
}

class BeautifulProgressBar extends JPanel {
    private int progress = 0;
    private final Runnable onComplete;
    private JLabel loadingLabel;

    public BeautifulProgressBar(Runnable onComplete) {
        this.onComplete = onComplete;
        setBackground(new Color(0x123456));
        setLayout(null);

        loadingLabel = new JLabel("Loading....", SwingConstants.CENTER);
        loadingLabel.setFont(new Font("Calibre", Font.BOLD, 20));
        loadingLabel.setForeground(Color.WHITE);
        loadingLabel.setBounds(298, 350, 700, 40);
        this.add(loadingLabel);
    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;


        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int barWidth = width - 40;
        int barHeight = 30;


        g2d.setColor(new Color(0xCCCCCC));
        g2d.fillRoundRect(20, height / 2 - barHeight / 2, barWidth, barHeight, 15, 15);


        GradientPaint gradient = new GradientPaint(0, 0, new Color(0x00BCD4), barWidth, 0, new Color(0x0097A7));
        g2d.setPaint(gradient);
        g2d.fillRoundRect(20, height / 2 - barHeight / 2, (int) (barWidth * progress / 100.0), barHeight, 15, 15);

        g2d.setFont(new Font("Calibre", Font.BOLD, 20));
        g2d.setColor(Color.WHITE);
        String progressText = progress + "%";
        int textWidth = g2d.getFontMetrics().stringWidth(progressText);
        g2d.drawString(progressText, width / 2 - textWidth / 2, height / 2 + 5);
    }

    public void startAnimation() {
        Timer timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                progress = Math.min(progress + 1, 100);
                repaint();

                if (progress == 100) {
                    ((Timer) e.getSource()).stop();
                    onComplete.run();
                }
            }
        });
        timer.start();
    }
}