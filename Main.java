import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
import javax.sound.sampled.*;

public class Main {
    private static boolean isMusicMuted = false;
    private static Clip musicClip;

    public static void main(String[] args) {
        playMusic(); // Start playing music at launch
        showInitialLoadingFrame();
    }

    private static void showInitialLoadingFrame() {
        JFrame initialLoadingFrame = new JFrame("Tic-Tac-Toe");
        initialLoadingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initialLoadingFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        initialLoadingFrame.setSize(800, 800);
        initialLoadingFrame.getContentPane().setLayout(new BorderLayout());
        initialLoadingFrame.getContentPane().setBackground(new Color(0x2E3B4E)); // Darker background for contrast
        initialLoadingFrame.setResizable(true);

        JLabel loadingLabel = new JLabel("Initializing...", SwingConstants.CENTER);
        loadingLabel.setFont(new Font("Calibre", Font.BOLD, 30));
        loadingLabel.setForeground(Color.WHITE);
        initialLoadingFrame.getContentPane().add(loadingLabel, BorderLayout.CENTER);

        initialLoadingFrame.setVisible(true);

        Timer timer = new Timer(2000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                initialLoadingFrame.dispose();
                new BeautifulProgressBarFrame(() -> showMainMenu());
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private static void showMainMenu() {
        Border border = BorderFactory.createLineBorder(Color.YELLOW, 9, false);
        JFrame menuFrame = new JFrame("Tic-Tac-Toe");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        menuFrame.setSize(800, 800);
        menuFrame.getContentPane().setLayout(new BorderLayout());
        menuFrame.getContentPane().setBackground(new Color(0x2E3B4E)); // Darker background for contrast
        menuFrame.setResizable(true);

        // Title Panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(0x2E3B4E));

        JLabel titleLabel = new JLabel("TIC-TAC-TOE", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Calibre", Font.BOLD, 40));
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setBorder(border);
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // Music Toggle Button Wrapper at top-right corner
        JButton musicToggleButton = new JButton("🔊 Mute");
        musicToggleButton.setFont(new Font("Calibre", Font.BOLD, 18));
        musicToggleButton.setBackground(Color.YELLOW);
        musicToggleButton.setForeground(new Color(0x2E3B4E));
        musicToggleButton.setFocusable(false);
        musicToggleButton.setPreferredSize(new Dimension(120, 60));

        // Set the button position to top-right corner
        JPanel musicPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        musicPanel.setBackground(new Color(0x2E3B4E));
        musicPanel.add(musicToggleButton);

        titlePanel.add(musicPanel, BorderLayout.EAST); // Adding the music button to the title panel at the right

        menuFrame.getContentPane().add(titlePanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(0x2E3B4E));
        buttonPanel.setLayout(new GridLayout(2, 1, 0, 0)); // 2 rows, 1 column to fit buttons

        // 1 vs 1 Button Wrapper
        JPanel oneVsOneWrapper = new JPanel();
        oneVsOneWrapper.setBackground(new Color(0x2E3B4E));
        JButton oneVsOneButton = new JButton("1 vs 1");
        oneVsOneButton.setFont(new Font("Calibre", Font.BOLD, 20));
        oneVsOneButton.setBackground(Color.YELLOW);
        oneVsOneButton.setForeground(new Color(0x2E3B4E));
        oneVsOneButton.setFocusable(false);
        oneVsOneButton.setPreferredSize(new Dimension(200, 60));
        oneVsOneWrapper.add(oneVsOneButton);
        buttonPanel.add(oneVsOneWrapper);

        // 1 vs Computer Button Wrapper
        JPanel oneVsComputerWrapper = new JPanel();
        oneVsComputerWrapper.setBackground(new Color(0x2E3B4E));
        JButton oneVsComputerButton = new JButton("1 vs Computer");
        oneVsComputerButton.setFont(new Font("Calibre", Font.BOLD, 20));
        oneVsComputerButton.setBackground(Color.YELLOW);
        oneVsComputerButton.setForeground(new Color(0x2E3B4E));
        oneVsComputerButton.setFocusable(false);
        oneVsComputerButton.setPreferredSize(new Dimension(200, 60));
        oneVsComputerWrapper.add(oneVsComputerButton);
        buttonPanel.add(oneVsComputerWrapper);

        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.YELLOW, 9), // colored border
                BorderFactory.createEmptyBorder(200, 200, 200, 200) // padding
        ));

        menuFrame.getContentPane().add(buttonPanel, BorderLayout.CENTER);

        // Action listeners for buttons
        oneVsOneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                showGameLoadingFrame(() -> new tictactoe());
            }
        });

        oneVsComputerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                showGameLoadingFrame(() -> new tictactoe2());
            }
        });

        musicToggleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toggleMusic(musicToggleButton);
            }
        });

        menuFrame.setVisible(true);
    }

    private static void toggleMusic(JButton musicToggleButton) {
        if (isMusicMuted) {
            isMusicMuted = false;
            musicToggleButton.setText("🔊 Mute");
            playMusic();
        } else {
            isMusicMuted = true;
            musicToggleButton.setText("🔇 Unmute");
            stopMusic();
        }
    }

    private static void playMusic() {
        try {
            if (musicClip == null || !musicClip.isRunning()) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(Main.class.getResource("music.wav"));
                musicClip = AudioSystem.getClip();
                musicClip.open(audioStream);
                musicClip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void stopMusic() {
        if (musicClip != null && musicClip.isRunning()) {
            musicClip.stop();
        }
    }

    private static void showGameLoadingFrame(Runnable onComplete) {
        JFrame loadingFrame = new JFrame("Tic-Tac-Toe");
        loadingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loadingFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        loadingFrame.setSize(800, 800);
        loadingFrame.getContentPane().setLayout(new BorderLayout());
        loadingFrame.getContentPane().setBackground(new Color(0x2E3B4E));
        loadingFrame.setResizable(true);

        JLabel loadingLabel = new JLabel("Loading...", SwingConstants.CENTER);
        loadingLabel.setFont(new Font("Calibre", Font.BOLD, 30));

        loadingLabel.setForeground(Color.WHITE);
        loadingFrame.getContentPane().add(loadingLabel, BorderLayout.CENTER);

        loadingFrame.setVisible(true);

        Timer timer = new Timer(2000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadingFrame.dispose();
                onComplete.run();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
}
