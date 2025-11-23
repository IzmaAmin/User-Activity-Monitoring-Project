package com.monitoring;

import com.monitoring.models.ActivityLog;
import com.monitoring.models.Document;
import com.monitoring.models.User;
import com.monitoring.models.SecurityManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;



public class UserDashboard {

   
    
    @FXML private Label welcomeLabel;             // Displays "Welcome, [Username]!"
    @FXML private ListView<String> docsList;      // List of user's documents
    @FXML private Button addDocBtn;                // Add new document button
    @FXML private Button logoutBtn;                // Logout button

   
    
    private User user;                             // Current logged-in user
    private ActivityLog activityLog;               // Global activity log
    private SecurityManager securityManager;       // Security manager

    private ObservableList<String> documents = FXCollections.observableArrayList();

  
    public void initData(User user, ActivityLog activityLog, SecurityManager securityManager) {

        this.user = user;
        this.activityLog = activityLog;
        this.securityManager = securityManager;

        welcomeLabel.setText("Welcome, " + user.getFullName() + "!");

        for (Document doc : user.getDocuments()) {
            documents.add(doc.getName());  // Add document name to list
        }

        docsList.setItems(documents);
    }

    
    @FXML
    private void onAddDocument(ActionEvent e) {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Document");
        dialog.setHeaderText("Enter document name");

        dialog.showAndWait().ifPresent(name -> {

            if (!name.trim().isEmpty()) {

                Document doc = new Document(name.trim(), "file");

                user.addDocument(doc);

                documents.add(name.trim());

                activityLog.add(new ActivityLog.ActivityRecord(
                    user.getUsername(),                      // WHO: Current user
                    "Added Document: " + name.trim(),         // WHAT: Action description
                    user.getIpAddress(),                      // WHERE: User's IP
                    1                                         // SEVERITY: 1 = Normal
                ));
            }
        });
    }

  
    @FXML
    private void onLogout(ActionEvent e) {
        try {

            Stage stage = (Stage) logoutBtn.getScene().getWindow();

            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                getClass().getResource("/login.fxml")
            );

            stage.setScene(new javafx.scene.Scene(loader.load(), 700, 450));

            stage.setTitle("Login");
        } catch (Exception ex) {
            ex.printStackTrace();  // Print error if something goes wrong
        }
    }
}

