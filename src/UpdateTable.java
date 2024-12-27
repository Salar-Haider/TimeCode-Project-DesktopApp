

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateTable extends JPanel {
    private CustomTextField tableNameField;
    private RoundedButton changeActivityNameButton;
    private RoundedButton changeTimingButton;
    private RoundedButton deleteActivityButton;
    private RoundedButton deleteTableButton;
    private SupabaseHandler sd;
    private String userID;

    public UpdateTable(String userId) {
        
        userID = userId;
        
        sd = new SupabaseHandler(userID);
        
        
        setLayout(new GridBagLayout());
        setBackground(new Color(159, 221, 225));

        // Setting up layout constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Big label at the top displaying "Update Table" - position it towards the top center
        JLabel updateTableLabel = new JLabel("Update Table");
        updateTableLabel.setFont(new Font("Arial", Font.BOLD, 26));  // Large font size
        updateTableLabel.setForeground(new Color(59, 172, 168));  // Color for the label
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;  // Span across both columns
        gbc.insets = new Insets(30, 10, 10, 10); // Add some top margin to move the label up
        gbc.anchor = GridBagConstraints.CENTER;  // Center the label
        add(updateTableLabel, gbc);

        // Label for table name with increased font size
        CustomLabel tableNameLabel = new CustomLabel("Enter Table Name:");
        tableNameLabel.setFont(new Font("Arial", Font.PLAIN, 18));  // Increased font size
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10); // Resetting insets for the table name label
        add(tableNameLabel, gbc);

        // Text field for entering table name with increased font size
        tableNameField = new CustomTextField(20, 18);
        tableNameField.setFont(new Font("Arial", Font.PLAIN, 18));  // Increased font size
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(tableNameField, gbc);

        // Button for changing activity name with increased font size
        changeActivityNameButton = new RoundedButton("Change Activity Name", new Color(72, 209, 204), 17, 17.5f, true);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(changeActivityNameButton, gbc);

        // Button for changing timing of an activity with increased font size
        changeTimingButton = new RoundedButton("Change Timing of an Activity", new Color(72, 209, 204), 17, 17.5f, true);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(changeTimingButton, gbc);

        // Button for deleting an activity with increased font size
        deleteActivityButton = new RoundedButton("Delete an Activity", new Color(72, 209, 204), 17, 17.5f, true);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(deleteActivityButton, gbc);

        // Button for deleting the entire table with increased font size
        deleteTableButton = new RoundedButton("Delete the Table", new Color(72, 209, 204), 17, 17.5f, true);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        add(deleteTableButton, gbc);

        // Action listener for the "Change Activity Name" button
        changeActivityNameButton.addActionListener(e -> this.updateActivityName());

        // Action listener for the "Change Timing" button
        changeTimingButton.addActionListener(e -> this.updateActivityTime());

        // Action listener for the "Delete Activity" button
        deleteActivityButton.addActionListener(e -> this.deleteActivity());
        
        
        deleteTableButton.addActionListener(e -> this.deleteTable());
        
    }

    // Getters for the input field and buttons, in case you need them for event handling
    private void updateActivityName() {
        
        
        
        if (tableNameField.getText().trim().isEmpty()) {
        CustomOptionPane.showCustomMessageDialog(this, "Please enter the table name before proceeding.", "Input Error", JOptionPane.ERROR_MESSAGE);
        return;
        }

        
        
        
        
        Container parentContainer = getParent();

        // Remove all components from the parent container (clearing the panel)
        parentContainer.removeAll();

        // Create the ChangeActivityName panel and add it to the parent container
        ChangeActivityName changeActivityNamePanel = new ChangeActivityName(tableNameField.getText() , userID);
        parentContainer.add(changeActivityNamePanel);

        // Revalidate and repaint the parent container to update the UI
        parentContainer.revalidate();
        parentContainer.repaint();
        
        
        
        
    }

    private void updateActivityTime() {
        
        
        if (tableNameField.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter the table name before proceeding.", "Input Error", JOptionPane.ERROR_MESSAGE);
        return;
        }
        
        
        
        
        Container parentContainer = getParent();

        // Remove all components from the parent container (clearing the panel)
        parentContainer.removeAll();

        // Create the ChangeActivityTime panel and add it to the parent container
        TimeUpdatePanel changeActivityTimePanel = new TimeUpdatePanel(tableNameField.getText() , userID);
        parentContainer.add(changeActivityTimePanel, BorderLayout.CENTER);

        // Revalidate and repaint the parent container to update the UI
        parentContainer.revalidate();
        parentContainer.repaint();
    }

    private void deleteActivity() {
        
        
        if (tableNameField.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter the table name before proceeding.", "Input Error", JOptionPane.ERROR_MESSAGE);
        return;
        }
        
        
        
        Container parentContainer = getParent();

        // Remove all components from the parent container (clearing the panel)
        parentContainer.removeAll();

        // Create the DeleteActivity panel and add it to the parent container
        DeleteActivity deleteActivityPanel = new DeleteActivity(tableNameField.getText() , userID);
        parentContainer.add(deleteActivityPanel);

        // Revalidate and repaint the parent container to update the UI
        parentContainer.revalidate();
        parentContainer.repaint();
    }
    
    
    
    
    private void deleteTable(){
        
        if (tableNameField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a timetable name!");
                return;
            }
    
    
    boolean deleted = sd.deleteTimetable(userID, tableNameField.getText());
    
    
    if (deleted) {
        tableNameField.setText("");
        JOptionPane.showMessageDialog(this, "Timetable "+ tableNameField.getText() +" is deleted successfully.");
        
    } else {
        JOptionPane.showMessageDialog(this, "Given Crediantials donot match.");

    }
    
    
    
    }
    
    
    
}
