
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Class to connect to a Supabase database and manage timetables.
 */
public class SupabaseHandler {
    
    private String userID ;
    
    JsonNode jsonResponse;

    // Supabase project details
    private static final String SUPABASE_URL = "https://lsvztvmtsbfjsajfehzi.supabase.co/rest/v1/timetables";
    private static final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imxzdnp0dm10c2JmanNhamZlaHppIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzE4NjQ0MjMsImV4cCI6MjA0NzQ0MDQyM30.yoHW49XDquGAWNHVvb1MnFh_7DCZyw4pBGW0aD09fow";

    // HTTP client to communicate with Supabase
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    /**
     * Constructor to initialize HTTP client and JSON object mapper.
     */
    public SupabaseHandler(String userId) {
        
        userID  = userId;
        
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Method to insert a new timetable entry into the database.
     *
     * @param timetableName Name of the timetable.
     * @param day           Day of the activity.
     * @param startTime     Start time of the activity.
     * @param endTime       End time of the activity.
     * @param activityName  Name of the activity.
     * @param userId        User ID associated with the timetable.
     */
    public void insertTimetable(String timetableName, String day, String startTime, String endTime, String activityName, String userId) {
        try {
            // Prepare the data to insert
            HashMap<String, String> data = new HashMap<>();
            data.put("timetable_name", timetableName);
            data.put("day", day);
            data.put("start_time", startTime);
            data.put("end_time", endTime);
            data.put("activity_name", activityName);
            data.put("user_id", userId);  // Add the user_id field

            // Convert data to JSON
            String jsonData = objectMapper.writeValueAsString(data);

            // Create the HTTP POST request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(SUPABASE_URL))
                    .header("Content-Type", "application/json")
                    .header("apikey", API_KEY)
                    .header("Authorization", "Bearer " + API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonData))
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Print the response
            if (response.statusCode() == 201) {
                System.out.println("Data inserted successfully!");
            } else {
                System.err.println("Failed to insert data. HTTP Status Code: " + response.statusCode());
                System.err.println("Response: " + response.body());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to fetch and display timetables from the database.
     */
    public JsonNode fetchTimetables() {
        try {
            // Prepare the HTTP GET request with a filter for user_id
            String urlWithFilter = SUPABASE_URL + "?user_id=eq." + userID;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlWithFilter))
                    .header("apikey", API_KEY)
                    .header("Authorization", "Bearer " + API_KEY)
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // Parse the JSON response
                jsonResponse = objectMapper.readTree(response.body());
            } else {
                System.err.println("Failed to fetch data. HTTP Status Code: " + response.statusCode());
                System.err.println("Response: " + response.body());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }  
        
        return jsonResponse;
    }
    
    
    
    
    
    public void signOutUser(String accessToken) {
    String supabaseSignOutUrl = "https://lsvztvmtsbfjsajfehzi.supabase.co/auth/v1/logout";
    String apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imxzdnp0dm10c2JmanNhamZlaHppIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzE4NjQ0MjMsImV4cCI6MjA0NzQ0MDQyM30.yoHW49XDquGAWNHVvb1MnFh_7DCZyw4pBGW0aD09fow";

    try {
        // Create HTTP client
        HttpClient client = HttpClient.newHttpClient();

        // Build sign-out request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(supabaseSignOutUrl))
                .header("Content-Type", "application/json")
                .header("apikey", apiKey)
                .header("Authorization", "Bearer " + accessToken) // Add user's session token
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        // Send the request
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            System.out.println("Sign-out successful.");
        } else {
            System.out.println("Sign-out failed. Response code: " + response.statusCode());
        }
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("An error occurred during sign-out.");
    }
}
    
    
    
    
    
    
    
    
    
    
    
    public boolean updateActivityName(String userID, String tableName, String day, String preActivityName, String newActivityName) {
    try {
        // Encode query parameters to handle special characters
        String encodedUserID = URLEncoder.encode(userID, StandardCharsets.UTF_8);
        String encodedTableName = URLEncoder.encode(tableName, StandardCharsets.UTF_8);
        String encodedDay = URLEncoder.encode(day, StandardCharsets.UTF_8);
        String encodedPreActivityName = URLEncoder.encode(preActivityName, StandardCharsets.UTF_8);

        // Build the filter query for Supabase
        String filterQuery = String.format(
            "user_id=eq.%s&timetable_name=eq.%s&day=eq.%s&activity_name=eq.%s",
            encodedUserID, encodedTableName, encodedDay, encodedPreActivityName
        );

        // Check if the record exists
        HttpRequest checkRequest = HttpRequest.newBuilder()
            .uri(URI.create(SUPABASE_URL + "?" + filterQuery))
            .header("apikey", API_KEY)
            .header("Authorization", "Bearer " + API_KEY)
            .GET()
            .build();

        HttpResponse<String> checkResponse = httpClient.send(checkRequest, HttpResponse.BodyHandlers.ofString());

        // If no matching record is found, return false
        if (checkResponse.statusCode() != 200 || checkResponse.body().isEmpty() || checkResponse.body().equals("[]")) {
            return false;
        }

        // Prepare the request payload to update the activity name
        HashMap<String, String> updateData = new HashMap<>();
        updateData.put("activity_name", newActivityName);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPayload = objectMapper.writeValueAsString(updateData);

        // Build the HTTP request to update
        HttpRequest updateRequest = HttpRequest.newBuilder()
            .uri(URI.create(SUPABASE_URL + "?" + filterQuery))
            .header("Content-Type", "application/json")
            .header("apikey", API_KEY)
            .header("Authorization", "Bearer " + API_KEY)
            .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonPayload))
            .build();

