
import java.io.*;
import java.nio.file.*;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class UserManager {

    
    
    

    
        public UserManager (){

            
            
            System.out.println("debug user manager called");

        }
    
    
    /**
     * Gets the file path in the parallel folder to the program file.
     *
     * @return The file path of the CSV file.
     */
    private  String getFilePath(String FileNmane) {
        String folderPath = Paths.get(".").toAbsolutePath().getParent().toString();
        return folderPath + File.separator + FileNmane;
        
    }

    /**
     * Ensures that the CSV file exists. Creates the file if it doesn't exist.
     *
     * @param filePath The path of the CSV file.
     */
    private  boolean ensureFileExists(String filePath) {
        
        
        
        boolean havedata = false;
        
        
        File csvFile = new File(filePath);
        try {
            if (!csvFile.exists() && csvFile.createNewFile()) {
                System.out.println("CSV file created: " + filePath);
                
                
                
                
                
                
            } else {
                System.out.println("CSV file exists at: " + filePath);
                
                
                
                havedata  = haveData(filePath);
                
                
                
                
            }
        } catch (IOException e) {
            System.err.println("An error occurred while creating the file: " + e.getMessage());
        }
        
        return havedata;
    }

    /**
     * Gets user input from the console with a prompt.
     *
     * @param prompt The message to display to the user.
     * @return The user's input as a String.
     */


    /**
     * Saves user data (userID and userToken) to the CSV file.
     *
     * @param filePath  The path of the CSV file.
     * @param userID    The userID to save.
     * @param userToken The userToken to save.
     */
    protected  void writeToUserData( String userID, String userToken) {
        
        System.out.println("debug write to user data called");
        
        String filePath  = getFilePath("userData.csv");
        
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(userID + "," + userToken);
            writer.newLine();
            System.out.println("Data saved successfully!");
        } catch (IOException e) {
            System.err.println("An error occurred while saving data: " + e.getMessage());
        }
    }
    
    
    
    

    
    
    
    
    
    

    /**
     * Reads and displays data from the CSV file in a formatted way.
     *
     * @param filePath The path of the CSV file.
     */
    private  String[] getUserData() {
        
        System.out.println("debug get user data called");
        
        String filePath  = getFilePath("userData.csv");
        
        
        String userID   ="" ;
        String  userToken = "";
        
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    
                    userID = parts[0];
                    userToken  = parts[1];
                    

                } else {
                    System.out.println("Invalid entry in file: " + line);
                }
            }
            
           
            
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }
        
        
        String[] userDataArray = {userID,userToken};
        
        return userDataArray;
    }
    
    
    
    
    
    

    
    
    
    
    
    
    
    
    
    
    protected void clearCSVFile() {
        
        System.out.println("debug clear csv file called");
        
        String filePath  = getFilePath("userData.csv");
        
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
        // Open the file in overwrite mode without writing anything, clearing its content
        writer.write("");
        System.out.println("CSV file content cleared successfully!");
    } catch (IOException e) {
        System.err.println("An error occurred while clearing the file: " + e.getMessage());
    }
}
    
    
    private boolean fileEnsurer(String fileName){
    
        System.out.println("debug file essurer called");
    
    String filePath = getFilePath(fileName);

        boolean havedata = ensureFileExists(filePath);
    
        return havedata; 
    }
    
    
    
    
    
    
    public  boolean haveData(String filePath) {
        
        System.out.println("debug csv file have data called");
        
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String firstLine = reader.readLine();
        return !(firstLine == null || firstLine.trim().isEmpty()); // Returns true if the file has data
    } catch (IOException e) {
        System.err.println("An error occurred while reading the file: " + e.getMessage());
        return false; // Assume the file is empty in case of an error (could also throw the exception)
    }
}
    
    
    
    protected void initialDiractor(){
    
    
        System.out.println("debug initial diractor called");
        
        
        boolean userDataEnsurity = fileEnsurer("userData.csv");
        
        
        if (userDataEnsurity){
        
        String[] userData = getUserData();
        
        String userID = userData[0];
        String userToken = userData[1];
        
        boolean present  = new SupabaseHandler("").isUserPresent(userID);
        
        
        if (!present){
            
            
            clearCSVFile();
        
        JOptionPane.showMessageDialog(null, "There is some issue with your Login! please login again", 
            "Error", JOptionPane.INFORMATION_MESSAGE);
        
        
        
        System.out.println("debug user id editted , , is present");
        
        new LoginSignupFrame();
        
        return;
        
        }
        else{
        
            
        System.out.println("debug home opened from user manager");
            
            
        new Home(userID, userToken);

        }  
            
        
        }
        
        else{
            
            
            System.out.println("debug no user id present , redirected to login");
        
        new LoginSignupFrame();
        
        
        }
    
    }
    
    
    
    
    

 
    
    
}
