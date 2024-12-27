


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SignupFrame extends JFrame {

    private JTextField enamilFeild;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton signupButton;
    private boolean hovered = false;

    private Image backgroundImage;

    // Supabase details
    private static final String SUPABASE_URL = "https://lsvztvmtsbfjsajfehzi.supabase.co/auth/v1/signup";
    private static final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imxzdnp0dm10c2JmanNhamZlaHppIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzE4NjQ0MjMsImV4cCI6MjA0NzQ0MDQyM30.yoHW49XDquGAWNHVvb1MnFh_7DCZyw4pBGW0aD09fow";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SignupFrame() {
        
        System.out.println("debug signup frame called");
        
        setTitle("Signup");
        setSize(900, 600);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setCustomLogo();

        backgroundImage = new ImageIcon("images/signupBack.jpg").getImage();

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        mainPanel.setBounds(0, 0, getWidth(), getHeight());
        mainPanel.setLayout(null);

        enamilFeild = new JTextField();
        enamilFeild.setBounds(500, 120, 250, 55);
        enamilFeild.setFont(new Font("Arial", Font.PLAIN, 18));
        enamilFeild.setBorder(BorderFactory.createTitledBorder("Email Address"));
        

        passwordField = new JPasswordField();
        passwordField.setBounds(500, 210, 250, 55);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));
        

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(500, 300, 250, 55);
        confirmPasswordField.setFont(new Font("Arial", Font.PLAIN, 18));
        confirmPasswordField.setBorder(BorderFactory.createTitledBorder("Confirm Password"));
        
        
        
        
        enamilFeild.setBackground(new Color(187, 231, 234));
        passwordField.setBackground(new Color(187, 231, 234));
        confirmPasswordField.setBackground(new Color(187, 231, 234));
        
        
        
        
        mainPanel.add(enamilFeild);
        mainPanel.add(passwordField);
        mainPanel.add(confirmPasswordField);
        
        

        signupButton = new JButton("Sign Up") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hovered ? new Color(70, 130, 180) : new Color(100, 149, 237));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
            }
        };

        signupButton.setForeground(Color.WHITE);
        signupButton.setFont(new Font("Arial", Font.BOLD, 15));
        signupButton.setFocusPainted(false);
        signupButton.setContentAreaFilled(false);
        signupButton.setBorderPainted(false);
        signupButton.setBounds(630, 380, 120, 40);

        signupButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                hovered = true;
                signupButton.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                hovered = false;
                signupButton.repaint();
            }
        });

        signupButton.addActionListener(e -> {
            String email = enamilFeild.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                CustomOptionPane.showCustomMessageDialog(this, "All fields are required!", "Error", JOptionPane.INFORMATION_MESSAGE);
            } else if (!password.equals(confirmPassword)) {
                CustomOptionPane.showCustomMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.INFORMATION_MESSAGE);
            } else {
                
                
                
                boolean emailValid = isValidEmail(email);
                
        if (emailValid){
                
                
                boolean alreadyAUser = new SupabaseHandler("").isUserPresentByEmail(email);
                
                
                if (alreadyAUser){
                
                    CustomOptionPane.showCustomMessageDialog(this, "This Email is alredy a user", "Dual Signup", JOptionPane.INFORMATION_MESSAGE);
                
                }
                
                
                else{
                    signupUser(email, password);
                }
                
            }
                
                
        else{
        
        
                            CustomOptionPane.showCustomMessageDialog(this, "InValed Email Adress", "Dual Signup", JOptionPane.INFORMATION_MESSAGE);

        
        
        }
                
                
                
                
            }
        });

        mainPanel.add(signupButton);
        add(mainPanel);

        setVisible(true);
    }

    private void signupUser(String email, String password) {
        try {
            HashMap<String, String> data = new HashMap<>();
            data.put("email", email);
            data.put("password", password);

            String jsonData = objectMapper.writeValueAsString(data);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(SUPABASE_URL))
                    .header("Content-Type", "application/json")
                    .header("apikey", API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonData))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200 || response.statusCode() == 201) {
                CustomOptionPane.showCustomMessageDialog(this, "Verification email sent! Please check your inbox.", "Success", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("debug signup successfull , go to login page");

            new LoginSignupFrame();
            this.dispose();
            
            
            } else {
                CustomOptionPane.showCustomMessageDialog(this, "Signup failed: " + response.body(), "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            CustomOptionPane.showCustomMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    
    
    
    
    
    
    private void setCustomLogo() {
        try {
            // Load the image from resources or a file
            ImageIcon icon = new ImageIcon("images/mylogo.png"); // Replace with your logo path
            
            // Resize the image to the desired size (e.g., 32x32 pixels)
            Image resizedImage = icon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH); // Width, Height
            
            // Set the resized image as the frame icon
            setIconImage(resizedImage);
        } catch (Exception e) {
            System.err.println("Error loading or resizing logo: " + e.getMessage());
        }
    }
    
    
    
    public boolean isValidEmail(String email) {
    // Regular expression for a valid email address
    String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    // Check if the email matches the regex
    if (email == null || email.isEmpty()) {
        return false; // Null or empty email is invalid
    }

    return email.matches(emailRegex);
}
    
    
    
}
