
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Box;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class ActivityPanel extends JPanel {
    
    private CustomComboBox<Integer> startHourComboBox, startMinuteComboBox;
    private CustomComboBox<Integer> endHourComboBox, endMinuteComboBox;
    private CustomTextField activityField;

    public ActivityPanel() {
        
        
        
        
        this.setBackground(new Color(120,193,198));
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        
        
        
        
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Activity");
         
         LineBorder lineBorder = new LineBorder(new Color(80, 150 , 160), 2);
         
         titledBorder.setBorder(lineBorder);
         
        this.setBorder(titledBorder);
        
        
        
        
//        setBorder(BorderFactory.createTitledBorder("Activity"));

        // Activity Name at the top
        JPanel activityNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        activityNamePanel.setBackground(new Color(120,193,198));
        
        activityNamePanel.add(new CustomLabel("Activity Name:") );
        activityField = new CustomTextField(20 , 17);
        
        activityNamePanel.add(activityField);
        add(activityNamePanel);

        // Start and End Time on the next line
        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        timePanel.setBackground(new Color(120,193,198));

        // Start Time
        timePanel.add(new CustomLabel("Start Time:"));
        startHourComboBox = new CustomComboBox<>(new Integer[]{}  ,13);
        for (int i = 0; i < 24; i++) startHourComboBox.addItem(i);
        timePanel.add(startHourComboBox);
        timePanel.add(new CustomLabel("hr" , 13));

        startMinuteComboBox = new CustomComboBox<>(new Integer[]{} ,13);
        for (int i = 0; i < 60; i++) startMinuteComboBox.addItem(i);
        timePanel.add(startMinuteComboBox);
        timePanel.add(new CustomLabel("min" , 13));

        // Add space between Start Time and End Time
        timePanel.add(Box.createHorizontalStrut(20)); // Add horizontal space

        // End Time
        timePanel.add(new CustomLabel("End Time:"));
        endHourComboBox = new CustomComboBox<>(new Integer[]{} ,13);
        for (int i = 0; i < 24; i++) endHourComboBox.addItem(i);
        timePanel.add(endHourComboBox);
        timePanel.add(new CustomLabel("hr" , 13));

        endMinuteComboBox = new CustomComboBox<>(new Integer[]{} ,13);
        for (int i = 0; i < 60; i++) endMinuteComboBox.addItem(i);
        timePanel.add(endMinuteComboBox);
        timePanel.add(new CustomLabel("min", 13));

        add(timePanel);
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

    public String getActivityName() {
        return activityField.getText();
    }
}

// CustomLabel class for styling

