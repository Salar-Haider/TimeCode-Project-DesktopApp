
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Home extends JFrame {
    private RoundedButton createTimetableButton, viewTimetableButton, homeButton, updateButton, exitButton, SignOutButton;
    private JPanel sideMenu;
    private RoundedButton menuButton;
    private boolean menuVisible = false;
    private int menuWidth = 200;
    private Timer slideTimer;
    private JPanel upperPanel;
    private JPanel lowerPanel;
    private JLayeredPane layeredPane;
    private String userID, accessToken;
    int homeCalled = 1;

    public Home(String userId , String accessToken) {
        
        
        
        userID = userId;
        
        accessToken = accessToken;
        
        
        System.out.println("debug home called, no "+homeCalled);
        homeCalled++;
        
        setCustomLogo();
        
        
        
        
        System.out.println("user id :  "+ userID);
        System.out.println("user  token :  "+ accessToken);
        
        
        
        
        layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        this.setContentPane(layeredPane);

        sideMenu = new JPanel();
        sideMenu.setLayout(null);
        sideMenu.setSize(menuWidth, 600);
        sideMenu.setBackground(new Color(108, 188, 193));
        sideMenu.setVisible(true);

        createTimetableButton = new RoundedButton("Create Time Table", new Color(72, 209, 204), 12, 13, true);
        viewTimetableButton = new RoundedButton("View Time Tables", new Color(72, 209, 204), 12, 13, true);
        homeButton = new RoundedButton("Home", new Color(72, 209, 204), 12, 13, true);
        updateButton = new RoundedButton("Update Time Table", new Color(72, 209, 204), 12, 13, true);
        exitButton = new RoundedButton("Exit", new Color(72, 209, 204), 12, 13, true);
        SignOutButton = new RoundedButton("Sign Out", new Color(72, 209, 204), 12, 13, true);

        homeButton.setBounds(20, 60, 150, 30);
        createTimetableButton.setBounds(20, 110, 150, 30);
        viewTimetableButton.setBounds(20, 160, 150, 30);
        updateButton.setBounds(20, 210, 150, 30);
        SignOutButton.setBounds(20, 260, 150, 30);
        exitButton.setBounds(20, 310, 150, 30);

        sideMenu.add(homeButton);
        sideMenu.add(createTimetableButton);
        sideMenu.add(viewTimetableButton);
        sideMenu.add(updateButton);
        sideMenu.add(SignOutButton);
        sideMenu.add(exitButton);

        
        
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openHome();
                triggerMenuToggle();
            }
        });
        
        
        
        createTimetableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                openCreatingPanel();
                triggerMenuToggle();
            }
        });
        
        
        
        
        viewTimetableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                openDisplayPanel();
                triggerMenuToggle();
            }
        });
        
        
        
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                openUpdateTablePanel();
                triggerMenuToggle();
            }
        });
        
        
        SignOutButton.addActionListener(e -> this.signOutAction());
        
        
        exitButton.addActionListener(e -> System.exit(0));
        
        

        initializeFrame();
        initializeUpperPanel();
        initializeLowerPanel();
        initializeMenuButton();
        initializeSideMenu();

        this.setVisible(true);
    }

    private void initializeFrame() {
        this.setTitle("Time Code");
        this.setSize(900, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
    }

    private void initializeUpperPanel() {
        upperPanel = new JPanel();
        upperPanel.setBounds(0, 0, this.getWidth(), 50);
        upperPanel.setLayout(null);
        upperPanel.setBackground(new Color(159, 221, 225));
        upperPanel.setBorder(null);
        layeredPane.add(upperPanel, Integer.valueOf(2));
    }

    private void initializeLowerPanel() {
        lowerPanel = new JPanel();
        lowerPanel.setBounds(0, 50, this.getWidth() - 15, this.getHeight() - 85);
        lowerPanel.setLayout(new BorderLayout());
        layeredPane.add(lowerPanel, Integer.valueOf(0));
        lowerPanel.setBorder(null);
        lowerPanel.add(new HomePanel(this), BorderLayout.CENTER);
    }

    private void initializeMenuButton() {
        menuButton = new RoundedButton("☰", new Color(72, 209, 204), 16, 16, true);
        menuButton.setBounds(10, 10, 50, 30);
        menuButton.addActionListener(e -> triggerMenuToggle());
        upperPanel.add(menuButton);
    }

    private void initializeSideMenu() {
        sideMenu.setBounds(-menuWidth, 0, menuWidth, this.getHeight());
        layeredPane.add(sideMenu, Integer.valueOf(1));
    }

    public void triggerMenuToggle() {
        if (slideTimer != null && slideTimer.isRunning()) {
            slideTimer.stop();
        }

        if (menuVisible) {
            slideOutMenu();
        } else {
            slideInMenu();
        }
    }

    private void slideInMenu() {
        slideTimer = new Timer(10, e -> {
            int newX = sideMenu.getX() + 8;
            if (newX >= 0) {
                newX = 0;
                menuVisible = true;
                slideTimer.stop();
            }
            sideMenu.setLocation(newX, 0);
            this.repaint();
        });
        slideTimer.start();
    }

    private void slideOutMenu() {
        slideTimer = new Timer(10, e -> {
            int newX = sideMenu.getX() - 8;
            if (newX <= -menuWidth) {
                newX = -menuWidth;
                menuVisible = false;
                slideTimer.stop();
            }
            sideMenu.setLocation(newX, 0);
            this.repaint();
        });
        slideTimer.start();
    }

    public void openCreatingPanel() {



        lowerPanel.removeAll();
        CreatingPanel creatingPanel = new CreatingPanel(this , userID);
        JScrollPane scrollPane = new JScrollPane(creatingPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);
        lowerPanel.add(scrollPane, BorderLayout.CENTER);
        lowerPanel.revalidate();
        lowerPanel.repaint();
        



    }

    private void openDisplayPanel() {




        lowerPanel.removeAll();
        lowerPanel.add(new TimetableDisplayPanel(userID), BorderLayout.CENTER);
        lowerPanel.revalidate();
        lowerPanel.repaint();






    }

    private void openUpdateTablePanel() {


        lowerPanel.removeAll();
        lowerPanel.add(new UpdateTable(userID), BorderLayout.CENTER);
        lowerPanel.revalidate();
        lowerPanel.repaint();



    }

    private void openHome() {
        lowerPanel.removeAll();
        lowerPanel.add(new HomePanel(this), BorderLayout.CENTER);
        lowerPanel.revalidate();
        lowerPanel.repaint();
    }
    
    
    private void signOutAction() {
        
    
    System.out.println("debug signout called , home disposed");
    new UserManager().clearCSVFile();
    
    this.dispose();
    
    
// Dispose the current frame
   
    // Schedule the creation of the LoginSignupFrame to run after disposal
    new LoginSignupFrame();
}


    
    
    
    private void setCustomLogo() {
        try {
            // Load the image from resources or a file
            ImageIcon icon = new ImageIcon("images/mylogo.png"); // Replace with your logo path
            
            // Resize the image to the desired size (e.g., 32x32 pixels)
            Image resizedImage = icon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH); // Width, Height
            
            // Set the resized image as the frame icon
            setIconImage(resizedImage);
        } catch (Exception e) {
            System.err.println("Error loading or resizing logo: " + e.getMessage());
        }
    }
    
    
    
    
    
}
