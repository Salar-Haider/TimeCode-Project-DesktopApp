import javax.swing.*;
import java.awt.*;
import javax.swing.UIManager.LookAndFeelInfo;


public class CustomOptionPane extends JOptionPane {

    public static void showCustomMessageDialog(Component parent, String message, String title, int messageType) {
        
        
//        try {
//            for(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
//            
//                if("Nimbus".equals(info.getName())){
//                
//                UIManager.setLookAndFeel(info.getClassName());
//                }
//            
//            }
//        } catch (Exception e) {
//        }
        
        
        // Create a panel to customize the message text
        JPanel messagePanel = new JPanel(new BorderLayout());
        JLabel messageLabel = new JLabel(message, JLabel.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Customize font and size
        messageLabel.setForeground(new Color(50, 50, 50)); // Customize text color
        messagePanel.add(messageLabel, BorderLayout.CENTER);



        // Show the custom dialog
        JOptionPane.showMessageDialog(parent, messagePanel, title, messageType);

        // Reset UIManager properties to default after customization
        
    }

    // Test the CustomOptionPane
    
}
