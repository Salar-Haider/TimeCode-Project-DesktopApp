import javax.swing.*;
import java.awt.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.ComboBoxUI;
import java.awt.event.ActionListener;

public class CustomComboBox<E> extends JComboBox<E> {

    public CustomComboBox(E[] items  ,int fontSize) {
        // Call the super constructor to initialize the combo box with the items array
        super(items);
        
        // Set custom font, text color, and background color
        this.setFont(new Font("Arial", Font.PLAIN, fontSize)); // Custom font
        this.setForeground(new Color(22, 131, 181));  // Blue text color
        this.setBackground(Color.WHITE);  // White background color
        
        // Set a custom UI for the combo box to modify the dropdown button appearance
        this.setUI(new CustomComboBoxUI());
    }
    
    // Custom ComboBoxUI to modify dropdown button appearance
    private static class CustomComboBoxUI extends BasicComboBoxUI {
        
        @Override
        protected RoundedButton createArrowButton() {
            // Override to customize the dropdown button appearance
            RoundedButton arrowButton = new RoundedButton("",Color.WHITE, 1,1 , false);
            arrowButton.setPreferredSize(new Dimension(10, 10)); // Set a custom size for the arrow button
            arrowButton.setBackground(Color.WHITE);  // Set a custom background color for the arrow button
            arrowButton.setOpaque(true);  // Make sure the button is opaque to show its background color
            arrowButton.setBorder(BorderFactory.createLineBorder(new Color(50,181,176), 2)); // Border around the button
            arrowButton.setIcon(new ImageIcon("images/dropimg4.png"));  // Optionally set a custom icon for the dropdown arrow
            arrowButton.setFocusable(true);  // Prevent the button from gaining focus
            return arrowButton;
        }
    }

    
}
