
import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginSignupFrame extends JFrame {
    
    
    
    private Image bgImage = Toolkit.getDefaultToolkit().getImage("images/loginBack.jpg");

    private JTextField emailField;
    private JPasswordField passwordField;
    private RoundedButton loginButton;
    private RoundedButton signupButton;
    private JLabel messageLabel;
    private RoundedButton forgotbtn;

    // Supabase configuration
    private static final String SUPABASE_URL = "https://lsvztvmtsbfjsajfehzi.supabase.co/auth/v1/token?grant_type=password";
    private static final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imxzdnp0dm10c2JmanNhamZlaHppIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzE4NjQ0MjMsImV4cCI6MjA0NzQ0MDQyM30.yoHW49XDquGAWNHVvb1MnFh_7DCZyw4pBGW0aD09fow";
    private String userId;      // To store the user ID
    private String accessToken; // To store the access token

    public LoginSignupFrame() {
        
        setCustomLogo();
        
        System.out.println("debug login frame called");
        
        // Set custom background panel as content pane
        BackgroundPanel backgroundPanel = new BackgroundPanel(bgImage);
        setContentPane(backgroundPanel);

        // Frame settings
        setTitle("Login");
        setSize(900, 600);
        setResizable(false); // Prevent resizing
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null); // Absolute positioning

        // Initialize components
        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new RoundedButton("Login", new Color(100, 149, 237), 14, 16.5f, true); // Cornflower blue
        signupButton = new RoundedButton("Signup", new Color(72, 209, 204), 14, 16.5f, true); // Medium turquoise
        messageLabel = new JLabel(" ");
        messageLabel.setForeground(new Color(70, 70, 70)); // Soft dark color

        // Set bounds for each component (x, y, width, height)
        messageLabel.setBounds(500, 70, 300, 25);
        emailField.setBounds(500, 110, 300, 55);
        passwordField.setBounds(500, 210, 300, 55);
        loginButton.setBounds(500, 320, 140, 45);
        signupButton.setBounds(660, 320, 140, 45);

        emailField.setBackground(new Color(187, 231, 234));
        passwordField.setBackground(new Color(187, 231, 234));

        // Set text properties for email and password fields
        Font fieldFont = new Font("Arial", Font.PLAIN, 18);
        emailField.setFont(fieldFont);
        emailField.setForeground(new Color(10, 109, 116)); // Darker color than the background
        passwordField.setFont(fieldFont);
        passwordField.setForeground(new Color(10, 109, 116)); // Darker color than the background

        forgotbtn = new RoundedButton("Forgot Password", new Color(159, 221, 225), 12, 12, true);
        forgotbtn.setBounds(676, 390, 126, 30);
        forgotbtn.setBorder(null);

        // Add components to the frame
        add(messageLabel);
        add(emailField);
        add(passwordField);
        add(loginButton);
        add(signupButton);
        add(forgotbtn);

        // Set field styles
        emailField.setBorder(BorderFactory.createTitledBorder("Email"));
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));

        // Action listeners for validation
        loginButton.addActionListener(e -> validateAndLogin());
        signupButton.addActionListener(e -> validateAndSignup());
        
        this.setVisible(true);
        
    }

    private void validateAndLogin() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please enter both email and password.");
            messageLabel.setForeground(Color.RED);
            return;
        }
        
        
        
        boolean emailValid = isValidEmail(email);
        
        
        if(!emailValid){
        
            CustomOptionPane.showCustomMessageDialog(this, "InValid Email Adress", "Dual Signup", JOptionPane.INFORMATION_MESSAGE);

            return;
        }
        
        
        
        
        

        messageLabel.setText("Attempting to login...");
        messageLabel.setForeground(new Color(0, 100, 0)); // Dark green for success message

        try {
            // Build the login request
            HttpClient client = HttpClient.newHttpClient();
            HashMap<String, String> loginData = new HashMap<>();
            loginData.put("email", email);
            loginData.put("password", password);

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(loginData);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(SUPABASE_URL))
                    .header("Content-Type", "application/json")
                    .header("apikey", API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            // Send the request
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // Parse the JSON response
                JsonNode responseData = objectMapper.readTree(response.body());
                userId = responseData.get("user").get("id").asText();
                accessToken = responseData.get("access_token").asText();
                
                
                
                
                System.out.println("debug user manager called from login page");
                
                
                new UserManager().writeToUserData(userId, accessToken);
                
                
                
                
                
                
                
                
                

                messageLabel.setText("Login successful!");
                messageLabel.setForeground(new Color(0, 100, 0)); // Dark green
                
                
                
                System.out.println("debug login successfull , go to home");

                new Home(userId, accessToken);
                
                
               System.out.println("debug time to dispose login frame after ssuccessfull login");

                this.dispose();
            
            
            
            } else {
                messageLabel.setText("Invalid Email or Password");
                messageLabel.setForeground(Color.RED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("An error occurred during login.");
            messageLabel.setForeground(Color.RED);
        }
    }

    private void validateAndSignup() {
        
        
        System.out.println("debug signup button pressed");
        
        new SignupFrame();
        
        
        System.out.println("debug time to dispose login frame after signup button pressed");
        
        this.dispose();
    }

    // Custom JPanel for background handling
    private static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(Image backgroundImage) {
            this.backgroundImage = backgroundImage;
            setBackground(new Color(240, 248, 255)); // Light blueish background as default
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
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


