package com.monitoring;



import com.monitoring.models.ActivityLog;
import com.monitoring.models.User;
import com.monitoring.models.SecurityManager;
import com.monitoring.utils.SearchingAlgorithms;  // Searching algorithm (Linear Search)
import com.monitoring.utils.SortingAlgorithms;     // Sorting algorithm (by timestamp)
import com.monitoring.utils.DataGenerator;         // Generates random data
import javafx.collections.FXCollections;           // JavaFX collections
import javafx.collections.ObservableList;          // List that updates UI automatically
import javafx.event.ActionEvent;                   // Button click event
import javafx.fxml.FXML;                           // Connects Java with FXML UI
import javafx.geometry.Insets;                     // Spacing/padding
import javafx.scene.Scene;                         // Window content
import javafx.scene.chart.BarChart;                // Bar chart for analytics
import javafx.scene.chart.PieChart;                // Pie chart for analytics
import javafx.scene.control.*;                     // UI controls (buttons, tables, etc)
import javafx.scene.layout.GridPane;               // Grid layout for forms
import javafx.stage.Stage;                         

import java.io.IOException;
import java.time.format.DateTimeFormatter;         
import java.util.List;


public class AdminDashboard {

   
    
    @FXML private Label adminInfo;                                              // Shows admin name + user count
    @FXML private TableView<ActivityLog.ActivityRecord> logsTable;             // Main table showing all activities
    @FXML private TableColumn<ActivityLog.ActivityRecord, String> colTime;     // Column: Timestamp
    @FXML private TableColumn<ActivityLog.ActivityRecord, String> colUser;     // Column: Username
    @FXML private TableColumn<ActivityLog.ActivityRecord, String> colAction;   // Column: Action performed
    @FXML private TableColumn<ActivityLog.ActivityRecord, String> colIp;       // Column: IP Address
    @FXML private TableColumn<ActivityLog.ActivityRecord, Integer> colSeverity;// Column: Severity level
    @FXML private TextField searchField;                                        // Text field for searching
    @FXML private Button searchBtn;                                             // Search button
    @FXML private Button sortBtn;                                               // Sort by time button
    @FXML private Button suspiciousBtn;                                         // View suspicious activities button
    @FXML private Button addUserBtn;                                            // Add new user button
    @FXML private Button logoutBtn;                                             // Logout button
    @FXML private PieChart analyticsChart;                                      // Pie chart (success vs failed)
    @FXML private BarChart<String, Number> activityBar;                         // Bar chart (activity trends)


    
    private List<User> userList;                                             
    private ActivityLog activityLog;                                            
    private SecurityManager securityManager;                                  
    private ObservableList<ActivityLog.ActivityRecord> tableData = FXCollections.observableArrayList();

  
    public void initData(List<User> userList, ActivityLog activityLog, SecurityManager securityManager) {
        this.userList = userList;
        this.activityLog = activityLog;
        this.securityManager = securityManager;
        
        adminInfo.setText("Admin: SIMI | Total Users: " + userList.size() + " | Suspicious: " + securityManager.getSuspiciousCount());

        tableData.setAll(activityLog.toList());
        logsTable.setItems(tableData);
      
        
        colTime.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
            c.getValue().getTimestamp().format(DateTimeFormatter.ofPattern("HH:mm:ss yyyy-MM-dd"))
        ));
        
        colUser.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
            c.getValue().getUsername()
        ));
        
        colAction.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
            c.getValue().getAction()
        ));
        
        colIp.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
            c.getValue().getIp()
        ));
        
        colSeverity.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(
            c.getValue().getSeverity()
        ).asObject());

        buildCharts();
    }

    private void buildCharts() {
        analyticsChart.getData().clear();
        
                long success = activityLog.countByActionPrefix("Successful"); 
        long failed = activityLog.countByActionPrefix("Failed");       
        

        analyticsChart.getData().addAll(
            new PieChart.Data("Successful", success), 
            new PieChart.Data("Failed", failed)     
        );
    }

    
    @FXML
    private void onSearch(ActionEvent e) {
        String q = searchField.getText().trim();
        
        if (q.isEmpty()) {
            tableData.setAll(activityLog.toList());
            return;
        }
        
      
        List<ActivityLog.ActivityRecord> res = SearchingAlgorithms.searchByUsernameOrIp(
            activityLog.toList(),  
            q                     
        );
        
        tableData.setAll(res);
    }

   
    @FXML
    private void onSort(ActionEvent e) {
        List<ActivityLog.ActivityRecord> list = activityLog.toList();
        
        
        SortingAlgorithms.sortByTimestamp(list);
        
        tableData.setAll(list);
    }

  
    @FXML
    private void onViewSuspicious(ActionEvent e) {
        
        List<ActivityLog.ActivityRecord> suspicious = activityLog.findBySeverity(2);
        
        tableData.setAll(suspicious);
    }

    @FXML
    private void onAddUser(ActionEvent e) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Add New User");
        dialog.setHeaderText("Create a new user account");

       
        GridPane grid = new GridPane();
        grid.setHgap(10);   
        grid.setVgap(10);   
        grid.setPadding(new Insets(10));  

        
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");  
        
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");   
        
        TextField fullNameField = new TextField();
        fullNameField.setPromptText("Full Name");
        
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        
        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone");
        
        ComboBox<String> purposeBox = new ComboBox<>();
        purposeBox.setItems(FXCollections.observableArrayList("personal", "work", "company"));
        purposeBox.setPrefWidth(150);  // Set width

       
        
        grid.add(new Label("Username:"), 0, 0);           // Column 0 = Label
        grid.add(usernameField, 1, 0);                     // Column 1 = Input field
        
        grid.add(new Label("Password:"), 0, 1);
        grid.add(passwordField, 1, 1);
        
        grid.add(new Label("Full Name:"), 0, 2);
        grid.add(fullNameField, 1, 2);
        
        grid.add(new Label("Email:"), 0, 3);
        grid.add(emailField, 1, 3);
        
        grid.add(new Label("Phone:"), 0, 4);
        grid.add(phoneField, 1, 4);
        
        grid.add(new Label("Purpose:"), 0, 5);
        grid.add(purposeBox, 1, 5);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

       
        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                
                String username = usernameField.getText().trim();      
                String password = passwordField.getText();
                String fullName = fullNameField.getText().trim();
                String email = emailField.getText().trim();
                String phone = phoneField.getText().trim();
                String purpose = purposeBox.getValue() != null ? purposeBox.getValue() : "personal";

            
                if (username.isEmpty() || password.isEmpty() || fullName.isEmpty() || email.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Please fill all fields");
                    alert.showAndWait();
                    return null;  
                }

                
                if (userList.stream().anyMatch(u -> u.getUsername().equalsIgnoreCase(username))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Username already exists!");
                    alert.showAndWait();
                    return null;  
                }

                
                
                String ip = DataGenerator.randomIP();
                
                User newUser = new User(username, password, fullName, email, phone, purpose, ip);
                
                userList.add(newUser);
                
                
                activityLog.add(new ActivityLog.ActivityRecord(
                    "ADMIN",                        
                    "Created new user: " + username, 
                    ip,                             
                    1                               
                ));

               
                Alert alert = new Alert(Alert.AlertType.INFORMATION, 
                    "User " + username + " created successfully!");
                alert.showAndWait();

            
                adminInfo.setText("Admin: SIMI | Total Users: " + userList.size() + 
                    " | Suspicious: " + securityManager.getSuspiciousCount());
            }
            return null;
        });

        dialog.showAndWait();
    }

    
    @FXML
    private void onLogout(ActionEvent e) throws IOException {
        Stage stage = (Stage) logoutBtn.getScene().getWindow();
        
        
        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
            getClass().getResource("/login.fxml")
        );
        
       
        stage.setScene(new Scene(loader.load(), 700, 450));

        stage.setTitle("User Activity Monitoring System - Login");
    }

}