        // Send the HTTP request
        HttpResponse<String> updateResponse = httpClient.send(updateRequest, HttpResponse.BodyHandlers.ofString());

        // Check for a successful response
        return updateResponse.statusCode() == 204; // HTTP 204: No Content (Success)
    } catch (Exception e) {
        e.printStackTrace();
        return false; // Return false if an exception occurs
    }
}

    
    
    
    
    
    
    
    
    
    
    
    public boolean updateActivityTime(String userID, String tableName, String day, String activityName, String newStartingTime, String newEndingTime) {
    try {
        // Encode query parameters to handle special characters
        String encodedUserID = URLEncoder.encode(userID, StandardCharsets.UTF_8);
        String encodedTableName = URLEncoder.encode(tableName, StandardCharsets.UTF_8);
        String encodedDay = URLEncoder.encode(day, StandardCharsets.UTF_8);
        String encodedActivityName = URLEncoder.encode(activityName, StandardCharsets.UTF_8);

        // Build the filter query for Supabase
        String filterQuery = String.format(
            "user_id=eq.%s&timetable_name=eq.%s&day=eq.%s&activity_name=eq.%s",
            encodedUserID, encodedTableName, encodedDay, encodedActivityName
        );

        // Check if the record exists
        HttpRequest checkRequest = HttpRequest.newBuilder()
            .uri(URI.create(SUPABASE_URL + "?" + filterQuery))
            .header("apikey", API_KEY)
            .header("Authorization", "Bearer " + API_KEY)
            .GET()
            .build();

        HttpResponse<String> checkResponse = httpClient.send(checkRequest, HttpResponse.BodyHandlers.ofString());

        // If no matching record is found, return false
        if (checkResponse.statusCode() != 200 || checkResponse.body().isEmpty() || checkResponse.body().equals("[]")) {
            return false;
        }

        // Prepare the request payload for the update
        HashMap<String, String> updateData = new HashMap<>();
        updateData.put("start_time", newStartingTime);
        updateData.put("end_time", newEndingTime);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPayload = objectMapper.writeValueAsString(updateData);

        // Build the HTTP request to update
        HttpRequest updateRequest = HttpRequest.newBuilder()
            .uri(URI.create(SUPABASE_URL + "?" + filterQuery))
            .header("Content-Type", "application/json")
            .header("apikey", API_KEY)
            .header("Authorization", "Bearer " + API_KEY)
            .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonPayload))
            .build();

        // Send the HTTP request
        HttpResponse<String> updateResponse = httpClient.send(updateRequest, HttpResponse.BodyHandlers.ofString());

        // Check for a successful response
        return updateResponse.statusCode() == 204; // HTTP 204: No Content (Success)
    } catch (Exception e) {
        e.printStackTrace();
        return false; // Return false if an exception occurs
    }
}

    
    
    
    
    
    
    
    
    
    public boolean deleteActivity(String userID, String tableName, String day, String activityName) {
    try {
        // Encode query parameters to handle special characters
        String encodedUserID = URLEncoder.encode(userID, StandardCharsets.UTF_8);
        String encodedTableName = URLEncoder.encode(tableName, StandardCharsets.UTF_8);
        String encodedDay = URLEncoder.encode(day, StandardCharsets.UTF_8);
        String encodedActivityName = URLEncoder.encode(activityName, StandardCharsets.UTF_8);

        // Build the filter query for Supabase
        String filterQuery = String.format(
            "user_id=eq.%s&timetable_name=eq.%s&day=eq.%s&activity_name=eq.%s",
            encodedUserID, encodedTableName, encodedDay, encodedActivityName
        );

        // Check if the record exists
        HttpRequest checkRequest = HttpRequest.newBuilder()
            .uri(URI.create(SUPABASE_URL + "?" + filterQuery))
            .header("apikey", API_KEY)
            .header("Authorization", "Bearer " + API_KEY)
            .GET()
            .build();

        HttpResponse<String> checkResponse = httpClient.send(checkRequest, HttpResponse.BodyHandlers.ofString());

        // If no matching record is found, return false
        if (checkResponse.statusCode() != 200 || checkResponse.body().isEmpty() || checkResponse.body().equals("[]")) {
            return false;
        }

        // Build the HTTP request to delete the record
        HttpRequest deleteRequest = HttpRequest.newBuilder()
            .uri(URI.create(SUPABASE_URL + "?" + filterQuery))
            .header("apikey", API_KEY)
            .header("Authorization", "Bearer " + API_KEY)
            .method("DELETE", HttpRequest.BodyPublishers.noBody())
            .build();

        // Send the HTTP request
        HttpResponse<String> deleteResponse = httpClient.send(deleteRequest, HttpResponse.BodyHandlers.ofString());

        // Check for a successful response
        return deleteResponse.statusCode() == 204; // HTTP 204: No Content (Success)
    } catch (Exception e) {
        e.printStackTrace();
        return false; // Return false if an exception occurs
    }
}

    
    
    
    
    
    
    
    
    
    public boolean deleteTimetable(String userID, String tableName) {
    try {
        // Encode query parameters to handle special characters
        String encodedUserID = URLEncoder.encode(userID, StandardCharsets.UTF_8);
        String encodedTableName = URLEncoder.encode(tableName, StandardCharsets.UTF_8);

        // Build the filter query for Supabase
        String filterQuery = String.format(
            "user_id=eq.%s&timetable_name=eq.%s",
            encodedUserID, encodedTableName
        );

        // Check if any matching records exist
        HttpRequest checkRequest = HttpRequest.newBuilder()
            .uri(URI.create(SUPABASE_URL + "?" + filterQuery))
            .header("apikey", API_KEY)
            .header("Authorization", "Bearer " + API_KEY)
            .GET()
            .build();

        HttpResponse<String> checkResponse = httpClient.send(checkRequest, HttpResponse.BodyHandlers.ofString());

        // If no matching records are found, return false
        if (checkResponse.statusCode() != 200 || checkResponse.body().isEmpty() || checkResponse.body().equals("[]")) {
            return false;
        }

        // Build the HTTP request to delete all matching records
        HttpRequest deleteRequest = HttpRequest.newBuilder()
            .uri(URI.create(SUPABASE_URL + "?" + filterQuery))
            .header("apikey", API_KEY)
            .header("Authorization", "Bearer " + API_KEY)
            .method("DELETE", HttpRequest.BodyPublishers.noBody())
            .build();

        // Send the HTTP request
        HttpResponse<String> deleteResponse = httpClient.send(deleteRequest, HttpResponse.BodyHandlers.ofString());

        // Check for a successful response
        return deleteResponse.statusCode() == 204; // HTTP 204: No Content (Success)
    } catch (Exception e) {
        e.printStackTrace();
        return false; // Return false if an exception occurs
    }
}

    
    
    
    
    
    
    
    
    public boolean isUserPresent(String userID) {
    try {
        // Prepare the JSON body for the RPC function
        String requestBody = "{\"uuid\":\"" + userID + "\"}";

        // Create the HTTP POST request with the fully qualified function name
        String functionURL = "https://lsvztvmtsbfjsajfehzi.supabase.co/rest/v1/rpc/check_user_exists"; // Use the correct schema

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(functionURL))
                .header("apikey", API_KEY)
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        // Send the request and get the response
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            // Parse the response, which will return "true" or "false"
            return Boolean.parseBoolean(response.body().trim());
        } else {
            System.err.println("Failed to call function. HTTP Status Code: " + response.statusCode());
            System.err.println("Response: " + response.body());
            return false;
        }
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
    
    
    public boolean isUserPresentByEmail(String email) {
    try {
        // Prepare the JSON body for the RPC function
        String requestBody = "{\"email_address\":\"" + email + "\"}";

        // Update the URL to call the new Supabase function
        String functionURL = "https://lsvztvmtsbfjsajfehzi.supabase.co/rest/v1/rpc/check_user_by_email"; // Use the correct schema

        // Create the HTTP POST request with the fully qualified function name
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(functionURL))
                .header("apikey", API_KEY) // Replace with your Supabase API key
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        // Send the request and get the response
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            // Parse the response, which will return "true" or "false"
            return Boolean.parseBoolean(response.body().trim());
        } else {
            System.err.println("Failed to call function. HTTP Status Code: " + response.statusCode());
            System.err.println("Response: " + response.body());
            return false;
        }
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

    
    
    
    
    
    

}
