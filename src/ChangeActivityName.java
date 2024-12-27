



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeActivityName extends JPanel {

    // Declare components
    private CustomLabel dayLabel;
    private CustomComboBox<String> dayComboBox;  // Changed to JComboBox
    private CustomLabel prevActivityLabel;
    private CustomTextField prevActivityTextField;
    private CustomLabel newActivityLabel;
    private CustomTextField newActivityTextField;
    private RoundedButton applyButton;
    private String timeTableName;
    private SupabaseHandler se;
    private String userID;

    public ChangeActivityName(String Tname , String userId) {

        timeTableName = Tname;
        
        userID  = userId;
        
        se = new SupabaseHandler(userID);
        
        

        // Set layout for the panel
        setLayout(new GridBagLayout());  // Using GridBagLayout
        this.setBackground(new Color(159, 221, 225));

        // Initialize components
        dayLabel = new CustomLabel("Choose the day:");
        dayLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        // Create the combo box with days of the week
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        dayComboBox = new CustomComboBox<>(days , 20);

        prevActivityLabel = new CustomLabel("Enter previous activity name:");
        prevActivityTextField = new CustomTextField(20, 20);
        prevActivityLabel.setFont(new Font("Arial", Font.BOLD, 20));
        

        newActivityLabel = new CustomLabel("Enter new activity name:");
        
        newActivityLabel.setFont(new Font("Arial", Font.BOLD, 20));
        newActivityTextField = new CustomTextField(20, 20);
        

        applyButton = new RoundedButton("Apply Changes", new Color(72, 209, 204), 15, (float) 15, true);

        // Setting up GridBagConstraints for the layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Space between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add "Change Activity Name" label at the top (centered)
        JLabel changeActivityNameLabel = new JLabel("         Change Activity Name");
        changeActivityNameLabel.setFont(new Font("Arial", Font.BOLD, 24));  // Large font size
        changeActivityNameLabel.setForeground(new Color(72, 209, 204));  // Color for the label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;  // Span across both columns
        gbc.insets = new Insets(30, 10, 10, 10); // Add some top margin
        gbc.anchor = GridBagConstraints.CENTER;  // Center the label
        add(changeActivityNameLabel, gbc);

        // Add day label and combo box
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(dayLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(dayComboBox, gbc);

        // Add previous activity label and text field
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(prevActivityLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(prevActivityTextField, gbc);

        // Add new activity label and text field
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(newActivityLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        add(newActivityTextField, gbc);

        // Add apply button (spanning both columns)
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;  // Make the button span both columns
        add(applyButton, gbc);

        // Action listener for the button
        applyButton.addActionListener(e-> applyChange());
    }
    
    
    private void applyChange(){
        
        
        if (getDayName().trim().isEmpty() || getPrevActivityName().trim().isEmpty() || getNewActivityName().trim().isEmpty()) {
                CustomOptionPane.showCustomMessageDialog(this, "All feilds are required!","",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
    
    
     boolean editted = se.updateActivityName(userID, timeTableName, getDayName(), getPrevActivityName(), getNewActivityName());
                
                
                if (editted) {
                    
                    dayComboBox.setSelectedItem("Monday");
                    prevActivityTextField.setText("");
                    newActivityTextField.setText("");
                    
                    CustomOptionPane.showCustomMessageDialog(this, "Activity name updated successfully.", "",JOptionPane.INFORMATION_MESSAGE);
                    
                } else {
                    CustomOptionPane.showCustomMessageDialog(this, "Given Crediantials donot match.", "", JOptionPane.INFORMATION_MESSAGE);

                }
    
    
    }
    

    // Optional: Methods to get the values from the fields if needed
    public String getDayName() {
        return (String) dayComboBox.getSelectedItem();
    }

    public String getPrevActivityName() {
        return prevActivityTextField.getText();
    }

    public String getNewActivityName() {
        return newActivityTextField.getText();
    }
}
