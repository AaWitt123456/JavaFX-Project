package com.example.awjavafxapplication.Controller;

import com.example.awjavafxapplication.Model.Inventory;
import com.example.awjavafxapplication.Model.Part;
import com.example.awjavafxapplication.Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * this controls the modify product menu.
 *
 */

public class ModifyProductController implements Initializable {

    Stage stage;
    Parent scene;
    private ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();

    @FXML private TextField modifyProductSearchBox;
    @FXML private TextField modifyProductID;
    @FXML private TextField modifyProductName;
    @FXML private TextField modifyProductStock;
    @FXML private TextField modifyProductPrice;
    @FXML private TextField modifyProductMax;
    @FXML private TextField modifyProductMin;
    @FXML private TableView<Part> modifyProductTable;
    @FXML private TableColumn<?, ?> modifyProductPartIDCol;
    @FXML private TableColumn<?, ?> modifyPartNameCol;
    @FXML private TableColumn<?, ?> modifyProductInventoryCol;
    @FXML private TableColumn<?, ?> modifyProductPriceCol;
    @FXML private TableView<Part> associatedProductTable;
    @FXML private TableColumn<?, ?> associatedProductIDCol;
    @FXML private TableColumn<?, ?> associatedPartNameCol;
    @FXML private TableColumn<?, ?> associatedStockCol;
    @FXML private TableColumn<?, ?> associatedPriceCol;

    private int currentIndex = 0;
    Product modifiedProduct = null;

    /**
     * sends product data to product menu tables
     *
     *
     * @param selectedIndex
     * @param product
     */

    public void sendProduct(int selectedIndex, Product product){

        modifiedProduct = product;
        currentIndex = selectedIndex;

        modifyProductID.setText(String.valueOf(product.getId()));
        modifyProductName.setText(String.valueOf(product.getName()));
        modifyProductStock.setText(String.valueOf(product.getStock()));
        modifyProductPrice.setText(String.valueOf(product.getPrice()));
        modifyProductMax.setText(String.valueOf(product.getMax()));
        modifyProductMin.setText(String.valueOf(product.getMin()));

        for (Part part: product.getAllAssociatedParts()) {
            associatedPartsList.add(part);
        }
    }

    /**
     * This searches for products and gives an error if none are found.
     *
     * @param event
     */

    public void modifyProductPartSearch(ActionEvent event) {
        String searchText = modifyProductSearchBox.getText();
        ObservableList<Part> results = Inventory.lookupPart(searchText);

        try {
            while (results.size() == 0 ) {
                int partID = Integer.parseInt(searchText);
                results.add(Inventory.lookupPart(partID));
            }
            modifyProductTable.setItems(results);
        } catch (NumberFormatException e) {
            Alert noParts = new Alert(Alert.AlertType.ERROR);
            noParts.setTitle("Error");
            noParts.setContentText("Not found: Text is case sensitive");
            noParts.showAndWait();
        }
    }

    /**
     * returns to main menu
     *
     * @param event
     * @throws IOException
     */
    public void modifyProductCancelButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/com/example/awjavafxapplication/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * saves a new product checks if the maximum number is larger than minimum checks if all text fields are filled, and gives an error if not then returns to main menu.
     *
     * @param event
     * @throws IOException
     */

    public void modifyProductSaveButton(ActionEvent event) throws IOException{
        try {

            int id = Integer.parseInt(modifyProductID.getText());
            String name = modifyProductName.getText();
            int stock = Integer.parseInt(modifyProductStock.getText());
            double price = Double.parseDouble(modifyProductPrice.getText());
            int max = Integer.parseInt(modifyProductMax.getText());
            int min = Integer.parseInt(modifyProductMin.getText());

            if (min >= max) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Maximum must be Larger than minimum");
                alert.showAndWait();
                return;
            }

            if (max < stock) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Stock must be less than maximum");
                alert.showAndWait();
                return;
            }
            if (min > stock) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Stock must be larger than minimum");
                alert.showAndWait();
                return;
            }

            Product updatedProduct = new Product(id, name, price, stock, min, max);
            if (updatedProduct != associatedPartsList) {
                Inventory.updateProduct(currentIndex, updatedProduct);
            }


            for (Part part: associatedPartsList) {
                if (part != associatedPartsList)
                    updatedProduct.addAssociatedParts(part);
            }

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/com/example/awjavafxapplication/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();


        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Incorrect value");
            alert.showAndWait();
        }
    }

    /**
     * this deletes parts from the related product, also checks if a part is selected before deleting.
     *
     * @param event
     */

    public void removeAssociatedPartButton(ActionEvent event) {
        Part selectedPart = associatedProductTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Select part");
            alert.showAndWait();
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setContentText("Do you want to remove the selected part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            modifiedProduct.deleteAssociatedPart(selectedPart);
            associatedPartsList.remove(selectedPart);
            associatedProductTable.setItems(associatedPartsList);
        }
    }

    /**
     * this adds a new part to the product checks if a part is selected or not and gives an error if not selected.
     *
     * @param event
     */

    public void addPartToProduct(ActionEvent event) {
        Part selectedPart = modifyProductTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Select part");
            alert.showAndWait();
            return;
        }
        else if (!associatedPartsList.contains(selectedPart)) {
            associatedPartsList.add(selectedPart);
            associatedProductTable.setItems(associatedPartsList);
        }

    }

    /**
     *  populates data
     * @param url
     * @param resourceBundle
     */

    public void initialize(URL url, ResourceBundle resourceBundle) {

        modifyProductTable.setItems(Inventory.getAllParts());
        modifyProductPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedProductTable.setItems(associatedPartsList);
        associatedProductIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}
