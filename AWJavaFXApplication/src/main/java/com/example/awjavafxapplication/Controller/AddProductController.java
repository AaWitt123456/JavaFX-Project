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
import java.util.ResourceBundle;

/**
 * this allows for adding new products to the products table as well as adding parts to those products.
 *
 */
public class AddProductController implements Initializable {
    static int ProductID;
    private int increment;
    Stage stage;
    Parent scene;
    private ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();

    @FXML private TextField addProductSearchBox;
    @FXML private TextField addProductName;
    @FXML private TextField addProductStock;
    @FXML private TextField addProductPrice;
    @FXML private TextField addProductMax;
    @FXML private TextField addProductMin;
    @FXML private TableView<Part> associatedProductTable;
    @FXML private TableView<Part> addProductTable;
    @FXML private TableColumn<?, ?> addProductPartIDCol;
    @FXML private TableColumn<?, ?> addPartNameCol;
    @FXML private TableColumn<?, ?> addProductStockCol;
    @FXML private TableColumn<?, ?> addProductPriceCol;
    @FXML private TableColumn<?, ?> associatedProductIDCol;
    @FXML private TableColumn<?, ?> associatedPartNameCol;
    @FXML private TableColumn<?, ?> associatedStockCol;
    @FXML private TableColumn<?, ?> associatedPriceCol;


    /**
     * this allows for searching and if no match is found will display an error .
     *
     * @param event
     */
    public void addProductPartSearch(ActionEvent event) {
        String searchText = addProductSearchBox.getText();
        ObservableList<Part> results = Inventory.lookupPart(searchText);
        try {
            while (results.size() == 0 ) {
                int partID = Integer.parseInt(searchText);
                results.add(Inventory.lookupPart(partID));
            }
            addProductTable.setItems(results);
        } catch (NumberFormatException e) {
            Alert noParts = new Alert(Alert.AlertType.ERROR);
            noParts.setTitle("Error");
            noParts.setContentText("Not found");
            noParts.showAndWait();
        }
    }

    /**
     * this cancels and returns to menu .
     *
     * @param event
     * @throws IOException
     */
    public void addProductCancelButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/com/example/awjavafxapplication/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * this saves a new product as well as increments by 1 with each new product, it also checks if the minimum value is less than the max
     * as well as checks if any values are missing and if so returns an error.
     *
     * I used the same increment method as in the addPartController so I had the same issue when originally trying to figure it out.
     *
     * @param event
     * @throws IOException
     */
    public void productSaveButton(ActionEvent event) throws IOException{
        try {

            if (ProductID == 0) {
                ProductID = ProductID + 5;

            } else {
                increment = increment + 1;
                ProductID = increment + ProductID;

            }


            String name = addProductName.getText();
            int stock = Integer.parseInt(addProductStock.getText());
            double price = Double.parseDouble(addProductPrice.getText());
            int max = Integer.parseInt(addProductMax.getText());
            int min = Integer.parseInt(addProductMin.getText());


            if (min >= max) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Maximum must be greater than minimum");
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

            Product product = new Product(ProductID, name, price, stock, min, max);

            for (Part part : associatedPartsList) {
                if (part != associatedPartsList)
                    product.addAssociatedParts(part);
            }

            Inventory.getAllProducts().add(product);

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
     * this checks if a product is highlighted and then removes if it is, it also displays error message if a product is not selected.
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
            return;
        }
        else if (associatedPartsList.contains(selectedPart))
        {
            associatedPartsList.remove(selectedPart);
            associatedProductTable.setItems(associatedPartsList);
        }
    }

    /**
     * this adds a part to a product and checks if a part is selected or not and gives an error if one is not selected.
     *
     * @param event
     */

     public void addProductAddPartButton(ActionEvent event) {
        Part selectedPart = addProductTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Select part");
            alert.showAndWait();
            return;
        }
        else if (!associatedPartsList.contains(selectedPart))
        {
            associatedPartsList.add(selectedPart);
            associatedProductTable.setItems(associatedPartsList);
        }
    }

    /**
     * this populates the table
     *
     * @param url
     * @param resourceBundle
     */


    public void initialize(URL url, ResourceBundle resourceBundle) {

    addProductTable.setItems(Inventory.getAllParts());
    addProductPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
    addPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    addProductStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    addProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


    associatedProductTable.setItems(associatedPartsList);
    associatedProductIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
    associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    associatedStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    associatedPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

}


}
