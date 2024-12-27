//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//
//public class HomePanel extends JPanel {
//
//    private Image backgroundImage;  // Variable to hold background image
//
//    public HomePanel() {
//        // Set the layout to null for absolute positioning
//        setLayout(null);
//        this.setBorder(null);
//
//        // Set the size of the panel
//        setSize(900, 650);
//
//        // Load custom background image (ensure the path is correct)
//        backgroundImage = new ImageIcon("images/homeBack.jpg").getImage();  // Replace with your image path
//
//        // Create the "Get Started" button
//        RoundedButton getStartedButton = new RoundedButton("Get Started" , new Color(131, 197, 201), 16 , 17,true) {
//            @Override
//            protected void paintComponent(Graphics g) {
//                Graphics2D g2 = (Graphics2D) g;
//                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//                
//                if (getModel().isRollover()) {
//                    g2.setColor(new Color(70, 130, 180).darker());  // Darker steel blue on hover
//                } else {
//                    g2.setColor(new Color(70, 130, 180));  // Steel blue
//                }
//
//                // Draw rounded rectangle for the button
//                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
//                super.paintComponent(g);
//            }
//        };
//
//        
//
//        // Set button size and position to center it on the panel
//        getStartedButton.setBounds(500, 380, 150, 50);  // Center the button at (375, 300)
//        
//        
//
//        // Add mouse listener to change cursor and repaint on hover
//        getStartedButton.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseEntered(MouseEvent e) {
//                setCursor(new Cursor(Cursor.HAND_CURSOR));
//                getStartedButton.setBackground(new Color(113, 170, 174));
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//                getStartedButton.setBackground(new Color(131, 197, 201));
//            }
//        });
//
//        // Add the button to the panel
//        add(getStartedButton);
//    }
//
//    // Override paintComponent to draw the background image
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//
//        if (backgroundImage != null) {
//            // Set high quality rendering hints for the image scaling
//            Graphics2D g2d = (Graphics2D) g;
//            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
//            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//            // Scale the image to fit the panel's size while preserving quality
//            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
//        }
//    }
//}
//




























import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class HomePanel extends JPanel {
    private BufferedImage backgroundImage;
    private BufferedImage scaledImage;

    public HomePanel(Home parent) {
        setLayout(null);
        setSize(900, 650);

        try {
            backgroundImage = ImageIO.read(new File("images/homeBack.jpg")); // Update with your image path
        } catch (IOException e) {
            e.printStackTrace();
        }

        JButton getStartedButton = new JButton("Get Started");
        getStartedButton.setBounds(500, 380, 150, 50);
        getStartedButton.setBackground(new Color(131, 197, 201));
        getStartedButton.setForeground(Color.WHITE);
        getStartedButton.setFont(new Font("Arial", Font.BOLD, 16));
        getStartedButton.setFocusPainted(false);
        getStartedButton.setBorderPainted(false);
        getStartedButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        getStartedButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                getStartedButton.setBackground(new Color(113, 170, 174));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                getStartedButton.setBackground(new Color(131, 197, 201));
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                parent.openCreatingPanel();
            }
        });

        add(getStartedButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            int width = getWidth();
            int height = getHeight();
            if (scaledImage == null || scaledImage.getWidth() != width || scaledImage.getHeight() != height) {
                scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = scaledImage.createGraphics();
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                g2d.drawImage(backgroundImage, 0, 0, width, height, null);
                g2d.dispose();
            }
            g.drawImage(scaledImage, 0, 0, null);
        }
    }
}
