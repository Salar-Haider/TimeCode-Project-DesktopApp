




import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class DayPanel extends JPanel {
    private CustomComboBox<String> dayComboBox;
    private ArrayList<ActivityPanel> activityPanels;
    private RoundedButton addActivityButton;
    private JPanel activityContainer;

    public DayPanel() {
        
        
        
        
        setLayout(new GridBagLayout());
        
        this.setBackground(new Color(159,221,225));
        
        
         TitledBorder titledBorder = BorderFactory.createTitledBorder("Day Details");
         
         LineBorder lineBorder = new LineBorder(new Color(80, 150 , 160), 2);
         
         titledBorder.setBorder(lineBorder);
         
        this.setBorder(titledBorder);

        // Change the title color to blue
        
        
//        setBorder(BorderFactory.createTitledBorder("Day Details"));
        activityPanels = new ArrayList<>();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(new CustomLabel("Select Day:"), gbc);

        dayComboBox = new CustomComboBox<>(new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"}  ,15);
        gbc.gridx = 1;
        add(dayComboBox, gbc);   

        addActivityButton = new RoundedButton("Add an Activity", new Color(72, 209, 204) , 10 , (float) 10.5,true);
        addActivityButton.addActionListener(e -> addActivityPanel());

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(addActivityButton, gbc);

        activityContainer = new JPanel();
        activityContainer.setLayout(new BoxLayout(activityContainer, BoxLayout.Y_AXIS));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(activityContainer, gbc);

        addActivityPanel();
    }

    private void addActivityPanel() {
        ActivityPanel activityPanel = new ActivityPanel();
        activityPanels.add(activityPanel);
        activityContainer.add(activityPanel);
        activityContainer.revalidate();
        activityContainer.repaint();
    }

    public String getDay() {
        return (String) dayComboBox.getSelectedItem();
    }

    public ArrayList<ActivityPanel> getActivityPanels() {
        return activityPanels;
    }

    public void setAddActivityButtonInvisible() {
        addActivityButton.setVisible(false);
    }
}
