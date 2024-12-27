import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.LineBorder;

public class CustomTextField extends JTextField {

    public CustomTextField(int columns , int fontSize) {
        // Call the super constructor to initialize the text field with the specified number of columns
        super(columns);

        // Set font and font size
        this.setFont(new Font("Arial", Font.PLAIN, fontSize));  // Example font: Arial, size: 15

        // Set text color to blue
        this.setForeground(new Color(22, 131, 181));

        // Set the background color to white
        this.setBackground(new Color(190,240,243));

        // Optionally, set a yellow border color for a visual effect
        this.setBorder(new LineBorder(new Color(80, 150 , 160), 2)); // We'll handle the border drawing ourselves
    }

    

    
}
