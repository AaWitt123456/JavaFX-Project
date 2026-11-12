package com.example.awjavafxapplication.Controller;

import com.example.awjavafxapplication.Model.inHouse;
import com.example.awjavafxapplication.Model.Inventory;
import com.example.awjavafxapplication.Model.Outsourced;
import com.example.awjavafxapplication.Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * this controls the modify part menu and allows parts to be modified and updated.
 *
 */


public class ModifyPartController {

    Stage stage;
    Parent scene;

    @FXML private RadioButton modifyPartInHouse;
    @FXML private RadioButton modifyPartOutsourced;
    @FXML private Label machineOrCompany;
    @FXML private TextField modifyPartID;
    @FXML private TextField modifyPartName;
    @FXML private TextField modifyPartStock;
    @FXML private TextField modifyPartPrice;
    @FXML private TextField modifyPartMax;
    @FXML private TextField modifyPartMin;
    @FXML private TextField modifyMachineID;

    private int currentIndex = 0;

    /**
     * sends part information from main screen.
     *
     * @param selectedIndex
     * @param part
     */

    public void sendPart(int selectedIndex, Part part){

        currentIndex = selectedIndex;

        if (part instanceof inHouse) {
            modifyPartInHouse.setSelected(true);
            modifyMachineID.setText(String.valueOf(((inHouse) part).getMachineId()));
            this.machineOrCompany.setText("Machine ID");
        }
        else {
            modifyPartOutsourced.setSelected(true);
            modifyMachineID.setText(((Outsourced) part).getCompanyName());
            this.machineOrCompany.setText("Company");
        }

        modifyPartID.setText(String.valueOf(part.getId()));
        modifyPartName.setText(String.valueOf(part.getName()));
        modifyPartStock.setText(String.valueOf(part.getStock()));
        modifyPartPrice.setText(String.valueOf(part.getPrice()));
        modifyPartMax.setText(String.valueOf(part.getMax()));
        modifyPartMin.setText(String.valueOf(part.getMin()));


    }



    /**
     *
     * @param event
     */

    public void addPartInHouse(ActionEvent event) {
        machineOrCompany.setText("Machine ID");
    }

    /**
     *
     * @param event
     */
    public void addPartOutsourced(ActionEvent event) {
        machineOrCompany.setText("Company");
    }

    /**
     * saves a modified part, checks for max and minimum requirements and gives error if not fufilled, gives error for unfilled text boxes
     * then returns to menu.
     *
     * @param event
     * @throws IOException
     */

    public void modifyPartSaveButton(ActionEvent event) throws IOException {
        try {
            int partID = Integer.parseInt(modifyPartID.getText());
            String name = modifyPartName.getText();
            int inStock = Integer.parseInt(modifyPartStock.getText());
            double price = Double.parseDouble(modifyPartPrice.getText());
            int min = Integer.parseInt(modifyPartMin.getText());
            int max = Integer.parseInt(modifyPartMax.getText());
            int machineID;

            String companyName;

            if (max < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Maximum must be greater than minimum.");
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

            System.out.println(modifyPartOutsourced.isSelected() + " outsourced");
            System.out.println(modifyPartInHouse.isSelected() + " in-house");
            if (modifyPartInHouse.isSelected()) {
                machineID = Integer.parseInt(modifyMachineID.getText());
                inHouse updatedPart = new inHouse(partID, name, price, inStock, min, max, machineID);
                Inventory.updatePart(currentIndex, updatedPart);
            }
            if (modifyPartOutsourced.isSelected()) {
                companyName = modifyMachineID.getText();
                Outsourced updatedPart = new Outsourced(partID, name, price, inStock, min, max, companyName);
                Inventory.updatePart(currentIndex, updatedPart);
            }
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/com/example/awjavafxapplication/MainMenu.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Missing or incorrect value");
            alert.showAndWait();
        }
    }


    /**
     * cancels and returns to main menu.
     *
     * @param event
     * @throws IOException
     */
    public void modifyPartCancelButton(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/com/example/awjavafxapplication/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();


    }
}
