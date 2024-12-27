import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteActivity extends JPanel {

    private CustomLabel dayLabel;
    private CustomComboBox<String> dayComboBox;
    private CustomLabel activityLabel;
    private CustomTextField activityTextField;
    private RoundedButton deleteButton;
    private String tableName;
    private String userID;
    private SupabaseHandler sd;

    public DeleteActivity(String Tname , String userId) {
        
        tableName = Tname;
        
        userID = userId;
        
        sd = new SupabaseHandler(userID);
        
        setLayout(new GridBagLayout());
        this.setBackground(new Color(159,221,225));
        
        

        // Initialize components
        dayLabel = new CustomLabel("Choose the day:");
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        dayComboBox = new CustomComboBox<>(days , 16);

        activityLabel = new CustomLabel("Enter activity name:");
        activityTextField = new CustomTextField(20 , 19);

        deleteButton = new RoundedButton("Delete Activity", new Color(72, 209, 204) , 14 , (float) 14.5,true);
        
        
        dayLabel.setFont(new Font("Arial", Font.BOLD, 19));
        activityLabel.setFont(new Font("Arial", Font.BOLD, 19));
        

        // Set up layout constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add components to panel
        // Day choice label and combo box
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(dayLabel, gbc);
        gbc.gridx = 1;
        add(dayComboBox, gbc);

        // Activity label and text field
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(activityLabel, gbc);
        gbc.gridx = 1;
        add(activityTextField, gbc);

        // Delete button
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(deleteButton, gbc);

        // Action listener for button
        deleteButton.addActionListener(e-> deleteActivityMethod());
    }
    
    
    
    private void deleteActivityMethod(){
        
        
        
        
        if (getActivityName().trim().isEmpty()) {
                CustomOptionPane.showCustomMessageDialog(this, "Please enter Activity name!" , "" , JOptionPane.INFORMATION_MESSAGE);
                return;
            }
    
    
    
    boolean deleted = sd.deleteActivity(userID, tableName, getSelectedDay(), getActivityName());
    
    
    if (deleted) {
        
        dayComboBox.setSelectedItem("Monday");
        activityTextField.setText("");
        
        
        CustomOptionPane.showCustomMessageDialog(this, "Activity ("+ getActivityName() +") deleted successfully." , "" , JOptionPane.INFORMATION_MESSAGE);
                    
    } else {
        CustomOptionPane.showCustomMessageDialog(this, "Given Crediantials donot match." , ""  , JOptionPane.INFORMATION_MESSAGE);

    }
    
    
    
    
    }
    
    
    
    

    // Optional getters for selected values
    public String getSelectedDay() {
        return (String) dayComboBox.getSelectedItem();
    }

    public String getActivityName() {
        return activityTextField.getText();
    }
}
