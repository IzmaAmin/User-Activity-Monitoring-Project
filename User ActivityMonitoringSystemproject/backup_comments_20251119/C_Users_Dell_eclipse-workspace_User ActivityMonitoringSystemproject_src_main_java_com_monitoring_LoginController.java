package com.monitoring;

import com.monitoring.models.ActivityLog;
import com.monitoring.models.Document;
import com.monitoring.models.User;
import com.monitoring.models.SecurityManager;
import com.monitoring.utils.DataGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class LoginController {


    
    @FXML private TextField usernameField;        
    @FXML private PasswordField passwordField;   
    @FXML private Button loginButton;              
    @FXML private Button hiddenAdminButton;       
    @FXML private Label statusLabel;              

 
    
    private static List<User> userList = new ArrayList<>(
        java.util.Arrays.asList(DataGenerator.generateDummyUsers(50))
    );
    
    private static ActivityLog activityLog = new ActivityLog();
    
    private static SecurityManager securityManager = new SecurityManager();

    
    @FXML
    public void initialize() {
        hiddenAdminButton.setVisible(false);  
    }

    
    @FXML
    private void onKeyPressed(KeyEvent e) {
        if (e.isControlDown() && e.isShiftDown() && e.getCode() == KeyCode.A) {
            hiddenAdminButton.setVisible(true);  
            statusLabel.setText("Admin login revealed");  
        }
    }

   
    @FXML
    private void onLogin(ActionEvent event) throws IOException {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please enter username and password");
            return;
        }

        User user = findUser(username);
        
        String ip = DataGenerator.randomIP();

        if (user == null) {

            activityLog.add(new ActivityLog.ActivityRecord(
                username, 
                "Failed Login (unknown user)", 
                ip, 
                2  // Severity 2 = warning
            ));
            showAlert(Alert.AlertType.ERROR, "Invalid credentials");
            return;
        }

        if (securityManager.isLocked(user.getUsername())) {
            showAlert(Alert.AlertType.ERROR, 
                "Too many failed attempts. Your account is locked for 3 days. A verification email has been sent.");
            System.out.println("[Email] Verification email sent to " + user.getEmail());

            activityLog.add(new ActivityLog.ActivityRecord(
                username, 
                "Login Attempt While Locked", 
                ip, 
                3  // Severity 3 = alert
            ));
            return;
        }

        if (user.getPassword().equals(password)) {

            securityManager.resetAttempts(user.getUsername());

            activityLog.add(new ActivityLog.ActivityRecord(
                username, 
                "Successful Login", 
                ip, 
                0  // Severity 0 = info
            ));

            openUserDashboard(event, user);
        } else {
          
            int attempts = securityManager.recordFailedAttempt(user.getUsername());

            activityLog.add(new ActivityLog.ActivityRecord(
                username, 
                "Failed Login", 
                ip, 
                2  // Severity 2 = warning
            ));

            if (attempts >= 5) {
                securityManager.lockAccount(user.getUsername(), 3);  // Lock for 3 days
                showAlert(Alert.AlertType.ERROR, 
                    "Too many failed attempts. Your account is locked for 3 days. A verification email has been sent.");
                System.out.println("[Email] Verification email sent to " + user.getEmail());
            } 

            else if (attempts >= 3) {
                showAlert(Alert.AlertType.WARNING, "Warning: multiple failed attempts.");
            } 

            else {
                showAlert(Alert.AlertType.ERROR, "Invalid credentials");
            }
        }
    }

  
    @FXML
    private void onAdminReveal(ActionEvent event) throws IOException {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Admin Login");
        dialog.initModality(Modality.APPLICATION_MODAL);  // Block interaction with other windows

        Label userLabel = new Label("Username:");
        Label passLabel = new Label("Password:");
        TextField userTextField = new TextField();
        PasswordField passTextField = new PasswordField();

        GridPane grid = new GridPane();
        grid.setHgap(10);  // Horizontal gap
        grid.setVgap(10);  // Vertical gap

        grid.add(userLabel, 0, 0);
        grid.add(userTextField, 1, 0);
        grid.add(passLabel, 0, 1);
        grid.add(passTextField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                return new Pair<>(userTextField.getText(), passTextField.getText());
            }
            return null;
        });

        dialog.showAndWait().ifPresent(pair -> {

            if (pair.getKey().equals("SIMI") && pair.getValue().equals("SIMIGROUP123")) {

                try {
                    openAdminDashboard(event);  // Open admin dashboard
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {

                showAlert(Alert.AlertType.ERROR, "Invalid admin credentials");
            }
        });
    }

 
    private User findUser(String username) {
        for (User u : userList) {

            if (u.getUsername().equalsIgnoreCase(username)) {
                return u;
            }
        }
        return null;  // Not found
    }

   
    private void openUserDashboard(ActionEvent event, User user) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/user_dashboard.fxml"));
        Parent root = loader.load();

        UserDashboard controller = loader.getController();
        controller.initData(user, activityLog, securityManager);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 800, 600));
        stage.setTitle("User Dashboard - " + user.getFullName());
    }

    private void openAdminDashboard(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin_dashboard.fxml"));
        Parent root = loader.load();

        AdminDashboard controller = loader.getController();
        controller.initData(userList, activityLog, securityManager);

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1000, 700));
        stage.setTitle("Admin Dashboard");
    }

    
    private void showAlert(Alert.AlertType type, String msg) {
        Alert alert = new Alert(type, msg, ButtonType.OK);

        alert.initOwner(loginButton.getScene() == null ? null : loginButton.getScene().getWindow());

        alert.showAndWait();
    }

   
    
    public static List<User> getUserList() { return userList; }
    public static ActivityLog getActivityLog() { return activityLog; }
    public static SecurityManager getSecurityManager() { return securityManager; }
}

