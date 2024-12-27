
import com.fasterxml.jackson.databind.JsonNode;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class TimetableDisplayPanel extends JPanel {
    private JScrollPane scrollPane;
    private Connection conn;
    private String userID ;

    public TimetableDisplayPanel(String userId) {
        
        userID = userId;
        
        setLayout(new BorderLayout()); // Use BorderLayout for easy placement of components
        setBackground(Color.WHITE);

        // Create the main timetable panel where timetables will be added
        JPanel timetablePanel = new JPanel();
        timetablePanel.setLayout(new BoxLayout(timetablePanel, BoxLayout.Y_AXIS)); // Vertical layout for timetable sections
        timetablePanel.setBackground(Color.WHITE);  // Ensure the background is white

        // Load the timetables into the display panel
        loadTimetables(timetablePanel);

        // Wrap timetablePanel in a JScrollPane for scrolling
        scrollPane = new JScrollPane(timetablePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  // Always show vertical scrollbar
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadTimetables(JPanel timetablePanel) {
        
            SupabaseHandler sf = new SupabaseHandler(userID);
            
            
            JsonNode jsonResponse = sf.fetchTimetables();
            

            String currentTimetable = "";
            String currentDay = "";
            JPanel currentTimetablePanel = null;
            JPanel currentDayPanel = null;
            
            
            
            
            if (jsonResponse.isEmpty()){
            
            CustomOptionPane.showCustomMessageDialog(this, "No time Table found in the Records" , "NO DATA FOUND" , JOptionPane.OK_OPTION);
            
            return;
            }


                
                for (JsonNode node : jsonResponse) {
                    
                    
                    
                    
                    String timetableName = node.path("timetable_name").asText();
                    String day = node.path("day").asText();
                    String startTime = node.path("start_time").asText();
                    String endTime = node.path("end_time").asText();
                    String activityName = node.path("activity_name").asText();
                    
                    
                    
                    

                    // Check if we're on a new timetable
                    if (!timetableName.equals(currentTimetable)) {
                        currentTimetable = timetableName;
                        currentDay = ""; // Reset day tracker for a new timetable

                        // Create and add a new timetable panel
                        currentTimetablePanel = new JPanel();
                        
                        currentTimetablePanel.setBackground(new Color(235, 255, 254));
                        
                        currentTimetablePanel.setLayout(new BoxLayout(currentTimetablePanel, BoxLayout.Y_AXIS));
                        currentTimetablePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),
                                "Timetable: " + timetableName, 0, 0, new Font("Arial", Font.BOLD, 14), Color.BLACK));
                        timetablePanel.add(currentTimetablePanel);
                    }

                    // Check if we're on a new day within the same timetable
                    if (!day.equals(currentDay)) {
                        currentDay = day;

                        // Create and add a new day panel
                        currentDayPanel = new JPanel();
                        
                        currentDayPanel.setBackground(new Color(235, 255, 254));
                        
                        
                        
                        currentDayPanel.setLayout(new BoxLayout(currentDayPanel, BoxLayout.Y_AXIS));
                        currentDayPanel.setBorder(BorderFactory.createTitledBorder(day));
                        
                        
                        
                        currentTimetablePanel.add(currentDayPanel);
                    }

                    // Add activity details to the current day panel
                    JPanel activityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    
                    
                    activityPanel.setBackground(new Color(196, 255, 251));
                    
                    
                    
                    CustomLabel activityLabel = new CustomLabel(" >  "+activityName +"        TIMING : ( "+ startTime + " - " + endTime+" )"  );
                    
                    
                    
                    
                    activityLabel.setBackground(new Color(217, 252, 250));
                    
                    activityPanel.add(activityLabel);
                    currentDayPanel.add(activityPanel);
                    
                }
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
            

        
    }

    
}
























