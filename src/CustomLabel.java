import javax.swing.*;
import java.awt.*;

public class CustomLabel extends JLabel {

    public CustomLabel(String text) {
        // Call the constructor of the superclass (JLabel) to set the text
        super(text);

        // Set a custom font and font size (for example, "Arial" with size 16)
        this.setFont(new Font("Arial", Font.BOLD, 16));

        // Set the color of the text to gray
        setForeground(new Color(5,53,56));
    }
    
    
    
    public CustomLabel(String text , int fontSize) {
        // Call the constructor of the superclass (JLabel) to set the text
        super(text);

        // Set a custom font and font size (for example, "Arial" with size 16)
        this.setFont(new Font("Arial", Font.PLAIN, fontSize));
        
//        setHorizontalAlignment(SwingConstants.RIGHT);

        // Set the color of the text to gray
        setForeground(Color.GRAY);
    }

    
}
