package com.example.awjavafxapplication.Controller;

import com.example.awjavafxapplication.Model.inHouse;
import com.example.awjavafxapplication.Model.Inventory;
import com.example.awjavafxapplication.Model.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * this is the part controller, it controls how the add part menu functions.
 */

public class AddPartController {
    static int partID;
    private int increment;

    Stage stage;
    Parent scene;
    @FXML
    private Label MachineOrCompany;
    @FXML
    private RadioButton InHouseRadio;
    @FXML
    private RadioButton OutsourcedRadio;
    @FXML
    private TextField addPartName;
    @FXML
    private TextField addPartStock;
    @FXML
    private TextField addPartPrice;
    @FXML
    private TextField addPartMax;
    @FXML
    private TextField addPartMin;
    @FXML
    private TextField addPartMachineId;

    /**
     *
     * @param actionEvent
     */
    public void onActionAddInHousePart(ActionEvent actionEvent) {
        MachineOrCompany.setText("Machine ID");
    }

    /**
     *
     * @param actionEvent
     */
    public void onActionAddOutSourcedPart(ActionEvent actionEvent) {
        MachineOrCompany.setText("Company");
    }

    /**
     * this saves the newly created part and also increments the ID by 1. it also checks if the maximum is larger than the minimum
     * and if all the buttons and text fields have been filled or not and gives errors if anything is missing. Then returns to main menu.
     *
     * One challenge I had was setting up the increment ID as I tried to put the partID integer and the increment integer within addPartSaveButton
     * I fixed that by realizing it had to go at the top under add part controller.
     *
     * @param event
     * @throws IOException
     */

    public void addPartSaveButton(ActionEvent event) throws IOException {
        try {



            if (partID == 0) {
                partID = partID + 5;

            } else {
                increment = increment + 1;
                partID = increment + partID;


           }


            String name = addPartName.getText();
            int inStock = Integer.parseInt(addPartStock.getText());
            double price = Double.parseDouble(addPartPrice.getText());
            int min = Integer.parseInt(addPartMin.getText());
            int max = Integer.parseInt(addPartMax.getText());

            int machineID = 0;
            String companyName;

            if (max <= min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Maximum must be larger than minimum");
                alert.showAndWait();
                return;
            }

            if (max < inStock) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Stock must be less than maximum");
                alert.showAndWait();
                return;
            }
            if (min > inStock) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Stock must be larger than minimum");
                alert.showAndWait();
                return;
            }


            if(!OutsourcedRadio.isSelected() && !InHouseRadio.isSelected() ) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Select whether in-house or outsourced");
                alert.showAndWait();
                return;

            }

            System.out.println(OutsourcedRadio.isSelected() + " Outsourced");
            System.out.println(InHouseRadio.isSelected() + " In-House");
            if (OutsourcedRadio.isSelected()) {
                companyName = addPartMachineId.getText();
                Outsourced addPart = new Outsourced(partID, name, price, inStock, min, max, companyName);
                Inventory.addPart(addPart);
            }
            if (InHouseRadio.isSelected()) {
                machineID = Integer.parseInt(addPartMachineId.getText());
                inHouse addPart = new inHouse(partID, name, price, inStock, min, max, machineID);
                Inventory.addPart(addPart);
            }
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/com/example/awjavafxapplication/MainMenu.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Missing or incorrect values");
            alert.showAndWait();
        }
    }

    /**
     * this just cancels and returns to the main menu.
     *
     * @param event
     * @throws IOException
     */


    public void addPartCancelButton(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/com/example/awjavafxapplication/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
