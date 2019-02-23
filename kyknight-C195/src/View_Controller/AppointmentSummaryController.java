/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Appointment;
import Model.AppointmentList;
import static Model.AppointmentList.getAppList;
import Model.DBManager;
import static Model.DBManager.updateAppList;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author kyleighknight
 */
public class AppointmentSummaryController implements Initializable {
    
    //Labels
    @FXML private Label appSumScreenLabel, appSumTitleLabel, appSumDescLabel, appSumLocationLabel, appSumContactLabel,
            appSumUrlLabel, appSumDateLabel, appSumStartLabel, appSumEndLabel, appSumCreatedByLabel;
    //labels for Appointment Details 
    @FXML private Label appSumDetailTitleLabel, appSumDetailDescLabel, appSumDetailLocationLabel, appSumDetailContactLabel,
            appSumDetailUrlLabel, appSumDetailDateLabel, appSumDetailStartLabel, appSumDetailEndLabel, appSumDetailCreatedByLabel;
    
    //Buttons
    @FXML private Button appSumAppDetailsButton, appSumModAppButton, appSumDeleteAppButton, appSumExitButton;

    //Tableview
    @FXML private TableView<Appointment> appSumTableview;
    @FXML private TableColumn<Appointment,String> appSumDateTableCol;
    @FXML private TableColumn<Appointment,String> appSumTitleTableCol;
    @FXML private TableColumn<Appointment,String> appSumContactTableCol;
    
    private static int appIndexMod;
    
    /**
     * Sets the language of the labels and text fields to the local language 
     */
    /*private void setLanguage(){
        ResourceBundle rb = ResourceBundle.getBundle("AddModifyApp", Locale.getDefault());
        appSumDateTableCol.setText(rb.getString("dateLabel"));
        appSumTitleTableCol.setText(rb.getString("titleLabel"));
        appSumContactTableCol.setText(rb.getString("contactLAbel"));
        appSumAppDetailsButton.setText(rb.getString("appDetailsButton"));
        appSumModAppButton.setText(rb.getString("modifyButton"));
        appSumDeleteAppButton.setText(rb.getString("deleteButton"));
        appSumExitButton.setText(rb.getString("exitButton"));
        appSumTitleLabel.setText(rb.getString("titleLabel"));
        appSumDescLabel.setText(rb.getString("descLabel"));
        appSumLocationLabel.setText(rb.getString("locationLabel"));
        appSumContactLabel.setText(rb.getString("contactLabel"));
        appSumUrlLabel.setText(rb.getString("urlLabel"));
        appSumDateLabel.setText(rb.getString("dateLabel"));
        appSumStartLabel.setText(rb.getString("startLabel"));
        appSumEndLabel.setText(rb.getString("endLabel"));
        appSumCreatedByLabel.setText(rb.getString("createdByLabel"));
    }*/
    
    /**
     * This method, when the Appointment Details button is selected, checks if there is an
     * appointment is selected. If not, returns error message in window. If so, displays the 
     * selected appointment details.
     * @param event 
     */
    private void AppSumAppDetailButtonPushed(ActionEvent event){
        Appointment app = appSumTableview.getSelectionModel().getSelectedItem();
        //is an appointment selected?
        //yes
        if (app == null){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Please Select an appointment to view appointment details.");
            alert.showAndWait();
        } /* nope */ else {
            appSumDetailTitleLabel.setText(app.getTitle());
            appSumDetailDescLabel.setText(app.getDesc());
            appSumDetailLocationLabel.setText(app.getLocation());
            appSumDetailContactLabel.setText(app.getContact());
            appSumDetailUrlLabel.setText(app.getUrl());
            appSumDetailDateLabel.setText(app.getDateString());
            appSumDetailStartLabel.setText(app.getStartString());
            appSumDetailEndLabel.setText(app.getEndString());
            appSumDetailCreatedByLabel.setText(app.getCreatedBy());
        }
    }
    
    private void AppSumModAppButtonPushed(ActionEvent event){
        Appointment appMod = appSumTableview.getSelectionModel().getSelectedItem();
        //is an appointment selected?
        //yes
        if (appMod == null){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Please Select an appointment to modify an appointment.");
            alert.showAndWait();
        }
        appIndexMod = getAppList().indexOf(appMod);
        
        try{
            Parent modAppParent = FXMLLoader.load(getClass().getResource("ModifyAppointment.fxml"));
            Scene modAppScene = new Scene(modAppParent);
            Stage modAppStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            modAppStage.setScene(modAppScene);
            modAppStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * This method deletes an appointment, if selected, and checks to see if there is an
     * appointment selected. If not, displays error message.
     * @param event 
     */
    private void AppSumDeleteButtonPushed(ActionEvent event){
        Appointment appDelete = appSumTableview.getSelectionModel().getSelectedItem();
        //is an appointment selected?
        //yes
        if (appDelete == null){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Please select an appointment to delete an appointment.");
            alert.showAndWait();
        }
        DBManager.deleteApp(appDelete);
    }
    
    /**
     * This method, when cancel button is selected and 'OK' is selected, will redirect the user to the Main screen.
     * @param event 
     */
    private void AppSumExitButtonPushed(ActionEvent event){
        //called the method from AddAppointmentController.java because I'm lazy
        AddAppointmentController exitPushed = new AddAppointmentController();
        exitPushed.AddModAppCancelButtonPushed(event);
    }
    
    /**
     * This method returns the appointment index to modify
     * @return 
     */
    public static int getAppIndexMod(){
        return appIndexMod;
    }
    
    /**
     * This method updates the table view
     */
    public void UpdateAddAppTableView(){
        updateAppList();
        appSumTableview.setItems(AppointmentList.getAppList());
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*setLanguage();*/
        
        //Action button assignment
        appSumAppDetailsButton.setOnAction(event -> AppSumAppDetailButtonPushed(event));
        appSumModAppButton.setOnAction(event -> AppSumModAppButtonPushed(event));
        appSumDeleteAppButton.setOnAction(event -> AppSumDeleteButtonPushed(event));
        appSumExitButton.setOnAction(event -> AppSumExitButtonPushed(event));
        
        //Grab data to table views
        appSumDateTableCol.setCellValueFactory(cellData -> cellData.getValue().dateStringProperty());
        appSumTitleTableCol.setCellValueFactory(cellData -> cellData.getValue().titleStringProperty());
        appSumContactTableCol.setCellValueFactory(cellData -> cellData.getValue().contactStringProperty());
        
        UpdateAddAppTableView();
    }    
    
}
