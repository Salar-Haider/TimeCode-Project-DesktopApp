
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class CreatingPanel extends JPanel {
    private CustomTextField timetableNameField;
    private RoundedButton addDayButton,  saveButton;
    private ArrayList<DayPanel> dayPanels;
    
    private SupabaseHandler sw;
    
    private String user_ID ;
    String startTime;
    String endTime ;
    String activityName;
    
    private Home home;
    
    
    
    private Connection conn;

    public CreatingPanel(Home home, String userID)  {
        
        
        
        
        
        user_ID = userID;
        
        sw = new SupabaseHandler(user_ID);
        
        
        
        
        
        setLayout(new GridBagLayout());
        setBackground(new Color(159,221,225));
        this.setBorder(null);

        // Panel with GridBagLayout to arrange components
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);  // Padding around components

        // Timetable Name Label and TextField at the top
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(new CustomLabel("Timetable Name:"), gbc);

        timetableNameField = new CustomTextField(20, 18);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        timetableNameField.setBackground(new Color(190,240,243));
        this.add(timetableNameField, gbc);

        // Initialize the day panels list
        dayPanels = new ArrayList<>();

        // Add initial day panel
        
        DayPanel dayPanel = new DayPanel();
        dayPanels.add(dayPanel);

        
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 5, 10, 5);

        this.add(dayPanel, gbc);

        
        
//        addNewDayPanel();  

        // "Add Another Day" button on the left, "Save" button at the bottom
        addDayButton = new RoundedButton("Add Another Day", new Color(72, 209, 204) , 11 , (float) 11.5,true);
        addDayButton.addActionListener(e -> addNewDayPanel());

        saveButton = new RoundedButton("Save", new Color(72, 209, 204) , 11 , (float) 11.5,true);
        saveButton.addActionListener(new SaveAction());

        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(addDayButton, gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.EAST;
        this.add(saveButton, gbc);

        
        
    }

    private void addNewDayPanel() {
        

       this.remove(addDayButton);
       this.remove(saveButton);
       
       
       
       for (DayPanel panel1 : dayPanels){
       
       
       panel1.setAddActivityButtonInvisible();
       
       }
       
        
        
        
        
        DayPanel dayPanel = new DayPanel();
        dayPanels.add(dayPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 5, 10, 5);

        this.add(dayPanel, gbc);
        
        
        
        
        
        
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(addDayButton, gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.EAST;
        this.add(saveButton, gbc);

        
        
        
        
        
        
        this.revalidate();
        this.repaint();
    }

    private class SaveAction implements ActionListener {
        
        
        public void actionPerformed(ActionEvent e) {
            
            String timetableName = timetableNameField.getText();
            if (timetableName.trim().isEmpty()) {
                CustomOptionPane.showCustomMessageDialog(CreatingPanel.this, "Please enter a timetable name!" , "" , JOptionPane.INFORMATION_MESSAGE);
                return;
            }

                for (DayPanel dayPanel : dayPanels) {
                    String day = dayPanel.getDay();

                    for (ActivityPanel activityPanel : dayPanel.getActivityPanels()) {
                        
                       
                        startTime = activityPanel.getStartTime();
                        endTime = activityPanel.getEndTime();
                        activityName = activityPanel.getActivityName();
                        
                        
                    if (day.trim().isEmpty() || startTime.trim().isEmpty() || endTime.trim().isEmpty() || activityName.trim().isEmpty() ) {
                        CustomOptionPane.showCustomMessageDialog(CreatingPanel.this, "All feilds are required!" , "" ,JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                        
                        
                        
                        sw.insertTimetable(timetableName, day, startTime, endTime, activityName, user_ID);
                        
                        
                       
                    }
                }
                
                
                
                home.openCreatingPanel();
                

                CustomOptionPane.showCustomMessageDialog(CreatingPanel.this, "Timetable \"" + timetableName + "\" saved!" , "" , JOptionPane.INFORMATION_MESSAGE);
                
                
                
        }
    }
    
    
    
    
    
    
    private void connection(){
    
    
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/timetable_db", "root" , "");
        } catch (SQLException ex) {
            
            CustomOptionPane.showCustomMessageDialog(this,"Connection Error" , "" ,  JOptionPane.INFORMATION_MESSAGE);
        }
    
    
    
    }
    
    
    
    


}

