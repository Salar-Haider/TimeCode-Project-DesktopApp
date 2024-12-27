
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimeUpdatePanel extends JPanel {

    private CustomLabel dayLabel, startTimeLabel, endTimeLabel, startHourLabel, startMinuteLabel, endHourLabel, endMinuteLabel;
    private CustomLabel activityNameLabel; // New label
    private CustomTextField activityNameField; // New text field
    private CustomComboBox<String> dayComboBox;
    private CustomComboBox<Integer> startHourComboBox;
    private CustomComboBox<Integer> startMinuteComboBox;
    private CustomComboBox<Integer> endHourComboBox;
    private CustomComboBox<Integer> endMinuteComboBox;
    private RoundedButton applyButton;
    private String tableName;
    private String userID;
    private SupabaseHandler se;

    public TimeUpdatePanel(String Tname , String userId) {
        tableName = Tname;
        
        userID = userId;
        
        
        se = new SupabaseHandler(userID);

        setLayout(null); // Use null layout for absolute positioning
        this.setBackground(new Color(159, 221, 225));
        this.setPreferredSize(new Dimension(800, 600)); // Set the preferred size of the panel

        // Initialize components
        dayLabel = new CustomLabel("Choose the day:");
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        dayComboBox = new CustomComboBox<>(days, 16);

        // New components for activity name
        activityNameLabel = new CustomLabel("Enter the Activity Name:");
        activityNameField = new CustomTextField(20, 18);

        startTimeLabel = new CustomLabel("Enter new start time:");
        startHourComboBox = createHourComboBox();
        startMinuteComboBox = createMinuteComboBox();

        endTimeLabel = new CustomLabel("Enter new ending time:");
        endHourComboBox = createHourComboBox();
        endMinuteComboBox = createMinuteComboBox();

        startHourLabel = new CustomLabel("hr");
        startMinuteLabel = new CustomLabel("min");
        endHourLabel = new CustomLabel("hr");
        endMinuteLabel = new CustomLabel("min");

        // Set font styles for the labels
        dayLabel.setFont(new Font("Arial", Font.BOLD, 19));
        activityNameLabel.setFont(new Font("Arial", Font.BOLD, 19)); // Font for new label
        startTimeLabel.setFont(new Font("Arial", Font.BOLD, 19));
        endTimeLabel.setFont(new Font("Arial", Font.BOLD, 19));
        startHourLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        startMinuteLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        endHourLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        endMinuteLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        applyButton = new RoundedButton("Apply Changes", new Color(72, 209, 204), 14, 14.5f, true);

        // Set the position and size of each component using setBounds()

        // Title Label
        JLabel changeActivityTimingLabel = new JLabel("Change Activity Timing");
        changeActivityTimingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        changeActivityTimingLabel.setForeground(new Color(72, 209, 204));
        changeActivityTimingLabel.setBounds(280, 90, 280, 30);
        add(changeActivityTimingLabel);

        // Day selection
        dayLabel.setBounds(170, 200, 200, 30);
        dayComboBox.setBounds(440, 195, 150, 30);

        // New activity name field
        activityNameLabel.setBounds(170, 250, 250, 30); // Position below day label
        activityNameField.setBounds(440, 250, 250, 30); // Position aligned with combo box

        // Start time selection
        startTimeLabel.setBounds(170, 300, 200, 30);
        startHourComboBox.setBounds(440, 300, 50, 20);
        startHourLabel.setBounds(493, 300, 50, 20);
        startMinuteComboBox.setBounds(550, 300, 50, 20);
        startMinuteLabel.setBounds(603, 300, 50, 20);

        // End time selection
        endTimeLabel.setBounds(170, 350, 250, 30);
        endHourComboBox.setBounds(440, 350, 50, 20);
        endHourLabel.setBounds(493, 350, 50, 20);
        endMinuteComboBox.setBounds(550, 350, 50, 20);
        endMinuteLabel.setBounds(603, 350, 50, 20);

        // Apply button
        applyButton.setBounds(300, 450, 170, 40);

        // Add components to the panel
        add(dayLabel);
        add(dayComboBox);
        add(activityNameLabel); // Add new label
        add(activityNameField); // Add new text field
        add(startTimeLabel);
        add(startHourComboBox);
        add(startHourLabel);
        add(startMinuteComboBox);
        add(startMinuteLabel);
        add(endTimeLabel);
        add(endHourComboBox);
        add(endHourLabel);
        add(endMinuteComboBox);
        add(endMinuteLabel);
        add(applyButton);

        // Action listener for the Apply button
        applyButton.addActionListener(e-> applyChange());
    }
    
    
    
    private void applyChange(){
        
        
        
        if (getSelectedDay().trim().isEmpty() || getActivityName().trim().isEmpty()) {
                CustomOptionPane.showCustomMessageDialog(this, "All feilds are required!" ,"", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        
        
    
    
    boolean timeUpdate = se.updateActivityTime(userID, tableName, getSelectedDay(), getActivityName(), getStartTime(), getEndTime());
                
            
            if (timeUpdate) {
                
                dayComboBox.setSelectedItem("Monday");
                activityNameField.setText("");
                startHourComboBox.setSelectedItem(0);
                startMinuteComboBox.setSelectedItem(0);
                endHourComboBox.setSelectedItem(0);
                endMinuteComboBox.setSelectedItem(0);
                
                CustomOptionPane.showCustomMessageDialog(this, "Time updated successfully.","Success",JOptionPane.INFORMATION_MESSAGE);
                    
                } else {
                    CustomOptionPane.showCustomMessageDialog(this, "Given Crediantials donot match.","Failed",JOptionPane.INFORMATION_MESSAGE);

                }
    
    
    
    }
    
    

    private CustomComboBox<Integer> createHourComboBox() {
        Integer[] hours = new Integer[24];
        for (int i = 0; i < 24; i++) {
            hours[i] = i;
        }
        CustomComboBox<Integer> comboBox = new CustomComboBox<>(hours, 13);
        comboBox.setPreferredSize(new Dimension(50, 30)); // Narrow the width
        return comboBox;
    }

    private CustomComboBox<Integer> createMinuteComboBox() {
        Integer[] minutes = new Integer[60];
        for (int i = 0; i < 60; i++) {
            minutes[i] = i;
        }
        CustomComboBox<Integer> comboBox = new CustomComboBox<>(minutes, 13);
        comboBox.setPreferredSize(new Dimension(50, 30)); // Narrow the width
        return comboBox;
    }

    // Optional getters for selected values
    public String getSelectedDay() {
        return (String) dayComboBox.getSelectedItem();
    }

    public String getActivityName() {
        return activityNameField.getText();
    }

    public String getStartTime() {
        int hour = (int) startHourComboBox.getSelectedItem();
        int minute = (int) startMinuteComboBox.getSelectedItem();
        return String.format("%02d:%02d", hour, minute);
    }

    public String getEndTime() {
        int hour = (int) endHourComboBox.getSelectedItem();
        int minute = (int) endMinuteComboBox.getSelectedItem();
        return String.format("%02d:%02d", hour, minute);
    }
}
