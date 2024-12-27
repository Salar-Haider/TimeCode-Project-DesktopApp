
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.JOptionPane;


public class TimeCode {
    
    
    
    
    
    
    
    
    public static boolean isConnectedToInternet() {
        try {
            // Specify a commonly accessible URL
            URL url = new URL("http://www.google.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(3000); // Set timeout to 3 seconds
            connection.setReadTimeout(3000);
            connection.connect();

            // If the response code is in the 200 range, the connection is successful
            return (connection.getResponseCode() >= 200 && connection.getResponseCode() < 300);
        } catch (IOException e) {
            // Handle exception (e.g., no internet)
            return false;
        }
    }
    
    
    
    
    
    
    
    
    
    
    public static void main(String[] args) {
        System.out.println("debug time code started");
        
        
        
        
        
        
        if (isConnectedToInternet()) {
            
            System.out.println("You are connected to the internet!");
            
            
            UserManager um = new UserManager();
            um.initialDiractor();
            
            
            
            
            
        } else {
            System.out.println("You are NOT connected to the internet!");
            
            CustomOptionPane.showCustomMessageDialog(null, "No Internet Connection! Connect to Network and try again" , "No Connection" , JOptionPane.INFORMATION_MESSAGE);
            
            
        }
        
        
        
        
        
        
    }
    
}
