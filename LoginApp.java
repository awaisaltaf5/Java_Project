import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class LoginForm extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JPanel loginPanel, registerPanel;
    private JTextField loginEmailField, registerFullNameField, registerEmailField;
    private JPasswordField loginPasswordField, registerPasswordField;
    private JCheckBox termsCheckBox;
    private JButton loginButton, switchToRegisterButton, registerButton, switchToLoginButton;
    private Map<String, String> userCredentials;

    public LoginForm() {
        super("Tic Tac Toe Login");
        userCredentials = loadUserCredentials();

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        createLoginPanel();
        createRegisterPanel();

        mainPanel.add(loginPanel, "Login");
        mainPanel.add(registerPanel, "Register");

        add(mainPanel);
        cardLayout.show(mainPanel, "Login");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        setResizable(true);
    }

    private void createLoginPanel() {
        loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(33, 150, 243)); // Blue background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding between components
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START; // Align labels and fields closer

        JLabel emailLabel = new JLabel("Email Address:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 16));
        emailLabel.setForeground(Color.WHITE);
        loginPanel.add(emailLabel, gbc);

        gbc.gridx++;
        loginEmailField = new JTextField(20);
        loginEmailField.setFont(new Font("Arial", Font.PLAIN, 14));
        loginPanel.add(loginEmailField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 16));
        passwordLabel.setForeground(Color.WHITE);
        loginPanel.add(passwordLabel, gbc);

        gbc.gridx++;
        loginPasswordField = new JPasswordField(20);
        loginPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        loginPanel.add(loginPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(255, 69, 0));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginPanel.add(loginButton, gbc);

        gbc.gridy++;
        JLabel updatePasswordLabel = new JLabel("Update Password!");
        updatePasswordLabel.setForeground(Color.WHITE);
        updatePasswordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        updatePasswordLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginPanel.add(updatePasswordLabel, gbc);

        gbc.gridy++;
        switchToRegisterButton = new JButton("Sign Up");
        switchToRegisterButton.setBackground(new Color(34, 139, 34));
        switchToRegisterButton.setForeground(Color.WHITE);
        switchToRegisterButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginPanel.add(switchToRegisterButton, gbc);

        loginButton.addActionListener(e -> login());
        switchToRegisterButton.addActionListener(e -> cardLayout.show(mainPanel, "Register"));

        updatePasswordLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showUpdatePasswordDialog();
            }
        });
    }

    private void createRegisterPanel() {
        registerPanel = new JPanel(new GridBagLayout());
        registerPanel.setBackground(new Color(33, 150, 243));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START; // Align labels and fields closer

        JLabel registerLabel = new JLabel("Create Your Account");
        registerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        registerLabel.setForeground(Color.WHITE);
        gbc.gridwidth = 2;
        registerPanel.add(registerLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        JLabel fullNameLabel = new JLabel("Full Name:");
        fullNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        fullNameLabel.setForeground(Color.WHITE);
        registerPanel.add(fullNameLabel, gbc);

        gbc.gridx++;
        registerFullNameField = new JTextField(20);
        registerPanel.add(registerFullNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel emailLabel = new JLabel("Email Address:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 16));
        emailLabel.setForeground(Color.WHITE);
        registerPanel.add(emailLabel, gbc);

        gbc.gridx++;
        registerEmailField = new JTextField(20);
        registerPanel.add(registerEmailField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 16));
        passwordLabel.setForeground(Color.WHITE);
        registerPanel.add(passwordLabel, gbc);

        gbc.gridx++;
        registerPasswordField = new JPasswordField(20);
        registerPanel.add(registerPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        termsCheckBox = new JCheckBox("I accept all terms & conditions");
        termsCheckBox.setForeground(Color.RED);
        registerPanel.add(termsCheckBox, gbc);

        gbc.gridy++;
        registerButton = new JButton("Sign Up");
        registerButton.setBackground(new Color(255, 69, 0));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerPanel.add(registerButton, gbc);

        gbc.gridy++;
        switchToLoginButton = new JButton("Login");
        switchToLoginButton.setBackground(new Color(34, 139, 34));
        switchToLoginButton.setForeground(Color.WHITE);
        switchToLoginButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerPanel.add(switchToLoginButton, gbc);

        registerButton.addActionListener(e -> register());
        switchToLoginButton.addActionListener(e -> cardLayout.show(mainPanel, "Login"));
    }

    private void showUpdatePasswordDialog() {
        JTextField emailField = new JTextField();
        JPasswordField oldPasswordField = new JPasswordField();
        JPasswordField newPasswordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();

        Object[] message = {
                "Email:", emailField,
                "Old Password:", oldPasswordField,
                "New Password:", newPasswordField,
                "Confirm Password:", confirmPasswordField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Update Password", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String email = emailField.getText();
            String oldPassword = new String(oldPasswordField.getPassword());
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (userCredentials.containsKey(email) && userCredentials.get(email).equals(oldPassword)) {
                if (newPassword.equals(confirmPassword)) {
                    userCredentials.put(email, newPassword);
                    saveUserCredentials();
                    JOptionPane.showMessageDialog(this, "Password Updated Successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "New passwords do not match!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid email or old password!");
            }
        }
    }

    private void login() {
        String email = loginEmailField.getText();
        String password = new String(loginPasswordField.getPassword());
        if (userCredentials.containsKey(email) && userCredentials.get(email).equals(password)) {
            JOptionPane.showMessageDialog(this, "Login Successful!");
            dispose();
            Main.main(new String[]{});
        } else {
            JOptionPane.showMessageDialog(this, "Invalid email or password!");
        }
    }

    private void register() {
        String email = registerEmailField.getText();
        String password = new String(registerPasswordField.getPassword());
        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Invalid email format.");
            return;
        }
        if (!termsCheckBox.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please accept the terms & conditions!");
            return;
        }
        if (!userCredentials.containsKey(email)) {
            userCredentials.put(email, password);
            saveUserCredentials();
            JOptionPane.showMessageDialog(this, "Registration Successful!");
            cardLayout.show(mainPanel, "Login");
        } else {
            JOptionPane.showMessageDialog(this, "Email already exists.");
        }
    }

    private boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(regex);
    }

    private Map<String, String> loadUserCredentials() {
        Map<String, String> credentials = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("user_credentials.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) credentials.put(parts[0], parts[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return credentials;
    }

    private void saveUserCredentials() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("user_credentials.txt"))) {
            for (Map.Entry<String, String> entry : userCredentials.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new LoginForm();
    }
}
